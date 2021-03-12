package me.taminging.demospringsecurityform.config;

import me.taminging.demospringsecurityform.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;


    //Role Hierarchy를 주기 위해 AccessDecisionManager를 수정
    //혹은 ExpressionHandler를 설정할 수 있도록 할 수 있다.
    public AccessDecisionManager accessDecisionManager() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(defaultWebSecurityExpressionHandler);
        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);
        return new AffirmativeBased(voters);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/info","/account/**", "/signup").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .accessDecisionManager(accessDecisionManager())
                //.expressionHandler(Something())
                .anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
                .permitAll();
            //.usernameParameter("");
            //.passwordParameter("");
            //failForwardUrl
            //successForwardUrl
            //loginPage
        http.httpBasic();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http.anonymous()
                .principal("anonymousUser");

        http.sessionManagement()
                .sessionFixation().changeSessionId()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)// 세션 만드는 전략
                .maximumSessions(1)//추가 로그인 1명만
                .maxSessionsPreventsLogin(true)// 다른 브라우저가 접속하면 만료
                .expiredUrl("/login");// 세션 만료 시 URL 이동
                //.invalidSessionUrl("/login");

        // 하위 스레드에도 SecuirtyContext가 공유된다.
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }
}