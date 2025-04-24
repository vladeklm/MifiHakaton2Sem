# 🐳 Настройка окружения PostgreSQL и Redis с помощью Docker Compose

![Docker](https://img.shields.io/badge/Docker-3.0-blue?logo=docker)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-7.0-red?logo=redis)

Это руководство описывает процесс настройки среды разработки с использованием:
- **PostgreSQL** — реляционная СУБД
- **pgAdmin** — веб-интерфейс для PostgreSQL
- **Redis** — хранилище ключ-значение

## 🛠 Основные компоненты конфигурации

Файл `docker-compose.yaml` включает:

| Сервис     | Версия    | Порт   | Назначение               |
|------------|-----------|--------|--------------------------|
| PostgreSQL | latest    | 5432   | Основная база данных     |
| pgAdmin    | 4         | 8080   | Веб-интерфейс управления |
| Redis      | latest    | 6379   | Кэш-хранилище            |

## ⚙️ Конфигурация Docker Compose

Создайте файл `docker-compose.yaml`:

version: '3.8'

services:
postgres:
image: postgres:latest
environment:
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
POSTGRES_DB: mifidatabase
ports:
- "5432:5432"
  volumes:
- postgres_data:/var/lib/postgresql/data
- ./init.sql:/docker-entrypoint-initdb.d/init.sql

pgadmin:
image: dpage/pgadmin4
environment:
PGADMIN_DEFAULT_EMAIL: admin@example.com
PGADMIN_DEFAULT_PASSWORD: admin
ports:
- "8080:80"
  volumes:
- ./scripts/pgadmin/servers.json:/pgadmin4/servers.json

redis:
image: redis:latest
ports:
- "6379:6379"
  volumes:
- redis_data:/data

volumes:
postgres_data:
redis_data:



> **Важно!** Перед запуском измените:
> - `POSTGRES_USER`/`POSTGRES_PASSWORD`
> - `PGADMIN_DEFAULT_PASSWORD`

## 📊 Инициализация базы данных

Создайте файл `init.sql`:

CREATE TABLE users (
id SERIAL PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (username, email) VALUES
('user1', 'user1@example.com'),
('user2', 'user2@example.com'),
('user3', 'user3@example.com');



## 🔐 Настройка pgAdmin

1. Создайте структуру каталогов:
   mkdir -p scripts/pgadmin


2. Добавьте `scripts/pgadmin/servers.json`:
   {
   "Servers": {
   "1": {
   "Name": "Local PostgreSQL",
   "Group": "Servers",
   "Port": 5432,
   "Username": "postgres",
   "Host": "postgres",
   "SSLMode": "prefer",
   "MaintenanceDB": "postgres",
   "Password": "postgres"
   }
   }
   }



## 🚀 Запуск системы

Запуск в фоновом режиме
docker-compose up -d

Проверка статуса
docker-compose ps



## 🔌 Доступ к сервисам

| Сервис       | Endpoint                 | Учетные данные                  |
|--------------|--------------------------|----------------------------------|
| **PostgreSQL** | `localhost:5432`        | User: `postgres`<br>Pass: `postgres` |
| **pgAdmin**    | http://localhost:8080   | Email: `admin@example.com`<br>Pass: `admin` |
| **Redis**      | `redis://localhost:6379` | Аутентификация не требуется      |

## 🛠 Устранение неполадок

### 🔌 Ошибка подключения pgAdmin
1. Проверьте сетевые настройки:
   docker network inspect bridge



2. Убедитесь в правильности данных:
   docker exec -it postgres psql -U postgres -c "\conninfo"



### 🗃 Проблемы с инициализацией БД
Полная пересборка с очисткой данных
docker-compose down -v
docker-compose up -d



## 🛑 Остановка системы
Остановка с сохранением данных
docker-compose down

Полная очистка
docker-compose down -v



---

**Готово!** Ваша среда разработки готова к использованию. Для обновления конфигурации выполните:
docker-compose up -d --force-recreate

