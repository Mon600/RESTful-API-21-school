CREATE TABLE image (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    image BYTEA NOT NULL
);


CREATE TABLE product (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(64) NOT NULL,
    category VARCHAR(64) NOT NULL,
    price INTEGER NOT NULL CHECK(price > 0),
    available_stock INTEGER NOT NULL CHECK(available_stock >= 0),
    last_update_date DATE,
    supplier_id uuid NOT NULL,
    image_id uuid,
    CONSTRAINT fk_supplier
        FOREIGN KEY(supplier_id)
        REFERENCES supplier(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_image
        FOREIGN KEY(image_id)
        REFERENCES image(id)
        ON DELETE SET NULL,
    CONSTRAINT uk_name_suppler_id UNIQUE(name, supplier_id)
);