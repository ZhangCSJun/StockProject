#基础镜像
# create my tomcat Dockerfile
# version 1.0

# Base images
FROM mytomcat:1.0

# MAINTAINER
LABEL maintainer="Zhang Jun"

# TOMCAT CONTAINER'S ENV SETTING
ENV CATALINA_BASE:   /usr/local/tomcat \
    CATALINA_HOME:   /usr/local/tomcat \
    CATALINA_TMPDIR: /usr/local/tomcat/temp \
    JRE_HOME:        /usr

# COPY war to tomcat ，usage like ADD
COPY target/*.war ${CATALINA_HOME}/webapps/

# SPECIFY CONTAINER‘S PORT TO EXPOSE
EXPOSE 8080

#启动入口
ENTRYPOINT ["catalina.sh","run"]