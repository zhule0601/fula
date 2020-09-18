package com.fula.service.impl;

import com.fula.mapper.UserDao;
import com.fula.model.User;
import com.fula.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    /**
     * @author zl
     * @description 不存在插入, 存在则更新
     * @param wxId wxId
     * @return int
     */
    @Override
    public int addWXUser(String wxId) {
        if (StringUtils.isNoneBlank(wxId)) {
            User user = new User();
            user.setGmtCreate(new Date());
            user.setGmtModified(new Date());
            user.setWxUid(wxId);
            user.setState("NORMAL");
            return userDao.insertForDuplicateKey(user);
        }
        return -1;
    }

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }
}
