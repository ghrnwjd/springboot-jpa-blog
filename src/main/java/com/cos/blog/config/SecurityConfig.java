package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있도록 하는것
@Configuration // 빈등록 IoC관리
@EnableWebSecurity // 시큐리티 필터를 거는 것 = 스프링 시큐리티가 활성화가 되어 있는데  어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
// 3개는 거의 세트
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean // IoC가 된다. => 리턴하는 값을 스프링에서 관리하게 된다.
    public BCryptPasswordEncoder encodePWD() {
        // String encPassword = new BCryptPasswordEncoder().encode("1234");
        return  new BCryptPasswordEncoder();

    }


    /* 시큐리티가 대신 로그인을 해주기에 password를 가로채는데
    해당 password 가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
     같은 해쉬로 암호화 하여 DB에 있는 해쉬랑 비교가능*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
                .authorizeRequests()
                    .antMatchers("/", "/auth/**" , "/js/**", "/css/**" , "/image/**")
                     .permitAll()
                     .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm")
                    .loginProcessingUrl("/auth/loginProc")
                    // 스프링 시큐리티가 해당 주소로 요청하는 로그인을 가로채 대신 로그인한다.
                    .defaultSuccessUrl("/");
                    //.failureUrl("/fail");

    }
}
