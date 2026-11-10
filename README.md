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
- [x] **阶段 2**：任务（Task）增删改查接口，内存存储 + 统一返回格式
- [ ] 阶段 3：接入 MySQL + MyBatis-Plus，数据真正落库
- [ ] 阶段 4：打卡记录、连续打卡天数统计、全局异常处理
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

所有接口统一返回 `code / message / data` 三段结构，成功时 `code` 为 200：

```json
{ "code": 200, "message": "成功", "data": { } }
```

失败时 `code` 为 400（参数错误）/ 404（资源不存在）/ 500（服务端异常），`data` 为 `null`。

### 基础接口

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/health` | 健康检查，返回服务状态与服务器时间 |
| GET | `/api/hello?name=xxx` | 阶段 1 的联调用测试接口（后续会移除） |

### 任务接口

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/tasks` | 查询任务列表（按 id 倒序） |
| GET | `/api/tasks/{id}` | 按 id 查询单个任务 |
| POST | `/api/tasks` | 新增任务，Body 传 `title`、`description` |
| PUT | `/api/tasks/{id}` | 修改任务名称与描述 |
| DELETE | `/api/tasks/{id}` | 删除任务 |

请求示例：

```bash
# 查列表
curl http://localhost:8080/api/tasks

# 新增（Windows 的 cmd 里请把单引号换成双引号并转义）
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"每天练30分钟吉他","description":"周末可以休息"}'
```

响应示例：

```json
{
  "code": 200,
  "message": "成功",
  "data": {
    "id": 3,
    "title": "每天练30分钟吉他",
    "description": "周末可以休息",
    "createdAt": "2026-11-10 14:24:37"
  }
}
```

## 目录结构

```
checkin-server
├── src/main/java/com/checkin
│   ├── CheckinApplication.java        # 启动类（程序入口）
│   ├── common/Result.java             # 统一返回格式
│   ├── controller
│   │   ├── HealthController.java      # 健康检查接口
│   │   └── TaskController.java        # 任务接口
│   ├── service
│   │   └── TaskService.java           # 任务业务逻辑（当前为内存存储）
│   └── entity
│       └── Task.java                  # 任务实体
├── src/main/resources
│   └── application.yml                # 配置文件（端口等）
├── pom.xml                            # Maven 依赖配置
└── .gitignore                         # 忽略编译产物与密钥文件
```

## 开发笔记

- 端口被占用时，改 `application.yml` 里的 `server.port` 即可。
- 启动类必须放在最外层包（`com.checkin`），否则子包里的接口会 404。
- 当前数据存在内存 `ConcurrentHashMap` 里，**重启服务数据会清空**，阶段 3 接 MySQL 后解决。
- 用 `body` 传 JSON 时记得设置 `Content-Type: application/json`，否则后端收到的字段全是 null。

## 作者

计算机科学与技术 在读，正在通过「三个串联的小项目」练习完整的软件开发链路。
