# Spring-Security-Study

##Spring Init Project 
```
- https://start.spring.io/
- Maven Web, thymeleaf
- Sample Controller , Page (admin, dashboard, index, info)
- Spring Security Dependeny 추가
``` 
##UsernameAuthenticationFilter
```
   - 인증된 Authentication 객체를 SecurityContextHolder 에 넣어주는 필터
   - SecurityContextHolder.getContext().setAuthentication(authentication)과 같은 역할을 한다.
```
##SecurityContextPersistenceFilter
```
   - Session을 캐시하여 여러 요청에서 authentication을 공유할 수 있는 필터
   - SecurityContextRepository를 통해 HTTP Session이 아닌 다른 곳에 저장하는 것도 가능하다.
```
##DelegatingFilterProxy
```
    - 일반적인 서블릿 필터
    - 서블릿 필터 처리를 스프링에 들어있는 빈으로 위임 시킬 때 사용하는 서블릿 필터
    - 스프링 부트를 사용할 때는 자동으로 등록된다. (SecurityFilterAutoConfigration)
```
##FilterChainProxy
```
    - springSecurityFilterChain 기본 등록
``` 
##AccessDecisionManager
```
    - Access Control 결정을 내리는 인터페이스
    - 3가지 구현체 기본 제공
        1. AffirmativeBased : 여러 Voter 중 한명이라도 허용하면 허용 (기본 전략)
        2. ConsensusBased : 다수결
        3. UnanimousBased : 만장 일치
    - AccessDecisionVoter
        * 해당 Authentication이 특정한 Object에 접근할 때 필요한 ConfigAttribute 만족하는지 확인 (Security에 설정한 Matchers 및 Role 확인)
        * WebExpressionVoter : 웹 시큐리티에서 사용하는 기본 구현체 , ROLE_XXXX 확인
        * RoleHierarchyVoter : 계층형 Role 지원 ADMIN > MANAGER > USER
```

##FilterSecurityInterceptor
```
    - AccessDecisionManager를 사용하여 Access Control 또는 예외 처리 하는 필터
    - 대부분의 경우 FilterChainProxy의 마지막에 들어있다
```

##ExceptionTranslationFilter
```
    - AccessDeniedException, AuthenticationException 처리하는 필터
```

##AuthenticationException
```
    - UsernameAuthenticationFilter 실행
    - AbstractSecurityInterceptor 하위 클래스에서 발생하는 예외만 처리 
    - 그렇다면 UsernameAuthenticationFilter에서 발생한 예외는?
```

##AccessDeniedException
```
    - 익명의 사용자 일 경우 UsernameAuthenticationFilter 실
    - 익명의 사용자가 아니면 AccessDeniedHandler에게 위임
```
##WebAsyncManagerIntegrationFilter 
```
    - SecurityContext를 동일하게 처리할 수 있도록 도와주는 Callable이여도  
    - 즉 스레드적으로 갈려도 같은 SecurityContext로 접근하여 처리 될 수 있다
    - PreProcess에서 SecurityContext 설정
    - PostProcess에서 SecurityContext 정리 - Clean up (초기화아니 비워둔다가 적당한 표현인 듯)
```
##스프링 시큐리티와 @Async
```
    - SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL); : 하위 Thread에서도 Spring Context가 공유된다.
    - @EnableAsync를 사용해야 @Async가 작동한다.
```
##SpringContextPersistenceFilter
```
    - 여러 요청간에 Spring Context를 공유할 수 있는 기능
    - Dashboard를 가도 다른 url에 접근하여도 Spring Context가 공유되는 것을 도와준다.
    - SpringContextRepository에서 읽어온다. 기본적으로 Http Session에 있는 것으로 받아 Spring Context로 읽어온다.
    - 이 필터가 설정되지 않으면 매번 인증을 해야한다.
```
##HeaderWriterFilter
```
    - 응답 해더에 시큐리티 관련된 헤더를 추가해주는 필터
```

##CSRFFilter
```
    - CSRF 어택 방지 필터
    - CSRF 토큰을 통해 아무 도메인이나 접근하지 못하도록 제어한다.
    - 서버에서 클라이언트에게 로그인 폼으로 CSRF 토큰을 보낸다.
    - 로그인할 경우 CSRF 토큰으로 해당 웹사이트로 정상적인지 확인한다.
    - 다른 웹에서 쉽게 접근하지 못하도록 방지한 토큰이다.
    - 사용하고 싶지않은 경우 configure에서 http.csrf().disable();
    - LoginPage 만들 경우 이러한 모든 것들을 고려해야한다.
```
##LogoutFilter
```
    - LogoutHandler : 로그아웃 시 필요한 처리 (CSRFLogoutFilter, SecurityContextLogoutFilter)
    - LogoutSuccessHandler : 로그아웃 성공이후 처
```
##UsernamePasswordAuthenticationFilter
```
    - username과 password를 통해 AuthenticationManager를 통해 인증을 시도   
    - AuthenticationManager가 여러 AuthenticationProvider를 사용하여 인증을 시도하는데
    - 그 중 현재는 DaoAuthenticationProvider를 사용하여 UserDetailService 방식으로 UserDetails를 가져와 password를 비교한다.
``` 
##DefaultLoginGeneratingFilter
```
    - 기본 로그인 폼 생성해주는 필
    - Username Parameter / Password Parameter 변경
    - FailForwardUrl / SuccessForwardUrl   
    - LoginPage () 사용하는 순간 Custom 한 로그인 페이지를 만드는 거라 필터가 생성되지 않는다.
```
##로그인 / 로그아웃 폼 커스텀 페이지 
```
    - LoginPage () 사용하는순간 DefaultLogin/LogoutGeneratingFilter가 사라져서 구현해줘야한다.
```

##BasicAuthenticationFilter
```
    - Security에 httpBasic() 설정을 해놓으면 자동으로 적용
    - username과 password를 인코딩하여 해더의 Authentication에 담아서 사용
    - HTTPS를 사용하여 하나라도 스니핑 당하지 않도록 처리
```
##RequestCacheAwareFilter
```
    - 현재 요청과 관련 있는 캐시된 요청이 있는지 찾아서 적용하는 필터
        - 캐시된 요청이 없다면 , 현재 요청 처리
        - 캐시된 요청이 있다면 , 해당 캐시된 요청 처리
    - 크게 설정하지 않는 설정
```
##SecurityContextHolderRequestAware
```
    - 시큐리티 관련 서블릿 API를 구현해주는 필터 
    - 크게 설정하지 않는 설정 
```
##AnonymousAuthenticationFilter
```
    - Security Context에 Authentication이 null이면 익명 Authentication을 만들어주고 아무일도 일어나지 않도록 한다.
    - Null Object Pattern
    - Null을 넣어주는게 아니라 Null 대체하는 객체를 생성하는 패
```
##SessionManagementFilter
```
    - 세션 변조 방지 전략 설정 : SessionFixation
``` 
##ExceptionTranslateFilter   
##FilterSecurityInterceptor

