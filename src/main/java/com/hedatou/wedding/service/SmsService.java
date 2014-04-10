package com.hedatou.wedding.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hedatou.wedding.common.JsonUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.representation.Form;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    private static final String YUNPIAN_URL = "http://yunpian.com/v1/sms/tpl_send.json";
    @Value("${sms.api.key}")
    private String apiKey;
    @Value("${sms.api.conn.timeout}")
    private int connTimeout;
    @Value("${sms.api.read.timeout}")
    private int readTimeout;
    private Client client;
    private ExecutorService executor = Executors.newCachedThreadPool();

    @PostConstruct
    public void init() {
        client = Client.create();
        client.setConnectTimeout(connTimeout);
        client.setReadTimeout(readTimeout);
    }

    @PreDestroy
    public void destroy() {
        client.destroy();
        executor.shutdown();
    }

    public void send(String mobile, String message) {
        logger.info("send sms, mobile:{}, message:{}", mobile, message);
        final Form params = new Form();
        params.add("apikey", apiKey);
        params.add("mobile", mobile);
        params.add("tpl_id", "324223");
        params.add("tpl_value", String.format("#message#=%s", message));
        executor.execute(new Runnable() {
            public void run() {
                String json = client.resource(YUNPIAN_URL).post(String.class, params);
                logger.info("send sms, api result:{}", json);
                JsonNode root = JsonUtils.fromJson(json);
                if (root.has("code")) {
                    JsonNode code = root.get("code");
                    if (code.isIntegralNumber() && code.intValue() == 0)
                        return;
                }
                logger.warn("send sms failure, result:{}", json);
            }
        });
    }

}
