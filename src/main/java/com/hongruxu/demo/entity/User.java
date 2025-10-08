package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data 
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

}

