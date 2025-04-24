-- Insert Initial Addresses
INSERT INTO addresses (street, city, state)
VALUES ('123 Main St', 'Springfield', 'IL'),
       ('456 Elm St', 'Springfield', 'IL');

-- Insert Initial Users
INSERT INTO users (username, password, email, first_name, last_name, profile_picture_url, role, address_id)
VALUES ('admin', 'adminpassword', 'admin@example.com', 'Admin', 'User', 'http://example.com/images/admin.jpg', 'ADMIN',
        1),
       ('user1', 'userpassword1', 'user1@example.com', 'John', 'Doe', 'http://example.com/images/user1.jpg', 'USER', 1),
       ('user2', 'userpassword2', 'user2@example.com', 'Jane', 'Smith', 'http://example.com/images/user2.jpg', 'USER',
        2);

-- Insert Initial Products
INSERT INTO products (name, description, price, quantity, user_id)
VALUES ('Product A', 'Description for Product A', 10.99, 100, 1),
       ('Product B', 'Description for Product B', 19.99, 200, 1),
       ('Product C', 'Description for Product C', 5.99, 50, 2);