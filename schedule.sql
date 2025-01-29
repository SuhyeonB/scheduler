-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `email` VARCHAR(255) NULL,
    `name` VARCHAR(45) NULL,
    `password` VARCHAR(255) NULL,
    `createdAt` DATETIME NULL,
    `updatedAt` DATETIME NULL
);

-- -----------------------------------------------------
-- Table `schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedule` (
    `schedule_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `todo` VARCHAR(255) NULL,
    `createdAt` DATETIME NULL,
    `updatedAt` DATETIME NULL,
    `user_user_id` BIGINT NOT NULL,
    FOREIGN KEY (`user_user_id`)
    REFERENCES `user` (`user_id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);