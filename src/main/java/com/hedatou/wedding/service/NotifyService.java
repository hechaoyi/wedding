package com.hedatou.wedding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.hedatou.wedding.domain.Event;
import com.hedatou.wedding.domain.Event.EventType;

@Service
public class NotifyService {

    @Autowired
    private SimpMessagingTemplate messaging;

    public void access(String source) {
        Event event = new Event(EventType.ACCESS);
        if (source.matches("\\d+"))
            source += "号桌";
        else if (source.equals("main"))
            source = "主桌";
        else
            source = "签到台";
        event.put("source", source);
        messaging.convertAndSend("/queue/event", event);
    }

    public void mobile(String mobile) {
        Event event = new Event(EventType.MOBILE);
        event.put("mobile", mobile);
        messaging.convertAndSend("/queue/event", event);
    }

    public void register(String name) {
        Event event = new Event(EventType.REGISTER);
        event.put("name", name);
        messaging.convertAndSend("/queue/event", event);
    }

    public void bless(String name, String bless) {
        Event event = new Event(EventType.BLESS);
        event.put("name", name);
        event.put("bless", bless);
        messaging.convertAndSend("/queue/event", event);
    }

}
