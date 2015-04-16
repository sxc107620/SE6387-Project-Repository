<?php
	error_reporting(0);
	include ("./php/includes/settings.inc.php");        // database settings
	include ("./php/includes/connectdb.inc.php"); 
	include ("./php/includes/sql.php");
	if(isset($_REQUEST['route']) && $_REQUEST['route']!= "") {
		$chartData = getData($_REQUEST['route'], $_REQUEST['f'], $_REQUEST['t']);
		$data = array();
		for($i = 0; $i < count($chartData)/2; $i++) {
			array_push($data, array(getStringBetween($chartData[2*$i]," ",":"), $chartData[2*$i+1]));
		}
		echo json_encode($data);
	}
	function getStringBetween($str,$from,$to) {
		$sub = substr($str, strpos($str,$from)+strlen($from),strlen($str));
		return substr($sub,0,strpos($sub,$to));
	}
?>