package com.hongruxu.demo.controller.AccountController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "account", description = "一个简单的账户操作模拟")
@ApiResponse(responseCode = "200", description = "成功")
@Controller
public class AccountController {
    // 这个版本先简单实现，实际上业务逻辑是需要再实现一个service层
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransferFlowMapper transferFlowMapper;

    // 查询指定账户信息，
    @GetMapping("/account/{id}")
    public Account getAccount(@RequestParam String id) {
        return accountMapper.getById(Integer.parseInt(id));

    }
    // 查询指定账户交易记录
    // TODO:没有考虑分页问题,也没考虑按时间筛选
    @GetMapping("/transfer/{id}")
    public List<TransferFlow> getTransfer(@RequestParam String param) {
        return transferFlowMapper.getByFromAccount(Integer.parseInt(param));
    }

    // 转账操作
    // TODO 事务和案全未考虑全面，只是简单的实现了逻辑，也没有实现 B账户逻辑
    @PostMapping("/transfer")
    @Transactional
    public int transfer(@RequestBody AccountTransfer trans) {

        Account fromAccount = accountMapper.getById(trans.getFromAccount());
        Account toAccount = accountMapper.getById(trans.getToAccount());
        // 先做一个简单判断吧
        if (fromAccount.getBalance() < trans.getAmount()){
            return -1;
        }
        fromAccount.setBalance(fromAccount.getBalance()-trans.getAmount());
        toAccount.setBalance(toAccount.getBalance()+trans.getAmount());
        transferFlowMapper.insert(trans.getFromAccount(),trans.getToAccount(),trans.getAmount(),fromAccount.getBalance(),toAccount.getBalance());
        accountMapper.update(fromAccount);
        // 简单的模拟一下事务异常逻辑，假设转账金额为99，则抛个异常，验证是事务rollback
        if(trans.getAmount() == 99){
            throw new RuntimeException("测试转账事务失败");
        }
        accountMapper.update(toAccount);
        // 返回值这里有待考虑
        return 0;
    } 
}
