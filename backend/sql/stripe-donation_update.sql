-- Dec 31
ALTER TABLE `orders` ADD `donation_id` INT NOT NULL AFTER `user_id`;

-- Jan 16
ALTER TABLE `donations` ADD `confirmation_email` VARCHAR(10000) NOT NULL AFTER `confirmation_message`;