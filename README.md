### Wallet Service
Сервис для управления кошельками с операциями пополнения и снятия средств.

### Требования
- Docker & Docker Compose (Docker Desktop)
- Java 17 (если запускать локально без Docker)
- Maven (если запускать локально)

### Запуск с Docker Compose
В проекте настроен docker-compose.yml для запуска PostgreSQL и приложения Wallet Service.
1. Создайте файл .env в корне проекта со следующими переменными:
- `POSTGRES_DB=`
- `POSTGRES_USER=`
- `POSTGRES_PASSWORD=`
- `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/wallet_db`
- `SPRING_DATASOURCE_USERNAME=`
- `SPRING_DATASOURCE_PASSWORD=`
2. Запустите контейнеры `docker-compose up --build`
3. Сервис будет доступен по адресу: http://localhost:8081/api/v1

### Конфигурация приложения
- Подключение к базе данных PostgreSQL настроено через переменные окружения.
- Используется Liquibase для миграций базы данных (db/changelog/db.changelog-master.yaml).
- Порт приложения в контейнере — 8080, проброшен на 8081 хоста. 
### Если хотите запустить сервис локально:
- Убедитесь, что PostgreSQL запущен и настроена база данных с нужными параметрами.
- В файле application.properties установите параметры подключения к БД.
- Выполните: `mvn spring-boot:run`

