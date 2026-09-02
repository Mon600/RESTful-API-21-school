CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE', 'NOT_SPECIFIED');


CREATE TABLE address (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    country VARCHAR(32) NOT NULL,
    city VARCHAR(64) NOT NULL,
    street VARCHAR(128) NOT NULL,

    CONSTRAINT uk_country_city_street UNIQUE(country, city, street)
);

CREATE TABLE client (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    client_name VARCHAR(64) NOT NULL,
    client_surname VARCHAR(64) DEFAULT NULL,
    birthday DATE NOT NULL,
    gender gender_enum DEFAULT 'NOT_SPECIFIED',
    registration_date TIMESTAMPTZ,
    address_id uuid NOT NULL,
    CONSTRAINT fk_address_client
        FOREIGN KEY(address_id)
        REFERENCES address(id)
        ON DELETE CASCADE,

    CONSTRAINT check_birthday_past CHECK(birthday BETWEEN '1900-01-01' AND CURRENT_DATE),
    CONSTRAINT uk_name_surname_birthday_address_id UNIQUE(client_name, client_surname, birthday, address_id)
);








