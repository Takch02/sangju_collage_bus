# 빌드 스테이지
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Gradle 파일 복사 및 실행 권한 설정
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew

# 의존성 미리 다운로드 (캐싱 최적화)
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사 및 빌드
COPY src ./src
RUN ./gradlew build -x test --no-daemon

# 실행 스테이지
FROM openjdk:17-jdk-slim

WORKDIR /app
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 빌드된 JAR 복사 및 실행 설정
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
