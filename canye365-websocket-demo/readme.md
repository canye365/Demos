# 测试 WebSocket

## 测试API

> 需要注意，修改spring-boot-starter-websocket依赖
>
>添加 `<scope>provided</scope>`
>
>确保运行时和tomcat不会出现冲突

### 1. tomcat 插件启动

### 2. chrome插件测试

[Simple WebSocket Client](https://chrome.google.com/webstore/detail/simple-websocket-client/pfdhoblngboilpfeibdedpjgfnlcodoo/related?utm_source=chrome-ntp-icon)

![](https://image.canye365.cn/img/20200528072241.png)

### 3. js-websocket 客户端测试

index.html

![](https://image.canye365.cn/img/20200528073024.png)

## 测试 集成spring-boot

>springboot自身具备tomcat插件，所以注释掉tomcat依赖

![](https://image.canye365.cn/img/20200528084943.png)
