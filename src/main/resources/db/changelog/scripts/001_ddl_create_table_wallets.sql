create table wallets(
    id uuid primary key,
    balance bigint not null default 0
);