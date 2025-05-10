INSERT INTO client_types (id, name,is_actual) VALUES
                                                  (1, 'Физическое лицо',true),
                                                  (2, 'Юридическое лицо',true);

INSERT INTO operation_types (id, name,is_actual,code) VALUES
                                                     (1, 'Поступление',true,1),
                                                     (2, 'Списание',true,2);

INSERT INTO operation_statuses (id, name,is_actual,code) VALUES
                                                        (1, 'Новая',true,1),
                                                        (2, 'Подтвержденная',true,2),
                                                        (3, 'В обработке',true,3),
                                                        (4, 'Отменена',true,4),
                                                        (5, 'Платеж выполнен',true,5),
                                                        (6, 'Платеж удален',true,6),
                                                        (7, 'Возврат',true,7);

INSERT INTO operation_categories (id, name,is_actual) VALUES
                                                          (1, 'Зарплата',true),
                                                          (2, 'Пополнение счета',true),
                                                          (3, 'Возврат средств',true),
                                                          (4, 'Налоговый вычет',true),
                                                          (5, 'Перевод между счетами',true),
                                                          (6, 'Оплата услуг',true),
                                                          (7, 'Кредитный платеж',true),
                                                          (8, 'Налоговый платеж',true);


-- Вставка пользователей с created_at и updated_at, password = password1234
INSERT INTO users (login, password, role, created_at, updated_at)
VALUES
    ('test1', '$2a$12$apuFcZlMOD4seJRqgDnfFuqRMB.cfkijRwGD6HNri1cQ5MzjDF4yK', 'ROLE_USER', NOW(), NOW()),
    ('test2', '$2a$12$apuFcZlMOD4seJRqgDnfFuqRMB.cfkijRwGD6HNri1cQ5MzjDF4yK', 'ROLE_USER', NOW(), NOW()),
    ('test3', '$2a$12$apuFcZlMOD4seJRqgDnfFuqRMB.cfkijRwGD6HNri1cQ5MzjDF4yK', 'ROLE_USER', NOW(), NOW()),
    ('test4', '$2a$12$apuFcZlMOD4seJRqgDnfFuqRMB.cfkijRwGD6HNri1cQ5MzjDF4yK', 'ROLE_USER', NOW(), NOW()),
    ('test5', '$2a$12$apuFcZlMOD4seJRqgDnfFuqRMB.cfkijRwGD6HNri1cQ5MzjDF4yK', 'ROLE_USER', NOW(), NOW());

-- Вставка клиентов с created_at и updated_at
INSERT INTO clients (user_id, first_name, second_name, created_at, updated_at)
VALUES
    (1, 'Иван', 'Иванов', NOW(), NOW()),
    (2, 'Петр', 'Петров', NOW(), NOW()),
    (3, 'компания', 'ООО "Ромашка"', NOW(), NOW()),
    (4, 'компания', 'АО "Технологии', NOW(), NOW()),
    (5, 'компания', 'ЗАО "Тучка"', NOW(), NOW());

-- Вставка операций поступления с created_at и updated_at (случайные даты в пределах 2 лет)
WITH categories AS (
    SELECT
        FLOOR(1 + RANDOM() * 4)::int AS category_id
    FROM generate_series(1, 4500)
)
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
    c.category_id,
    1,
    CASE WHEN FLOOR(1 + RANDOM() * 5)::int <= 3 THEN 1 ELSE 2 END,
    FLOOR(1 + RANDOM() * 7)::int,
    NOW() - (RANDOM() * 730 * 24 * 60 * 60 || ' seconds')::interval,
    FLOOR(100 + RANDOM() * 100000)::numeric(12,2),
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
            CASE c.category_id
                WHEN 1 THEN -- Зарплата
                    CASE WHEN RANDOM() > 0.5
                             THEN 'зарплата ' ||
                                  CASE FLOOR(1 + RANDOM() * 12)::int
                                      WHEN 1 THEN 'январь'
                                      WHEN 2 THEN 'февраль'
                                      WHEN 3 THEN 'март'
                                      WHEN 4 THEN 'апрель'
                                      WHEN 5 THEN 'май'
                                      WHEN 6 THEN 'июнь'
                                      WHEN 7 THEN 'июль'
                                      WHEN 8 THEN 'август'
                                      WHEN 9 THEN 'сентябрь'
                                      WHEN 10 THEN 'октябрь'
                                      WHEN 11 THEN 'ноябрь'
                                      WHEN 12 THEN 'декабрь'
                                      END
                         ELSE 'аванс ' ||
                              CASE FLOOR(1 + RANDOM() * 12)::int
                                  WHEN 1 THEN 'январь'
                                  WHEN 2 THEN 'февраль'
                                  WHEN 3 THEN 'март'
                                  WHEN 4 THEN 'апрель'
                                  WHEN 5 THEN 'май'
                                  WHEN 6 THEN 'июнь'
                                  WHEN 7 THEN 'июль'
                                  WHEN 8 THEN 'август'
                                  WHEN 9 THEN 'сентябрь'
                                  WHEN 10 THEN 'октябрь'
                                  WHEN 11 THEN 'ноябрь'
                                  WHEN 12 THEN 'декабрь'
                                  END
                        END
                WHEN 3 THEN -- Возврат средств
                    CASE FLOOR(1 + RANDOM() * 4)::int
                        WHEN 1 THEN 'возврат долга от друга'
                        WHEN 2 THEN 'возврат займа'
                        WHEN 3 THEN 'компенсация'
                        ELSE 'возврат ошибочного платежа'
                        END
                WHEN 2 THEN -- Пополнение счета
                    CASE FLOOR(1 + RANDOM() * 5)::int
                        WHEN 1 THEN 'подработка'
                        WHEN 2 THEN 'фриланс'
                        WHEN 3 THEN 'доход от инвестиций'
                        WHEN 4 THEN 'подарок'
                        ELSE 'дополнительный заработок'
                        END
                WHEN 4 THEN -- Налоговый вычет
                    CASE FLOOR(1 + RANDOM() * 3)::int
                        WHEN 1 THEN 'за обучение'
                        WHEN 2 THEN 'за лечение'
                        ELSE 'за покупку квартиры'
                        END
                ELSE
                    CASE FLOOR(1 + RANDOM() * 5)::int
                        WHEN 1 THEN 'Оплата по договору'
                        WHEN 2 THEN 'Перевод средств'
                        WHEN 3 THEN 'Возврат ошибочного платежа'
                        WHEN 4 THEN 'Ежемесячный платеж'
                        ELSE 'Без комментариев'
                        END
                END
        ELSE NULL
        END,
    NOW() - (RANDOM() * 90 * 24 * 60 * 60 || ' seconds')::interval,
    NOW() - (RANDOM() * 90 * 24 * 60 * 60 || ' seconds')::interval
FROM categories c;

-- Вставка операций списания с created_at и updated_at (случайные даты в пределах 2 лет)
WITH categories AS (
    SELECT
        FLOOR(5 + RANDOM() * 4)::int AS category_id  -- Категории с 5 по 8
    FROM generate_series(1, 4500)
)
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
    c.category_id,
    2,  -- Фиксируем тип операции как "Списание"
    CASE WHEN FLOOR(1 + RANDOM() * 5)::int <= 3 THEN 1 ELSE 2 END,
    FLOOR(1 + RANDOM() * 7)::int,
    NOW() - (RANDOM() * 730 * 24 * 60 * 60 || ' seconds')::interval,
    FLOOR(100 + RANDOM() * 100000)::numeric(12,2),
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
            CASE c.category_id
                WHEN 5 THEN -- Перевод между счетами
                    CASE FLOOR(1 + RANDOM() * 5)::int
                        WHEN 1 THEN 'Перевод на депозит'
                        WHEN 2 THEN 'Перевод между своими счетами'
                        WHEN 3 THEN 'Перевод родственнику'
                        WHEN 4 THEN 'Перевод на брокерский счет'
                        ELSE 'Перевод на другой счет'
                        END
                WHEN 6 THEN -- Оплата услуг
                    CASE FLOOR(1 + RANDOM() * 8)::int
                        WHEN 1 THEN 'Оплата ЖКХ'
                        WHEN 2 THEN 'Оплата мобильной связи'
                        WHEN 3 THEN 'Оплата интернета'
                        WHEN 4 THEN 'Оплата подписки'
                        WHEN 5 THEN 'Оплата налогов'
                        WHEN 6 THEN 'Оплата штрафа'
                        WHEN 7 THEN 'Оплата кредита'
                        ELSE 'Оплата услуг'
                        END
                WHEN 7 THEN -- Кредитный платеж
                    CASE FLOOR(1 + RANDOM() * 4)::int
                        WHEN 1 THEN 'Платеж по ипотеке'
                        WHEN 2 THEN 'Платеж по кредитной карте'
                        WHEN 3 THEN 'Платеж по потребительскому кредиту'
                        ELSE 'Ежемесячный платеж по кредиту'
                        END
                WHEN 8 THEN -- Налоговый платеж
                    CASE FLOOR(1 + RANDOM() * 5)::int
                        WHEN 1 THEN 'Налог на доходы'
                        WHEN 2 THEN 'Налог на имущество'
                        WHEN 3 THEN 'Транспортный налог'
                        WHEN 4 THEN 'Земельный налог'
                        ELSE 'Налоговый платеж'
                        END
                ELSE
                    CASE FLOOR(1 + RANDOM() * 5)::int
                        WHEN 1 THEN 'Оплата по договору'
                        WHEN 2 THEN 'Перевод средств'
                        WHEN 3 THEN 'Возврат ошибочного платежа'
                        WHEN 4 THEN 'Ежемесячный платеж'
                        ELSE 'Без комментариев'
                        END
                END
        ELSE NULL
        END,
    NOW() - (RANDOM() * 90 * 24 * 60 * 60 || ' seconds')::interval,
    NOW() - (RANDOM() * 90 * 24 * 60 * 60 || ' seconds')::interval
FROM categories c;


