<?php defined('BASEPATH') OR exit('No direct script access allowed');

/* 
 * Code written by Nguyen Van Hung at @imrhung
 * Feel free to re-use or share it.
 * Happy code!!!
 */

class Email_Model extends CI_Model {
    
    public function __construct() {
        parent::__construct();
        
        $this->load->library('email');
        $this->config->load('email');
    }
    
    public function sendConfirmMail($displayName, $recipient, $subject, $message){
        // Some mail config:
        $nameDisplay = $displayName;
        
        $config['protocol'] = 'smtp';       // Using protocol SMTP for send mail
        $config['smtp_host'] = 'ssl://smtp.zoho.com';
        //$config['smtp_crypto'] = 'tls';     // Choose way for send mail
        $config['smtp_port'] = '465';
        $config['smtp_timeout'] = '7';
        $config['smtp_user'] = $this->config->item("email_user");  // name's user using send mail with SMTP
        $config['smtp_pass'] = $this->config->item("email_pass");  // Password of user
        $config['charset'] = 'utf-8';
        $config['newline'] = "\r\n";
        $config['mailtype'] = 'text';       // or html
        $config['validation'] = TRUE;       // bool whether to validate email or not    
        $config['wordwrap'] = FALSE;

        // Making mail content.
        $this->email->initialize($config);

        $this->email->from($this->config->item("email_user"), $nameDisplay);
        $this->email->to($recipient);

        $this->email->subject($subject);
        $this->email->message($message);

        // Start sending mail. This will block process for a while.
        return $this->email->send();
        
        //echo $this->email->print_debugger();
    }
    
    public function sendConfirmMailwBcc($displayName, $recipient, $bcc, $subject, $message){
        // Some mail config:
        $nameDisplay = $displayName;
        
        $config['protocol'] = 'smtp';       // Using protocol SMTP for send mail
        $config['smtp_host'] = 'ssl://smtp.zoho.com';
        //$config['smtp_crypto'] = 'tls';     // Choose way for send mail
        $config['smtp_port'] = '465';
        $config['smtp_timeout'] = '7';
        $config['smtp_user'] = $this->config->item("email_user");  // name's user using send mail with SMTP
        $config['smtp_pass'] = $this->config->item("email_pass");  // Password of user
        $config['charset'] = 'utf-8';
        $config['newline'] = "\r\n";
        $config['mailtype'] = 'text';       // or html
        $config['validation'] = TRUE;       // bool whether to validate email or not    
        $config['wordwrap'] = FALSE;

        // Making mail content.
        $this->email->initialize($config);

        $this->email->from($this->config->item("email_user"), $nameDisplay);
        $this->email->to($recipient);
        $this->email->bcc($bcc);

        $this->email->subject($subject);
        $this->email->message($message);

        // Start sending mail. This will block process for a while.
        return $this->email->send();
        
        //echo $this->email->print_debugger();
    }
}

