SET FOREIGN_KEY_CHECKS = 0;

truncate table pet;
truncate table store;

INSERT into store(`id`, `name`, `location`, `contact_no`)
VALUES(21, 'super store', 'nassarawa', '09084883332');

INSERT INTO pet(`id`, `name`, `color`, `breed`, `age`, `pet_sex`, `store_id`)
VALUES (30, 'jill', 'blue', 'parrot', 6, 'MALE', 21),
(31, 'jack', 'black', 'dog', 2, 'MALE', 21),
(32, 'iclass', 'brown', 'goat', 32, 'FEMALE', 21),
(33, 'james', 'white', 'cat', 6, 'MALE', 21),
(34, 'Chip', 'black', 'pig', 6, 'MALE', 21),
(35, 'sally', 'brown', 'dog', 6, 'FEMALE', 21),
(36, 'san', 'blue', 'parrot', 6, 'MALE', 21);

SET FOREIGN_KEY_CHECKS = 1;