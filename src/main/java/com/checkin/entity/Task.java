package com.checkin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 任务实体类：描述"一个打卡任务包含哪些信息"。
 *
 * 现在它只是内存里一个普通 Java 对象（面试叫 POJO）；
 * 阶段 3 接数据库后，再给它加上 @TableName / @TableId 之类的注解，它就变成一张表。
 *
 * 字段设计思路（很简单，别想复杂）：
 *   id          主键，唯一标识一个任务，用自增数字
 *   title       任务名，必填，比如"每天背 20 个单词"
 *   description 任务描述，选填
 *   createdAt   创建时间，由后端自动生成，不由前端传（防止前端乱传时间）
 */
public class Task {

    private Long id;

    private String title;

    private String description;

    /**
     * @JsonFormat 控制这个时间转成 JSON 时长什么样。
     * 不加的话默认输出 "2026-11-10T14:24:10.1278291"（带纳秒，没法看），
     * 加上之后输出 "2026-11-10 14:24:10"，前端和人都好读。
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // ---------- getter / setter ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
