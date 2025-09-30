package com.hongruxu.demo.service;

import java.util.List;

import com.hongruxu.demo.entity.Account;
import com.hongruxu.demo.entity.Result;
import com.hongruxu.demo.entity.TransferFlow;

// 账户相关的 Service
public interface AccountService {
    
    public Account getAccountById(Integer accountId);

    public List<TransferFlow> getTransferFlow(Integer accountId, TransferType type);

    public Result transfer(Integer fromAccountId, Integer toAccountId, Integer amount);

    public Result creatAccount(Integer balance);
}
