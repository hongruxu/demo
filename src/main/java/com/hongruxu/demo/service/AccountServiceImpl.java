package com.hongruxu.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.hongruxu.demo.entity.Account;
import com.hongruxu.demo.entity.Result;
import com.hongruxu.demo.entity.TransferFlow;
import com.hongruxu.demo.mapper.AccountMapper;
import com.hongruxu.demo.mapper.TransferFlowMapper;



@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    private TransferFlowMapper transferFlowMapper;

    @Override
    public Account getAccountById(Integer accountId){
        return accountMapper.getById(accountId);
    }

    @Override
    public List<TransferFlow> getTransferFlow(Integer accountId, TransferType type){
        switch (type) {
            case ALL:
                return transferFlowMapper.getTransByAccount(accountId);
            case IN:
                return transferFlowMapper.getTransByFromAccount(accountId);
            case OUT:
                return transferFlowMapper.getTransByToAccount(accountId);
        }
        return null;
    }

    @Override
    @Transactional(isolation =Isolation.SERIALIZABLE) // 防止幻读
    public Result transfer(Integer fromAccountId, Integer toAccountId, Integer amount){

         Result ret = new Result();
        // 这里对参数做一些简单的判断
        if(amount <=0){
            ret.setCode(-100); // 错误码可再规范
            ret.setMessage("转账金额必须大于0");
            return ret;
        }
        Account fromAccount = accountMapper.getById(fromAccountId);
        if(fromAccount == null){
            ret.setCode(-101); // 错误码可再规范
            ret.setMessage("转出账户不存在");
            return ret;
        }
        if(fromAccount.getBalance() < amount){
            ret.setCode(-102); // 错误码可再规范
            ret.setMessage("转出账户余额不足");
            return ret;
        }
        Account toAccount = accountMapper.getById(toAccountId);
        if(toAccount == null){
            ret.setCode(-103); // 错误码可再规范
            ret.setMessage("转入账户不存在");
            return ret;
        }
        if(fromAccount.getAccountId() == toAccount.getAccountId()){
            ret.setCode(-104); // 错误码可再规范
            ret.setMessage("转入转出账户不能为同一账户");
            return ret;
        }

        // 这里逻辑应该做进一步的封装的，这里就简单示例一下了
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);
        TransferFlow transFlow = new TransferFlow();
        transFlow.setFromAccount(fromAccount.getAccountId());
        transFlow.setToAccount(toAccount.getAccountId());
        transFlow.setFromBalance(fromAccount.getBalance());
        transFlow.setToBalance(toAccount.getBalance());
        transFlow.setAmount(amount);

        transferFlowMapper.insert(transFlow);
        // 需要取一次操作时间
        transFlow = transferFlowMapper.getTransferFlowById(transFlow.getId());
        accountMapper.update(fromAccount);
        // 简单的模拟一下事务异常逻辑，假设转账金额为99，则抛个异常，验证是事务rollback
        if(amount == 99){
            throw new RuntimeException("测试转账事务失败");
        }
        accountMapper.update(toAccount);
        ret.setCode(0); // 错误码可再规范
        ret.setMessage("成功");
        ret.setContent(transFlow);
        return ret;
    }

    @Override
    public Result creatAccount(Integer balance){
        Result ret = new Result();
        Account account = new Account();
        account.setBalance(balance);
        int count = accountMapper.insert(account);
        if(count == 1) {
            ret.setCode(0);
            ret.setMessage("成功");
            ret.setContent(account);
            return ret;
        }
        ret.setCode(-1);
        ret.setMessage("新增账号失败");
        return ret;
    }

}
