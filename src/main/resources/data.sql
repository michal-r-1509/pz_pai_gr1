INSERT INTO vehicles (id, name, type, reg_no, capacity, pump_length)
VALUES (1, 'Mercedes Actros', 'MIXER', 'WA12345', 8.0, 0.0),
       (2, 'MAN TGS', 'PUMP', 'KR56789', 0.0, 42.0),
       (3, 'Putzmeister BSF 36', 'MIXER_PUMP', 'PO23456', 7.5, 36.0),
       (4, 'Scania R500', 'MIXER', 'GD98765', 9.0, 0.0),
       (5, 'Schwing S42SX', 'MIXER_PUMP', 'WP87654', 6.5, 18.0),
       (6, 'DAF CF 450', 'MIXER', 'LB34567', 9.0, 0.0);

INSERT INTO clients (id, name, street_and_no, post_code, city, taxpayer_ident_no, type)
VALUES
    (1, 'John Doe', '123 Elm St', '12345', 'Springfield', 1234567890, 'INDIVIDUAL'),
    (2, 'Acme Corp', '456 Oak Ave', '54321', 'Metropolis', 987654321, 'BUSINESS'),
    (3, 'Jane Smith', '789 Pine Dr', '67890', 'Smalltown', 456789123, 'INDIVIDUAL'),
    (4, 'Global Enterprises', '101 Maple Rd', '11223', 'Big City', 321654987, 'BUSINESS'),
    (5, 'Alpha Solutions', '202 Birch Ln', '33445', 'Riverdale', 135792468, 'BUSINESS'),
    (6, 'Beta Innovations', '303 Cedar Blvd', '55667', 'Lakeside', 246813579, 'BUSINESS');

INSERT INTO orders (id, date, time, amount, concrete_class, site_address, description, pump, done, client_id)
VALUES (1, '2024-10-01', '08:30:00', 12.00, 'C30/37', '123 Construction Ave, Warsaw', 'Delivery for foundation', true, false, 1),
       (2,'2024-10-02', '09:00:00', 15.00, 'C25/30', '45 Building St, Kraków', 'Slab concrete pour', false, true, 2),
       (3,'2024-10-03', '10:15:00', 20.00, 'C35/45', '78 Industrial Rd, Gdańsk', 'Industrial floor pour', true, false, 3),
       (4,'2024-10-04', '11:45:00', 25.00, 'C20/25', '22 Riverbank Dr, Poznań', 'Driveway pour', false, true, 1),
       (5,'2024-10-05', '07:30:00', 6.00, 'C40/50', '56 Greenfield Ln, Wrocław', 'High-strength mix for pillars',true, false, 2),
       (6,'2024-10-06', '08:00:00', 50.00, 'C30/37', '9 Harbor Rd, Szczecin', 'Marine construction', true, true, 3);

INSERT INTO schedules (id, date, start_time, end_time, vehicle_id)
VALUES (1,'2024-10-01', '08:00:00', '12:00:00', 1),
       (2, '2024-10-01', '13:00:00', '17:00:00', 2),
       (3, '2024-10-02', '07:00:00', '11:00:00', 3),
       (4, '2024-10-02', '12:30:00', '16:30:00', 4),
       (5, '2024-10-03', '09:00:00', '13:00:00', 5),
       (6, '2024-10-03', '14:00:00', '18:00:00', 6);

INSERT INTO batches (id,amount, done, schedule_id, order_id)
VALUES (1, 5.0, true, 1, 1),
       (2, 3.0, false,  2, 2),
       (3, 4.5, true,  3, 3),
       (4, 2.5, true,  4, 4),
       (5, 6.0, false, 5, 5),
       (6, 3.2, true,  6, 6);


