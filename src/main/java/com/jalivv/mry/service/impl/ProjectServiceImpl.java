package com.jalivv.mry.service.impl;

import com.jalivv.mry.entity.Project;
import com.jalivv.mry.dao.ProjectDao;
import com.jalivv.mry.entity.R;
import com.jalivv.mry.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Project)表服务实现类
 *
 * @author makejava
 * @since 2022-05-05 17:00:13
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectDao projectDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Project queryById(Long id) {
        return this.projectDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param project 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Project> queryByPage(Project project, PageRequest pageRequest) {
        long total = this.projectDao.count(project);
        return new PageImpl<>(this.projectDao.queryAllByLimit(project, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    public Project insert(Project project) {
        this.projectDao.insert(project);
        return project;
    }

    /**
     * 修改数据
     *
     * @param project 实例对象
     * @return 实例对象
     */
    @Override
    public Project update(Project project) {
        this.projectDao.update(project);
        return this.queryById(project.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.projectDao.deleteById(id) > 0;
    }

    /**
     * 获取所有的项目信息
     * @return
     */
    @Override
    public List<Project> getProinfos() {
        return projectDao.getProinfos();
    }

    /**
     * 分页获取项目信息
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Project> getProinfos(int start, int pageSize) {
        return projectDao.getProinfosFatch(start, pageSize);
    }
}
