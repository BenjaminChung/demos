package com.whale.demo.springboot.mapper;

import com.whale.demo.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by benjaminchung on 17/5/6.
 */
@Mapper
public interface UserMapper {
    @Select("select * from t_user where name = #{name}")
    User findUserByName(@Param("name")String name);
}
