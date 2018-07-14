FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD digital-login-application.jar app.jar
EXPOSE 8080
EXPOSE 8000
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xdebug"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]