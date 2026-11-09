package com.checkin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第一个接口控制器 —— 目的只有一个：证明"后端能跑起来、能被浏览器访问到"。
 *
 * @RestController = @Controller + @ResponseBody
 *   意思是：这个类里每个方法的返回值，都会自动转成 JSON 返回给前端，而不是去找网页模板。
 *
 * @RequestMapping("/api") 给这个类里所有接口统一加一个前缀，访问路径就是 /api/xxx
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * 健康检查接口：GET http://localhost:8080/api/health
     * 面试可以这么说：这是服务探活接口，运维/负载均衡靠它判断服务是否存活。
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        // LinkedHashMap 保证 JSON 字段顺序和我们 put 的顺序一致，看起来更舒服
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", "ok");                                    // 服务状态
        result.put("app", "checkin-server");                           // 应用名
        result.put("version", "0.0.1");                                // 版本号
        result.put("time", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // 服务器当前时间
        return result;
    }

    /**
     * 打招呼接口：GET http://localhost:8080/api/hello?name=小明
     *
     * @RequestParam 用来接收 URL 问号后面的参数，defaultValue 表示"没传就用默认值"
     */
    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "同学") String name) {
        return "你好，" + name + "！欢迎来到打卡系统后端。";
    }
}
