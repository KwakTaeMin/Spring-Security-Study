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