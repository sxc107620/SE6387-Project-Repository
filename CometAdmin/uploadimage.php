<?php
	if(isset($_POST['password']) && $_POST['password'] != "") {
		include ("./php/includes/settings.inc.php");        // database settings
		include ("./php/includes/connectdb.inc.php"); 
		include ("./php/includes/sql.php");
		session_start();
		
		changePass($_SESSION['uName'], md5($_POST['password']));
	}
		
	if(isset($_FILES['profilePic']) && $_FILES['profilePic'] != "") {	
		session_start();
		
		include ("./php/includes/ftp.php");
		
		$targetPath = "uploads/".$_SESSION['uName'];
		if (ftp_nlist($conn_id, $targetPath) == false) {
			ftp_mkdir($conn_id, $targetPath);
			ftp_site($conn_id, 'CHMOD 0777 '.$targetPath);
		}
		
		$source_file = $_FILES['profilePic']['tmp_name']; 
		
		$upload = ftp_put($conn_id, $targetPath ."/profilePic.jpg", $source_file, FTP_BINARY); 
		
		// close the FTP stream 
		ftp_close($conn_id);

	}
?>