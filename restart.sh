#!/bin/bash
#测试机一个简单的重启
PID=$(ps -ef|grep demo-0.0.1-SNAPSHOT.jar|grep -v grep |awk '{print $2}')
kill $PID
/usr/bin/java -jar /home/ubuntu/app/demo-0.0.1-SNAPSHOT.jar &