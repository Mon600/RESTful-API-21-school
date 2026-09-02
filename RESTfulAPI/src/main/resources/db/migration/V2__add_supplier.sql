CREATE TABLE supplier (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(64) NOT NULL,
    address_id uuid NOT NULL,
    phone_number VARCHAR(12) NOT NULL UNIQUE,
    CONSTRAINT fk_address_supplier
        FOREIGN KEY(address_id)
        REFERENCES address(id)
        ON DELETE CASCADE,
    CONSTRAINT uk_name_address_id_phone_supplier UNIQUE(name, address_id, phone_number)
);