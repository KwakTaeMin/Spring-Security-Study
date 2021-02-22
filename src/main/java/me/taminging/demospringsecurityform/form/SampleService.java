package me.taminging.demospringsecurityform.form;

import me.taminging.demospringsecurityform.account.Account;
import me.taminging.demospringsecurityform.account.AccountContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        // Thread Local
        Account account = AccountContext.getAccount();

        // UsernameAuthenticationFilter
        // - 인증된 Authentication 객체를 SecurityContextHolder 에 넣어주는 필터
        // - SecurityContextHolder.getContext().setAuthentication(authentication)과 같은 역할을 한다.

        // SecurityContextHolderPersistenceFilter
        // - Session을 캐시하여 여러 요청에서 authentication을 공유할 수 있는 필터
        // - SecurityContextRepository를 통해 HTTP Session이 아닌 다른 곳에 저장하는 것도 가능하다.

        // SecurityContextHolder and Authentication
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증이 된 후에 객체가 생성됨
        Object principal = authentication.getPrincipal(); // User Type의 객체 -> 누구에 대한 정보 UserDetailsService에서 만든 객체가 Return
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); // UserDetail -> ROLE Principal이 가지고있는 권한을 나타낸다
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated(); // 로그아웃되기전에는 항상 true
        //UserDetailsService 데이터 베이스 및 인메모리 등 어디선가 가져오는 DTO 역할
        //AuthenticationManager 역할
        //인증 Authentication 객체를 받아 인증을 확인한다.
         */
    }
}
