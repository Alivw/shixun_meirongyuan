package com.jalivv.mry.service.impl;

import com.jalivv.mry.entity.R;
import com.jalivv.mry.entity.Technician;
import com.jalivv.mry.dao.TechnicianDao;
import com.jalivv.mry.service.TechnicianService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Technician)表服务实现类
 *
 * @author makejava
 * @since 2022-05-05 17:00:47
 */
@Service("technicianService")
public class TechnicianServiceImpl implements TechnicianService {
    @Resource
    private TechnicianDao technicianDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Technician queryById(Long id) {
        return this.technicianDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param technician  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Technician> queryByPage(Technician technician, PageRequest pageRequest) {
        // long total = this.technicianDao.count(technician);
        // return new PageImpl<>(this.technicianDao.queryAllByLimit(technician, pageRequest), pageRequest, total);
        return null;
    }

    /**
     * 新增数据
     *
     * @param technician 实例对象
     * @return 实例对象
     */
    @Override
    public Technician insert(Technician technician) {
        this.technicianDao.insert(technician);
        return technician;
    }

    /**
     * 修改数据
     *
     * @param technician 实例对象
     * @return 实例对象
     */
    @Override
    public Technician update(Technician technician) {
        this.technicianDao.update(technician);
        return this.queryById(technician.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.technicianDao.deleteById(id) > 0;
    }

    @Override
    public R getTecInfos(int page, int limit) {

        try {
            List<Technician> technicians = technicianDao.queryAllByLimit((page - 1) * limit, limit);
            //获取总条数 返回给前端判断是否已经是最后一条数据。如果是则不在发送请求
            Long count = technicianDao.queryCount();
            return new R(20, String.valueOf(count), technicians);
        } catch (Exception e) {
            System.out.println(e);
            return R.error("9999", "网络异常！");
        }
    }

    @Override
    public R getTecInfoById(Long id) {
        try {
            Technician tecInfoById = technicianDao.getTecInfoById(id);
            return R.ok( tecInfoById);
        } catch (Exception e) {
            System.out.println(e);
            return R.error("9999", "网络异常！");
        }
    }
}
