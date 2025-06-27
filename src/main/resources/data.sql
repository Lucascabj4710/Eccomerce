

-- Ahora insertás productos referenciando esas categorías
INSERT INTO product (name, image_url, price, color, material, waist, id_category, stock) VALUES
('Pulsera de cuero trenzado', 'url_imagen1.jpg', 5999.99, 'Negro', 'Cuero', NULL, 1, 25),
('Pulsera con dijes', 'url_imagen2.jpg', 4599.50, 'Rosa', 'Silicona', NULL, 1, 30),
('Pulsera tejida artesanal', 'url_imagen3.jpg', 3499.90, 'Multicolor', 'Hilo', NULL, 1, 20),

('Cinturón clásico', 'url_imagen4.jpg', 8999.50, 'Marrón', 'Cuero', 'M', 2, 40),
('Cinturón de lona', 'url_imagen5.jpg', 6599.00, 'Verde militar', 'Lona', 'L', 2, 35),
('Cinturón reversible', 'url_imagen6.jpg', 10499.99, 'Negro/Marrón', 'Cuero sintético', 'XL', 2, 15),

('Collar de acero inoxidable', 'url_imagen7.jpg', 10999.90, 'Plateado', 'Acero', NULL, 3, 30),
('Collar con dije de luna', 'url_imagen8.jpg', 7999.00, 'Dorado', 'Metal', NULL, 3, 25),
('Collar ajustable de cuero', 'url_imagen9.jpg', 6899.75, 'Negro', 'Cuero', NULL, 3, 18),
('Choker con perlas', 'url_imagen10.jpg', 5499.25, 'Blanco', 'Nácar', NULL, 3, 22);

