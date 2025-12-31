CREATE TABLE subscription (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255),
                              description VARCHAR(255),
                              price DOUBLE,
                              image_url VARCHAR(255),
                              category VARCHAR(50)  -- neue Spalte
);

