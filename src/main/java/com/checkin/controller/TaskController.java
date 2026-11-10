package com.checkin.controller;

import com.checkin.common.Result;
import com.checkin.entity.Task;
import com.checkin.service.TaskService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 任务接口层：只做三件事 —— 收请求、判断参数、把结果包装成 Result 返回。
 * 业务逻辑一律不写在这里，全部交给 TaskService。
 *
 * RESTful 风格速记：
 *   GET    查     /api/tasks        查列表
 *   GET    查     /api/tasks/1      查单个
 *   POST   增     /api/tasks        新增（新增用 POST，不是 GET）
 *   PUT    改     /api/tasks/1      修改
 *   DELETE 删     /api/tasks/1      删除
 * 同样的路径，靠"请求方法"区分操作，这就是 REST 的核心思想。
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    /** 业务层对象，通过构造方法注入（Spring 会自动把 TaskService 对象塞进来，不用自己 new） */
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ---------------- 1. 查列表 ----------------

    /**
     * GET http://localhost:8080/api/tasks
     */
    @GetMapping
    public Result<List<Task>> list() {
        return Result.ok(taskService.list());
    }

    // ---------------- 2. 查单个 ----------------

    /**
     * GET http://localhost:8080/api/tasks/1
     * @PathVariable 用来接路径里的那个数字（{id} 占位）
     */
    @GetMapping("/{id}")
    public Result<Task> getById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);
        return task.map(Result::ok)                                  // 查到了 → 包装成成功结果
                .orElseGet(() -> Result.fail(404, "任务不存在"));      // 没查到 → 返回 404
    }

    // ---------------- 3. 新增 ----------------

    /**
     * POST http://localhost:8080/api/tasks
     * @RequestBody 把前端发来的 JSON 自动转换成 Task 对象（这个转换叫反序列化）
     *
     * 用 Postman / 浏览器插件发请求时，Body 选 raw + JSON，内容像这样：
     * { "title": "每天练 30 分钟吉他", "description": "周末可以休息" }
     */
    @PostMapping
    public Result<Task> create(@RequestBody Task task) {
        // 参数校验：任务名不能为空，前端传空就直接拒绝，不要让它进业务层
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            return Result.fail(400, "任务名称不能为空");
        }
        return Result.ok(taskService.create(task));
    }

    // ---------------- 4. 修改 ----------------

    /**
     * PUT http://localhost:8080/api/tasks/1
     */
    @PutMapping("/{id}")
    public Result<Task> update(@PathVariable Long id, @RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            return Result.fail(400, "任务名称不能为空");
        }
        Task updated = taskService.update(id, task);
        if (updated == null) {
            return Result.fail(404, "任务不存在，改不了");
        }
        return Result.ok(updated);
    }

    // ---------------- 5. 删除 ----------------

    /**
     * DELETE http://localhost:8080/api/tasks/1
     * Result<Void> 表示这个接口没有 data 要返回
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        boolean removed = taskService.delete(id);
        if (!removed) {
            return Result.fail(404, "任务不存在，删不了");
        }
        return Result.ok();
    }
}
