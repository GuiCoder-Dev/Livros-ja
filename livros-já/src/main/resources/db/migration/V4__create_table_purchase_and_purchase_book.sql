CREATE TABLE purchase(
    id int auto_increment primary key,
    customer_id int not null,
    nfe varchar(255),
    price decimal(10,2) not null,
    created_at DATETIME not null,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE purchase_book(
    PRIMARY KEY (purchase_id, book_id),
    purchase_id int not null,
    book_id int not null,
    FOREIGN KEY (purchase_id) REFERENCES purchase(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);

