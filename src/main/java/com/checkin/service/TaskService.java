package com.checkin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.checkin.entity.Task;
import com.checkin.mapper.TaskMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 任务业务层。
 *
 * 阶段 2 数据存在内存 Map 里，重启就没了；
 * 阶段 3 换成了 MySQL，但你可以对比一下：Controller 一行都没改，只改了这个类。
 * 这就是分层的意义 —— 换存储方式时，影响范围被控制在 Service 这一层。
 */
@Service
public class TaskService {

    /**
     * 数据访问对象，通过构造方法注入（Spring 自动把生成的 TaskMapper 塞进来）
     * 有了它就不用自己写 JDBC 连接、拼 SQL、关连接，MyBatis 全包了。
     */
    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    // ---------------- 查：列表 ----------------

    /**
     * 查全部任务，按 id 倒序（新任务在前面）。
     *
     * LambdaQueryWrapper 是 MyBatis-Plus 的条件构造器：
     *   orderByDesc(Task::getId) 等价于 SQL 里的 ORDER BY id DESC，
     *   用方法引用写字段名，编译器能帮你检查拼写，比手写字符串 "id" 安全。
     */
    public List<Task> list() {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Task::getId);
        return taskMapper.selectList(wrapper);
    }

    // ---------------- 查：单个 ----------------

    public Optional<Task> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        // selectById 查不到会返回 null，这里用 Optional 包一层，强制调用方判断空
        return Optional.ofNullable(taskMapper.selectById(id));
    }

    // ---------------- 增 ----------------

    /**
     * 新增任务。
     * id 交给数据库自增（所以先把前端传来的 id 置空，防止乱传覆盖其他数据），
     * createAt 由后端生成，不信前端。
     */
    public Task create(Task task) {
        task.setId(null);
        task.setCreatedAt(LocalDateTime.now());
        taskMapper.insert(task);
        // 插入后 task 里的 id 已经被 MyBatis-Plus 自动回填了，可以直接返回
        return task;
    }

    // ---------------- 改 ----------------

    /**
     * 修改任务：先查再改，查不到返回 null 让 Controller 报 404。
     *
     * 为什么不直接用 updateById(传进来的对象)？
     * 因为那样会把 createdAt 也覆盖成 null（前端没传这个字段），数据就丢了。
     * 所以标准做法是：先查出完整对象 → 改允许改的字段 → 再更新。
     */
    public Task update(Long id, Task newTask) {
        Task old = taskMapper.selectById(id);
        if (old == null) {
            return null;
        }
        old.setTitle(newTask.getTitle());
        old.setDescription(newTask.getDescription());
        taskMapper.updateById(old);
        return old;
    }

    // ---------------- 删 ----------------

    /**
     * 删除任务：deleteById 返回受影响的行数，>0 说明真删掉了
     */
    public boolean delete(Long id) {
        return taskMapper.deleteById(id) > 0;
    }
}
