package com.jalivv.mry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jalivv.mry.entity.R;
import com.jalivv.mry.entity.User;
import com.jalivv.mry.dao.UserDao;
import com.jalivv.mry.service.UserService;
import com.jalivv.mry.uitl.HttpClientUtil;
import com.jalivv.mry.uitl.StringUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-05-05 17:01:05
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Long id) {
        return this.userDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param user        筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<User> queryByPage(User user, PageRequest pageRequest) {
        long total = this.userDao.count(user);
        return new PageImpl<>(this.userDao.queryAllByLimit(user, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        this.userDao.update(user);
        return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public R userRegister(User user) {
        String phone = user.getPhone();
        String username = user.getUsername();
        String password = user.getPassword();
        //1.非空校验 phone取出空格 只要一个代码两次 提取
        if (StringUtil.isNull(phone)) {
            return R.error("手机号为空", null);
        }

        if (StringUtil.isNull(password)) {
            return R.error("9002", null);
        }

        if (StringUtil.isNull(username)) {
            return R.error("参数为空", null);
        }
        try {
            //2.校验用户名是否存在 //如何校验？ 根据username查询user表 如果有数据 说明存在 返回提示信息
            //
            User queryUser = userDao.queryUserByUserName(username);
            if (queryUser != null) {
                return R.error("用户名已经存在", null);
            }
            //3.加密 source:加密的资源 123456 salt：盐值 hashIterations：加密次数 //123456 qianfeng 1qi2an34f56eng
            Md5Hash md5Hash = new Md5Hash(password, "qianfeng", 10);
            String newPassword = md5Hash.toString();
            //得到加密之后的密码 //4.保存
            user.setPassword(newPassword);
            //5.调用dao层进行持久化
            userDao.insert(user);
            return R.ok(null);
        } catch (Exception e) {
            System.out.println(e);
            return R.error("网络异常", null);
        }
    }

    @Override
    public R userLogin(String phone, String password, String code) {
        //1.非空校验
        if (StringUtil.isNull(phone)) {
            return R.error("手机号为空", null);
        }
        if (StringUtil.isNull(password)) {
            return R.error("9002", "密码为空");
        }
        //2.密码加密
        Md5Hash md5Hash = new Md5Hash(password, "qianfeng", 10);
        String nwePassword = md5Hash.toString();
        //3.根据手机号密码查询用户信息 user
        User user = userDao.queryUserByPhoneAndPwd(phone, nwePassword);
        if (user == null) {
            //说明错误
            return R.error("9005", "账号密码不匹配");
        }
        try {
            //4.调用微信的接口 请求方式 get 模拟一个get请求 //https://api.weixin.qq.com/sns/jscode2session? appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx4fa67535c57b43f3&secret=fb2ce1053ba9fba7fa3df6dcca6b062a&js_code=" + code + "&grant_type=authorization_code";
            String result = HttpClientUtil.doGet(url);
            //返回一个字符串包含session_key 和 openid
            System.out.println("result = " + result);
            //5.获取session_key 和 openid 字符串 转 json
            JSONObject jsonResult = (JSONObject) JSONObject.parse(result);
            String session_key = (String) jsonResult.get("session_key");
            String openid = (String) jsonResult.get("openid");
            System.out.println("session_key = " + session_key);
            System.out.println("openid = " + openid); //6.生成自定义登录状态


            //登录成功后自动生成openid sessionkey 和token，方便后续代码的优化
            Md5Hash md5Hash1 = new Md5Hash(session_key, openid, 10);
            String token = md5Hash1.toString();
            //7.保存(跟新) user token、session_key openid 根据 id去跟新
            user.setOpenid(openid);
            user.setSessionkey(session_key);
            user.setToken(token);
            userDao.update(user);
            return R.ok(token);
        } catch (Exception e) {
            System.out.println(e);
            return R.error("9999", "网络异常");
        }
    }
}
