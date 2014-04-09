package com.hedatou.wedding.web;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @SubscribeMapping("/topic/chat")
    public void joinChat() {
        System.out.println("join /topic/chat");
        // TODO
    }

    @MessageMapping("/chat")
    public MessageWrapper sendChat(MessageWrapper msg, Principal principal) {
        System.out.println(msg.getMsg());
        System.out.println(principal);
        msg.setUserName(principal.getName());
        return msg;
        // TODO
    }

    public static class MessageWrapper {
        private String msg;
        private String userName;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

    }

    @SubscribeMapping("/topic/lottery")
    public void joinLottery() {
        // TODO
    }

    @MessageMapping("/lottery")
    public void sendLottery() {
        // TODO
    }

}
