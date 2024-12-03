CREATE TABLE `todo` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `done` bit(1) DEFAULT 0,
                        `text` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
);