FROM adoptopenjdk/openjdk11-openj9
ENV POSTGRES_URL="changeme" \
    POSTGRES_USERNAME="changeme" \
    POSTGRES_PASSWORD="changeme"

ADD target/api-0.0.1.jar .

EXPOSE 8081

CMD ["java", "-jar", "api-0.0.1.jar"]