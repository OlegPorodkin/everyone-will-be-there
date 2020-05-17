create sequence hibernate_sequence start 1 increment 1;

create table bus_station
(
    id         int8 not null,
    address    varchar(255),
    end_time   time,
    name       varchar(255),
    phone      varchar(255),
    start_time time,
    city_id    int8,
    primary key (id)
);

create table bus_station_voyages
(
    bus_station_id int8 not null,
    voyages_id     int8 not null,
    primary key (bus_station_id, voyages_id)
);

create table city
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table city_bus_stations
(
    city_id         int8 not null,
    bus_stations_id int8 not null,
    primary key (city_id, bus_stations_id)
);

create table day_of_week
(
    voyage_id        int8 not null,
    day_of_departure varchar(255)
);

create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table usr
(
    id       int8    not null,
    active   boolean not null,
    address  varchar(255),
    email    varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

create table voyage
(
    id                      int8 not null,
    price                   int8,
    travel_time             time,
    travel_time_departure   varchar(255),
    travel_time_destination varchar(255),
    point_of_departure_id   int8,
    point_of_destination_id int8,
    primary key (id)
);

alter table if exists bus_station_voyages
    add constraint UK_bus_station_voyages_voyages_id unique (voyages_id);

alter table if exists city_bus_stations
    add constraint UK_city_bus_stations_bus_stations_id unique (bus_stations_id);

alter table if exists bus_station
    add constraint bus_station_city_id foreign key (city_id) references city;

alter table if exists bus_station_voyages
    add constraint bus_station_voyages_voyages_id foreign key (voyages_id) references voyage;

alter table if exists bus_station_voyages
    add constraint bus_station_voyages_bus_station_id foreign key (bus_station_id) references bus_station;

alter table if exists city_bus_stations
    add constraint city_bus_stations_bus_stations_id foreign key (bus_stations_id) references bus_station;

alter table if exists city_bus_stations
    add constraint city_bus_stations_city_id foreign key (city_id) references city;

alter table if exists day_of_week
    add constraint day_of_week_voyage_id foreign key (voyage_id) references voyage;

alter table if exists user_role
    add constraint user_role_user_id foreign key (user_id) references usr;

alter table if exists voyage
    add constraint voyage_point_of_departure_id foreign key (point_of_departure_id) references bus_station;

alter table if exists voyage
    add constraint voyage_point_of_destination_id foreign key (point_of_destination_id) references bus_station;
