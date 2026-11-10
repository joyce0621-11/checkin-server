-- =======================================================
--  打卡系统 · 建表脚本
--  使用方式（在项目根目录执行）：
--    "E:\develop\mysql-8.0.28-winx64\bin\mysql.exe" -u root -p123456 < db\schema.sql
--  或者在 Navicat / DataGrip / MySQL 命令行里打开本文件执行
-- =======================================================

-- 建库（已经有就跳过）
CREATE DATABASE IF NOT EXISTS checkin
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE checkin;

-- ---------------------------------------------
-- 任务表
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS task
(
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键，任务ID',
    title       VARCHAR(100) NOT NULL COMMENT '任务名称，如"每天背20个单词"',
    description VARCHAR(500)          DEFAULT NULL COMMENT '任务描述，可为空',
    created_at  DATETIME     NOT NULL COMMENT '创建时间，由后端生成',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='打卡任务表';
