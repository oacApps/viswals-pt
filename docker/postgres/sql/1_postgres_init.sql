CREATE TABLE users_data
(
    id             int4 NOT NULL, -- id
    first_name     varchar NULL,
    last_name      varchar NULL,
    email_address  varchar NULL,
    created_at     int8 NULL,
    deleted_at     int8 NULL,
    merged_at      int8 NULL,
    parent_user_id int4 NULL,
    CONSTRAINT users_data_pk PRIMARY KEY (id)
);
CREATE INDEX users_data_first_name_last_name_email_address_index ON public.users_data USING btree (first_name, last_name, email_address);

