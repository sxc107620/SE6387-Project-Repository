<?php
	error_reporting(0);
	include ("./php/includes/settings.inc.php");        // database settings
	include ("./php/includes/connectdb.inc.php"); 
	include ("./php/includes/sql.php");
	if(isset($_REQUEST['y']) && $_REQUEST['y']!= "") {
		$data = array();
		$month = array("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
		$color = array("#ffa500", "#ff0000", "#00FF00", "#40E0D0", "#000000", "#008080", "#000080", "#FFD700", "#7FFF00", "#272323", "#a17d7d", "#008000");
		for($mon = 1; $mon <= 12; $mon++) {
			$chartData = getDriverHoursPersonal($mon, $_REQUEST['y'], $_REQUEST['u']);
			if($chartData === NULL) $chartData = 0;
			array_push($data, array("label" => $month[$mon-1], "data" => $chartData, "color" => $color[$mon-1])); 
		}
		echo json_encode($data);
	}
?>