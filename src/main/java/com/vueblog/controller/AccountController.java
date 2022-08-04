package com.vueblog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vueblog.common.dto.LoginDto;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.User;
import com.vueblog.service.UserService;
import com.vueblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AccountController
 * @Description TODO
 * @Author Sunyuhang
 * @Date 2022年08月02日 09:10
 * @Version 1.0
 */
@RestController
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        // 断言拦截
        Assert.notNull(user, "用户不存在");
        //判断账号密码是否错误 因为是md5加密所以这里md5判断
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            // 密码错误，抛出异常
            return Result.error("密码不正确");
        }

        String jwt = jwtUtils.generateToken(user.getId());

        // 把token放到请求头header中
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");


        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map());

    }

    /**
     * 需要认证权限才能退出登录
     */
    @RequiresAuthentication
    @RequestMapping("/logout")
    public Result logout() {
        // 退出
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}
