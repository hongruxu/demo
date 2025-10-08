package com.hongruxu.demo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.hongruxu.demo.controller.AccountController.AccountController;
import com.hongruxu.demo.controller.HelloController.HelloController;
import com.hongruxu.demo.controller.UserController.UserController;
import com.hongruxu.demo.entity.Account;
import com.hongruxu.demo.entity.AccountTransfer;
import com.hongruxu.demo.entity.Result;
import com.hongruxu.demo.entity.TransferFlow;
import com.hongruxu.demo.entity.User;
import com.hongruxu.demo.mapper.UserMapper;
import com.hongruxu.demo.service.AccountService;
import com.hongruxu.demo.service.TransferType;



@SpringBootTest
@Transactional // 测试方法在事务中执行，测试后数据自动回滚，如果没有配test配置文件也不会影响到正式DB内容
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class DemoApplicationTests {

	@Autowired
	private HelloController helloController;

	@Autowired
	private AccountController accountController;

	@Autowired
    private UserController userController;

    
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
        userController.addUser(newUser);

        // 执行操作 
        User foundUser = userController.getUser(newUser.getId());
        
		// 验证结果
        assertNotNull(foundUser);
        assertEquals(newUser.getUserName(), foundUser.getUserName());
        assertEquals(newUser.getEmail(), foundUser.getEmail());
        assertEquals(newUser.getAge(), foundUser.getAge());

    }
	// 查找全部数据
	@Test
	void testFindAllUser(){
		// 准备数据
        User newUser = createTestUser();
        userController.addUser(newUser);

		// When
		List<User> users = userController.getUser();

		// Then
		assertNotNull(users);
		assertTrue(users.size() >= 2); // 初始化加了一条数据，此测试再加一条数据，一定是大于等于2条数据才对
	}
	@Test
	void testUpdateUser(){
		// 准备数据
        User newUser = createTestUser();
        userController.addUser(newUser);
		newUser.setAge(25);
		// When
		User newUser2 = userController.putUser(newUser.getId(), newUser);
		// Then
		assertEquals(newUser2.getUserName(), newUser.getUserName());
		assertEquals(newUser2.getAge(), newUser.getAge());
		assertEquals(newUser2.getEmail(), newUser.getEmail());

	}

	@Test
	void testDelUser(){
		// 准备数据
        User newUser = userController.addUser(createTestUser());
		// When
		int ret = userController.delUser(newUser.getId());
		// Then
		assertEquals(ret, 1);
	}

	// 模拟转账模块测试===============================
	@Test
	void TransferAccount(){
		Account a1 = new Account();
		a1.setBalance(100);
		Account a2 = new Account();
		a2.setBalance(50);
		// 创建账号测试
		Result<Account>  retFrom = accountController.createAccount(a1);
		Result<Account>  retTo = accountController.createAccount(a2);

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

		// 查询余额
		Result<Account> acc = accountController.getAccount(accountFrom.getAccountId());
		assertNotNull(acc);
		assertEquals(acc.getCode(), 0);
		assertEquals(acc.getContent().getBalance(), 100);


		AccountTransfer accTrans = new AccountTransfer();
		// 转账逻辑验证
		// 转账金额必须大于0测试
		accTrans.setFromAccount(accountFrom.getAccountId());
		accTrans.setToAccount(accountTo.getAccountId());
		accTrans.setAmount(-20);
		Result<TransferFlow> flow1 = accountController.transfer(accTrans);		
		assertEquals(flow1.getCode(), -100);

		// 转出账户不存在测试
		accTrans.setAmount(50);
		accTrans.setFromAccount(9999);
		Result<TransferFlow> flow2 = accountController.transfer(accTrans);		
		assertEquals(flow2.getCode(), -101);
		
		// 转账余额不足测试
		accTrans.setFromAccount(accountFrom.getAccountId());
		accTrans.setToAccount(accountTo.getAccountId());
		accTrans.setAmount(200);
		Result<TransferFlow> flow3 = accountController.transfer(accTrans);		
		assertEquals(flow3.getCode(), -102);
		
		// 转入账户不存在测试
		accTrans.setFromAccount(accountFrom.getAccountId());
		accTrans.setToAccount(9999);
		accTrans.setAmount(50);
		Result<TransferFlow> flow4 = accountController.transfer(accTrans);		
		assertEquals(flow4.getCode(), -103);


		// 转账正常测试
		accTrans.setFromAccount(accountFrom.getAccountId());
		accTrans.setToAccount(accountTo.getAccountId());
		accTrans.setAmount(30);
		Result<TransferFlow> flow = accountController.transfer(accTrans);
		
		assertEquals(flow.getCode(), 0);
		assertTrue(flow.getContent().getAmount() == 30);
		assertTrue(flow.getContent().getFromBalance() == 70);
		assertTrue(flow.getContent().getToBalance() == 80);

		// 转账正常反向测试
		accTrans.setFromAccount(accountTo.getAccountId());
		accTrans.setToAccount(accountFrom.getAccountId());
		accTrans.setAmount(10);
		Result<TransferFlow> flowR = accountController.transfer(accTrans);
		assertEquals(flowR.getCode(), 0);
		assertTrue(flowR.getContent().getAmount() == 10);
		assertTrue(flowR.getContent().getFromBalance() == 70);
		assertTrue(flowR.getContent().getToBalance() == 80);

		// 查询历史交易(全部)
		Result<List<TransferFlow>> trans = accountController.getTransferFrom(accountFrom.getAccountId());
		assertNotNull(trans);
		assertEquals(trans.getCode(), 0);
		assertTrue(trans.getContent().size() >=1);

		// 查询历史交易(入账)
		Result<List<TransferFlow>> trans2 = accountController.getTransferTo(accountTo.getAccountId());
		assertNotNull(trans2);
		assertEquals(trans2.getCode(), 0);
		assertTrue(trans2.getContent().size() >=1);
		// 查询历史交易(出账)
		Result<List<TransferFlow>> trans3 = accountController.getTransfer(accountFrom.getAccountId());
		assertNotNull(trans3);
		assertEquals(trans3.getCode(), 0);
		assertTrue(trans3.getContent().size() >=1);
	}

	@Test
	void transException(){
		Account a1 = new Account();
		a1.setBalance(100);
		Account a2 = new Account();
		a2.setBalance(50);
		// 创建账号测试
		Result<Account>  retFrom = accountController.createAccount(a1);
		Result<Account>  retTo = accountController.createAccount(a2);

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

		AccountTransfer accTrans = new AccountTransfer();
		// 转入转出账户不能为同一账户测试
		accTrans.setFromAccount(accountFrom.getAccountId());
		accTrans.setToAccount(accountFrom.getAccountId());
		accTrans.setAmount(50);
		Result<TransferFlow> flow5 = accountController.transfer(accTrans);		
		assertEquals(flow5.getCode(), -104);

		// 转账异常测试
		accTrans.setFromAccount(accountFrom.getAccountId());
		accTrans.setToAccount(accountTo.getAccountId());
		accTrans.setAmount(99);
		try{
			accountController.transfer(accTrans);
		}catch(Exception e){
			assertThrows(java.lang.RuntimeException.class, null);
		}
	}

}
