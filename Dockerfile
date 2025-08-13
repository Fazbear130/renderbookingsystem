# 使用 JDK 17 的基础镜像
FROM eclipse-temurin:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制 Maven 配置文件和源代码
COPY pom.xml .
COPY src ./src

# 安装 Maven（Alpine 镜像不自带）
RUN apk add --no-cache maven

# 构建项目
RUN mvn clean install -DskipTests

# 复制生成的 JAR 文件
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 设置容器运行时端口（与 application.properties 的 ${PORT:8080} 一致）
EXPOSE 8080

# 运行应用
ENTRYPOINT ["java", "-jar", "/app.jar"]