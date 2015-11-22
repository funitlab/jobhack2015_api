-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 25, 2015 at 04:26 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `stripe-donation`
--

-- --------------------------------------------------------

--
-- Table structure for table `stripe_plan`
--

CREATE TABLE IF NOT EXISTS `stripe_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `donation_id` int(11) NOT NULL,
  `plan_id` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `amount` int(11) NOT NULL DEFAULT '0',
  `interval` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `interval_count` int(11) NOT NULL,
  `currency` varchar(3) COLLATE utf8_unicode_ci NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=6 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


CREATE TABLE IF NOT EXISTS `organization` (
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


ALTER TABLE `orders` 
ADD COLUMN `country` VARCHAR(100) NULL AFTER `client_email`,
ADD COLUMN `address` VARCHAR(300) NULL AFTER `country`,
ADD COLUMN `city` VARCHAR(45) NULL AFTER `address`,
ADD COLUMN `state` VARCHAR(45) NULL AFTER `city`,
ADD COLUMN `zip_code` VARCHAR(20) NULL AFTER `state`;
ALTER TABLE `orders` 
ADD COLUMN `category` VARCHAR(200) NULL AFTER `amount`;


CREATE TABLE `donation_category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `donation_id` INT NULL,
  `category` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

  
-- ------------------------------------------
  ALTER TABLE `donations` 
ADD COLUMN `uid` VARCHAR(25) NULL AFTER `id`;
