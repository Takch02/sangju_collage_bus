# 빌드 스테이지
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Install curl for network debugging (optional)
RUN apt-get update && apt-get install -y curl

# Gradle 파일 복사
COPY build.gradle gradlew settings.gradle ./
COPY gradle ./gradle
COPY src ./src

# gradlew에 실행 권한 부여
RUN chmod +x ./gradlew

# 애플리케이션 빌드 (with debug output)
RUN ./gradlew build -x test --stacktrace --debug

# 실행 스테이지
FROM openjdk:17-jdk-slim

WORKDIR /app
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]