package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "账号信息")
public class Account {
    @Schema(description = "账号ID",example = "10000")
    Integer accountId;
    @Schema(description = "余额",example = "500")
    Integer balance;
    @Schema(description = "最近更新时间",example = "2025-01-01 00:00:00") 
    java.util.Date updateTime;

}
