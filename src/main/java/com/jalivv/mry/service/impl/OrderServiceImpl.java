package com.jalivv.mry.service.impl;

import com.jalivv.mry.dao.UserDao;
import com.jalivv.mry.entity.Order;
import com.jalivv.mry.dao.OrderDao;
import com.jalivv.mry.entity.R;
import com.jalivv.mry.service.OrderService;
import com.jalivv.mry.uitl.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (Order)表服务实现类
 *
 * @author makejava
 * @since 2022-05-05 16:59:45
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;


    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Order queryById(Long id) {
        return this.orderDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param order       筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Order> queryByPage(Order order, PageRequest pageRequest) {
        long total = this.orderDao.count(order);
        return new PageImpl<>(this.orderDao.queryAllByLimit(order, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order insert(Order order) {
        this.orderDao.insert(order);
        return order;
    }

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order update(Order order) {
        this.orderDao.update(order);
        return this.queryById(order.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.orderDao.deleteById(id) > 0;
    }

    @Override
    public R createOrder(Order order, String token) {

        String username = order.getUsername();
        String usertell = order.getUsertell();
        if (StringUtil.isNull(username) || StringUtil.isNull(usertell)) {
            return  R.error("9003", "参数为空");
        }
        try {
            //1.定义dao层 根据token获取到openid
            String openid = userDao.queryOpenidByToken(token); //2.获取系统当前时间

             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            order.setOpenid(openid);
            order.setPlacedate(sdf.format(new Date()));
            order.setOrderstate("0");
            //状态默认为0 //3.生成订单
            orderDao.insert(order);
            return  R.ok( "请求成功");


        } catch (Exception e) {
            System.out.println(e);
            return R.error("9999", "网络异常");
        }
    }
}
