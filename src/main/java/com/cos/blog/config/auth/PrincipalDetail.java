package com.cos.blog.config.auth;

import com.cos.blog.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면
// UserDetails 타입의 오브젝트를 스프링 시큐리티 고유한 세션저장소에 저장
@Getter
public class PrincipalDetail implements UserDetails {

    private User user; //콤포지션 (객체를 품고있다. extends => 상속)

    public PrincipalDetail(User user) {
        this.user = user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았는지 리턴한다. ( false : 만료되었다)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정이 잠겨있는지 않았는지 리턴한다 ( true : 잠기지 않았다. )
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호가 만료되지 않았는지 리턴한다 (true : 만료안됨)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화인지 리턴한다 ( true : 활성화 )
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        // 계정이 가지고 있는 권한 목록을 리턴한다.

//        collectors.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_" + user.getRole(); //ROLE_USER => ROLE_ 스프링 규칙
//            }
//        });

        collectors.add( ()->{ return "ROLE_"+user.getRole(); });
        return collectors;
    }
}
