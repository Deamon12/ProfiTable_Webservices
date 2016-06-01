/**
 * Database schema for ProfiTable mobile application
 * Author: Eric Gremban
 * Last update: 20160601
 */

/* Drop any versions of this database which existed previously */
DROP TABLE IF EXISTS Restaurant CASCADE;
DROP TABLE IF EXISTS Loc_Category CASCADE;
DROP TABLE IF EXISTS Employee CASCADE;
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Location CASCADE;
DROP TABLE IF EXISTS Tab CASCADE;
DROP TABLE IF EXISTS Discount CASCADE;
DROP TABLE IF EXISTS Menu_item CASCADE;
DROP TABLE IF EXISTS Category CASCADE;
DROP TABLE IF EXISTS Item CASCADE;
DROP TABLE IF EXISTS Food_attribute CASCADE;
DROP TABLE IF EXISTS Has_order CASCADE;
DROP TABLE IF EXISTS Has_disc CASCADE;
DROP TABLE IF EXISTS Has_cust CASCADE;
DROP TABLE IF EXISTS Ordered_item CASCADE;
DROP TABLE IF EXISTS Ordered_with CASCADE;
DROP TABLE IF EXISTS Has_attr CASCADE;
DROP TABLE IF EXISTS Has_cat CASCADE;

CREATE TABLE Restaurant (
   rest_id        BIGSERIAL,
   name           VARCHAR(50)    NOT NULL,
   CHECK (name <> ''),
   PRIMARY KEY (rest_id)
);

CREATE TABLE Employee (
   emp_id         BIGSERIAL,
   account_name   VARCHAR(50)    NOT NULL UNIQUE,
   emp_type       VARCHAR(20)    NOT NULL,
   first_name     VARCHAR(50)    NOT NULL,
   last_name      VARCHAR(50)    NOT NULL,
   password       VARCHAR(255)   NOT NULL,
   curr_device    VARCHAR(512)   NULL,
   restaurant     BIGINT,
   CHECK (account_name <> ''),
   CHECK (first_name <> ''),
   CHECK (last_name <> ''),
   CHECK (password <> ''),
   FOREIGN KEY (restaurant)      REFERENCES Restaurant(rest_id),
   PRIMARY KEY (emp_id)
);

CREATE TABLE Loc_Category (
   loccat_id      BIGSERIAL,
   name           VARCHAR(50)    NOT NULL,
   restaurant     BIGINT,
   CHECK (name <> ''),
   FOREIGN KEY (restaurant)      REFERENCES Restaurant(rest_id),
   PRIMARY KEY (loccat_id)
);

/*
Location status options: inprogress, completed
*/
CREATE TABLE Tab (
   tab_id         BIGSERIAL,
   tab_status     VARCHAR(20)    NOT NULL,
   time_in        TIMESTAMP,
   time_out       TIMESTAMP,
   PRIMARY KEY (tab_id)
);

/*
Location status options: occupied, available
*/
CREATE TABLE Location (
   loc_id         BIGSERIAL,
   loc_status     VARCHAR(20)    NOT NULL,
   name           VARCHAR(50)    NOT NULL,
   CHECK (name <> ''),
   loc_cat        BIGINT         NOT NULL,
   FOREIGN KEY (loc_cat)         REFERENCES Loc_Category(loccat_id),
   curr_tab       BIGINT,
   FOREIGN KEY (curr_tab)         REFERENCES Tab(tab_id),
   restaurant     BIGINT,
   FOREIGN KEY (restaurant)      REFERENCES Restaurant(rest_id),
   PRIMARY KEY (loc_id)
);

CREATE TABLE Customer (
   cust_id        BIGSERIAL,
   order_id       BIGINT,
   FOREIGN KEY (order_id)      REFERENCES Tab(tab_id),
   PRIMARY KEY (cust_id)
);

CREATE TABLE Discount (
   disc_id        BIGINT,
   disc_type      VARCHAR(50)    NOT NULL,
   disc_percent   FLOAT          NOT NULL,
   Available      BOOLEAN        NOT NULL,
   restaurant     BIGINT,
   CHECK (disc_type <> ''),
   CHECK (disc_percent>=0.0),
   CHECK (disc_percent<=1.0),
   FOREIGN KEY (restaurant)      REFERENCES Restaurant(rest_id),
   PRIMARY KEY (disc_id)
);

CREATE TABLE Menu_item (
   menu_id        BIGSERIAL,
   menu_name      VARCHAR(100)    NOT NULL,
   description    VARCHAR(300)   NOT NULL,
   price          SMALLINT       NOT NULL,
   Available      BOOLEAN        NOT NULL,
   restaurant     BIGINT,
   CHECK (menu_name <> ''),
   CHECK (price>=0),
   FOREIGN KEY (restaurant)      REFERENCES Restaurant(rest_id),
   PRIMARY KEY (menu_id)
);

/*
Item status options: ordered, cooking, ready, delivered, canceled
*/
CREATE TABLE Item (
   item_id        BIGSERIAL,
   notes          VARCHAR(100),
   item_status    VARCHAR(20)    NOT NULL,
   bring_first    BOOLEAN        NOT NULL,
   PRIMARY KEY (item_id)
);

CREATE TABLE Category (
   cat_id         BIGSERIAL,
   cat_name       VARCHAR(20)    NOT NULL,
   restaurant     BIGINT,
   CHECK (cat_name <> ''),
   FOREIGN KEY (restaurant)      REFERENCES Restaurant(rest_id),
   PRIMARY KEY (cat_id)
);

CREATE TABLE Food_attribute (
   attr_id        BIGSERIAL,
   attribute      VARCHAR(50)    NOT NULL,
   price_mod      SMALLINT       NOT NULL,
   Available      BOOLEAN        NOT NULL,
   restaurant     BIGINT,
   CHECK (attribute <> ''),
   CHECK (price_mod>=0),
   FOREIGN KEY (restaurant)      REFERENCES Restaurant(rest_id),
   PRIMARY KEY (attr_id)
);


/* TODO: For relational tables, do we want to cascade updates or deletes? */
CREATE TABLE Has_order (
   loc_id         BIGINT,
   order_id       BIGINT,
   emp_id         BIGINT,
   PRIMARY KEY (order_id),
   FOREIGN KEY (loc_id)         REFERENCES Location(loc_id),
   FOREIGN KEY (order_id)       REFERENCES Tab(tab_id),
   FOREIGN KEY (emp_id)         REFERENCES Employee(emp_id)
);

CREATE TABLE Has_disc (
   disc_id         BIGINT,
   order_id        BIGINT,
   FOREIGN KEY (disc_id)        REFERENCES Discount(disc_id),
   FOREIGN KEY (order_id)       REFERENCES Tab(tab_id)
);

CREATE TABLE Has_cust (
   cust_id         BIGINT,
   order_id        BIGINT,
   FOREIGN KEY (cust_id)        REFERENCES Customer(cust_id),
   FOREIGN KEY (order_id)       REFERENCES Tab(tab_id)
);

CREATE TABLE Ordered_item (
   item_id         BIGINT,
   cust_id         BIGINT,
   menu_id         BIGINT,
   FOREIGN KEY (item_id)        REFERENCES Item(item_id),
   FOREIGN KEY (cust_id)        REFERENCES Customer(cust_id),
   FOREIGN KEY (menu_id)        REFERENCES Menu_item(menu_id)
);

CREATE TABLE Ordered_with (
   item_id         BIGINT,
   attr_id         BIGINT,
   FOREIGN KEY (item_id)        REFERENCES Item(item_id),
   FOREIGN KEY (attr_id)        REFERENCES Food_attribute(attr_id)
);

CREATE TABLE Has_attr (
   menu_id         BIGINT,
   attr_id         BIGINT,
   default_incl    BOOLEAN NOT NULL,
   FOREIGN KEY (menu_id)        REFERENCES Menu_item(menu_id),
   FOREIGN KEY (attr_id)        REFERENCES Food_attribute(attr_id)
);

CREATE TABLE Has_cat (
   menu_id         BIGINT,
   cat_id          BIGINT,
   FOREIGN KEY (menu_id)        REFERENCES Menu_item(menu_id),
   FOREIGN KEY (cat_id)         REFERENCES Category(cat_id)
);

/* 
 * ####################
 * ####################
 * # Insert test data # 
 * ####################
 * ####################
 */
BEGIN TRANSACTION;
/* 1 */
INSERT INTO Restaurant (name) 
   VALUES('The Carlito Diner');
/* 2 */
INSERT INTO Restaurant (name) 
   VALUES('Turkish Pizza Parlour');
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Employee (emp_type, account_name, first_name, last_name, restaurant, password) 
   VALUES('Manager','bigX','Wayne','Static',1,'password');
/* 2 */
INSERT INTO Employee (emp_type, account_name, first_name, last_name, restaurant, password) 
   VALUES('Wait','pinkie','Nicki','Minaj',1,'password');
/* 3 */
INSERT INTO Employee (emp_type, account_name, first_name, last_name, restaurant, password) 
   VALUES('Wait','lilB','Justin','Bieber',1,'password');
/* 4 */
INSERT INTO Employee (emp_type, account_name, first_name, last_name, restaurant, password) 
   VALUES('Food','theSpice','Enrique','Iglesias',1,'password');
/* 5 */
INSERT INTO Employee (emp_type, account_name, first_name, last_name, restaurant, password) 
   VALUES('Food','spiderwebs','Gwen','Stefani',1,'password');
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Tab (tab_status) 
   VALUES('InProgress');
/* 2 */   
INSERT INTO Tab (tab_status) 
   VALUES('complete');
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Customer (order_id) 
   VALUES(1);
/* 2 */
INSERT INTO Customer (order_id) 
   VALUES(1);
/* 3 */
INSERT INTO Customer (order_id) 
   VALUES(2);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Loc_Category (name, restaurant) 
   VALUES('table',1);
/* 2 */
INSERT INTO Loc_Category (name, restaurant) 
   VALUES('bar',1);
/* 3 */
INSERT INTO Loc_Category (name, restaurant) 
   VALUES('take out',1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Pair table A',1,1);
/* 2 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant,curr_tab) 
   VALUES('occupied','barseat A',2,1,1);
/* 3 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','corner booth',1,1);
/* 4 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant,curr_tab) 
   VALUES('occupied','table for 8',1,1,2);
/* 5 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','order184-Shamim',3,1);
/* 6 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Pair table F',1,1);
/* 7 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Pair table B',1,1);
/* 8 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Pair table C',1,1);
/* 9 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Pair table D',1,1);
/* 10 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Pair table E',1,1);
/* 11 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table A',1,1);
/* 12 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table B',1,1);
/* 13 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table C',1,1);
/* 14 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table D',1,1);
/* 15 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Table for 6',1,1);
/* 16 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','order185-Greg',3,1);
/* 17 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','order186-Charles',3,1);
/* 18 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','order187-Tracy',3,1);
/* 19 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat B',2,1);
/* 20 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat C',2,1);
/* 21 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat D',2,1);
/* 22 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat E',2,1);
/* 23 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat F',2,1);
/* 24 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat G',2,1);
/* 25 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table E',1,1);
/* 26 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table F',1,1);
/* 27 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table G',1,1);
/* 28 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table H',1,1);
/* 29 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table I',1,1);
/* 30 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table J',1,1);
/* 31 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table K',1,1);
/* 32 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table L',1,1);
/* 33 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table M',1,1);
/* 34 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table N',1,1);
/* 35 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('available','Quad table O',1,1);
/* 36 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat H',2,1);
/* 37 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat I',2,1);
/* 38 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat J',2,1);
/* 39 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat K',2,1);
/* 40 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat L',2,1);
/* 41 */
INSERT INTO Location (loc_status, name, loc_cat, restaurant) 
   VALUES('occupied','Barseat M',2,1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Discount (disc_id, disc_type, disc_percent, Available, restaurant) 
   VALUES(1,'student',0.10,TRUE,1);
/* 2 */
INSERT INTO Discount (disc_id, disc_type, disc_percent, Available, restaurant) 
   VALUES(2,'veteran',0.20,TRUE,1);
/* 3 */
INSERT INTO Discount (disc_id, disc_type, disc_percent, Available, restaurant) 
   VALUES(3,'Complementary Meal',1.0,TRUE,1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Cheeseburger','Classic cheeseburger',200,TRUE,1);
/* 2 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Hambuger','Classic quarter pound burger',150,TRUE,1);
/* 3 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('French Fries','Yukon Gold, lightly seasoned',100,TRUE,1);
/* 4 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Soda','Free Refills',110,TRUE,1);
/* 5 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Vanilla Milk Shake','Traditional favorite',230,FALSE,1);
/* 6 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Chicken Tacos plate','Pollo asada, 3x with rice and beans',475,TRUE,1);
/* 7 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Acai Smoothie','Blended with blueberries, boysenberries, bananas and strawberries',525,TRUE,1);
/* 8 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Tropical Storm Smoothie','Pineapple juice, bananas, mangoes, and papaya. Topped with coconut',545,TRUE,1);
/* 9 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Piping Papaya Smoothie','Apple juice, peaches, raspberries, strawberries and spirulina',575,TRUE,1);
/* 10 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Just Peachie Smoothie','Apple juice, bananas and peaches',475,TRUE,1);
/* 11 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('PopEyes Punch Juice','Carrot and spinach.  Lots of spinach!',345,TRUE,1);
/* 12 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Busy Bee Juice','Orange juice, bananas, papaya, bee pollen',355,TRUE,1);
/* 13 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Guacamole Turkey Club','Grilled turkey, bacon, Swiss cheese, guacamole, lettuce, tomato and onions',835,TRUE,1);
/* 14 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Chicken Swiss Melt','Fresh Grilled chicken, bacon, Swiss, lettuce, tomato, onoins, grilled mushrooms, mayo on sourdough bread',775,TRUE,1);
/* 15 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('The Ultimate Veggie Sandwich','Lettuce, tomato, avocado, onions, cucumbers, cabbage, mushrooms, bell peppers, Swiss cheese, sprouts, mayo, carrots, basil dressing',699,TRUE,1);
/* 16 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Chips and Salsa','Corn chips',275,TRUE,1);
/* 17 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Garden Salad','Light and refreshing',295,TRUE,1);
/* 18 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Fruit Plate','Seasonal fruit choice, Light and refreshing',315,TRUE,1);
/* 19 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Double decker cheeseburger','The big one!',275,TRUE,1);
/* 20 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Protein style hambuger','lettuce wrapped beef patty',275,TRUE,1);
/* 21 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Oatmeal','Served with milk, brown sugar, raisins and bananas',275,TRUE,1);
/* 22 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Strawberry Milk Shake','Traditional favorite',230,FALSE,1);
/* 23 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Chocolate Milk Shake','Traditional favorite',230,FALSE,1);
/* 24 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Granola and Fruit','Served with milk, yogurt or soy milk and fruit',235,TRUE,1);
/* 25 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Light Breakfast','Fruit, cottage cheese and toast, with sauteed tomatoes, cheddar cheese, onion, jalapeños, guacamole and bell pepper',495,TRUE,1);
/* 26 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Black Egg Burrito','Black Beans, avocado and mixed cheeses',615,TRUE,1);
/* 27 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Veggie Egg Burrito','Sauteed mixed vegetables (zucchini, broccoli, avocados, carrots) and mixed cheeses',675,TRUE,1);
/* 28 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Cancun Breakfast Burrito','Sautéed tomatoes, cheddar cheese, onion, jalapeños, guacamole and bell pepper',775,TRUE,1);
/* 29 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Sante Fe Turkey Omelette','Ortega chilies, green onion, tomatoes, pepper jack cheese, turkey',925,TRUE,1);
/* 30 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Garlic Chicken Omelette','Marinated chicken breast in garlic sauce, w Swiss cheese',1025,FALSE,1);
/* 31 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Veggie Rama Omelette','Sautéed mushrooms, broccoli, zucchini, bell peppers, onion, alfalfa sprouts, avocado, mixed cheddar cheese',1100,FALSE,1);
/* 32 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Bottled Water','Overpriced and wastes plastic!',710,TRUE,1);
/* 33 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Horchata','South of the border rice beverage favorite',110,TRUE,1);
/* 34 */
INSERT INTO Menu_item (menu_name, description, price, Available, restaurant) 
   VALUES('Pina','South of the border pineapple favorite!',110,TRUE,1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Burgers',1);
/* 2 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Popular',1);
/* 3 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Drinks',1);
/* 4 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Sides',1);
/* 5 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Main course',1);
/* 6 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Breakfast',1);
/* 7 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Breakfast Burritos',1);
/* 8 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Sandwiches',1);
/* 9 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Smoothies',1);
/* 10 */
INSERT INTO Category (cat_name, restaurant) 
   VALUES('Shakes',1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Lettuce',0,TRUE,1);
/* 2 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Onion - fresh',0,TRUE,1);
/* 3 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Onion - grilled',0,TRUE,1);
/* 4 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Tomato',0,TRUE,1);
/* 5 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Guacamole',135,TRUE,1);
/* 6 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Salsa',0,TRUE,1);
/* 7 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Salt',0,TRUE,1);
/* 8 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Pepper',0,TRUE,1);
/* 9 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Cherry',0,TRUE,1);
/* 10 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Ice',135,TRUE,1);
/* 11 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Swiss Cheese',0,TRUE,1);
/* 12 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Mustard',0,TRUE,1);
/* 13 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Ketchup',0,TRUE,1);
/* 14 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Peanut Butter',100,TRUE,1);
/* 15 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Protein Powder',150,TRUE,1);
/* 16 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Vegan Protein Powder',200,TRUE,1);
/* 17 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Hummus',0,TRUE,1);
/* 18 */
INSERT INTO Food_attribute (attribute, price_mod, Available, restaurant) 
   VALUES('Berries',135,TRUE,1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
/* 1 */
INSERT INTO Item (item_status, bring_first) 
   VALUES('delivered', FALSE);
/* 2 */
INSERT INTO Item (item_status, bring_first) 
   VALUES('delivered', FALSE);
/* 3 */
INSERT INTO Item (item_status, notes, bring_first) 
   VALUES('cooking', 'add in chipotle mayo', FALSE);
/* 4 */
INSERT INTO Item (item_status, bring_first) 
   VALUES('cooking', TRUE);
/* 5 */
INSERT INTO Item (item_status, bring_first) 
   VALUES('ready', FALSE);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Has_order (loc_id, order_id, emp_id) 
   VALUES(2,1,2);
INSERT INTO Has_order (loc_id, order_id, emp_id) 
   VALUES(4,2,3);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Has_disc (disc_id, order_id) 
   VALUES(2,1);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Ordered_item (item_id, menu_id, cust_id) 
   VALUES(1,1,1);
INSERT INTO Ordered_item (item_id, menu_id, cust_id) 
   VALUES(2,1,2);
INSERT INTO Ordered_item (item_id, menu_id, cust_id) 
   VALUES(3,2,3);
INSERT INTO Ordered_item (item_id, menu_id, cust_id) 
   VALUES(4,4,1);
INSERT INTO Ordered_item (item_id, menu_id, cust_id) 
   VALUES(5,5,2);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(1,1);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(1,2);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(1,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(2,1);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(2,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(3,2);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(3,4);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(4,2);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(4,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(5,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(6,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(7,2);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(7,9);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(7,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(8,9);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(8,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(9,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(9,9);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(10,2);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(10,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(10,9);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(11,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(12,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(13,8);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(13,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(14,8);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(14,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(15,8);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(15,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(16,4);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(17,4);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(18,4);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(19,1);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(19,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(20,1);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(20,5);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(21,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(21,4);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(22,10);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(23,10);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(5,10);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(22,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(23,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(24,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(25,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(26,7);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(26,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(26,2);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(27,7);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(27,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(28,7);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(28,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(29,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(30,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(29,2);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(31,6);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(32,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(33,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(34,3);
INSERT INTO Has_cat (menu_id, cat_id) 
   VALUES(33,2);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 1, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 2, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 4, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 11, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(2, 1, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(2, 2, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(2, 4, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(3, 7, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(3, 8, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(3, 12, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(3, 13, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(3, 11, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 3, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 5, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 12, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(1, 13, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(2, 3, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(2, 5, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(2, 12, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(2, 13, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(4, 10, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(5, 9, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(6, 1, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(6, 3, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(6, 2, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(6, 5, FALSE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(6, 11, TRUE);
INSERT INTO Has_attr (menu_id, attr_id, default_incl) 
   VALUES(6, 4, TRUE);
COMMIT;
END TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(1,1);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(1,3);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(1,4);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(1,5);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(1,11);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(2,1);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(2,12);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(2,13);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(2,11);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(3,1);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(3,4);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(3,12);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(3,13);
INSERT INTO Ordered_with (item_id, attr_id) 
   VALUES(4,10);
COMMIT;
END TRANSACTION;

/* 
 * Example Query:
 *
 * Searching for all ordered items with 
 * their location and status
 */
select 
   l.loc_id, 
   l.name, 
   t.tab_status, 
   c.cust_id, 
   mi.menu_name
from
   location l, 
   tab t, 
   customer c, 
   has_order ho, 
   ordered_item oi, 
   menu_item mi
where
   ho.loc_id=l.loc_id
   AND
   ho.order_id=t.tab_id
   AND
   c.order_id=t.tab_id
   AND
   l.restaurant=1
   AND
   oi.cust_id=c.cust_id
   AND
   mi.menu_id=oi.menu_id
order by c.cust_id ASC;
/* should produce:
2  "barseat 4"    "inprogress"  1  "cheeseburger"
2  "barseat 4"    "inprogress"  1  "soda"
2  "barseat 4"    "inprogress"  2  "cheeseburger"
2  "barseat 4"    "inprogress"  2  "milkshake"
4  "table for 8"  "complete"    3  "burger"
*/