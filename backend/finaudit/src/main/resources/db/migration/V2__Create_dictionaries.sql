
CREATE TABLE person_types (
                              id BIGSERIAL PRIMARY KEY,
                              code VARCHAR(50) UNIQUE NOT NULL,
                              name VARCHAR(100) NOT NULL
);


CREATE TABLE transaction_types (
                                   id BIGSERIAL PRIMARY KEY,
                                   code VARCHAR(50) UNIQUE NOT NULL,
                                   name VARCHAR(100) NOT NULL
);


CREATE TABLE transaction_statuses (
                                      id BIGSERIAL PRIMARY KEY,
                                      code VARCHAR(50) UNIQUE NOT NULL,
                                      name VARCHAR(100) NOT NULL
);


CREATE TABLE banks (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       bik VARCHAR(20) UNIQUE
);


CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            type VARCHAR(20) NOT NULL
);


INSERT INTO person_types (code, name) VALUES
                                          ('INDIVIDUAL', 'Физическое лицо'),
                                          ('LEGAL', 'Юридическое лицо');

INSERT INTO transaction_types (code, name) VALUES
                                               ('INCOME', 'Поступление'),
                                               ('EXPENSE', 'Списание');

INSERT INTO transaction_statuses (code, name) VALUES
                                                  ('NEW', 'Новая'),
                                                  ('COMPLETED', 'Завершена'),
                                                  ('CANCELED', 'Отменена');

INSERT INTO categories (name, type) VALUES
                                        ('Зарплата', 'INCOME'),
                                        ('Продукты', 'EXPENSE'),
                                        ('Транспорт', 'EXPENSE');
