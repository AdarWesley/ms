FROM openjdk:8-alpine
VOLUME /tmp
COPY target/digital-login-application.jar /app.jar
EXPOSE 8080
EXPOSE 8000
#RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xdebug"
#ENV ENV="/etc/profile"
ENTRYPOINT [ "/bin/bash", "-l", "-c", "java $JAVA_OPTS -jar /app.jar" ]