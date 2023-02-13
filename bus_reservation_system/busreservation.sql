CREATE TABLE bus(
    bus_id INT AUTO_INCREMENT PRIMARY KEY,
    driver_id INT,
    route_id INT,
    date Date,
    departure_time TIME,
    arrival_time TIME,
    bus_ticket_price DECIMAL(10,2),
    number_of_seats INT
);

CREATE TABLE route(
    route_id INT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL
);

CREATE TABLE customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    address_id INT(255) NOT NULL
);

CREATE TABLE customer_management (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE bus_driver (
    bus_driver_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    address_id INT(255) NOT NULL,
    assigned_bus INT(255)
);

CREATE TABLE address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    street_address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL
);


CREATE TABLE reservation (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    seat INT,
    bus_id INT
);



ALTER TABLE bus ADD FOREIGN KEY (driver_id) REFERENCES bus_driver(bus_driver_id);
ALTER TABLE bus ADD FOREIGN KEY (route_id) REFERENCES route(route_id);

ALTER TABLE bus_driver ADD FOREIGN KEY (address_id) REFERENCES address(address_id);
ALTER TABLE bus_driver ADD FOREIGN KEY (assigned_bus) REFERENCES bus(bus_id);
ALTER TABLE customer ADD FOREIGN KEY (address_id) REFERENCES address(address_id);
ALTER TABLE reservation ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id);
ALTER TABLE reservation ADD FOREIGN KEY (bus_id) REFERENCES bus(bus_id);
ALTER TABLE table_name
ADD COLUMN gender CHAR(1) AFTER last_name;
