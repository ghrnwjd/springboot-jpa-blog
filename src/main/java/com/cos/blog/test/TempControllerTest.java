package com.cos.blog.test;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("hello Home(");

        //파일 리턴 기본 경로 : src/main/resource/static
        // 리턴명 : /home.html

        //풀경로 : src/ main/resource/static/home.html
        return "/home.html";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp() {
        // prefix : /WEB-INF/views/
        // suffix : .jsp
        // 풀 경로 : /WEB-INF/views/test.jsp

        // 톰캣에서 실행할 수 있도록 --> java 파일이기 때문에
        return "test";
    }
}
