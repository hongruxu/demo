package com.hongruxu.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hongruxu.demo.entity.Account;



public interface AccountMapper {

    @Select("SELECT * FROM account WHERE account_id=#{accountId}")
    Account getById(@Param("accountId") Integer accountId);

    @Select("SELECT * FROM account")
    List<Account> getAll();

    // 新建账号
    @Options(useGeneratedKeys = true,keyProperty="accountId")
    @Insert("INSERT INTO account (balance) VALUES(#{balance})")
    int insert(Account account);

    @Update("UPDATE account SET balance=#{balance} WHERE account_id=#{accountId}")
    int update(Account account);

} 