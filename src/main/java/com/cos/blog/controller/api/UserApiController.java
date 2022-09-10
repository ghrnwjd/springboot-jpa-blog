package com.cos.blog.controller.api;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApiController {
//
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user) {
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseDto<Integer> update(@RequestBody User user) {
        userService.회원수정(user);

        Authentication authentication
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getPassword(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
//        트랜잭션이 종료되기에 DB값은 변경되었지만
//        세션값은 변경되지 않은 상태 -> 우리가 직접 세션값을 변경
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
//
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authentication);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}


//    @PostMapping("/api/user/login")
//    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//        System.out.println("UserApiController : login 호출됨");
//        User principal = userService.로그인(user); // principal (접근주체)
//
//        if(principal != null) {
//            session.setAttribute("principal", principal);
//        }
//        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//    }