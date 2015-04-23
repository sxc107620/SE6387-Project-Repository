<?php
$ftp_server = "ftp.danmat.us";
$ftp_user_name = "cometdev";
$ftp_user_pass = "cometride1!";

putenv('TMPDIR=gs://tempprofilepic/');

// set up basic connection
$conn_id = ftp_connect($ftp_server);
ftp_pasv($conn_id, true); 

// login with username and password
$login_result = ftp_login($conn_id, $ftp_user_name, $ftp_user_pass); 

// check connection
if ((!$conn_id) || (!$login_result)) { 
    echo "FTP connection has failed!";
    echo "Attempted to connect to $ftp_server for user $ftp_user_name"; 
    exit; 
} 
?>