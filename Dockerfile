# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем исходные файлы в контейнер
COPY src /app/src

# Компилируем Java файлы
RUN javac -d . src/*.java src/backup_app/*.java

# Указываем команду для запуска приложения
CMD ["java", "Main"]
