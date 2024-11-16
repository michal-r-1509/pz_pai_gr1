INSERT INTO vehicles (name, type, reg_no, capacity, pump_length)
VALUES ('Mercedes Actros', 'MIXER', 'WA12345', 8.0, 0.0),
       ('MAN TGS', 'PUMP', 'KR56789', 0.0, 42.0),
       ('Putzmeister BSF 36', 'MIXER_PUMP', 'PO23456', 7.5, 36.0),
       ('Scania R500', 'MIXER', 'GD98765', 9.0, 0.0),
       ('Schwing S42SX', 'MIXER_PUMP', 'WP87654', 6.5, 18.0),
       ('DAF CF 450', 'MIXER', 'LB34567', 9.0, 0.0);

INSERT INTO orders (date, time, amount, concrete_class, site_address, description, pump, done, order_no)
VALUES ('2024-10-01', '08:30:00', 12.00, 'C30/37', '123 Construction Ave, Warsaw', 'Delivery for foundation', true,
        false, 'Z00001'),
       ('2024-10-02', '09:00:00', 15.00, 'C25/30', '45 Building St, Kraków', 'Slab concrete pour', false, true,
        'Z00002'),
       ('2024-10-03', '10:15:00', 20.00, 'C35/45', '78 Industrial Rd, Gdańsk', 'Industrial floor pour', true,
        false, 'Z00003'),
       ('2024-10-04', '11:45:00', 25.00, 'C20/25', '22 Riverbank Dr, Poznań', 'Driveway pour', false, true,
        'Z00004'),
       ('2024-10-05', '07:30:00', 6.00, 'C40/50', '56 Greenfield Ln, Wrocław', 'High-strength mix for pillars',
        true, false, 'Z00005'),
       ('2024-10-06', '08:00:00', 50.00, 'C30/37', '9 Harbor Rd, Szczecin', 'Marine construction', true, true,
        'Z00006');

INSERT INTO schedules (date, start_time, end_time, vehicle_id)
VALUES ('2024-10-01', '08:00:00', '12:00:00', 1),
       ('2024-10-01', '13:00:00', '17:00:00', 2),
       ('2024-10-02', '07:00:00', '11:00:00', 3),
       ('2024-10-02', '12:30:00', '16:30:00', 4),
       ('2024-10-03', '09:00:00', '13:00:00', 5),
       ('2024-10-03', '14:00:00', '18:00:00', 6);

INSERT INTO batches (amount, done, dn_no, schedule_id, order_id)
VALUES (5.0, true, '00001', 1, 1),
       (3.0, false, '00002', 2, 2),
       (4.5, true, '00003', 3, 3),
       (2.5, true, '00004', 4, 4),
       (6.0, false, '00005', 5, 5),
       (3.2, true, '00006', 6, 6);
