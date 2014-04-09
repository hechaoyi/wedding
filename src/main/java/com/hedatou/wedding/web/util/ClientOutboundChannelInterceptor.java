package com.hedatou.wedding.web.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ClientOutboundChannelInterceptor extends ChannelInterceptorAdapter {

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        if (!sent)
            return;
        // 下发后记录此用户最后一条发送成功的消息ID
        // TODO Auto-generated method stub
    }

}
