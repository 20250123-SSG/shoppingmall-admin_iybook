CREATE TABLE `tbl_order_detail` (
	`order_detail_id` INT NOT NULL AUTO_INCREMENT,
	`order_id` INT NOT NULL,
	`book_id` INT NOT NULL,
	`order_detail_total_count` INT NOT NULL,
	`order_detail_total_price` INT NOT NULL,
	PRIMARY KEY (`order_detail_id`)
);

CREATE TABLE `settlement` (
	`st_code` INT NOT NULL AUTO_INCREMENT,
	`st_date` DATE NOT NULL,
	`ex_date` DATE NOT NULL,
	`comp_date` DATE NULL,
	`st_price` INT NOT NULL,
	`st_status` VARCHAR(15) NOT NULL DEFAULT '정산예정',
	`order_id` INT NOT NULL,
	PRIMARY KEY (`st_code`),
	FOREIGN KEY (`order_id`) REFERENCES `tbl_order` (`order_id`)
);

CREATE TABLE `tbl_announcement` (
	`announcement_id` INT NOT NULL AUTO_INCREMENT,
	`user_id` INT NOT NULL,
	`title` VARCHAR(225) NOT NULL,
	`description` TEXT NOT NULL,
	`created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`publish_status` VARCHAR(30) NOT NULL DEFAULT '게시',
	PRIMARY KEY (`announcement_id`),
	FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`)
);

CREATE TABLE `tbl_category` (
	`category_id` INT NOT NULL AUTO_INCREMENT,
	`category_name` VARCHAR(100) NOT NULL,
	`created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`category_id`)
);

CREATE TABLE `tbl_order` (
	`order_id` INT NOT NULL AUTO_INCREMENT,
	`order_total_count` INT NOT NULL,
	`order_total_price` INT NOT NULL,
	`order_memo` VARCHAR(225) NULL,
	`created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`order_status` VARCHAR(30) NOT NULL DEFAULT '주문완료',
	`payment` VARCHAR(30) NOT NULL DEFAULT '카드',
	`customer_code` INT NOT NULL,
	PRIMARY KEY (`order_id`),
	FOREIGN KEY (`customer_code`) REFERENCES `customer` (`customer_code`)
);

CREATE TABLE `tbl_book` (
	`book_id` INT NOT NULL AUTO_INCREMENT,
	`book_name` VARCHAR(225) NOT NULL,
	`author_name` VARCHAR(50) NOT NULL,
	`publisher` VARCHAR(100) NULL,
	`book_price` INT NOT NULL DEFAULT 0,
	`book_image` VARCHAR(2000) NULL DEFAULT 'default image',
	`book_description` TEXT NULL,
	`category_id` INT NOT NULL,
	`published_at` DATE NULL,
	`created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`publish_status` VARCHAR(30) NOT NULL DEFAULT '판매중',
	`stock` INT NOT NULL,
	PRIMARY KEY (`book_id`),
	FOREIGN KEY (`category_id`) REFERENCES `tbl_category` (`category_id`)
);

CREATE TABLE `tbl_user` (
	`user_id` INT NOT NULL AUTO_INCREMENT,
	`user_login_id` VARCHAR(100) NOT NULL UNIQUE,
	`user_pwd` VARCHAR(100) NOT NULL,
	`user_name` VARCHAR(50) NOT NULL,
	`created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_id`)
);

CREATE TABLE `customer` (
	`customer_code` INT NOT NULL AUTO_INCREMENT,
	`customer_name` VARCHAR(15) NOT NULL,
	`customer_number` VARCHAR(15) NOT NULL,
	`customer_addr` VARCHAR(255) NOT NULL,
	`customer_gender` VARCHAR(3) NULL,
	`customer_age` INT NULL,
	PRIMARY KEY (`customer_code`)
);

-- Add foreign keys
ALTER TABLE `tbl_order_detail` ADD CONSTRAINT `FK_tbl_order_TO_tbl_order_detail_1` FOREIGN KEY (`order_id`) REFERENCES `tbl_order` (`order_id`);
ALTER TABLE `tbl_order_detail` ADD CONSTRAINT `FK_tbl_book_TO_tbl_order_detail_1` FOREIGN KEY (`book_id`) REFERENCES `tbl_book` (`book_id`);
ALTER TABLE `settlement` ADD CONSTRAINT `FK_tbl_order_TO_settlement_1` FOREIGN KEY (`order_id`) REFERENCES `tbl_order` (`order_id`);
ALTER TABLE `tbl_announcement` ADD CONSTRAINT `FK_tbl_user_TO_tbl_announcement_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`);
ALTER TABLE `tbl_order` ADD CONSTRAINT `FK_customer_TO_tbl_order_1` FOREIGN KEY (`customer_code`) REFERENCES `customer` (`customer_code`);
ALTER TABLE `tbl_book` ADD CONSTRAINT `FK_tbl_category_TO_tbl_book_1` FOREIGN KEY (`category_id`) REFERENCES `tbl_category` (`category_id`);