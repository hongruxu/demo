package com.hongruxu.demo.service;

import java.util.List;

import com.hongruxu.demo.entity.Account;
import com.hongruxu.demo.entity.Result;
import com.hongruxu.demo.entity.TransferFlow;

// 账户相关的 Service
public interface AccountService {
    
    public Result<Account> getAccountById(Integer accountId);

    public Result<List<TransferFlow>>getTransferFlow(Integer accountId, TransferType type);

    public Result<TransferFlow> transfer(Integer fromAccountId, Integer toAccountId, Integer amount);

    public Result<Account> creatAccount(Integer balance);
}
