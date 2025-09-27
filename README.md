# 这是一个简单的基于Spring boot 的 Java Web 项目。

## 功能说明
- 目前通过spring boot + Springdoc + mybatis 实现了一个简单的增删改查操作示例
- [部署体验地址](http://114.132.58.71:9999/swagger-ui/index.html)
- hello 部分的数据操作，后端是一个用map实现的简单内存数据块。服务重启内容丢失
- user 部分为针对一个定义了 id, username, email, passord等字段的一张简单的数据表的相关操作
- account 部分是一个简单模拟两个账户互转的逻辑


## 部署方法
1. Docke暂时没环境，采用机器直接部署的方式，前提条件需要建好数据库表和一台装有JDK的机器
2. 修改代码对应的数据库连接配置 (resources/application.yml)
3. 修改copy_to_test.sh脚本中的机器IP
4. 执行copy_to_test.sh，中间会需要交互输出密码，如在机器装上ssh key，可免密部署

## 说明
- 本代码只为演示基本功能，未考虑安全和一些运营标准。
- 配置中的数据库密码等名文提交也是不符合运营标准的，需要有另外的方法或是相应的平台管理密码
- 代码中的openapi描述写的较少，理论上也是不需要多写，如果都遵循RESTful规范，加上好的命名，描述是可以少写的。
- 示例代码逻辑轻，没有单独做service层
- 异常处理未做标准化处理，后续待完善
- 代码后台数据库为腾讯云上mysql,因一些资源原因，采用外网地址连接，现实不应该这样！！！


## 数据库定义

### account 表
```sql
CREATE TABLE `account` (
  `accountid` int NOT NULL AUTO_INCREMENT,
  `balance` bigint NOT NULL DEFAULT '0',
  `updatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`accountid`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
```

### transfer_flow 表
```sql
CREATE TABLE `transfer_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fromaccount` int NOT NULL DEFAULT '0',
  `toaccount` int NOT NULL DEFAULT '0',
  `amount` int NOT NULL DEFAULT '0',
  `frombalance` int NOT NULL DEFAULT '0',
  `tobalance` int NOT NULL DEFAULT '0',
  `optime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fromaccount` (`fromaccount`,`toaccount`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
```

## user 表
```sql
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
```

## change log 2025-09-26
- 通过 spring boot创建项目
- 引入 springdoc 生成openapi文档
- 引入 mybatis 及 mysql 组件，读取远程数据库
- 目前未对异常做处理，只是简单跑通逻辑
- 有一些问题待查，比如Bean的Getter/Setter方法通过注解方式在IDE内测试时没问题，但打包发布时Schema会为空。

## 2025-09-27
- 实现了一个简单的转账逻辑,为了验证事务，当转账金额为99是会写一半抛异常。验证数据一致性。
- 目前没有相应的环境，写了Dockerfile还没验证，
- 为了方便部署，写了一个脚本简单用scp部署到机器验证，出于安全考虑，脚本是交互式的，需要手工输入密码
- 文档按 hello/ user/ account功能进行了分组展示
- id做了限定必须为数字
- 完善了一些转账逻辑
