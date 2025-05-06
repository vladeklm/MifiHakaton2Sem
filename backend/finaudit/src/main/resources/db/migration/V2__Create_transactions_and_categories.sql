CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            type VARCHAR(20) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))
);

CREATE TABLE operations (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL REFERENCES users(id),
                              type VARCHAR(20) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
                              amount DECIMAL(15, 5) NOT NULL,
                              date TIMESTAMP NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              sender_bank VARCHAR(100),
                              receiver_bank VARCHAR(100),
                              inn VARCHAR(12),
                              account_number VARCHAR(50),
                              phone VARCHAR(20),
                              comment TEXT,
                              category_id BIGINT REFERENCES categories(id)
);
