CREATE TABLE tbl_user (
                        user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        user_login_id VARCHAR(100) NOT NULL UNIQUE,
                        user_pwd VARCHAR(100) NOT NULL,
                        user_name VARCHAR(50) NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tbl_notice (
                        notice_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        title VARCHAR(225) NOT NULL,
                        description TEXT NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        publish_status VARCHAR(30) NOT NULL,
                        CONSTRAINT FK_tbl_user_TO_tbl_notice_1 FOREIGN KEY (user_id) REFERENCES tbl_user(user_id)
);

CREATE TABLE tbl_book (
                          book_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          category_id INT NOT NULL,
                          book_name VARCHAR(225) NOT NULL,
                          author_name VARCHAR(50) NOT NULL,
                          publisher VARCHAR(100),
                          book_price INT NOT NULL DEFAULT 0,
                          book_image VARCHAR(2000),
                          book_description TEXT,
                          published_at DATE,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          publish_status VARCHAR(30) NOT NULL,
                          stock INT NOT NULL,
                          CONSTRAINT FK_tbl_category_TO_tbl_book_1 FOREIGN KEY (category_id) REFERENCES tbl_category(category_id)
);

CREATE TABLE tbl_category (
                        category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        category_name VARCHAR(100) NOT NULL UNIQUE,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE tbl_customer (
                        customer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        customer_name VARCHAR(15) NOT NULL,
                        customer_number VARCHAR(15) NOT NULL,
                        customer_addr VARCHAR(255) NOT NULL,
                        customer_gender VARCHAR(3),
                        customer_age INT
);

CREATE TABLE tbl_order (
                        order_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        customer_id INT NOT NULL,
                        order_total_count INT NOT NULL,
                        order_total_price INT NOT NULL,
                        order_memo VARCHAR(225),
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        order_status VARCHAR(30) NOT NULL,
                        payment VARCHAR(30) NOT NULL,
                        CONSTRAINT FK_customer_TO_tbl_order_1 FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE tbl_order_detail (
                        order_detail_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        order_id INT NOT NULL,
                        book_id INT NOT NULL,
                        order_detail_total_count INT NOT NULL,
                        order_detail_total_price INT NOT NULL,
                        CONSTRAINT FK_tbl_order_TO_tbl_order_detail_1 FOREIGN KEY (order_id) REFERENCES tbl_order(order_id),
                        CONSTRAINT FK_tbl_book_TO_tbl_order_detail_1 FOREIGN KEY (book_id) REFERENCES tbl_book(book_id)
);


CREATE TABLE tbl_settlement (
                        st_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        order_id INT NOT NULL,
                        st_date DATE NOT NULL,
                        ex_date DATE NOT NULL,
                        comp_date DATE,
                        st_price INT NOT NULL,
                        st_status VARCHAR(15) NOT NULL,
                        tax INT NOT NULL,
                        CONSTRAINT FK_tbl_order_TO_settlement_1 FOREIGN KEY (order_id) REFERENCES tbl_order(order_id)
);