FROM awesley/alpine-orajdk8u201:latest
VOLUME /tmp
COPY target/digital-login-application.jar /app.jar
EXPOSE 8080
EXPOSE 8000
#RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xdebug"
ENTRYPOINT [ "/bin/bash", "-c", "java $JAVA_OPTS -jar /app.jar" ]