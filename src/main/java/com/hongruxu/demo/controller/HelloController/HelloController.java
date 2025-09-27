package com.hongruxu.demo.controller.HelloController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.hongruxu.demo.persist.Persist;


@RestController
@Tag(name = "hello", description = "一个简单的RESTful接口组示例，分别展示了读取数据，写入数据，删除数据操作，数据通过内存map模拟存储。")
@ApiResponse(responseCode = "200", description = "成功")
@Controller
public class HelloController {

    // 写入一个key value
    @PostMapping("/hello/{key}")
    public String addOrUpdate(@PathVariable String key ,@RequestBody String entity) {
        return Persist.getInstance().put(key, entity);
    }

    // 查出一个key 的value
    @GetMapping("/hello/{key}")
    public String get(@PathVariable String key) {
        return Persist.getInstance().get(key);
    }

    @DeleteMapping("/hello/{key}")
    public String del(@PathVariable String key){
        return Persist.getInstance().del(key);

    }

}
