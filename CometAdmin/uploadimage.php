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
		$targetPath = "./uploads/".$_SESSION['uName'];
		if (!file_exists($targetPath)) {
			mkdir($targetPath, 0777, true);
		}
		move_uploaded_file ($_FILES['profilePic'] ['tmp_name'], $targetPath."/profilePic.jpg");
	}
?>