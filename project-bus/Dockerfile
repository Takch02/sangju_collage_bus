# 빌드 스테이지: 애플리케이션 빌드용
FROM openjdk:17-jdk-slim AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 또는 Maven 빌드 파일 복사 (사용하는 빌드 도구에 따라 수정)
COPY build.gradle gradlew settings.gradle ./
COPY gradle ./gradle
COPY src ./src

# 의존성 설치 및 애플리케이션 빌드 (Gradle 예시)
RUN ./gradlew build -x test

# 실행 스테이지: 실행용 이미지 생성
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 시간대 설정 (한국 시간: Asia/Seoul)
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출 (Spring Boot 기본 포트: 8080)
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]