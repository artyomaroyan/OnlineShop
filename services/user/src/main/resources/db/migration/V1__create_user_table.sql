create table if not exists users(
    id uuid not null unique primary key ,
    username varchar(16) unique not null ,
    password char(16) not null ,
    email varchar(254) not null unique ,
    role varchar(20) not null ,
    created_at timestamp default now(),
    active boolean default false
)