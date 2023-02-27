CREATE TABLE `article`
(
    `idx`        bigint       NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(200) NOT NULL,
    `contents`   MEDIUMTEXT   NOT NULL,
    `created_at` DATETIME     NOT NULL,
    `updated_at` DATETIME     NOT NULL,
    PRIMARY KEY (`idx`)
);