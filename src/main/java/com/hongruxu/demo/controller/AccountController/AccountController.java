package com.hongruxu.demo.controller.AccountController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.hongruxu.demo.entity.AccountTransfer;
import com.hongruxu.demo.entity.Result;
import com.hongruxu.demo.entity.TransferFlow;
import com.hongruxu.demo.service.AccountService;
import com.hongruxu.demo.service.TransferType;

import com.hongruxu.demo.entity.Account;

// 此段代码的返回值通过Result结构进行一次包装返回，保证返回结构有一定的规范和通用性。同时有一点点逻辑，进行多表操作

@RestController
@ApiResponse(responseCode = "200", description = "成功")
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Tag(name = "/account/{id}", description = "查询指定账户信息")
    @GetMapping("/account/{id}")
    public Result<Account> getAccount(@PathVariable("id") Integer id) {
        return  accountService.getAccountById(id);
    }

    // 没有考虑分页问题,也没考虑按时间筛选
    @Tag(name = "/account/transfer/from/{id}", description = "查询指定账户转出交易记录")
    @GetMapping("/account/transfer/from/{id}")
    public Result<List<TransferFlow>> getTransferFrom(@PathVariable("id") Integer id) {
        return accountService.getTransferFlow(id, TransferType.OUT);
    }

    @Tag(name = "/account/transfer/to/{id}", description = "查询指定账户转入交易记录")
    @GetMapping("/account/transfer/to/{id}")
    public Result<List<TransferFlow>> getTransferTo(@PathVariable("id") Integer id) {
        return  accountService.getTransferFlow(id, TransferType.IN);
    }

    @Tag(name = "/account/transfer/{id}", description = "查询指定账户转入转出交易记录")
    @GetMapping("/account/transfer/{id}")
    public Result<List<TransferFlow>> getTransfer(@PathVariable("id") Integer id) {
        return  accountService.getTransferFlow(id, TransferType.ALL);
    }

    // 只是简单的实现了一个A到B的转账，仅演示而已
    @Tag(name = "/account/transfer", description = "转账操作")
    @ApiResponse(responseCode = "500", description = "交易失败")
    @PostMapping("/account/transfer")
    public Result<TransferFlow> transfer(@RequestBody AccountTransfer trans) {
        return accountService.transfer(trans.getFromAccount(), trans.getToAccount(),trans.getAmount());
    }

    @Tag(name = "/account", description = "新增一个账号")
    @PostMapping("/account")
    public Result<Account> createAccount(@RequestBody Account account) {
        return accountService.creatAccount(account.getBalance());
        
    }
    
}
