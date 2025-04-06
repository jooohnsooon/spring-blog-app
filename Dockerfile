# ビルド用イメージ（Java 17 + Gradle）
FROM gradle:8.5-jdk17 AS builder

# アプリのコードをすべてコピー
COPY --chown=gradle:gradle . /app
WORKDIR /app

# Gradleでビルド（初回は少し時間かかる）
RUN gradle build --no-daemon

# 実行用の軽量イメージ（Javaのみ）
FROM openjdk:17-slim
WORKDIR /app

# ビルド済みのJARファイルをコピー
COPY --from=builder /app/build/libs/*.jar app.jar

# アプリを起動！
ENTRYPOINT ["java", "-jar", "app.jar"]
