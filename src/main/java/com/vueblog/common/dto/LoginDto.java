package com.vueblog.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName LoginDto
 * @Description TODO
 * @Author Sunyuhang
 * @Date 2022年08月02日 09:11
 * @Version 1.0
 */
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
