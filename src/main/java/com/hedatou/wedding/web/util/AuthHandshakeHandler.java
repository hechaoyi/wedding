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
public class AuthHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String cookies = request.getHeaders().getFirst("Cookie");
        if (StringUtils.isEmpty(cookies))
            return null;
        String token = Splitter.on(";").trimResults().omitEmptyStrings().withKeyValueSeparator("=").split(cookies)
                .get("l");
        if (StringUtils.isEmpty(token))
            return null;
        return new TokenPrincipal(token);
    }

    public static class TokenPrincipal implements Principal {

        private String token;

        public TokenPrincipal(String token) {
            this.token = token;
        }

        @Override
        public String getName() {
            return token;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((token == null) ? 0 : token.hashCode());
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
            TokenPrincipal other = (TokenPrincipal) obj;
            if (token == null) {
                if (other.token != null)
                    return false;
            } else if (!token.equals(other.token))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "TokenPrincipal [token=" + token + "]";
        }

        @Override
        public boolean implies(Subject subject) {
            if (subject == null)
                return false;
            return subject.getPrincipals().contains(this);
        }

    }

}
