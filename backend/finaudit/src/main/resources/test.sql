INSERT INTO client_types (id, name,is_actual) VALUES
                                                  (1, 'Физическое лицо',true),
                                                  (2, 'Юридическое лицо',true);

INSERT INTO operation_types (id, name,is_actual) VALUES
                                                     (1, 'Поступление',true),
                                                     (2, 'Списание',true);

INSERT INTO operation_statuses (id, name,is_actual) VALUES
                                                        (1, 'Новая',true),
                                                        (2, 'Подтвержденная',true),
                                                        (3, 'В обработке',true),
                                                        (4, 'Отменена',true),
                                                        (5, 'Платеж выполнен',true),
                                                        (6, 'Платеж удален',true),
                                                        (7, 'Возврат',true);

INSERT INTO operation_categories (id, name,is_actual) VALUES
                                                          (1, 'Зарплата',true),
                                                          (2, 'Перевод между счетами',true),
                                                          (3, 'Оплата услуг',true),
                                                          (4, 'Пополнение счета',true),
                                                          (5, 'Кредитный платеж',true),
                                                          (6, 'Налоговый платеж',true),
                                                          (7, 'Возврат средств',true);

-- Вставка пользователей с created_at и updated_at
INSERT INTO users (login, password, role, created_at, updated_at)
VALUES
    ('test1', 'Иванов Иван Иванович', 'ROLE_USER', NOW(), NOW()),
    ('test2', 'Петров Петр Петрович', 'ROLE_USER', NOW(), NOW()),
    ('test3', 'ООО "Ромашка"', 'ROLE_USER', NOW(), NOW()),
    ('test4', 'АО "Технологии"', 'ROLE_USER', NOW(), NOW()),
    ('test5', 'Сидорова Анна Михайловна', 'ROLE_USER', NOW(), NOW());

-- Вставка клиентов с created_at и updated_at
INSERT INTO clients (user_id, first_name, second_name, created_at, updated_at)
VALUES
    (1, 'Иван', 'Иванов', NOW(), NOW()),
    (2, 'Петр', 'Петров', NOW(), NOW()),
    (3, 'ООО "Ромашка"', '2', NOW(), NOW()),
    (4, 'АО "Технологии"', '2', NOW(), NOW()),
    (5, 'Анна', 'Сидорова', NOW(), NOW());

-- Вставка операций с created_at и updated_at (случайные даты в пределах 90 дней)
INSERT INTO operations (
    client_id,
    operation_category_id,
    operation_type_id,
    client_type_id,
    operation_status_id,
    date_time_operation,
    amount,
    bank_from_id,
    bank_to_id,
    bank_recipient_account_id,
    phone_number,
    inn,
    comment,
    created_at,
    updated_at
)
SELECT
    FLOOR(1 + RANDOM() * 5)::int,
    FLOOR(1 + RANDOM() * 7)::int,
    FLOOR(1 + RANDOM() * 2)::int,
    CASE WHEN FLOOR(1 + RANDOM() * 5)::int <= 3 THEN 1 ELSE 2 END,
    FLOOR(1 + RANDOM() * 7)::int,
    NOW() - (RANDOM() * 90 * 24 * 60 * 60 || ' seconds')::interval,
    FLOOR(100 + RANDOM() * 999900)::numeric(12,2),
    CASE WHEN RANDOM() > 0.3 THEN FLOOR(1 + RANDOM() * 10)::int ELSE NULL END,
    CASE WHEN RANDOM() > 0.3 THEN FLOOR(1 + RANDOM() * 10)::int ELSE NULL END,
    CASE WHEN RANDOM() > 0.4 THEN FLOOR(1 + RANDOM() * 100)::int ELSE NULL END,
    CASE
        WHEN RANDOM() > 0.5 THEN CONCAT('+7', FLOOR(9000000000 + RANDOM() * 999999999)::bigint)
        ELSE CONCAT('8', FLOOR(9000000000 + RANDOM() * 999999999)::bigint)
        END,
    FLOOR(10000000000 + RANDOM() * 89999999999)::bigint::text,
    CASE
        WHEN RANDOM() > 0.2 THEN
            CASE FLOOR(1 + RANDOM() * 5)::int
                WHEN 1 THEN 'Оплата по договору'
                WHEN 2 THEN 'Перевод средств'
                WHEN 3 THEN 'Возврат ошибочного платежа'
                WHEN 4 THEN 'Ежемесячный платеж'
                ELSE 'Без комментариев'
                END
        ELSE NULL
        END,
    NOW() - (RANDOM() * 90 * 24 * 60 * 60 || ' seconds')::interval,
    NOW() - (RANDOM() * 90 * 24 * 60 * 60 || ' seconds')::interval
FROM generate_series(1, 500);