create sequence P_POST_ID_SEQ start 1 increment  1;
create table P_POST (
ID bigserial,
P_TOPIC varchar(255) not null,
P_MESSAGE varchar(12) not null,
primary key (ID)
);