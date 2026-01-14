# [빌드 과정 없음! 실행만 하는 가벼운 이미지]
FROM openjdk:17-jdk-slim

WORKDIR /app

# ⭐️ 핵심: Gradle을 여기서 돌리지 않고, 이미 만들어진 JAR 파일을 복사만 함
COPY build/libs/*.jar app.jar

# 로그 폴더 생성 (권한 문제 방지)
RUN mkdir -p /app/logs

# 메모리 제한(400m)을 걸고 실행
ENTRYPOINT ["java", "-Xmx400m", "-jar", "app.jar"]