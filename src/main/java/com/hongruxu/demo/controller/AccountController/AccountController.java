package com.hongruxu.demo.controller.AccountController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.hongruxu.demo.entity.AccountTransfer;
import com.hongruxu.demo.entity.TransferFlow;
import com.hongruxu.demo.mapper.AccountMapper;
import com.hongruxu.demo.mapper.TransferFlowMapper;
import com.hongruxu.demo.entity.Account;

@RestController
@ApiResponse(responseCode = "200", description = "成功")
@ApiResponse(responseCode = "500", description = "交易失败")
@Controller
public class AccountController {
    // 这个版本先简单实现，实际上业务逻辑是需要再实现一个service层
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransferFlowMapper transferFlowMapper;

    @Tag(name = "get account", description = "查询指定账户信息")
    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable("id") Integer id) {
        return accountMapper.getById(id);
    }

    // 没有考虑分页问题,也没考虑按时间筛选
    @Tag(name = "get transfer", description = "查询指定账户转出交易记录")
    @GetMapping("/transfer/from/{id}")
    public List<TransferFlow> getTransferFrom(@PathVariable("id") Integer id) {
        return transferFlowMapper.getTransByFromAccount(id);
    }

    @Tag(name = "get transfer to ", description = "查询指定账户转入交易记录")
    @GetMapping("/transfer/to/{id}")
    public List<TransferFlow> getTransferTo(@PathVariable("id") Integer id) {
        return transferFlowMapper.getTransByToAccount(id);
    }

    @Tag(name = "get transfer  ", description = "查询指定账户转入转出交易记录")
    @GetMapping("/transfer/{id}")
    public List<TransferFlow> getTransfer(@PathVariable("id") Integer id) {
        return transferFlowMapper.getTransByAccount(id);
    }

    // 事务和安全未考虑全面，只是简单的实现了逻辑，也没有实现 B账户逻辑
    @Tag(name = "transfer", description = "转账操作")
    @PostMapping("/transfer")
    @Transactional
    public String transfer(@RequestBody AccountTransfer trans) {
        // 这里对参数做一些简单的判断
        if(trans.getAmount() <=0){
            return "转账金额必须大于0";
        }
        Account fromAccount = accountMapper.getById(trans.getFromAccount());
        if(fromAccount == null){
            // 返回参数这里只做一个简单的展示
            return "转出账户不存在";
        }
        if(fromAccount.getBalance() < trans.getAmount()){
            return "转出账户余额不足";
        }
        Account toAccount = accountMapper.getById(trans.getToAccount());
        if(toAccount == null){
            return "转入账户不存在";
        }
        if(fromAccount.getAccountId() == toAccount.getAccountId()){
            return "转入转出账户不能为同一账户";
        }

        // 这里逻辑应该做进一步的封装的，这里就简单示例一下了
        fromAccount.setBalance(fromAccount.getBalance()-trans.getAmount());
        toAccount.setBalance(toAccount.getBalance()+trans.getAmount());
        TransferFlow transFlow = new TransferFlow();
        transFlow.setFromAccount(fromAccount.getAccountId());
        transFlow.setToAccount(toAccount.getAccountId());
        transFlow.setFromBalance(fromAccount.getBalance());
        transFlow.setToBalance(toAccount.getBalance());
        transFlow.setAmount(trans.getAmount());

        transferFlowMapper.insert(transFlow);
        accountMapper.update(fromAccount);
        // 简单的模拟一下事务异常逻辑，假设转账金额为99，则抛个异常，验证是事务rollback
        if(trans.getAmount() == 99){
            throw new RuntimeException("测试转账事务失败");
        }
        accountMapper.update(toAccount);
        return "转账成功";
    } 

    @Tag(name = "create account", description = "新增一个账号")
    @PostMapping("/account")
    public Account createAccount(@RequestBody Account account) {
        int ret = accountMapper.insert(account);
        if(ret == 1) {
            return account;
        }
        return null;
  
    }
    
}
