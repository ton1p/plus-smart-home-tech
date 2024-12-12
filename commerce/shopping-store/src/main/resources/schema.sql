create table if not exists public.products
(
    product_id       char(255) unique primary key,
    product_name     char(250),
    description      char(250),
    image_src        char(250),
    quantity_state   char(6),
    product_state    char(10),
    rating           bigint,
    product_category char(8),
    price            float
);
