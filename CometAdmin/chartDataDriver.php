<?php
	error_reporting(0);
	include ("./php/includes/settings.inc.php");        // database settings
	include ("./php/includes/connectdb.inc.php"); 
	include ("./php/includes/sql.php");
	if(isset($_REQUEST['m']) && $_REQUEST['m']!= "") {
		$chartData = getDriverHours($_REQUEST['m']);
		$data = array();
		for($i = 0; $i < count($chartData)/2; $i++) {
			array_push($data, array($chartData[2*$i], (double)$chartData[2*$i+1]));
		}
		echo json_encode($data);
	}
?>