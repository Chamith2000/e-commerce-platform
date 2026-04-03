-- Insert a dummy cart for User 101
INSERT INTO carts (user_id, total_amount, created_at, updated_at)
VALUES
    (101, 254500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert cart items linked to the cart_id = 1
INSERT INTO cart_items (cart_id, product_id, product_name, product_image_url, quantity, unit_price, subtotal)
VALUES
    (1, 1, 'Gaming Laptop', 'http://example.com/laptop.jpg', 1, 250000.00, 250000.00),
    (1, 2, 'Wireless Mouse', 'http://example.com/mouse.jpg', 1, 4500.00, 4500.00);