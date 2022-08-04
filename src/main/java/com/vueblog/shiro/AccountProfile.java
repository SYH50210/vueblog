package com.vueblog.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AccountProfile
 * @Description TODO
 * @Author Sunyuhang
 * @Date 2022年08月01日 17:26
 * @Version 1.0
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

}
