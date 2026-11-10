package com.checkin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 任务实体类：描述"一个打卡任务包含哪些信息"。
 *
 * 阶段 2 它只是个普通 Java 对象（POJO）；现在加上 MyBatis-Plus 的注解后，
 * 它就和数据库里的 task 表一一对应了，这种对象面试叫 PO（Persistent Object）。
 *
 * 字段设计：
 *   id          主键，数据库自增
 *   title       任务名，必填
 *   description 任务描述，选填
 *   createdAt   创建时间，后端生成（对应表字段 created_at，靠驼峰自动映射）
 */
@TableName("task")
public class Task {

    /**
     * @TableId 标记主键。
     * type = IdType.AUTO 表示用数据库的 AUTO_INCREMENT，
     * 插入成功后 MyBatis-Plus 会把数据库生成的 id 自动回填到这个对象里。
     */
    @TableId(type = IdType.AUTO)
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
