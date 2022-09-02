drop table if exists article_detail;
drop table if exists article3;

create table article3
(
    idx        bigint       not null auto_increment,
    subject    varchar(255) not null,
    version    integer      not null,
    view_count bigint       not null,
    primary key (idx)
) engine=InnoDB;

create table article_detail
(
    idx         bigint       not null auto_increment,
    contents    longtext     not null,
    subject     varchar(255) not null,
    version     integer      not null,
    article_idx bigint,
    primary key (idx)
) engine=InnoDB;

alter table article_detail
    add constraint FKmngl6233uqt3bdljr8n6402pv
        foreign key (article_idx)
            references article3 (idx);