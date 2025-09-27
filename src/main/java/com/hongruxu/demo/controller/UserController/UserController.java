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
@Tag(name = "user", description = "一个简单的数据库读写操作")
@ApiResponse(responseCode = "200", description = "成功")
@Controller
public class UserController {
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userMapper.getById(id);
    }

    @GetMapping("/users")
    public List<User> getUser() {
        return userMapper.getAll();
    }

    @PostMapping("/user")
    public int addUser(@RequestBody User entity) {
        return userMapper.insert(entity);
    }

    @DeleteMapping("/user/{id}")
    public int delUser(@PathVariable Integer id) {
        return userMapper.deleteById(id);
    }
    @PutMapping("user/{id}")
    public int putUser(@PathVariable Integer id, @RequestBody User entity) {
        entity.setId(id);
        return  userMapper.update(entity);
    }
}
