package com.vueblog.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName JwtToken
 * @Description TODO
 * @Author Sunyuhang
 * @Date 2022年08月01日 16:02
 * @Version 1.0
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
