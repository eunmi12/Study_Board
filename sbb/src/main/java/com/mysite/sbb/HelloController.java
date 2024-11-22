package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //HelloController 클래스가 컨트롤러의 기능을 수행함을 알려줌.
public class HelloController {
    @GetMapping("/hello") //클라이언트의 요청으로 hello 메서드가 실행됨을 알려줌
    @ResponseBody //hello 메서드의 출력값 그대로 리턴할 것을 알려준다.
    public String hello(){
        return "Hello ";
    }
}
