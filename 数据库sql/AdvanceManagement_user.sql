create table user
(
    id       varchar(20) not null
        primary key,
    password varchar(20) not null,
    isAdmin  int         not null,
    checked  int         not null,
    tUsed    int         not null,
    tLast    int         not null,
    pUsed    int         not null,
    pLast    int         not null
);

INSERT INTO AdvanceManagement.user (id, password, isAdmin, checked, tUsed, tLast, pUsed, pLast) VALUES ('admin', '1234', 1, 1, 99, 4901, 5, 45);
INSERT INTO AdvanceManagement.user (id, password, isAdmin, checked, tUsed, tLast, pUsed, pLast) VALUES ('cxk', '1234', 0, 1, 82, 4918, 2, 48);
INSERT INTO AdvanceManagement.user (id, password, isAdmin, checked, tUsed, tLast, pUsed, pLast) VALUES ('hhh', '1', 0, 1, 0, 5000, 0, 50);
INSERT INTO AdvanceManagement.user (id, password, isAdmin, checked, tUsed, tLast, pUsed, pLast) VALUES ('men', '1234', 0, 1, 208, 4792, 1, 49);