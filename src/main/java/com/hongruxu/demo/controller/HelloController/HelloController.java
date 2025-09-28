package com.hongruxu.demo.controller.HelloController;

import java.util.Map;

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

// 一个非常简单的读写操作示例，入参出参都是 String，只展示把流程跑通

@RestController
@ApiResponse(responseCode = "200", description = "成功")
@Controller
public class HelloController {
    
    @Tag(name = "write value", description = "写入一个key value")
    @PostMapping("/hello/{key}")
    public String addOrUpdate(@PathVariable String key ,@RequestBody String entity) {
        return Persist.getInstance().put(key, entity);
    }

    @Tag(name = "query value", description = "查出一个key 的value")
    @GetMapping("/hello/{key}")
    public String get(@PathVariable String key) {
        return Persist.getInstance().get(key);
    }
    
    @Tag(name = "delete value", description = "删除一个key")
    @DeleteMapping("/hello/{key}")
    public String del(@PathVariable String key){
        return Persist.getInstance().del(key);
    }

    @Tag(name = "get all value", description = "删除一个key")
    @GetMapping("/hello")
    public Map<String, String> getAllValue() {
        return Persist.getInstance().getAll();
    }

}
