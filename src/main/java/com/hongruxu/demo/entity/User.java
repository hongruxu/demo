package com.hongruxu.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data 
@ToString
@Schema(description = "用户模型")
@TableName(value = "user")
public class User {
    @Schema(description = "用户ID", example = "1")
    private Long id; 
    @Schema(description = "用户名",example = "张三")
    private String userName;
    @Schema(description = "邮箱",example = "example@qq.com")
    private String email;
    @Schema(description = "年龄",example = "20")
    private Integer age;

}

