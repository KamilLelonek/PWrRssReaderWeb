# --- First database schema
 
# --- !Ups
 
CREATE TABLE feeds (
  title               varchar(4096) not null,
  link                varchar(4096) not null,
  description         varchar(4096) not null,
  channel             bigint not null,
  date                bigint not null,
  image               varchar(100000) not null
);
 
# --- !Downs
 
DROP TABLE IF EXISTS feeds;