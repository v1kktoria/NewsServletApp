FROM tomcat:10-jdk17-openjdk

COPY target/news-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 7777

CMD ["catalina.sh", "run"]