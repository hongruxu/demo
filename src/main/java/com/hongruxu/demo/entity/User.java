package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data 
@ToString
@Schema(description = "用户模型")
public class User {
    @Schema(description = "用户ID", example = "1")
    private Integer id; 
    @Schema(description = "用户名",example = "张三")
    private String userName;
    @Schema(description = "邮箱",example = "example@qq.com")
    private String email;
    @Schema(description = "年龄",example = "20")
    private Integer age;

    // public Integer getId() {
    //     return id;
    // }
    // public String getUserName() {
    //     return userName;
    // }
    // public String getEmail() {
    //     return email;
    // }
    // public Integer getAge() {
    //     return age;
    // }
    // public void setId(Integer id) {
    //     this.id = id;
    // }
    // public void setUserName(String userName) {
    //     this.userName = userName;
    // }
    // public void setEmail(String email) {
    //     this.email = email;
    // }
    // public void setAge(Integer age) {
    //     this.age = age;
    // }
}

