INSERT INTO movies (title, genre, duration, rating, release_year) VALUES
                                                                      ('The Lion King', 'Action', 118, 9.0, 2019),
                                                                      ('Harry Potter and the Philosopher''s Stone', 'fantasy', 152, 8.8, 2001),
                                                                      ('פעם הייתי', 'drama', 112, 9.5, 2010),
                                                                      ('Forest Gump', 'comedy', 142, 9.2, 1994);

INSERT INTO showtimes (movie_id, theater, start_time, end_time, price) VALUES
                                                                           (1, 'IMAX Theater', '2025-08-24 14:00:00', '2025-08-24 16:00:00', 20.00),
                                                                           (1, 'IMAX Theater', '2025-08-24 18:00:00', '2025-08-24 20:00:00', 20.00),
                                                                           (1, 'Cinema city', '2025-08-24 14:00:00', '2025-08-24 16:28:00', 20.00),
                                                                           (2, 'Cinema city', '2025-08-25 17:00:00', '2025-08-25 19:30:00', 13.00),
                                                                           (3, 'Hot Cinema oshiland', '2025-08-26 16:00:00', '2025-08-26 18:00:00', 18.00),
                                                                           (4, 'Hot Cinema Grand Haifa', '2025-08-27 15:30:00', '2025-08-27 18:00:00', 10.00);

INSERT INTO bookings (showtime_id, user_id, seat_number) VALUES
                                                             (1, 'a3f5c6d7-1111-2222-3333-444444444444'::uuid, 1),
                                                             (1, 'a3f5c6d7-1111-2222-3333-444444444444'::uuid, 10),
                                                             (2, 'b7e8f9a0-5555-6666-7777-888888888888'::uuid, 20),
                                                             (3, 'c1d2e3f4-9999-aaaa-bbbb-cccccccccccc'::uuid, 15),
                                                             (4, 'd1e2f3a4-dddd-eeee-ffff-000000000000'::uuid, 16);