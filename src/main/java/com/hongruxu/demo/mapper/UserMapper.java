package com.hongruxu.demo.mapper;

import com.baomidou.mybatisplus.annotation.TableName;

// import org.apache.ibatis.annotations.Delete;
// import org.apache.ibatis.annotations.Insert;
// import org.apache.ibatis.annotations.Options;
// import org.apache.ibatis.annotations.Param;
// import org.apache.ibatis.annotations.Select;
// import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongruxu.demo.entity.User;
//import java.util.List;


@TableName(value = "user")
public interface UserMapper extends BaseMapper<User> {

    // @Select("SELECT * FROM user WHERE id=#{id}")
    // User getById(@Param("id") Integer id);

    // @Select("SELECT * FROM user")
    // List<User> getAll();

    // @Options(useGeneratedKeys = true, keyProperty = "id")
    // @Insert("INSERT INTO user (user_name, email, age) VALUES(#{user.userName},#{user.email},#{user.age})")
    // int insert(@Param("user") User user);

    // @Update("UPDATE user SET user_name=#{user.userName}, email=#{user.email}, age=#{user.age} WHERE id=#{user.id}")
    // int update(@Param("user") User user);

    // @Delete("DELETE FROM user WHERE id=#{id}")
    // int deleteById(@Param("id") Integer id);
} 