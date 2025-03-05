# 빌드 스테이지
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# 필수 패키지 설치
RUN apt-get update && apt-get install -y curl ca-certificates unzip

# Gradle 파일 복사 및 설정
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew

# Java 및 Gradle 버전 확인
RUN java -version
RUN ./gradlew --version

# 의존성 미리 다운로드 (빌드 캐시 활용)
RUN ./gradlew build -x test --stacktrace --info --no-daemon

# 소스 코드 복사 및 빌드
COPY src ./src
RUN ./gradlew build -x test --stacktrace --info --no-daemon

# 실행 스테이지
FROM openjdk:17-jdk-slim

WORKDIR /app
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
