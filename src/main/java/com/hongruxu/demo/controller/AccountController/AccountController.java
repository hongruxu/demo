package com.hongruxu.demo.controller.AccountController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
@Controller
public class AccountController {
    // 这个版本先简单实现，实际上业务逻辑是需要再实现一个service层
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransferFlowMapper transferFlowMapper;

    @Tag(name = "get account", description = "查询指定账户信息")
    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Integer id) {
        return accountMapper.getById(id);

    }

    // TODO:没有考虑分页问题,也没考虑按时间筛选
    @Tag(name = "get transfer", description = "查询指定账户交易记录")
    @GetMapping("/transfer/{id}")
    public List<TransferFlow> getTransfer(@PathVariable Integer id) {
        return transferFlowMapper.getByFromAccount(id);
    }

    // TODO 事务和安全未考虑全面，只是简单的实现了逻辑，也没有实现 B账户逻辑
    @Tag(name = "transfer", description = "转账操作")
    @PostMapping("/transfer")
    @Transactional
    public int transfer(@RequestBody AccountTransfer trans) {

        Account fromAccount = accountMapper.getById(trans.getFromAccount());
        Account toAccount = accountMapper.getById(trans.getToAccount());
        // 先做一个简单判断吧
        if (fromAccount.getBalance() < trans.getAmount()){
            return -1;
        }
        // 这里逻辑应该做进一步的封装的，这里就简单示例一下了
        fromAccount.setBalance(fromAccount.getBalance()-trans.getAmount());
        toAccount.setBalance(toAccount.getBalance()+trans.getAmount());
        transferFlowMapper.insert(trans.getFromAccount(),trans.getToAccount(),trans.getAmount(),fromAccount.getBalance(),toAccount.getBalance());
        accountMapper.update(fromAccount);
        // 简单的模拟一下事务异常逻辑，假设转账金额为99，则抛个异常，验证是事务rollback
        if(trans.getAmount() == 99){
            throw new RuntimeException("测试转账事务失败");
        }
        accountMapper.update(toAccount);
        // 返回值暂用数字0表示成功，其它表示失败
        return 0;
    } 

    @Tag(name = "create account", description = "新增一个账号")
    @PostMapping("/account")
    public int createAccount(@RequestBody Account account) {
        return accountMapper.insert(account);
    }
    
}
