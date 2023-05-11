create table SNS_MEMBER
(
    id int auto_increment,
    email varchar(20) not null,
    nickname varchar(20) not null,
    birthday date not null,
    createdAt datetime not null,
    constraint member_id_uindex
        primary key (id)
);

create table SNS_MEMBERNICKNAMEHISTORY
(
    id int auto_increment,
    memberId int not null,
    nickname varchar(20) not null,
    createdAt datetime not null,
    constraint memberNicknameHistory_id_uindex
        primary key (id)
);

create table SNS_FOLLOW
(
    id int auto_increment,
    fromMemberId int not null,
    toMemberId int not null,
    createdAt datetime not null,
    constraint Follow_id_uindex
        primary key (id)
);

create unique index Follow_fromMemberId_toMemberId_uindex
    on SNS_FOLLOW (fromMemberId, toMemberId);


create table SNS_POST
(
    id int auto_increment,
    memberId int not null,
    contents varchar(100) not null,
    createdDate date not null,
    createdAt datetime not null,
    constraint POST_id_uindex
        primary key (id)
);

create index POST__index_member_id
    on SNS_POST (memberId);

create index POST__index_created_date
    on SNS_POST (createdDate);

create index POST__index_member_id_created_date
    on SNS_POST (memberId, createdDate);

explain
select createdDate, memberId, count(*) cnt
FROM SNS_POST
    use index (POST__index_member_id_created_date)
where memberId = 9 and createdDate between '2000-01-01' and '2022-12-31'
group by memberId, createdDate
;

create table SNS_TIMELINE
(
    id int auto_increment,
    memberId int not null,
    postId int not null,
    createdAt datetime not null,
    constraint Timeline_id_vindex primary key (id)
);

create index Timeline__index_member_id
    on SNS_TIMELINE (memberId);

