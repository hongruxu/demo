#!/bin/bash

# 生成 jar包
./mvnw clean package

# 上传到服务器
scp ./target/demo-0.0.1-SNAPSHOT.jar ubuntu@114.132.58.71:/home/ubuntu/app/demo-0.0.1-SNAPSHOT.jar

# 重启服务

ssh ubuntu@114.132.58.71 '/bin/bash /home/ubuntu/app/restart.sh'
