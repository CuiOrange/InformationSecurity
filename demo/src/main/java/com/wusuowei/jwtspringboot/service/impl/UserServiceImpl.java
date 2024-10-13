package com.wusuowei.jwtspringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wusuowei.jwtspringboot.constant.Constants;
import com.wusuowei.jwtspringboot.mapper.UserMapper;
import com.wusuowei.jwtspringboot.model.po.User;
import com.wusuowei.jwtspringboot.service.UserService;
import com.wusuowei.jwtspringboot.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lgy
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(User user) {
        String name = user.getUsername();
        String password = user.getPassword();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getPassword,password);
        wrapper.eq(User::getUsername, name);
        return userMapper.selectOne(wrapper);

    }

    @Override
    public User register(User user) {
        User one = getUserInfo(user);
        if (one == null) {
            one = user;
            userMapper.insert(user);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }

    private User getUserInfo(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", user.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

}
