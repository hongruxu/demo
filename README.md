# 这是一个简单的基于Spring boot 的 Java Web 项目。

## 功能说明
- 目前通过spring boot + Springdoc + mybatis 实现了一个简单的增删改查操作示例
- [部署体验地址](http://114.132.58.71:9999/swagger-ui/index.html)
- hello 部分的数据操作，后端是一个用map实现的简单内存数据块。服务重启内容丢失
- user 部分为针对一个定义了 id, username, email, passord等字段的一张简单的数据表的相关操作
- 代码后台数据库为腾讯云上mysql,目前为方便调试，采用外网地址连接，现实不应该这样！！！
- 代码中的openapi描述写的较少，理论上也是不需要多写，如果都遵循RESTful规范，加上好的命名，描述是可以少写的。
- 示例代码逻辑轻，没有单独做service层

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
- 有空尝试验证采用skywalking做一个简单监控

