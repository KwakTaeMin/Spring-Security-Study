# Spring-Security-Study

###### Spring Security Study Start 0 섹션 - 2021년 2월 21일  
Spring Init Project 
- https://start.spring.io/
- Maven Web, thymeleaf
- Sample Controller , Page (admin, dashboard, index, info)
- Spring Security Dependeny 추가
* 기본 계정 (user) 생성
* 로그로 패스워드 보여지게 된다.

- SecuiryConfig 설정
* @Configration
* @EnableWebSecurity
* extends WebSecurityConfigurerAdapter

* @Override
* configure(HttpSecurity http)
* configure(AuthenticationManagerBuilder auth) 

  UsernameAuthenticationFilter
   - 인증된 Authentication 객체를 SecurityContextHolder 에 넣어주는 필터
   - SecurityContextHolder.getContext().setAuthentication(authentication)과 같은 역할을 한다.

  SecurityContextPersistenceFilter
    - Session을 캐시하여 여러 요청에서 authentication을 공유할 수 있는 필터
    - SecurityContextRepository를 통해 HTTP Session이 아닌 다른 곳에 저장하는 것도 가능하다.


 DelegatingFilterProxy
    - 일반적인 서블릿 필터
    - 서블릿 필터 처리를 스프링에 들어있는 빈으로 위임 시킬 때 사용하는 서블릿 필터
    - 스프링 부트를 사용할 때는 자동으로 등록된다. (SecurityFilterAutoConfigration)

 FilterChainProxy
    - springSecurityFilterChain 기본 등록

 AccessDecisionManager
    - Access Control 결정을 내리는 인터페이스
    - 3가지 구현체 기본 제공
        1. AffirmativeBased : 여러 Voter 중 한명이라도 허용하면 허용 (기본 전략)
        2. ConsensusBased : 다수결
        3. UnanimousBased : 만장 일치
    - AccessDecisionVoter
        * 해당 Authentication이 특정한 Object에 접근할 때 필요한 ConfigAttribute 만족하는지 확인 (Security에 설정한 Matchers 및 Role 확인)
        * WebExpressionVoter : 웹 시큐리티에서 사용하는 기본 구현체 , ROLE_XXXX 확인
        * RoleHierarchyVoter : 계층형 Role 지원 ADMIN > MANAGER > USER

 FilterSecurityInterceptor
    - AccessDecisionManager를 사용하여 Access Control 또는 예외 처리 하는 필터
    - 대부분의 경우 FilterChainProxy의 마지막에 들어있다

ExceptionTranslationFilter
    - AccessDeniedException, AuthenticationException 처리하는 필터

AuthenticationException
    - UsernameAuthenticationFilter 실행
    - AbstractSecurityInterceptor 하위 클래스에서 발생하는 예외만 처리 
    - 그렇다면 UsernameAuthenticationFilter에서 발생한 예외는?

AccessDeniedException
    - 익명의 사용자 일 경우 UsernameAuthenticationFilter 실
    - 익명의 사용자가 아니면 AccessDeniedHandler에게 위임



