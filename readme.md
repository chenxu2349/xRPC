# xRPC: 动手设计实现一个RPC框架

项目启运行：启动Provider类进行监听，运行Consumer的main方法完成一次rpc调用

为了避免硬编码主机IP地址和端口，采用注册中心去解耦合

![img.png](img.png)
注册中心需要实现的：多设备间共享数据，服务挂掉能检测到（心跳机制），数据变更能及时同步（一致性），提升服务查找速度（本地缓存）

Mock:
![img_1.png](img_1.png)
mock后面不要有空格，否则报错
![img_2.png](img_2.png)
