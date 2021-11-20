create table if not exists users
(
    id integer not null generated always as identity,
    primary key (id)
);

create table if not exists predictions
(
    id                        integer not null generated always as identity,
    user_id                   integer not null references users on delete restrict,
    predicted_number_of_likes integer not null,
    primary key (id),
    check (predicted_number_of_likes >= 0)
);

insert into users default
values;

insert into predictions(user_id, predicted_number_of_likes)
values (1, 56);
insert into predictions(user_id, predicted_number_of_likes)
values (1, 24);