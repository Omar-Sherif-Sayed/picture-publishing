INSERT INTO
    picture_publishing.users (id,
                              created_date,
                              username,
                              email,
                              password,
                              type)
VALUES (1,
        CURRENT_TIMESTAMP,
        'admin',
        'admin',
        'admin123',
        1) ON
conflict do nothing;
