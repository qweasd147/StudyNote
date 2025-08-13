drop table if exists article_detail;
drop table if exists article;

create table article
(
    idx        bigint       not null auto_increment,
    subject    varchar(255) not null,
    version    integer      not null,
    view_count bigint       not null,
    primary key (idx)
);

create table article_detail
(
    idx         bigint        not null auto_increment,
    contents    varchar(1000) not null,
    subject     varchar(255)  not null,
    version     integer       not null,
    article_idx bigint,
    primary key (idx)
);

alter table article_detail
    add constraint fk_articledetail_article
        foreign key (article_idx)
            references article (idx);