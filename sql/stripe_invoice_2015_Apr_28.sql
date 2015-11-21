-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 28, 2015 at 11:15 AM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `stripe_invoice`
--

-- --------------------------------------------------------

--
-- Table structure for table `amounts`
--

CREATE TABLE IF NOT EXISTS `amounts` (
`id` int(11) NOT NULL,
  `donation_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL COMMENT 'in USD.',
  `description` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Define the payment amount of Donations.';

--
-- Dumping data for table `amounts`
--

INSERT INTO `amounts` (`id`, `donation_id`, `amount`, `description`) VALUES
(1, 3, 23, 'df'),
(2, 3, 0, 'sdf'),
(3, 3, 2, 'dsfdf');

-- --------------------------------------------------------

--
-- Table structure for table `app_sessions`
--

CREATE TABLE IF NOT EXISTS `app_sessions` (
  `session_id` varchar(40) NOT NULL DEFAULT '0',
  `ip_address` varchar(45) NOT NULL DEFAULT '0',
  `user_agent` varchar(120) NOT NULL,
  `last_activity` int(10) unsigned NOT NULL DEFAULT '0',
  `user_data` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `app_sessions`
--

INSERT INTO `app_sessions` (`session_id`, `ip_address`, `user_agent`, `last_activity`, `user_data`) VALUES
('9d3395b8c15dbbfdbaebdfa8e14c1229', '::1', 'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36', 1430212340, 'a:7:{s:9:"user_data";s:0:"";s:8:"identity";s:5:"admin";s:8:"username";s:5:"admin";s:5:"email";s:15:"admin@admin.com";s:7:"user_id";s:1:"1";s:14:"old_last_login";s:10:"1429967827";s:5:"token";s:28:"tok_15wUiEIeRrtAJIHSAkIwlRXE";}');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `street_address` varchar(300) NOT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `zip_code` varchar(20) NOT NULL,
  `country` varchar(30) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `fax_number` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `user_id`, `name`, `email`, `street_address`, `city`, `state`, `zip_code`, `country`, `phone_number`, `fax_number`, `created_date`) VALUES
(3, 1, 'Hung', 'me', 'asdf', 'as', 'as', '123', 'asd', 'asdff', 'asdf', '0000-00-00 00:00:00'),
(10, 1, 'asdfsdfd', 'sfsdfsf', '', '', '', '', '', '', '', '0000-00-00 00:00:00'),
(11, 1, 'sfasdf', 'sfdsdfsf', '', '', '', '', '', '', '', '0000-00-00 00:00:00'),
(14, 1, 'asfasf', 'sfasfsafd', '', '', '', '', '', '', '', '0000-00-00 00:00:00'),
(15, 1, 'Nguyễn Văn Hung', 'imrhung', 'sdsafd', '', '', '', '', '', '', '2015-04-26 04:55:16');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE IF NOT EXISTS `company` (
  `user_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `logo` varchar(400) NOT NULL,
  `url` varchar(300) NOT NULL,
  `street_address` varchar(300) NOT NULL,
  `city` varchar(20) NOT NULL,
  `state` varchar(20) NOT NULL,
  `zip_code` varchar(20) NOT NULL,
  `country` varchar(30) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `fax_number` varchar(50) NOT NULL,
  `primary_contact` varchar(200) NOT NULL,
  `currency` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`user_id`, `name`, `logo`, `url`, `street_address`, `city`, `state`, `zip_code`, `country`, `phone_number`, `fax_number`, `primary_contact`, `currency`) VALUES
(1, 'Funit Lab LLC,', 'http://localhost/stripe-invoice/assets/uploads/logo-home2.png', 'http://funitlab.com', '39/9', 'Ho Chi Minh City', 'South', '710000', 'Vietnam', '0912883476', '012393833', 'Hung Nguyen Van', 'USD');

-- --------------------------------------------------------

--
-- Table structure for table `donations`
--

CREATE TABLE IF NOT EXISTS `donations` (
`id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT '0' COMMENT 'Link to user.',
  `title` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `has_description` int(2) DEFAULT '0' COMMENT '1 is has',
  `description` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount_decide` int(2) DEFAULT '0' COMMENT '0 is make at checkout, 1 is set specific',
  `frequency` int(11) DEFAULT '0' COMMENT '0 is decide at checkout, 1 is once, 7 weekly, 30 monthly, 3 months, 6 months, 12 is yearly.',
  `confirmation_message` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `confirmation_email` varchar(10000) COLLATE utf8_unicode_ci NOT NULL,
  `public_state` int(2) DEFAULT '0' COMMENT '0 is draft, 1 is published.',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Store the offer donations.';

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`id`, `user_id`, `title`, `has_description`, `description`, `amount_decide`, `frequency`, `confirmation_message`, `confirmation_email`, `public_state`, `created_date`) VALUES
(3, 1, 'sfsaf', 0, '', 1, 0, 'sfas', 'afssaf', 1, '2015-04-12 07:59:55'),
(4, 1, NULL, 0, NULL, 0, 0, '', '', 0, '2015-04-12 07:59:56'),
(5, 1, NULL, 0, NULL, 0, 0, '', '', 0, '2015-04-12 07:59:57');

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
`id` mediumint(8) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `name`, `description`) VALUES
(1, 'admin', 'Administrator'),
(2, 'members', 'General User');

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE IF NOT EXISTS `invoice` (
`id` int(11) NOT NULL,
  `uid` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `invoice_number` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `title` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `frequency` int(4) NOT NULL,
  `date_send` date NOT NULL,
  `date_due` date NOT NULL,
  `confirmation_message` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `confirmation_email` varchar(10000) COLLATE utf8_unicode_ci NOT NULL,
  `public_state` int(2) NOT NULL,
  `payment_state` smallint(2) NOT NULL DEFAULT '0' COMMENT '0=not paid; 1=paid',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`id`, `uid`, `user_id`, `client_id`, `invoice_number`, `title`, `frequency`, `date_send`, `date_due`, `confirmation_message`, `confirmation_email`, `public_state`, `payment_state`, `created_date`) VALUES
(15, '5537bf9c5e137', 1, 15, '', 'asdfafs', 3, '2015-04-22', '2015-05-22', 'asfdsdfsfsdfsf', 'asdfasd', 1, 0, '2015-04-25 14:47:14'),
(18, '5537cdfd9095a', 1, 15, '', 'sdfsdf', 1, '2015-04-22', '2015-05-22', '', '', 1, 0, '2015-04-25 15:00:35'),
(19, '553b95330f443', 1, 15, '', 'Test again', 1, '2015-04-25', '2015-05-25', 'Your payment has been proceeded by Stripe. ', 'Thank you and best regards,', 1, 1, '2015-04-28 05:30:34'),
(20, '553baa2e3b0db', 1, 15, '', 'a', 1, '2015-04-25', '2015-05-25', 'bcd', 'abc', 0, 0, '2015-04-27 15:43:14'),
(21, '553baadae3afb', 1, 15, '', 'b', 1, '2015-04-25', '2015-05-25', 'asf', 'asf', 0, 0, '2015-04-25 15:00:29'),
(26, '553e63a137d24', 1, 15, '', 'Test plan', 30, '2015-04-25', '2015-05-25', 'Lala', 'Thank', 1, 0, '2015-04-27 16:28:17'),
(27, '553e63c05601a', 1, 15, '', 'Test plan', 30, '2015-04-25', '2015-05-25', 'Lala', 'Thank', 1, 0, '2015-04-27 16:28:48'),
(28, '553ef25cbe559', 1, 15, '', 'Test create plan', 7, '2015-04-19', '2015-05-19', '123', '123', 1, 1, '2015-04-28 08:33:10'),
(29, '553f4b95218b9', 1, 15, '', 'Try to get some money from some customers', 1, '2015-04-28', '2015-05-28', 'Thank you very much for your payment. Your service will be available soon.', 'Thank you vinamilk in the email. ', 1, 1, '2015-04-28 08:59:28'),
(30, '553f4df28a51d', 1, 3, '', 'The day you pay away', 3, '2015-04-28', '2015-05-28', 'La la', 'Ha ha', 1, 1, '2015-04-28 09:11:10');

-- --------------------------------------------------------

--
-- Table structure for table `invoice_item`
--

CREATE TABLE IF NOT EXISTS `invoice_item` (
`id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `description` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `rate` int(11) NOT NULL,
  `discount` int(11) NOT NULL,
  `discount_type` int(2) NOT NULL COMMENT '1 - $; 0 - %'
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `invoice_item`
--

INSERT INTO `invoice_item` (`id`, `invoice_id`, `description`, `quantity`, `rate`, `discount`, `discount_type`) VALUES
(51, 15, 'asfd', 0, 0, 0, 1),
(52, 15, 'sfa', 3, 2, 1, 0),
(77, 21, 'b', 2, 20, 0, 1),
(78, 21, 'c', 4, 53, 10, 0),
(81, 18, 'sdfsdf', 6, 6, 2, 0),
(82, 18, 'asfsaf', 4, 5, 4, 0),
(105, 20, 'a', 1, 2, 1, 0),
(106, 20, 'b', 2, 3, 1, 0),
(107, 26, 'Live', 23, 3, 12, 0),
(108, 26, 'lONG', 2, 12, 4, 1),
(113, 27, 'Live', 23, 3, 12, 0),
(114, 27, 'lONG', 2, 12, 4, 1),
(131, 19, 'Design', 1, 10, 9, 1),
(132, 19, 'Planing', 1, 10, 20, 0),
(135, 28, '123', 1, 12, 1, 1),
(136, 28, '1234', 1, 12, 7, 0),
(143, 29, 'Planning is powerful', 1, 12, 0, 1),
(144, 29, 'Fancy excution', 2, 10, 9, 0),
(147, 30, 'For the work we make', 1, 30, 0, 1),
(148, 30, 'For the thing we done', 4, 4, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `login_attempts`
--

CREATE TABLE IF NOT EXISTS `login_attempts` (
`id` mediumint(8) unsigned NOT NULL,
  `ip_address` varbinary(16) NOT NULL,
  `login` varchar(100) NOT NULL,
  `time` int(11) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
`id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `donation_id` int(11) NOT NULL,
  `order_number` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `client_name` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `client_email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `frequency` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Store all the complete payment. :)';

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE IF NOT EXISTS `payment` (
`id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `payment_number` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `payer_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `payer_email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `frequency` int(11) NOT NULL,
  `amount` float NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `invoice_id`, `payment_number`, `payer_name`, `payer_email`, `frequency`, `amount`, `created_date`) VALUES
(1, 19, '', 'Hung', 'imrhung@yhaoo.com', 7, 100, '2015-04-26 04:45:59'),
(2, 20, '', 'Tada', 'tada@somewhere.com', 1, 200, '2015-04-25 15:33:11'),
(3, 12, 'a', 'asdf', 'asfasdf', 30, 23, '2015-04-25 16:30:10'),
(4, 19, '', 'Hung Nguyen', 'imrhung@yahoo.com', 1, 9, '2015-04-28 05:30:34'),
(5, 28, '', 'Hung test plan', 'imrhung@yahoo.com', 7, 22.16, '2015-04-28 08:33:10'),
(6, 28, '', 'Hung test plan', 'imrhung@yahoo.com', 7, 22.16, '2015-04-28 08:53:47'),
(7, 29, '', 'Nguyen Van Hung', 'imrhung@yahoo.com', 1, 30.2, '2015-04-28 08:59:28'),
(8, 30, '', 'Hung Nguyen Van', 'imrhung@yahoo.com', 3, 42, '2015-04-28 09:11:10');

-- --------------------------------------------------------

--
-- Table structure for table `stripe_plan`
--

CREATE TABLE IF NOT EXISTS `stripe_plan` (
`id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `plan_id` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `amount` float NOT NULL DEFAULT '0',
  `interval_time` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `interval_count` int(11) NOT NULL,
  `currency` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `stripe_plan`
--

INSERT INTO `stripe_plan` (`id`, `invoice_id`, `plan_id`, `name`, `amount`, `interval_time`, `interval_count`, `currency`, `created_date`) VALUES
(7, 28, '28._Payment_to_FUNIT_LAB_LLC,_$282.68_every_1_month', '28. Payment to FUNIT LAB LLC, $282.68 every 1 month', 282.68, 'month', 1, 'usd', '2015-04-28 01:51:05'),
(8, 28, '28._Payment_to_FUNIT_LAB_LLC,_$282.68_every_1_month', '28. Payment to FUNIT LAB LLC, $282.68 every 1 month', 282.68, 'month', 1, 'usd', '2015-04-28 02:35:02'),
(9, 28, '28._Payment_to_FUNIT_LAB_LLC,_$276.92_every_1_month', '28. Payment to FUNIT LAB LLC, $276.92 every 1 month', 276.92, 'month', 1, 'usd', '2015-04-28 02:37:19'),
(10, 19, '19._Payment_to_FUNIT_LAB_LLC,_$170_every_1_week', '19. Payment to FUNIT LAB LLC, $170 every 1 week', 170, 'week', 1, 'usd', '2015-04-28 04:29:54'),
(11, 27, '27._Payment_to_FUNIT_LAB_LLC,_$80.72_every_1_month', '27. Payment to FUNIT LAB LLC, $80.72 every 1 month', 80.72, 'month', 1, 'usd', '2015-04-28 04:55:46'),
(12, 28, '28._Payment_to_FUNIT_LAB_LLC,_$22.16_every_1_week', '28. Payment to FUNIT LAB LLC, $22.16 every 1 week', 22.16, 'week', 1, 'usd', '2015-04-28 05:43:04'),
(13, 30, '30._Payment_to_FUNIT_LAB_LLC,_$42_every_3_month', '30. Payment to FUNIT LAB LLC, $42 every 3 month', 42, 'month', 3, 'usd', '2015-04-28 09:08:05');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`id` mediumint(8) unsigned NOT NULL,
  `ip_address` varbinary(16) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(80) NOT NULL,
  `salt` varchar(40) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `activation_code` varchar(40) DEFAULT NULL,
  `forgotten_password_code` varchar(40) DEFAULT NULL,
  `forgotten_password_time` int(11) unsigned DEFAULT NULL,
  `remember_code` varchar(40) DEFAULT NULL,
  `created_on` int(11) unsigned NOT NULL,
  `last_login` int(11) unsigned DEFAULT NULL,
  `active` tinyint(1) unsigned DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `company` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `ip_address`, `username`, `password`, `salt`, `email`, `activation_code`, `forgotten_password_code`, `forgotten_password_time`, `remember_code`, `created_on`, `last_login`, `active`, `first_name`, `last_name`, `company`, `phone`) VALUES
(1, 0x7f000001, 'admin', '59beecdf7fc966e2f17fd8f65a4a9aeb09d4a3d4', '9462e8eee0', 'admin@admin.com', '', NULL, NULL, '9d029802e28cd9c768e8e62277c0df49ec65c48c', 1268889823, 1430041318, 1, 'Super', 'Admin', 'ADMIN', '222-333-4444');

-- --------------------------------------------------------

--
-- Table structure for table `users_groups`
--

CREATE TABLE IF NOT EXISTS `users_groups` (
`id` mediumint(8) unsigned NOT NULL,
  `user_id` mediumint(8) unsigned NOT NULL,
  `group_id` mediumint(8) unsigned NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users_groups`
--

INSERT INTO `users_groups` (`id`, `user_id`, `group_id`) VALUES
(1, 1, 1),
(2, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_stripe_connect`
--

CREATE TABLE IF NOT EXISTS `user_stripe_connect` (
`id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `token_type` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `stripe_publishable_key` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `scope` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `livemode` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `stripe_user_id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `refresh_token` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `access_token` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_stripe_connect`
--

INSERT INTO `user_stripe_connect` (`id`, `user_id`, `token_type`, `stripe_publishable_key`, `scope`, `livemode`, `stripe_user_id`, `refresh_token`, `access_token`, `created_date`) VALUES
(2, 1, 'bearer', 'pk_test_bcMF1pcgVUcxUOH5WHeqUOGf', 'read_write', '0', 'acct_15kdVaIeRrtAJIHS', 'rt_68ftFyAETlL7OfA3R7hdaUclFcaN7ZAUQ1YqO0GIzROMaR9F', 'sk_test_KD4W6m4A97nxFt51dqUsbrs0', '2015-04-28 02:33:59');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `amounts`
--
ALTER TABLE `amounts`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `app_sessions`
--
ALTER TABLE `app_sessions`
 ADD PRIMARY KEY (`session_id`), ADD KEY `last_activity_idx` (`last_activity`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
 ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `donations`
--
ALTER TABLE `donations`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `invoice_item`
--
ALTER TABLE `invoice_item`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login_attempts`
--
ALTER TABLE `login_attempts`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stripe_plan`
--
ALTER TABLE `stripe_plan`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_groups`
--
ALTER TABLE `users_groups`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_stripe_connect`
--
ALTER TABLE `user_stripe_connect`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `amounts`
--
ALTER TABLE `amounts`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `donations`
--
ALTER TABLE `donations`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
MODIFY `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `invoice_item`
--
ALTER TABLE `invoice_item`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=149;
--
-- AUTO_INCREMENT for table `login_attempts`
--
ALTER TABLE `login_attempts`
MODIFY `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `stripe_plan`
--
ALTER TABLE `stripe_plan`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `users_groups`
--
ALTER TABLE `users_groups`
MODIFY `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `user_stripe_connect`
--
ALTER TABLE `user_stripe_connect`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
