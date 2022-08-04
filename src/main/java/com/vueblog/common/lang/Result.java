package com.vueblog.common.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Result
 * @Description TODO
 * @Author Sunyuhang
 * @Date 2022年07月27日 14:01
 * @Version 1.0
 */
@Data
public class Result implements Serializable {
    private int code; //200表示正常 400表示异常
    private String msg;
    private Object data;

    // 成功
    public static Result success(Object data) {
        return success(200, "操作成功!", data);
    }

    // 成功
    public static Result success(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    // 失败
    public static Result error(String msg) {
        return error(msg, null);
    }

    // 失败
    public static Result error(String msg, Object data) {
        return error(400, msg, data);
    }


    // 失败
    public static Result error(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

}
