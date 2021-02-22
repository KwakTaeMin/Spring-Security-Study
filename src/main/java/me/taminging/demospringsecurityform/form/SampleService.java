package me.taminging.demospringsecurityform.form;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 인증이 된 후에 객체가 생성됨
        Object principal = authentication.getPrincipal(); // UserDetail -> Account
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); // UserDetail -> ROLE Principal이 가지고있는 권한을 나타낸다
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated(); // 로그아웃되기전에는 항상 true


    }
}
