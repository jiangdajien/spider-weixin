### Linux下安装JDK8

* 下载
在oracle.com的官网上[下载最新的JDK版本](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)，请选择xxx.tar.gz的安装包。
* 上传
使用rz命令上传安装包到Linux系统
上传目录为 /export/apps/  ，没有这个目录就新建。
* 解析
使用tar -zxvf xxx.tar.gz 命令解压安装包
使用mv 命令，将安装包的名称重命名为/export/apps/jdk
* 配置环境变量
编辑 /etc/profile 文件，按G跳到文件尾部，添加下列内容。
```shell
#set java env
export JAVA_HOME=/export/apps/jdk
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```
* 使环境变量生效
source /etc/profile
* 验证
输入 java -version ，如果有返回值就算成功了。
