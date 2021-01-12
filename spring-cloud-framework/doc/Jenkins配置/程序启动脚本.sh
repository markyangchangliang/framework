#!/bin/sh
export BUILD_ID=markyangCloud
ps -ef | grep 1.0.0 | awk '{print $2}' | xargs kill -9
rootPath=/var/jenkins_home/workspace/spring-cloud-framework
nohup java -jar "${rootPath}/eureka/target/eureka-1.0.0.jar" > /var/markyang-cloud-logs/eureka.log 2>&1 &
sleep 10
echo "Eureka启动成功"
nohup java -jar "${rootPath}/auth/target/auth-1.0.0.jar" > /var/markyang-cloud-logs/auth.log 2>&1 &
sleep 20
echo "Auth服务启动成功"
nohup java -jar "${rootPath}/gateway/target/gateway-1.0.0.jar" > /var/markyang-cloud-logs/gateway.log 2>&1 &
nohup java -jar "${rootPath}/app/system/target/system-1.0.0.jar" > /var/markyang-cloud-logs/system.log 2>&1 &
echo "Gateway服务启动成功"
echo "System服务启动成功"