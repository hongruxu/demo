package com.hongruxu.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "转账流水")
public class TransferFlow {
    @Schema(description = "流水ID",example = "11111")
    Integer id;
    @Schema(description ="转出账号",example = "10000")
    Integer fromAccount;
    @Schema(description = "转入账号",example = "10001")
    Integer toAccount;
    @Schema(description = "转出账号操作后余额",example = "100")
    Integer fromBalance;
     @Schema(description = "转入账号操作后余额",example = "100")
    Integer toBalance;
    @Schema(description = "操作时间",example = "2025-01-01 00:00:00")
    java.util.Date opTime;

    public Integer getId(){
        return id;
    }
    public Integer getFromAccount() {
        return fromAccount;
    }
    public Integer getToAccount() {
        return toAccount;
    }
    public Integer getFromBalance() {
        return fromBalance;
    }
    public Integer getToBalance() {
        return toBalance;
    }
    public java.util.Date getOpTime() {
        return opTime;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public void setFromAccount(Integer fromAccount) {
        this.fromAccount = fromAccount;
    }
    public void setToAccount(Integer toAccount) {
        this.toAccount = toAccount;
    }
    public void setFromBalance(Integer fromBalance) {
        this.fromBalance = fromBalance;
    }
    public void setToBalance(Integer toBalance) {
        this.toBalance = toBalance;
    }
    public void setOpTime(java.util.Date opTime) {
        this.opTime = opTime;
    }

    
}
