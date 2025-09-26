package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

//@Data 这里生成Getter/Setter方法打包后再跑有问题，待查，先手工生成Setter/Getter
@ToString
@Schema(description = "用户模型")
public class User {
    @Schema(description = "用户ID", example = "1")
    private Integer id; 
    @Schema(description = "用户名",example = "张三")
    private String username;
    @Schema(description = "邮箱",example = "example@qq.com")
    private String email;
    @Schema(description = "密码",example = "abc123")
    private String password;
    
    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

