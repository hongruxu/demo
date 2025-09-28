#!/bin/bash
#测试机一个简单的重启
PID=$(ps -ef|grep demo-0.0.1-SNAPSHOT.jar|grep -v grep |awk '{print $2}')
kill $PID
nohup /usr/bin/java -jar /home/ubuntu/app/demo-0.0.1-SNAPSHOT.jar >> /home/ubuntu/app/output.log 2>&1 &