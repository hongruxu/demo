package com.hongruxu.demo.controller.UserController;

import java.util.stream.Gatherer.Integrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hongruxu.demo.mapper.UserMapper;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.hongruxu.demo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Tag(name = "user", description = "一个简单的数据库读写操作")
@ApiResponse(responseCode = "200", description = "成功")
@Controller
public class UserController {
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        try{
            User user = userMapper.getById(Integer.parseInt(id));
            return user;

        }catch(Exception e){
            return new User(); 
        }
    }

    @PostMapping("/user")
    public int addUser(@RequestBody User entity) {
        return userMapper.insert(entity);
    }

    @DeleteMapping("/user/{id}")
    public int delUser(@PathVariable String id) {
        try{
            return userMapper.deleteById(Integer.parseInt(id));

        }catch(Exception e){
            return -1; 
        }
    }
    
}
