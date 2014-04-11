package com.hedatou.wedding.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.hedatou.wedding.domain.Chat;
import com.hedatou.wedding.service.ChatService;
import com.hedatou.wedding.web.dto.ChatInputDto;

@Controller
public class WebSocketController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    public Chat chat(ChatInputDto chat) throws UnsupportedEncodingException {
        String msg = URLDecoder.decode(chat.getMsg(), "UTF-8");
        return chatService.chat(chat.getToken(), msg);
    }

}
