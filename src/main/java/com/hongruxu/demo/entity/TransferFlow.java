package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "转账流水")
public class TransferFlow {
    @Schema(description = "流水ID",example = "11111")
    Integer id;
    @Schema(description ="转出账号",example = "10000")
    Integer fromAccount;
    @Schema(description = "转入账号",example = "10001")
    Integer toAccount;
    @Schema(description = "转入金我额",example = "100")
    Integer amount;
    @Schema(description = "转出账号操作后余额",example = "100")
    Integer fromBalance;
     @Schema(description = "转入账号操作后余额",example = "100")
    Integer toBalance;
    @Schema(description = "操作时间",example = "2025-01-01 00:00:00")
    String opTime;
    
}
