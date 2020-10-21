启动测试的 docker 容器
预装了 java, maven, git等常用java 环境

构建镜像
docker build -t test_image:v1 .

启动容器
docker run -itd test_image:v1 /bin/bash

进入容器
docker exec -it ${容器id} bash

