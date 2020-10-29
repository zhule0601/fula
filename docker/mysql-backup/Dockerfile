FROM mysql:5.6


ENV BASE_PATH="/opt"
ENV TZ=Asia/Shanghai
ENV backup_path=$BASE_PATH/"backup"
ENV PATH=$PATH:$BASE_PATH

WORKDIR $BASE_PATH

COPY mysql-backup.sh $BASE_PATH/mysql-backup
COPY mysql-restore.sh $BASE_PATH/mysql-restore

RUN chmod 755 $BASE_PATH/mysql-backup
RUN chmod 755 $BASE_PATH/mysql-restore

RUN mkdir -p $backup_path

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# CMD ["/bin/bash","monitor.sh"]
