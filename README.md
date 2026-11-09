# checkin-server · 简易任务打卡系统（Java 后端）

一个用 Spring Boot 写的任务打卡系统后端，用于日常习惯/任务打卡记录。
这是我三个练手项目的第 1 个，后续会和第 2 个项目（AI Agent 助手）联动，让 AI 通过接口读写打卡数据。

> 项目正在分阶段开发中，提交记录保留了真实的开发过程。

## 技术栈

| 分类 | 技术 |
| --- | --- |
| 语言 | Java 21 |
| 框架 | Spring Boot 3.5 |
| 构建 | Maven |
| 后续接入 | MySQL / MyBatis-Plus（阶段 3） |

## 当前进度

- [x] **阶段 1**：项目骨架搭建，服务能启动，健康检查接口可用
- [ ] 阶段 2：任务（Task）增删改查接口，先用内存存储
- [ ] 阶段 3：接入 MySQL + MyBatis-Plus，数据真正落库
- [ ] 阶段 4：打卡记录、连续打卡天数统计、统一返回格式与全局异常处理
- [ ] 阶段 5：内置简单网页 + 接口文档 + 与 AI Agent 联动

## 快速开始

环境要求：JDK 21、Maven 3.9+

```bash
# 1. 进入项目目录
cd checkin-server

# 2. 启动项目
mvn spring-boot:run
```

看到 `打卡系统后端启动成功` 就说明起来了，然后浏览器打开：

- 健康检查：<http://localhost:8080/api/health>
- 打招呼：<http://localhost:8080/api/hello?name=小明>

## 接口说明

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/health` | 健康检查，返回服务状态与服务器时间 |
| GET | `/api/hello?name=xxx` | 测试接口，返回一句问候语 |

示例返回（`/api/health`）：

```json
{
  "status": "ok",
  "app": "checkin-server",
  "version": "0.0.1",
  "time": "2026-11-07 12:30:00"
}
```

## 目录结构

```
checkin-server
├── src/main/java/com/checkin
│   ├── CheckinApplication.java      # 启动类（程序入口）
│   └── controller
│       └── HealthController.java    # 健康检查接口
├── src/main/resources
│   └── application.yml              # 配置文件（端口等）
├── pom.xml                          # Maven 依赖配置
└── .gitignore                       # 忽略编译产物与密钥文件
```

## 开发笔记

- 端口被占用时，改 `application.yml` 里的 `server.port` 即可。
- 启动类必须放在最外层包（`com.checkin`），否则子包里的接口会 404。

## 作者

计算机科学与技术 在读，正在通过「三个串联的小项目」练习完整的软件开发链路。
