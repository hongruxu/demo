CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_name` varchar(50) NOT NULL,
  `age` int NOT NULL DEFAULT 0,
  `email` varchar(100) NOT NULL DEFAULT ''
); 

CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `balance` bigint NOT NULL DEFAULT '0',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP
);  


CREATE TABLE IF NOT EXISTS `transfer_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `from_account` int NOT NULL DEFAULT '0',
  `to_account` int NOT NULL DEFAULT '0',
  `amount` int NOT NULL DEFAULT '0',
  `from_balance` int NOT NULL DEFAULT '0',
  `to_balance` int NOT NULL DEFAULT '0',
  `op_time` datetime DEFAULT CURRENT_TIMESTAMP
) ;