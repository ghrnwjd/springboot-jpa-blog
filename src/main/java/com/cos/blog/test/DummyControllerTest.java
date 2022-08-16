package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

// html 파일이 아닌 data를 리턴해주는 RestController
@RestController
public class DummyControllerTest {
    //{id}주소로 파라미터를 전달 받을 수 있음.
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {

        //user/4 를 찾을 때 데이터베이스에 존재하지 않으면 null을 리턴하는데
        // Optional로 너의 user 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해

      // 람다 식
//        User user = userRepository.findById(id).orElseThrow(()=> {
//            return new IllegalArgumentException("해당 사용자는 없습니다,");
//        });

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });

        // 요청 : 웹브라우저
        // user 객체 = java 오브젝트
        // user 객체를 웹브라우저로 이해할 수 있는 데이터로 변환 -> json (Gson 라이브러리이용)
        // 스프링부트는 MessageConverter 라는 애가 응답시 자동 작동
        // 만약 자바 오브젝트를 리턴하게 되면 MC가 Jackson 라이브러리 호출하여
        // user 오브젝트를 json으로 변환하여 웹브라우저에 반환
        return user;
    }
    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;
    //http://localhost:8000/blog/dummy/join (요청)
    //http 의 body 에 username, password, email 을 가지고 요청
    @PostMapping("/dummy/join")
    public String join(User user) { //key = value (약속된 규칙)
        System.out.println("username is " + user.getUsername());
        System.out.println("email is " + user.getEmail());
        System.out.println("password is " + user.getPassword());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원 가입이 완료되었습니다.";
    }
}
