package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "转账通用返回结构")
public class Result<T> {
    @Schema(description = "返回码，0表示成功，可以取Object中的内容，其它失败")
    Integer code;

    @Schema(description = "返回描述，如果失败，失败原因会在此描述")
    String message;

    @Schema(description = "具体返回内容，当返回值为0，且接口有要返回的内容时，在此字段输出")
    T content;
}
