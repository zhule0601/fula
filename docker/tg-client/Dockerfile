FROM centos:7

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV BASE_PATH="/opt"
ENV TG_PATH=$BASE_PATH/tg_client
ENV PATH=$PATH:$BASE_PATH

RUN yum install -y gcc make git readline-devel libconfig-devel jansson-devel openssl-devel libevent-devel lua-devel

RUN git clone --recursive http://github.com/vysheng/tg.git $TG_PATH

WORKDIR $TG_PATH

RUN ./configure
RUN make

RUN ln -snf $TG_PATH/bin/telegram-cli $BASE_PATH/telegram-cli
COPY tg.sh $BASE_PATH/tg
RUN chmod 755 $BASE_PATH/tg

WORKDIR $BASE_PATH

CMD /bin/bash
