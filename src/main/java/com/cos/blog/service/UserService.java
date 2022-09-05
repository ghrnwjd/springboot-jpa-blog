package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// 스프링으 컴포넌트 스캔을 통해 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Transactional
    public void 회원가입(User user) {

        String rawPassword = user.getPassword(); // 비밀번호 원문
        String encPassword = encoder.encode(rawPassword); // 해쉬
        user.setRole(RoleType.USER);
        user.setPassword(encPassword);
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        //수정시 영속성 컨텍스트 User 오브젝트를 영속화 시키고
        // 영속화 된 User 오브젝트를 수정
        // Select 를 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 위해
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update

        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("회원찾기실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

        //세션 등록
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 회원 수정 함수 종료 = 서비스 종료 = 트랜잭션 종료 = commit 이 자동으로 됨
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날린다.
    }



//    @Transactional(readOnly = true) //Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 ( 정합성 유지 )
//    public User 로그인(User user) {
//        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//    }
}
