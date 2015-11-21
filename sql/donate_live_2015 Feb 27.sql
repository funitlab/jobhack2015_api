-- phpMyAdmin SQL Dump
-- version 4.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 27, 2015 at 10:00 AM
-- Server version: 5.5.41-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `donate`
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
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Define the payment amount of Donations.';

--
-- Dumping data for table `amounts`
--

INSERT INTO `amounts` (`id`, `donation_id`, `amount`, `description`) VALUES
(85, 16, 10, 'Sponsor Vy'),
(112, 34, 500, 'School Tuition'),
(113, 35, 10, 'donate description'),
(115, 19, 12, 'sdf'),
(116, 36, 23, '2222'),
(117, 37, 10, 'Hi'),
(118, 38, 10, 'Hi'),
(119, 39, 23, 'sdsdf'),
(125, 32, 1, ''),
(128, 40, 23, 'sdsdf'),
(130, 42, 500, 'School Tuition'),
(133, 44, 2, 'sdfd'),
(139, 47, 0, ''),
(143, 48, 0, ''),
(144, 46, 0, '');

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
('015a3864eee50d4e60b231fd49ab3306', '180.76.4.136', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424747146, ''),
('10082d4dbfd15b4841099f217a7cd1ba', '66.249.79.135', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424716411, ''),
('13daa0a702d887927d525a57f6adf424', '66.249.65.199', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424919014, ''),
('14df6de8a3e4df809b84e33b74e76e1b', '66.249.75.23', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424977159, ''),
('2a062c8557d90bf625a7a97302df7b0a', '42.115.161.73', 'Mozilla/5.0 (Windows NT 6.3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36', 1424831968, 'a:7:{s:9:"user_data";s:0:"";s:8:"identity";s:5:"admin";s:8:"username";s:5:"admin";s:5:"email";s:15:"admin@admin.com";s:7:"user_id";s:1:"1";s:14:"old_last_login";s:10:"1424667045";s:5:"token";s:28:"tok_15ZmkO2qkSgQkGwYw88ACG9J";}'),
('34a6cbd898f2a7348374943249301290', '66.249.79.143', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424765956, ''),
('36e1b665315862d410d8d354ca8860c7', '80.103.83.12', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36', 1424915941, ''),
('388b893beea0952e18b922ea05ad4b33', '66.249.79.151', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424774232, ''),
('393709eba88e79da8427bc2515f79efe', '182.182.93.13', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36', 1425014568, ''),
('3f2977970ac8a3388c7390f010adb897', '1.52.163.250', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.3', 1424854409, 'a:6:{s:9:"user_data";s:0:"";s:8:"identity";s:6:"ach_vn";s:8:"username";s:6:"ach_vn";s:5:"email";s:14:"info@achvn.org";s:7:"user_id";s:1:"8";s:14:"old_last_login";s:10:"1424838151";}'),
('4570d20e9869c39655b45c3f98bf74e5', '66.249.79.135', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424765958, ''),
('4e65b8b7d1b516f9c1180579945e55a8', '66.249.75.39', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424983356, ''),
('4eda8dba57ea5e82eaa3489103a20312', '180.242.184.215', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36', 1424927950, ''),
('500c516f83ce86521b3765bfd9fe852a', '180.76.4.183', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424854827, ''),
('52bbdf533f4e353008db54185a98e1b3', '187.11.169.98', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36', 1425045987, ''),
('530cb4fe1b999ab635ac43e7678ae2bf', '1.52.163.250', 'Mozilla/5.0 (iPad; CPU OS 7_1_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D201 Safari', 1424941127, ''),
('57f669ab60371733b5a2a73259e8bab8', '1.52.163.250', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.3', 1424881063, 'a:2:{s:9:"user_data";s:0:"";s:5:"token";s:28:"tok_15a3MyFjFSFAtcdzqys6o7WT";}'),
('5aba9f09ed606359c0d749d3f477f940', '66.249.65.199', 'Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e', 1424925031, ''),
('5d8f0a70ea5865b5199f7926825622ce', '42.116.160.91', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.3', 1425003739, ''),
('62308eea24e435c5cf132a2d1a21f4d4', '157.55.39.120', 'Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)', 1425036282, ''),
('67697bfcfa43e0ea0dc364d6b53de1bc', '1.52.163.250', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.3', 1424836745, ''),
('6856501a18655deaafd24384a2c01ac4', '1.52.163.250', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.3', 1424863212, 'a:7:{s:9:"user_data";s:0:"";s:8:"identity";s:6:"ach_vn";s:8:"username";s:6:"ach_vn";s:5:"email";s:14:"info@achvn.org";s:7:"user_id";s:1:"8";s:14:"old_last_login";s:10:"1424853474";s:5:"token";s:28:"tok_15a39fFjFSFAtcdz5Q0Mssn9";}'),
('6bc46c84163c2715db56e0c9f89b1bb3', '66.249.75.23', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424975613, ''),
('6f97b91eaa2548e9ef5a103e7f90351e', '180.76.4.37', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424984248, ''),
('7534a036188db3c2bb5e197fe43d4587', '1.52.163.250', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.3', 1424941154, ''),
('7c628c19f77867b0483f4318fa969b0c', '201.92.121.108', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36', 1424870014, ''),
('7fa0957507b919d48e01476894cd6f55', '66.249.79.143', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424797549, ''),
('7fe53741c7a37aab1ee8688d3ea8f2cc', '180.76.4.179', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424955580, ''),
('828370696b7f1aefe93e6fbb090a3da7', '200.124.46.67', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36', 1424919892, ''),
('83a0c00c6842effda0452dc55c64e431', '66.249.79.135', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424850520, ''),
('8b1a19668d171d84de40415f05c6e3cc', '66.249.79.151', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424797549, ''),
('9176d44da90910b972fc1c91b86bd251', '180.76.4.69', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424804659, ''),
('93d2b3e3df5e57f95f4cda4c91c3e3ef', '180.76.4.231', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424876732, ''),
('999c16ed48384335dfb2afb778d17169', '180.76.4.171', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424962777, ''),
('a50918e92f2d83ffd74c04c90ba11118', '42.115.161.73', 'Mozilla/5.0 (Windows NT 6.3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36', 1424835348, 'a:7:{s:9:"user_data";s:0:"";s:8:"identity";s:5:"admin";s:8:"username";s:5:"admin";s:5:"email";s:15:"admin@admin.com";s:7:"user_id";s:1:"1";s:14:"old_last_login";s:10:"1424700345";s:5:"token";s:28:"tok_15ZvrU2qkSgQkGwYI0zCudph";}'),
('b334bcb8830aa1ae11f221079c131854', '66.249.79.151', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424877612, ''),
('b4d714591304f72b747748d22c023e97', '66.249.79.143', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424850521, ''),
('b7db2d3494cbba38a90098de4dc687b0', '41.182.170.83', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36', 1424946584, ''),
('ba8bd807a064cc5fe0a50340e4c22fdb', '1.52.163.250', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/600.3.18 (KHTML, like Gecko) Version/8.0.3 Safari/600.3.18', 1424855175, 'a:6:{s:9:"user_data";s:0:"";s:8:"identity";s:5:"admin";s:8:"username";s:5:"admin";s:5:"email";s:15:"admin@admin.com";s:7:"user_id";s:1:"1";s:14:"old_last_login";s:10:"1424852729";}'),
('d300984480649b655b0f7d3d2a3e7932', '66.249.65.195', 'Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e', 1424925030, ''),
('d99cefc891e0f3049fb1181abfd08008', '180.76.5.62', 'Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)', 1424719099, ''),
('d9eec57eb33c85ab172223a0d2350df8', '183.80.227.7', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.111 Safari/537.3', 1424776992, 'a:6:{s:5:"token";s:28:"tok_15ZECI2qkSgQkGwYpGkbqnCJ";s:8:"identity";s:5:"admin";s:8:"username";s:5:"admin";s:5:"email";s:15:"admin@admin.com";s:7:"user_id";s:1:"1";s:14:"old_last_login";s:10:"1424658962";}'),
('da0cad30ece3c55de0f3b0bae7a2d520', '88.13.37.32', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36', 1424974119, ''),
('e9708f1bce76597c59dab0ee63f4b4bb', '66.249.79.135', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424782413, ''),
('ed7878bcef8ce5f908416f386dccd694', '180.76.4.60', 'Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)', 1424775743, ''),
('f40a01701858dd251b6afd257e03f0ea', '66.249.79.151', 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', 1424879846, '');

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Store the offer donations.';

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`id`, `user_id`, `title`, `has_description`, `description`, `amount_decide`, `frequency`, `confirmation_message`, `confirmation_email`, `public_state`, `created_date`) VALUES
(16, 1, 'Sponsor Vy', 1, 'You will sponsor Vy. A little girl in our Girl''s Home.', 0, 1, 'Thanks for sponsoring Vy. You will be notified via Email on your contribution.', '', 1, '2014-12-11 07:25:04'),
(19, 2, 'sdafdsf', 0, 'sadf', 0, 1, 'sdafsfd', '', 1, '2014-12-11 07:29:58'),
(20, 2, 'sdafdsf', 0, 'sadf', 0, 1, 'sdafsfd', '', 0, '2014-12-11 07:30:03'),
(32, 2, 'asdfsd', 0, '', 0, 1, '', '', 0, '2014-12-15 03:22:44'),
(34, 1, 'Sponsor Hung', 1, 'Help Hung go to school and become a man of God.', 0, 0, 'Thank you for sponsoring sponsoring Hung.', '', 1, '2014-12-15 13:41:00'),
(35, 1, 'donate to vietnam', 1, 'description here', 0, 0, 'thanks for donating', '', 1, '2014-12-26 15:57:43'),
(36, 2, 'wrwe', 0, '', 0, 1, '', '', 1, '2014-12-29 04:13:20'),
(37, 1, 'Test', 1, 'Hi', 0, 0, 'Thanks', '', 1, '2014-12-29 08:15:34'),
(38, 1, 'Test', 1, 'Hi', 0, 0, 'Thanks', '', 1, '2014-12-29 08:15:55'),
(39, 2, 'dsfdfdsfdf', 0, '', 0, 1, '', '', 1, '2014-12-29 08:24:41'),
(40, 2, 'dsfdfdsfdf', 0, '', 0, 1, '', '', 0, '2014-12-29 08:24:43'),
(42, 1, 'SCHOOL TUITION', 1, 'Provide school tuition for one child for a year', 0, 0, 'Thank you for providing school tuition for one child for a year. ', '', 1, '2014-12-29 17:32:06'),
(44, 6, 'sdfasfd', 0, '', 0, 1, '', '', 1, '2014-12-30 13:11:01'),
(46, 1, 'Sponsor a child', 1, 'Give a child a home for $125 per month or as much as your can provide: ', 0, 1, 'Thank you so much for donating to Agape Children''s Home. We are a 501(c)(3) organization. All your donations are tax deductible, please keep the conformation email we sent to you for that purpose. ', '', 1, '2015-02-23 02:38:23'),
(47, 1, 'Test tada', 0, '', 0, 1, '', '', 0, '2015-02-25 03:17:10'),
(48, 8, 'Sponsor a child', 1, 'Give a child a home for $125 per month or as much as your can provide: ', 0, 1, 'Thank you so much for donating to Agape Children''s Home. We are a 501(c)3 organization. All your donations are tax deductible.', '', 1, '2015-02-25 09:04:35');

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Store all the complete payment. :)';

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `donation_id`, `order_number`, `client_name`, `client_email`, `frequency`, `amount`, `created_date`) VALUES
(1, 2, 0, NULL, 'Hung', 'imrhung@yahoo.com', 1, 12, '2014-12-15 10:42:00'),
(2, 1, 0, NULL, 'Teset', 'luan@alignlab.com', 1, 423, '2014-12-15 10:44:11'),
(3, 1, 0, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 400, '2014-12-15 13:43:39'),
(4, 1, 0, NULL, 'luan jenkisn', 'luan@gmail.com', 7, 10, '2014-12-26 15:59:19'),
(5, 1, 0, NULL, 'Luan', 'luan@gmail.com', 1, 43, '2014-12-29 09:41:56'),
(6, 1, 0, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 12, '2014-12-29 17:37:06'),
(7, 1, 0, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 30, 10, '2014-12-29 17:43:07'),
(8, 1, 0, NULL, 'Nguyen Hung', 'imrhung@yahoo.com', 1, 43, '2014-12-30 12:48:39'),
(9, 1, 0, NULL, 'Howie Schneider', 'howie43@comcast.net', 1, 100, '2014-12-31 02:00:49'),
(10, 1, 46, NULL, 'Hung1', 'imrhung@yahoo.com', 1, 12, '2015-02-25 03:29:26'),
(11, 1, 46, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 10, '2015-02-25 04:00:42'),
(12, 1, 46, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 10, '2015-02-25 04:12:40'),
(13, 1, 46, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 10, '2015-02-25 04:15:53'),
(14, 1, 46, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 1, '2015-02-25 09:10:14'),
(15, 1, 46, NULL, 'luan jenkins', 'info@luanjenkins.com', 1, 10, '2015-02-25 10:57:30'),
(16, 1, 46, NULL, 'Luan Jenkins', 'info@luanjenkins.com', 1, 10, '2015-02-25 11:00:08'),
(17, 1, 46, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 10, '2015-02-25 11:02:51'),
(18, 1, 46, NULL, 'Luan Jenkins', 'info@luanjenkins.com', 1, 9, '2015-02-25 11:04:58'),
(19, 8, 48, NULL, 'Luan Jenkins', 'luan@alignlab.com', 1, 10, '2015-02-25 11:11:05'),
(20, 8, 48, NULL, 'Luan Jenkins', 'luan.jenkins@gmail.com', 1, 3, '2015-02-25 11:30:26');

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
(1, 0x7f000001, 'admin', '59beecdf7fc966e2f17fd8f65a4a9aeb09d4a3d4', '9462e8eee0', 'admin@admin.com', '', NULL, NULL, '9d029802e28cd9c768e8e62277c0df49ec65c48c', 1268889823, 1424855023, 1, 'Super', 'Admin', 'ADMIN', '222-333-4444'),
(2, 0x00000000000000000000000000000001, 'imrhung', 'bcfc0f50b64d0d6a1db95eacea85f273ca15f7c3', NULL, 'imrhung@yahoo.com', NULL, NULL, NULL, 'e9baea7bb6208e4a02bd762ace9cc27166762c8a', 1416963267, 1419827748, 1, NULL, NULL, 'Hung', NULL),
(4, 0x00000000000000000000000000000001, 'superluan', 'c60bb929306bf4e725a0c681341aa6bbbd6e00d8', NULL, 'luan@here.com', NULL, NULL, NULL, NULL, 1418269472, 1418269472, 1, NULL, NULL, 'Luan', NULL),
(8, 0x0134a3fa, 'ach_vn', 'a8b82f40ca9e343c1e2877367609067e5a1d3c5e', NULL, 'info@achvn.org', NULL, NULL, NULL, NULL, 1424838151, 1424854427, 1, NULL, NULL, 'Agape Children''s Home', NULL);

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
(2, 1, 2),
(3, 2, 2),
(5, 4, 2),
(6, 5, 2),
(7, 6, 2),
(8, 7, 2),
(9, 8, 2);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_stripe_connect`
--

INSERT INTO `user_stripe_connect` (`id`, `user_id`, `token_type`, `stripe_publishable_key`, `scope`, `livemode`, `stripe_user_id`, `refresh_token`, `access_token`, `created_date`) VALUES
(3, 2, 'bearer', 'pk_test_mSGJeWjkWtkyJnnwJjf8YRM8', 'read_write', '0', 'acct_15626hJ1it99DvEI', 'rt_5HYtjS6UO1dHlaujPnE5nz2USICp13vx1munxUMZAGpBfQ2A', 'sk_test_uXBSvmhKMJ0lHE6d9ifOUl6n', '2014-12-15 10:41:09'),
(4, 1, 'bearer', 'pk_test_SgCiOSkzHnpYvbbG9o6fLTaA', 'read_write', '0', 'acct_103jwS2qkSgQkGwY', 'rt_5KbWxIYwVm6PUT3YW4Q4pas5xJ41eFLlrrtAasMcfgwwbKM1', 'sk_test_bZHqO3jX8ocmVzYJ9twAHjdB', '2014-12-15 10:42:58'),
(5, 8, 'bearer', 'pk_test_8oPNhhrOrgI8QkeLo1AJt6eX', 'read_write', '0', 'acct_15EuR0FjFSFAtcdz', 'rt_5lY6X7KChxEiuOWtx77WKOqxQ5bppnZ6tbxBwdLkAHbg37U9', 'sk_test_leiNKz8ojM1tJpNS0lSlX0yL', '2015-02-25 08:59:15');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=145;
--
-- AUTO_INCREMENT for table `donations`
--
ALTER TABLE `donations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `login_attempts`
--
ALTER TABLE `login_attempts`
  MODIFY `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
