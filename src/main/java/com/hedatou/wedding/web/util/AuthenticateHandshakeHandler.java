package com.hedatou.wedding.web.util;

import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.google.common.base.Splitter;

@ControllerAdvice
public class AuthenticateHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String cookies = request.getHeaders().getFirst("Cookie");
        if (StringUtils.isEmpty(cookies))
            return null;
        String userName = Splitter.on(";").trimResults().omitEmptyStrings().withKeyValueSeparator("=").split(cookies)
                .get("u");
        if (StringUtils.isEmpty(userName))
            return null;
        return new PrincipalImpl(userName);
    }

    public static class PrincipalImpl implements Principal {

        private String userName;

        public PrincipalImpl(String userName) {
            this.userName = userName;
        }

        @Override
        public String getName() {
            return userName;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((userName == null) ? 0 : userName.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            PrincipalImpl other = (PrincipalImpl) obj;
            if (userName == null) {
                if (other.userName != null)
                    return false;
            } else if (!userName.equals(other.userName))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "PrincipalImpl [userName=" + userName + "]";
        }

        @Override
        public boolean implies(Subject subject) {
            if (subject == null)
                return false;
            return subject.getPrincipals().contains(this);
        }

    }

}
