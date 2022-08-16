package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;


// 사용자가 요청 -> 응답(HTML 파일) -> @Controller
// 사용자가 요청 -> 응답(DATA) -> @RestController

@RestController
public class HttpControllerTest {

    //인터넷 브라우저는 get방식만 지원함.
    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m) {// id=1&username=ssar&password=1234&email=ssar@nate.com
        return "get 요청" +m.getId()+"," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
    }

    //http://localhost:8080/http/post (insert)
    @PostMapping("http/post") // HTML form tag
    public String postTest(Member m) {
        return "post 요청" +m.getId()+"," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
    }

//    @PostMapping("http/post")   // row data -> MIME ( text/plain)
//    public String postTest(@RequestBody String text) {
//        return "post 요청" + text;
//    }

//    @PostMapping("http/post") // json data -> MIME (application/json)
//    public String postTest(@RequestBody Member m) {
//        return "post 요청" + m.getId()+"," + m.getUsername() + "," + m.getPassword()() + "," + m.getEmail();
//    }


    //http://localhost:8080/http/put (update)
    @PutMapping("http/put")
    public String putTest(@RequestBody Member m) {
        return"put 요청" + m.getId()+"," + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
    }
    //http://localhost:8080/http/delete (delete)
    @DeleteMapping("http/delete")
    public String deleteTest() {
        return "delete 요청";
    }

}
