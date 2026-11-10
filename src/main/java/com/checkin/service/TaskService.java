package com.checkin.service;

import com.checkin.entity.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 任务业务层：增删改查真正干活的地方。
 *
 * @Service 告诉 Spring："这个类是业务类，帮我创建好对象放进容器"，
 *          只有这样 Controller 里才能直接注入使用（这叫依赖注入）。
 *
 * 本阶段数据存在内存里（一个 Map），程序一重启数据就没了 —— 这是故意的，
 * 阶段 3 会换成 MySQL，那时候我们只改这一层，Controller 一行都不用动。
 * 这正是"分层"的好处。
 */
@Service
public class TaskService {

    /**
     * 存数据的地方：key 是任务 id，value 是任务对象。
     * 用 ConcurrentHashMap 而不是 HashMap，是因为 Tomcat 处理请求是多线程的，
     * HashMap 在多线程下并发写入会出问题（面试常问）。
     */
    private final Map<Long, Task> taskStore = new ConcurrentHashMap<>();

    /** id 生成器：从 0 开始，每次 +1。AtomicLong 保证多线程下不会生成重复 id */
    private final AtomicLong idGenerator = new AtomicLong(0);

    /**
     * 初始化两块演示数据，这样你打开浏览器就能看到东西，不用先手动新增。
     * 阶段 3 接了数据库之后，这个方法可以删掉。
     * @PostConstruct 意思是：这个对象创建好之后，自动执行一次。
     */
    @PostConstruct
    public void initDemoData() {
        Task t1 = new Task();
        t1.setTitle("每天背 20 个单词");
        t1.setDescription("用单词 App 打卡，连续 7 天算一个小目标");
        create(t1);

        Task t2 = new Task();
        t2.setTitle("每天跑步 2 公里");
        t2.setDescription("晚上 9 点前完成");
        create(t2);
    }

    // ---------------- 查：列表 ----------------

    /**
     * 查全部任务，按 id 从大到小排（新建的排前面，符合直觉）
     */
    public List<Task> list() {
        List<Task> tasks = new ArrayList<>(taskStore.values());
        tasks.sort(Comparator.comparing(Task::getId).reversed());
        return tasks;
    }

    // ---------------- 查：单个 ----------------

    /**
     * 按 id 查一个任务。
     * 返回 Optional 而不是直接返回 null，是为了强制调用方处理"查不到"的情况，
     * 减少空指针异常（NullPointerException）。
     */
    public Optional<Task> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(taskStore.get(id));
    }

    // ---------------- 增 ----------------

    /**
     * 新增任务：id 和创建时间都由后端生成，忽略前端传来的值
     */
    public Task create(Task task) {
        Task saved = new Task();
        saved.setId(idGenerator.incrementAndGet());
        saved.setTitle(task.getTitle());
        saved.setDescription(task.getDescription());
        saved.setCreatedAt(LocalDateTime.now());
        taskStore.put(saved.getId(), saved);
        return saved;
    }

    // ---------------- 改 ----------------

    /**
     * 修改任务：只允许改 title 和 description，改不到就返回 null
     * 注意这里是"整体覆盖"，不是"只改传了的字段"，逻辑简单，等阶段 3 再优化。
     */
    public Task update(Long id, Task newTask) {
        Task old = taskStore.get(id);
        if (old == null) {
            return null;   // 不存在，交给 Controller 返回 404
        }
        old.setTitle(newTask.getTitle());
        old.setDescription(newTask.getDescription());
        // 注意：old 是 Map 里那个对象的引用，改它就是改 Map 里的数据，不用再 put 一次
        return old;
    }

    // ---------------- 删 ----------------

    /**
     * 删除任务：返回 true 表示删掉了，false 表示本来就没有这个 id
     */
    public boolean delete(Long id) {
        return taskStore.remove(id) != null;
    }
}
