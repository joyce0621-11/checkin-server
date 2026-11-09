package com.checkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类（整个程序的入口，就相当于 main 方法所在的地方）
 *
 * @SpringBootApplication 这个注解是个"大礼包"，里面包含三件事：
 *   1. 自动配置：Spring 自动帮你把 Tomcat、JSON 转换等东西配好
 *   2. 组件扫描：自动扫描本包（com.checkin）以及子包下的 @RestController / @Service 等类
 *   3. 标记这是配置类
 *
 * 注意：这个类必须放在最外层的包里（com.checkin），
 *      否则写在子包里的 Controller 会扫描不到，接口会 404。
 */
@SpringBootApplication
public class CheckinApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckinApplication.class, args);
        System.out.println("""
                --------------------------------------------------
                 打卡系统后端启动成功！
                 浏览器访问: http://localhost:8080/api/health
                --------------------------------------------------
                """);
    }
}
