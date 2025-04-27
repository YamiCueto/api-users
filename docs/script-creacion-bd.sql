CREATE TABLE user (
                      id UUID PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      created TIMESTAMP,
                      modified TIMESTAMP,
                      last_login TIMESTAMP,
                      token VARCHAR(500),
                      is_active BOOLEAN
);

CREATE TABLE phone (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       number VARCHAR(20),
                       citycode VARCHAR(10),
                       contrycode VARCHAR(10),
                       user_id UUID,
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id)
);
