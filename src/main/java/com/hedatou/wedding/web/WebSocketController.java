package com.hedatou.wedding.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @SubscribeMapping("/topic/chat")
    public void joinChat() {
        // TODO
    }

    @MessageMapping("/chat")
    public void sendChat() {
        // TODO
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
