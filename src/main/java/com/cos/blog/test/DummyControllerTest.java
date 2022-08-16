package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

// html 파일이 아닌 data를 리턴해주는 RestController
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id ) {
       try {
           userRepository.deleteById(id);
       }
       catch (EmptyResultDataAccessException e) {
           return "삭제에 실패하였습니다. 해당 id는 DB에 존재하지 않습니다.";
       }
        return "삭제되었습니다. id : " + id;
    }
    //password랑 email 만 수정 예정
    @Transactional // 함수 종료시 자동 commit 됌.
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser ) {
        //내가 보낸 json 데이터를 요청 => Spring이 Java Object(Message Converter 의 Jackson 라이브러리가 변환함)

       // save 를 이용하여 update 할 시
        // save함수를 id를 전달하지 않으면 insert 하고 id를 전달하면 update를 해준다.
        User user = userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        }); // 영속화 되었음.

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());


        // userRepository.save(requestUser);

        // 더티 체킹
        // 영속화시키고 변경을 하면 변경을 감지하여 데이터베이스에 수정을 알려준다
        return user;
    }
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        //한 페이지당 2건의 데이터를 리턴받아 볼 예정
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }
    //{id}주소로 파라미터를 전달 받을 수 있음.
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {

        //user/4 를 찾을 때 데이터베이스에 존재하지 않으면 null을 리턴하는데
        // Optional로 너의 user 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해

      // 람다 식
        //     User user = userRepository.findById(id).orElseThrow(()=> {
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
