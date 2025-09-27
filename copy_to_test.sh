#!/bin/bash

# 一个简单的部署脚本，将代码部署到机器并重启
# 生成 jar包
./mvnw clean package

# 上传到服务器
cp ./restart.sh ./target/
scp ./target/{demo-0.0.1-SNAPSHOT.jar,restart.sh} ubuntu@114.132.58.71:/home/ubuntu/app/

# 重启服务
ssh ubuntu@114.132.58.71 '/bin/bash /home/ubuntu/app/restart.sh'
