FROM centos:7

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN yum install -y wget vim git

# 配置环境变量
ENV rootDir=/opt
ENV JAVA_HOME=$rootDir/java
ENV M2_HOME=$rootDir/maven
ENV PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin

# workdir
WORKDIR $rootDir

RUN mkdir -p $JAVA_HOME \
    mkdir -p $M2_HOME


# 安装 wget vim git
RUN    yum install -y wget \
    && yum install -y vim \
    && yum install -y git 

# 安装 jdk8 
RUN    wget --no-cookies --no-check-certificate --header \
             "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" \
             "http://download.oracle.com/otn-pub/java/jdk/8u141-b15/336fa29ff2bb4ef291e347e091f7f4a7/jdk-8u141-linux-x64.tar.gz" \
    && tar -zxf jdk-8u141-linux-x64.tar.gz -C $JAVA_HOME --strip-components 1 \
    && rm -f jdk-8u141-linux-x64.tar.gz

# 安装 maven
RUN    wget http://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz \
    && tar -zxf apache-maven-3.5.4-bin.tar.gz -C $M2_HOME --strip-components 1 \
    && rm -f apache-maven-3.5.4-bin.tar.gz

CMD /bin/bash
