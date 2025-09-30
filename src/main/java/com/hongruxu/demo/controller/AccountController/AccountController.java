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

    @Tag(name = "get account", description = "查询指定账户信息")
    @GetMapping("/account/{id}")
    public Result getAccount(@PathVariable("id") Integer id) {
        Result ret = new Result();
        Account acc = accountService.getAccountById(id);
        if(acc== null){
            ret.setCode(404);
            ret.setMessage("内容没找到");
        }else{
            ret.setCode(0);
            ret.setMessage("成功");
            ret.setContent(acc);
        } 
        return ret;
    }

    // 没有考虑分页问题,也没考虑按时间筛选
    @Tag(name = "get transfer", description = "查询指定账户转出交易记录")
    @GetMapping("/transfer/from/{id}")
    public Result getTransferFrom(@PathVariable("id") Integer id) {
        Result ret = new Result();
        List<TransferFlow> l =  accountService.getTransferFlow(id, TransferType.OUT);
        if(l == null){
            ret.setCode(404);
            ret.setMessage("没有可显示的内容");
        }else {
            ret.setCode(0);
            ret.setMessage("成功");
            ret.setContent(l);
        }
        return ret;
    }

    @Tag(name = "get transfer to ", description = "查询指定账户转入交易记录")
    @GetMapping("/transfer/to/{id}")
    public Result getTransferTo(@PathVariable("id") Integer id) {
        Result ret = new Result();
        List<TransferFlow> l =  accountService.getTransferFlow(id, TransferType.IN);
        // 简单的做下返回值封装
        if(l == null){
            ret.setCode(404);
            ret.setMessage("没有可显示的内容");
        }else {
            ret.setCode(0);
            ret.setMessage("成功");
            ret.setContent(l);
        }
        return ret;
    }

    @Tag(name = "get transfer  ", description = "查询指定账户转入转出交易记录")
    @GetMapping("/transfer/{id}")
    public Result getTransfer(@PathVariable("id") Integer id) {
        Result ret = new Result();
        List<TransferFlow> l =  accountService.getTransferFlow(id, TransferType.ALL);
        if(l == null){
            ret.setCode(404);
            ret.setMessage("没有可显示的内容");
        }else {
            ret.setCode(0);
            ret.setMessage("成功");
            ret.setContent(l);
        }
        return ret;
    }

    // 只是简单的实现了一个A到B的转账，仅演示而已
    @Tag(name = "transfer", description = "转账操作")
    @ApiResponse(responseCode = "500", description = "交易失败")
    @PostMapping("/transfer")
    public Result transfer(@RequestBody AccountTransfer trans) {
        return accountService.transfer(trans.getFromAccount(), trans.getToAccount(),trans.getAmount());
    }

    @Tag(name = "create account", description = "新增一个账号")
    @PostMapping("/account")
    public Result createAccount(@RequestBody Account account) {
        return accountService.creatAccount(account.getBalance());
    }
    
}
