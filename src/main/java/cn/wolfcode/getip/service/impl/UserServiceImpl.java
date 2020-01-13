package cn.wolfcode.getip.service.impl;

import cn.wolfcode.getip.domain.User;
import cn.wolfcode.getip.mapper.UserMapper;
import cn.wolfcode.getip.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void save(User user) {
        userMapper.insert(user);
    }
}
