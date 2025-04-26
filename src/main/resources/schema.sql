-- Create User Table
CREATE TABLE users
(
    id                  SERIAL PRIMARY KEY,
    username            VARCHAR(50) UNIQUE  NOT NULL,
    password            VARCHAR(255)        NOT NULL,
    email               VARCHAR(100) UNIQUE NOT NULL,
    first_name          VARCHAR(50),
    last_name           VARCHAR(50),
    profile_picture_url VARCHAR(255),
    role                VARCHAR(20) DEFAULT 'USER',
    created_at          TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

-- Create Product Table
CREATE TABLE products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    quantity    INT       DEFAULT 0,
    user_id     INT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);