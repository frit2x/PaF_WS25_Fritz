CREATE TABLE subscription (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255),
                              description VARCHAR(255),
                              price DOUBLE,
                              image_url VARCHAR(255),
                              category VARCHAR(50)  -- neue Spalte
);

-- NEU: Users
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL
);

-- NEU: Cart
CREATE TABLE cart (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id BIGINT,
                      FOREIGN KEY (user_id) REFERENCES users(id)
);

-- NEU: Many-to-Many Tabelle
CREATE TABLE cart_subscriptions (
                                    cart_id BIGINT,
                                    subscriptions_id BIGINT,
                                    PRIMARY KEY (cart_id, subscriptions_id),
                                    FOREIGN KEY (cart_id) REFERENCES cart(id),
                                    FOREIGN KEY (subscriptions_id) REFERENCES subscription(id)
);
