package com.hongruxu.demo.controller.UserController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.hongruxu.demo.mapper.UserMapper;
import com.hongruxu.demo.entity.User;

@RestController
@ApiResponse(responseCode = "200", description = "成功")
@Controller
public class UserController {
    
    @Autowired
    private UserMapper userMapper;

    @Tag(name = "get user", description = "获取用户信息")
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userMapper.getById(id);
    }

    @Tag(name = "get all user", description = "获取全量用户信息")
    @GetMapping("/user")
    public List<User> getUser() {
        return userMapper.getAll();
    }

    @Tag(name = "create user", description = "创建一个用户")
    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        int ret =  userMapper.insert(user);
        if(ret == 1){
            return user;
        }
        return null;
    }

    @Tag(name = "del user", description = "删除一个用户")
    @DeleteMapping("/user/{id}")
    public int delUser(@PathVariable Integer id) {
        return userMapper.deleteById(id);
    }

    @Tag(name = "update user", description = "修改一个用户")
    @PutMapping("user/{id}")
    public User putUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        int ret = userMapper.update(user);
        if(ret == 1){
            return user;
        }
        return null;
    }
}
