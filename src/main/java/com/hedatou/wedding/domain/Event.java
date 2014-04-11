package com.hedatou.wedding.domain;

import java.util.Map;

import com.google.common.collect.Maps;

public class Event {

    public static enum EventType {
        ACCESS, // 访问
        MOBILE, // 绑定手机号
        REGISTER, // 注册
        BLESS, // 祝词
        UPGRADE; // 提升幸运值
    }

    private final EventType type;
    private final Map<String, Object> detail;

    public Event(EventType type) {
        this.type = type;
        this.detail = Maps.newHashMap();
    }

    public EventType getType() {
        return type;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void put(String key, Object value) {
        detail.put(key, value);
    }

}
