package com.vueblog.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.common.lang.Result;
import com.vueblog.entity.Blog;
import com.vueblog.service.BlogService;
import com.vueblog.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2022-07-27
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    BlogService blogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage<Blog> pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    //@PathVariable动态路由
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        Blog blog = blogService.getById(id);
        //判断是否为空 为空则断言异常
        Assert.notNull(blog, "该博客已被删除");

        return Result.success(blog);
    }

    /**
     * 添加编辑  没有id则添加 有id则编辑
     */
    @RequiresAuthentication
    @PostMapping("/addOrEdit")
    public Result addOrEdit(@RequestBody @Validated Blog blog) {

        System.out.println(blog);

        Blog temp = null;

        if (blog.getId() != null) {
            // 编辑
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有编辑权限!");
        } else {
            // 新增
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtils.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);

        return Result.success(null);
    }


    @RequiresAuthentication
    @PostMapping("/del/{id}")
    public Result del(@PathVariable Long id) {
        boolean b = blogService.removeById(id);
        //判断是否为空 为空则断言异常
        if (b == true) {
            return Result.success("博客删除成功");
        } else {
            return Result.error("博客删除失败");
        }
    }


}
