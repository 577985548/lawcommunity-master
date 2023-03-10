package com.law.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.law.community.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserplusMapper extends BaseMapper<User> {
}
