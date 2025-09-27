package com.hongruxu.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hongruxu.demo.entity.TransferFlow;

public interface TransferFlowMapper {

    @Select("SELECT * FROM transfer_flow WHERE from_account=#{fromAccount}")
    List<TransferFlow> getTransByFromAccount(@Param("fromAccount") Integer fromAccount);

    @Select("SELECT * FROM transfer_flow WHERE to_account=#{toAccount}")
    List<TransferFlow> getTransByToAccount(@Param("toAccount") Integer toAccount);

    @Select("SELECT * FROM transfer_flow WHERE from_account = #{account} OR to_account=#{account}")
    List<TransferFlow> getTransByAccount(@Param("account") Integer account);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO transfer_flow (from_account,to_account,amount,from_balance,to_balance)"+
        " values (#{transferFlow.fromAccount},#{transferFlow.toAccount},#{transferFlow.amount},#{transferFlow.fromBalance},#{transferFlow.toBalance})")
    int insert(@Param("transferFlow") TransferFlow transferFlow);

} 