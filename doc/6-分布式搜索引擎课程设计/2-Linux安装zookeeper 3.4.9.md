# Linux安装zookeeper3.4.9
* [下载zookeeper](http://zookeeper.apache.org)
* 上传到node2,node3,node4
* 依次解压
```shell
tar -zxvf zookeeper-3.4.9.tar.gz -C /export/servers/
cd /export/servers/
ln -s zookeeper-3.4.9 zk
```
* 依次在node2,node3,node4上修改配置文件
```shell
mkdir -p /export/data/zk
cd /export/servers/zk/conf
cp zoo_sample.cfg  zoo.cfg
vi zoo.cfg
修改1：
dataDir=/export/data/zk
新增1：
server.1=node2:2887:3887
server.2=node3:2887:3887
server.3=node4:2887:3887
```
* 依次在每台机器上创建myid文件
```shell
在node2上 touch /export/data/zk/myid 输入内容1
在node3上 touch /export/data/zk/myid 输入内容2
在node4上 touch /export/data/zk/myid 输入内容3
```
*配置环境变量
```shell
vi /etc/profile
export ZK_HOME=/export/servers/zk
export PATH=${ZK_HOME}/bin:$PATH

source /etc/profile
```
* 依次在node2，node3,node4上启动集群
```shell
zkServer.sh start
```