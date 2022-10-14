-- Helper script to create the tables IN POSTGRES if we want to control that manually (we must disable spring configuration then)
CREATE TABLE IF NOT EXISTS amenity (
  id SERIAl PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS address (
  id SERIAL PRIMARY KEY,
  street_name VARCHAR(255) NOT NULL,
  state_name VARCHAR(255) NOT NULL,
  country_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotel (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  rating INT,
  address_id INT NOT NULL,

  CONSTRAINT fk_hotel_address_id FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE IF NOT EXISTS hotel_amenities (
  hotel_id INT NOT NULL,
  amenities_id INT NOT NULL,

  CONSTRAINT fk_ha_pk PRIMARY KEY (hotel_id, amenity_id),
  CONSTRAINT fk_ha_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotel (id),
  CONSTRAINT fk_ha_amenity_id FOREIGN KEY (amenities_id) REFERENCES amenity (id)
);
