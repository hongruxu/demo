package com.hongruxu.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hongruxu.demo.entity.User;
import java.util.List;


@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    User getById(@Param("id") Integer id);

    @Select("select * from uesr")
    List<User> getAll();

    @Insert("INSERT INTO user (username, email,password) VALUES(#{username},#{email},#{password})")
    int insert(User user);

    @Update("UPDATE user SET username=#{username}, email=#{email}, password=#{password} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id=#{id}")
    int deleteById(Integer id);
} 