package com.hongruxu.demo.controller.AccountController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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
import com.hongruxu.demo.mapper.AccountMapper;
import com.hongruxu.demo.mapper.TransferFlowMapper;
import com.hongruxu.demo.entity.Account;

// 此段代码的返回值通过Result结构进行一次包装返回，保证返回结构有一定的规范和通用性。同时有一点点逻辑，进行多表操作

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
    public Result getAccount(@PathVariable("id") Integer id) {
        Result ret = new Result();
        Account acc = accountMapper.getById(id);
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
        List<TransferFlow> l =  transferFlowMapper.getTransByFromAccount(id);
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
        List<TransferFlow> l =  transferFlowMapper.getTransByToAccount(id);
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
        List<TransferFlow> l =  transferFlowMapper.getTransByAccount(id);
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
    @Transactional(isolation = Isolation.SERIALIZABLE) //涉及到读写事务，这里隔离级别设置为可串行化
    public Result transfer(@RequestBody AccountTransfer trans) {
        Result ret = new Result();
        // 这里对参数做一些简单的判断
        if(trans.getAmount() <=0){
            ret.setCode(-100); // 错误码可再规范
            ret.setMessage("转账金额必须大于0");
            return ret;
        }
        Account fromAccount = accountMapper.getById(trans.getFromAccount());
        if(fromAccount == null){
            ret.setCode(-101); // 错误码可再规范
            ret.setMessage("转出账户不存在");
            return ret;
        }
        if(fromAccount.getBalance() < trans.getAmount()){
            ret.setCode(-102); // 错误码可再规范
            ret.setMessage("转出账户余额不足");
            return ret;
        }
        Account toAccount = accountMapper.getById(trans.getToAccount());
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
        ret.setCode(0); // 错误码可再规范
        ret.setMessage("成功");
        ret.setContent(transFlow);
        return ret;
    } 

    @Tag(name = "create account", description = "新增一个账号")
    @PostMapping("/account")
    public Result createAccount(@RequestBody Account account) {
        Result ret = new Result();
        int count = accountMapper.insert(account);
        if(count == 1) {
            ret.setCode(0);
            ret.setMessage("成功");
            ret.setContent(1);
            return ret;
        }
        ret.setCode(-1);
        ret.setMessage("新增账号失败");
        return ret;

    }
    
}
