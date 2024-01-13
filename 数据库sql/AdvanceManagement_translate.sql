create table translate
(
    id     varchar(20)                    not null,
    time   varchar(30)                    not null
        primary key,
    from1  varchar(10)                    null,
    to1    varchar(10)                    null,
    q      varchar(10000) charset utf8mb3 null,
    result varchar(10000) charset utf8mb3 null
);

INSERT INTO AdvanceManagement.translate (id, time, from1, to1, q, result) VALUES ('admin', '2023-12-08_21:51:34', 'en', 'zh', 'hello', '你好');
