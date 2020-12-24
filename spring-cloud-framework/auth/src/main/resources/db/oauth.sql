# token 采用数据库存储时使用
create table if not exists oauth_access_token
(
    token_id          varchar(256) null,
    token             blob         null,
    authentication_id varchar(256) not null primary key,
    user_name         varchar(256) null,
    client_id         varchar(256) null,
    authentication    blob         null,
    refresh_token     varchar(256) null
);
create table if not exists oauth_approvals
(
    userId         varchar(256) null,
    clientId       varchar(256) null,
    scope          varchar(256) null,
    status         varchar(10)  null,
    expiresAt      datetime     null,
    lastModifiedAt datetime     null
);
create table if not exists oauth_client_token
(
    token_id          varchar(256) null,
    token             blob         null,
    authentication_id varchar(256) not null primary key,
    user_name         varchar(256) null,
    client_id         varchar(256) null
);
create table if not exists oauth_code
(
    code           varchar(256) null,
    authentication blob         null
);
create table if not exists oauth_refresh_token
(
    token_id       varchar(256) null,
    token          blob         null,
    authentication blob         null
);
# token 采用数据库存储时使用 结束

# OAuth客户端存储（重要）
create table if not exists oauth_client_details
(
    client_id               varchar(256) not null primary key,
    resource_ids            varchar(256) null,
    client_secret           varchar(256) null,
    scope                   varchar(256) null,
    authorized_grant_types  varchar(256) null,
    web_server_redirect_uri varchar(256) null,
    authorities             varchar(256) null,
    access_token_validity   int          null,
    refresh_token_validity  int          null,
    additional_information  json         null,
    autoapprove             varchar(256) null
);
# 社区账号与本地账号关联表（重要）
create table if not exists oauth_social_user_connection
(
    user_id          varchar(255) not null,
    provider_id      varchar(255) not null,
    provider_user_id varchar(255) not null,
    `rank`           int          not null,
    display_name     varchar(255) null,
    profile_url      varchar(512) null,
    image_url        varchar(512) null,
    access_token     varchar(512) not null,
    secret           varchar(512) null,
    refresh_token    varchar(512) null,
    expire_time      bigint       null,
    primary key (user_id, provider_id, provider_user_id),
    constraint user_connection_rank unique (user_id, provider_id, `rank`)
);