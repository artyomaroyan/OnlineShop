create table if not exists users(
    id uuid not null unique primary key ,
    username varchar(16) unique not null ,
    password varchar(500) not null ,
    email varchar(254) not null unique ,
    roles varchar(100) not null ,
    created_at timestamp default now(),
    active boolean default false
)