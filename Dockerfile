# 使用官方Java运行时环境作为父镜像
FROM openjdk:17-jre-slim
 
# 从jar包位置复制jar包到容器中
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
 
# 设置容器启动时执行的命令
ENTRYPOINT ["java","-jar","/app.jar"]
