package com.law.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.law.community.mapper.UserplusMapper;
import com.law.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserplusService {
    @Autowired
    private UserplusMapper userplusMapper;

    public void createOrUpdate(User user) {
        User dbUser = userplusMapper.selectOne(new QueryWrapper<User>().eq("account_id", user.getAccountId()));
        if (dbUser == null) {
//            插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userplusMapper.insert(user);
        }else{
//            更新
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("account_id",user.getAccountId());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userplusMapper.update(dbUser,updateWrapper);
        }
    }
}
