package com.hongruxu.demo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.hongruxu.demo.controller.HelloController.HelloController;
import com.hongruxu.demo.entity.Account;
import com.hongruxu.demo.entity.Result;
import com.hongruxu.demo.entity.TransferFlow;
import com.hongruxu.demo.entity.User;
import com.hongruxu.demo.mapper.UserMapper;
import com.hongruxu.demo.service.AccountService;



@SpringBootTest
@Transactional // 测试方法在事务中执行，测试后数据自动回滚，如果没有配test配置文件也不会影响到正式DB内容
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class DemoApplicationTests {

	@Autowired
	private HelloController helloController;

	@Autowired
    private UserMapper userMapper;

	@Autowired
	private AccountService accountService;
    
	// Hello 模块测试 ==================================
	// 注意Hello模块底层用到了ConcurrentHashMap，在put,remove操作时返回的都是上一次的历史值。
	// 而Hello模块是简单的将map操作的结果返回，因此需要注意测试返回值判定
	@Test
	void crudHello(){
		// 写入一条数据
		String addResult = helloController.addOrUpdate("test_one", "hello world!");
		assertEquals(addResult, null); 
		
		// 查询一条数据
		String getResutl = helloController.get("test_one");
		assertEquals(getResutl, "hello world!");

		// 修改一条数据
		String updateResutl = helloController.addOrUpdate("test_one", "hello world 2 !");
		assertEquals(updateResutl, "hello world!");
		
		// 查询修改后的数据
		String getResutl2 = helloController.get("test_one");
		assertEquals(getResutl2, "hello world 2 !");

		// 删除数据
		String delResult = helloController.del("test_one");
		assertEquals(delResult, "hello world 2 !");

		// 查询删除后的数据
		String getResutl3 = helloController.get("test_one");
		assertEquals(getResutl3, null);

	}
	// User 模块测试=======================================================
    // 测试数据
    private User createTestUser() {
        User u =  new User();
        u.setId(1);
        u.setUserName("test_name_2");
        u.setAge(18);
        u.setEmail("test@qq.com");
        return u;
    }
	// 数据按ID查找
	@Test
    void testGetUserById(){
        // 准备数据
        User newUser = createTestUser();
        userMapper.insert(newUser);

        // 执行操作 
        User foundUser = userMapper.getById(newUser.getId());
        
		// 验证结果
        assertNotNull(foundUser);
        assertEquals(newUser.getUserName(), foundUser.getUserName());
        assertEquals(newUser.getEmail(), foundUser.getEmail());
        assertEquals(newUser.getAge(), foundUser.getAge());

    }
	// 查找全部数据
	@Test
	void testFindAll(){
		// 准备数据
        User newUser = createTestUser();
        userMapper.insert(newUser);

		// When
		List<User> users = userMapper.getAll();

		// Then
		assertNotNull(users);
		assertTrue(users.size() >= 2); // 初始化加了一条数据，此测试再加一条数据，一定是大于等于2条数据才对
	}

	// 模拟转账模块测试===============================
	@Test
	void TransferAccount(){

		// 创建账号测试
		Result<Account>  retFrom = accountService.creatAccount(100);
		Result<Account>  retTo = accountService.creatAccount(50);

		// 验证账Result
		assertNotNull(retFrom);
		assertNotNull(retTo);
		assertEquals(retFrom.getCode(), 0);
		assertEquals(retTo.getCode(), 0);

		// 拿到创建好的账号
		Account accountFrom = retFrom.getContent();
		Account accountTo = retTo.getContent();

		// 验证账号是否为空
		assertNotNull(accountFrom);
		assertNotNull(accountTo);
		// 验证账号余额和账号是否符合预期
		assertTrue(accountFrom.getAccountId() > 0); // 创建成功的账号一定是大于0的值
		assertTrue(accountFrom.getBalance() == 100);
		assertTrue(accountTo.getAccountId() > 0);
		assertTrue(accountTo.getBalance() == 50);

		// 转账逻辑验证
		// 转账金额必须大于0测试
		Result<TransferFlow> flow1 = accountService.transfer(accountFrom.getAccountId(),accountTo.getAccountId(),-20);		
		assertEquals(flow1.getCode(), -100);

		// 转出账户不存在测试
		Result<TransferFlow> flow2 = accountService.transfer(9999,accountTo.getAccountId(),50);		
		assertEquals(flow2.getCode(), -101);
		
		// 转账余额不足测试
		Result<TransferFlow> flow3 = accountService.transfer(accountFrom.getAccountId(),accountTo.getAccountId(),200);		
		assertEquals(flow3.getCode(), -102);
		
		// 转入账户不存在测试
		Result<TransferFlow> flow4 = accountService.transfer(accountFrom.getAccountId(),9999,50);		
		assertEquals(flow4.getCode(), -103);

		// 转入转出账户不能为同一账户测试
		Result<TransferFlow> flow5 = accountService.transfer(accountFrom.getAccountId(),accountFrom.getAccountId(),50);		
		assertEquals(flow5.getCode(), -104);

		// 转账测试正常测试
		Result<TransferFlow> flow = accountService.transfer(accountFrom.getAccountId(),accountTo.getAccountId(),30);
		
		assertEquals(flow.getCode(), 0);
		assertTrue(flow.getContent().getAmount() == 30);
		assertTrue(flow.getContent().getFromBalance() == 70);
		assertTrue(flow.getContent().getToBalance() == 80);

	}

}
