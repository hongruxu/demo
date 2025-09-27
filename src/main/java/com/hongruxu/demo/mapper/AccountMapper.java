package com.hongruxu.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hongruxu.demo.entity.Account;
import java.util.List;


@Mapper
public interface AccountMapper {

    @Select("select * from account where accountid=#{accountId}")
    Account getById(@Param("accountId") Integer accountId);

    @Select("select * from account")
    List<Account> getAll();

    // 新建账号
    @Insert("INSERT INTO account (balance) VALUES(#{balance}")
    int insert(Account account);

    @Update("UPDATE account SET balance=#{balance} WHERE accountid=#{accountId}")
    int update(Account account);

} 