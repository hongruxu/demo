package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "转账结构")
public class AccountTransfer {
    @Schema(description = "来源账号",example = "10000")
    Integer fromAccount;
    @Schema(description = "目标账号",example = "10001")
    Integer toAccount;
    @Schema(description = "转账金额,单位分",example = "100")
    Integer amount;
    public Integer getFromAccount() {
        return fromAccount;
    }
    public Integer getToAccount() {
        return toAccount;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setFromAccount(Integer fromAccount) {
        this.fromAccount = fromAccount;
    }
    public void setToAccount(Integer toAccount) {
        this.toAccount = toAccount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
}
