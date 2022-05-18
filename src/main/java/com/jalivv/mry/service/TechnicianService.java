package com.jalivv.mry.service;

import com.jalivv.mry.entity.R;
import com.jalivv.mry.entity.Technician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Technician)表服务接口
 *
 * @author makejava
 * @since 2022-05-05 17:00:47
 */
public interface TechnicianService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Technician queryById(Long id);

    /**
     * 分页查询
     *
     * @param technician 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Technician> queryByPage(Technician technician, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param technician 实例对象
     * @return 实例对象
     */
    Technician insert(Technician technician);

    /**
     * 修改数据
     *
     * @param technician 实例对象
     * @return 实例对象
     */
    Technician update(Technician technician);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    R getTecInfos(int page, int limit);

    R getTecInfoById(Long id);
}
