CREATE DATABASE osbb;
USE osbb;

CREATE TABLE houses(
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `address` VARCHAR(255) NOT NULL,
	`number` INT NOT NULL
);

CREATE TABLE apartments(
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `houses_id` INT NOT NULL,
    `number` INT NOT NULL,
    `sqare` DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY(`houses_id`) REFERENCES houses(`id`)
);

CREATE TABLE members_osbb(
    `id` INT AUTO_INCREMENT PRIMARY KEY,
	`surname` VARCHAR(45) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
	`patronymic` VARCHAR(45) NOT NULL,
    `role` ENUM('Учасник', 'Працівник', 'Член правління', 'Голова') NOT NULL,
    `phone_number` INT NOT NULL,
    `email` VARCHAR(255) NOT NULL
);

CREATE TABLE residents(
    `members_osbb_id` INT,
    `apartments_id` INT,
    `entry_rights_territory` BOOLEAN NOT NULL,
	PRIMARY KEY(`members_osbb_id`, `apartments_id`),
    FOREIGN KEY(`members_osbb_id`) REFERENCES members_osbb(`id`),
    FOREIGN KEY(`apartments_id`) REFERENCES apartments(`id`)
);

CREATE TABLE ownerships(
    `members_osbb_id` INT,
    `apartments_id` INT,
	PRIMARY KEY(`members_osbb_id`, `apartments_id`),
    FOREIGN KEY(`members_osbb_id`) REFERENCES members_osbb(`id`),
    FOREIGN KEY(`apartments_id`) REFERENCES apartments(`id`)
);
