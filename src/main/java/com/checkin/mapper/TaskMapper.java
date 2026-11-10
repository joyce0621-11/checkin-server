package com.checkin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.checkin.entity.Task;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务数据访问层（Mapper 层 / DAO 层）。
 *
 * 这个接口里一行方法都没写，但已经拥有了这些能力（都是 BaseMapper 给的）：
 *   insert(task)      新增
 *   deleteById(id)    按 id 删除
 *   updateById(task)  按 id 更新（只更新非 null 字段）
 *   selectById(id)    按 id 查询
 *   selectList(...)   条件查询
 *
 * 这就是 MyBatis-Plus 的价值：单表 CRUD 不用写 SQL，也不用写实现类。
 *
 * @Mapper 告诉 MyBatis：启动时为这个接口生成实现类（代理对象）。
 *         没有这个注解，Spring 启动会报 "找不到 TaskMapper 这个 Bean"。
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
}
