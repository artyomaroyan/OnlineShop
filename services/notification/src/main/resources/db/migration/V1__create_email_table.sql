create table if not exists notification.email(
    id uuid unique not null primary key ,
    mail_to varchar(254) not null ,
    mail_from varchar(254) not null ,
    email_type varchar(50) not null ,
    send_date date not null
);