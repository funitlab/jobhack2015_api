<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */

$config['email_user'] = 'invoice@alignlab.com';
$config['email_pass'] = 'invoice400187';

// Config email for default email function of codeignter

$config['protocol'] = 'smtp';       // Using protocol SMTP for send mail
$config['smtp_host'] = 'ssl://smtp.zoho.com';
//$config['smtp_crypto'] = 'tls';     // Choose way for send mail
$config['smtp_port'] = '465';
$config['smtp_timeout'] = '7';
$config['smtp_user'] = 'donate@alignlab.com';  // name's user using send mail with SMTP
$config['smtp_pass'] = 'donate400187';  // Password of user
$config['charset'] = 'utf-8';
$config['newline'] = "\r\n";
$config['mailtype'] = 'text';       // or html
$config['validation'] = TRUE;       // bool whether to validate email or not    
$config['wordwrap'] = FALSE;

