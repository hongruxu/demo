package com.hongruxu.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hongruxu.demo.entity.TransferFlow;



@Mapper
public interface TransferFlowMapper {

    @Select("select * from transfer_flow where fromaccount=#{fromaccount}")
    List<TransferFlow> getByFromAccount(@Param("fromaccount") Integer fromaccount);

    @Insert("INSERT INTO transfer_flow (fromaccount,toaccount,amount,frombalance,tobalance) values (#{fromAccount},#{toAccount},#{amount},#{fromBalance},#{toBalance})")
    int insert(Integer fromAccount,Integer toAccount, Integer amount, Integer fromBalance, Integer toBalance);

} 