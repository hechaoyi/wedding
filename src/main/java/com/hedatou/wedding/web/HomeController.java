package com.hedatou.wedding.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @MessageMapping("/request")
    @SendTo("/topic/response")
    public Wrapper hello(Wrapper request) {
        Wrapper response = new Wrapper();
        response.setMsg("Hello, " + request.getMsg());
        return response;
    }

    public static class Wrapper {
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

}
