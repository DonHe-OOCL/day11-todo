CREATE TABLE IF NOT EXISTS todo (
                                    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    done tinyint DEFAULT NULL,
                                    text varchar(255) DEFAULT NULL
);