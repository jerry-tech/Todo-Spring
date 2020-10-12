CREATE TABLE IF NOT EXISTS `user` (
	`user_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`username`	TEXT NOT NULL UNIQUE,
	`password`	TEXT NOT NULL,
	`email_add`	TEXT NOT NULL
);
INSERT INTO `user` (user_id,username,password,email_add) VALUES (2,'udoh','$2a$10$57gwRvoZuPaZiWMosgLUr.HBc3gXHc0r6ujBCrl3Gm4.UKtQdOQOS','ujeremiah200@gmail.com'),
 (3,'jeryy','$2a$10$gnit8R6hZnOFZZL77uC8seZFT34cyy3zqrLczqP7j3VSFij4LqeL2','ujeremiah200@gmail.com'),
 (4,'jeryyb','$2a$10$HR2qr4WH2gJAMJYMVONpquL7BTAJtU70v4WD47VZjtXEjZ/LbWTka','ujeremiah200@gmail.com'),
 (5,'Udoh','$2a$10$zXTnI144UjFpDUBYlcQLkeBgSxY.X29XUtK31f5u3GQQW1SQZlWzm','ujeremiah200@gmail.com'),
 (7,'Jeremiah','$2a$10$tZr4b4H7tQZilbd72Kepc.Gp/rIci9N9G8Q4HKRopD4sA0keiUN92','ujeremiah200@gmail.com');
CREATE TABLE IF NOT EXISTS `todo` (
	`todo_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`rating`	TEXT,
	`content`	TEXT NOT NULL,
	`creation_date`	TEXT DEFAULT current_timestamp,
	`user_id`	INTEGER,
	`status`	TEXT,
	FOREIGN KEY(`user_id`) REFERENCES `user` on delete cascade
);
INSERT INTO `todo` (todo_id,name,rating,content,creation_date,user_id,status) VALUES (3,'mystuff','4.6','None of jerry business','2020-09-15 23:47:02',7,'ongoing'),
 (4,'Sunday','4.9','None of jerry sunday','2020-09-17 23:40:09',2,'ongoing'),
 (5,'Monday','4.9','None of jerry sunday','2020-10-04 21:43:53',7,'ongoing'),
 (6,'yunus','4.9','yeter good','2020-10-04 21:45:27',2,'completed'),
 (7,'Goodnews','4.4','None of good sunday','2020-10-05 11:37:19',2,NULL),
 (8,'Goodnews','4.4','None of good sunday','2020-10-05 11:42:27',2,NULL),
 (9,'Goodnews','4.4','None of good sunday','2020-10-05 11:43:10',2,NULL);
COMMIT;
