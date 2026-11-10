package com.checkin.common;

import java.io.Serializable;

/**
 * 统一返回格式：所有接口都返回这个壳子，前端/调用方永远只需要认这三个字段。
 *
 * 为什么要统一？
 *   如果每个接口返回格式都不一样，前端要写一堆 if 判断，AI Agent 那边也不好解析。
 *   统一之后：code 判断成功失败，message 给人看，data 给程序用。
 *
 * 结构示例：
 *   成功：{ "code": 200, "message": "成功", "data": {...} }
 *   失败：{ "code": 404, "message": "任务不存在", "data": null }
 *
 * 泛型 <T> 是什么意思？
 *   表示 data 的类型可以变：查列表时是 List<Task>，查单个时是 Task，删除时是 null。
 *   不写泛型的话就得为每种类型各写一个类。
 */
public class Result<T> implements Serializable {

    /** 状态码：200 成功 / 400 参数不对 / 404 找不到 / 500 服务器内部错误 */
    private int code;

    /** 提示文字：直接给人看的，建议写中文 */
    private String message;

    /** 真正的数据：查列表就是数组，查详情就是一个对象，失败时为 null */
    private T data;

    // ---------- 构造方法 ----------

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ---------- 静态工厂方法：让调用处写起来短一点 ----------

    /** 成功并带数据：return Result.ok(task); */
    public static <T> Result<T> ok(T data) {
        return new Result<>(200, "成功", data);
    }

    /** 成功但不带数据：比如删除成功 */
    public static <T> Result<T> ok() {
        return new Result<>(200, "成功", null);
    }

    /** 失败，自定义状态码和原因 */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    // ---------- getter / setter ----------
    // Spring 转 JSON 时靠这些方法读字段，不能少（没有 Lombok 就得手写）

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
