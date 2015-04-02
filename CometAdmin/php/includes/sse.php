<?php
	header("Content-Type: text/event-stream");
	header("Cache-Control: no-cache");
	header("Connection: keep-alive");
	
	include ("settings.inc.php");        // database settings
	include ("connectdb.inc.php"); 
	
	$routeList = array();
	$getList = mysql_query("SELECT routeid FROM `routes` WHERE 1");
	mysql_data_seek($getList, 0);
	while ($row = mysql_fetch_assoc($getList)) {
	  array_push($routeList, $row['routeid']);
	}
	
	for($i=0; $i<count($routeList); $i++) {
	$result = array();
	$getList = mysql_query("SELECT latitude, longitude, capacity, type FROM `shuttles` WHERE `routeid` = '".$routeList[$i]."'");
	mysql_data_seek($getList, 0);
	while ($row = mysql_fetch_assoc($getList)) {
	  array_push($result, $row['latitude'], $row['longitude'], $row['capacity'], $row['type']);
	}

	for($j=0; $j<(count($result))/4; $j++) {
		$shuttle = array(
			"Route" => $routeList[$i],
			"Latitude" => $result[4*$j],
			"Longitude" => $result[4*$j+1],
			"Capacity" => $result[4*$j+2],
			"Type" => $result[4*$j+3][0]
		);
		$JSON = json_encode($shuttle, JSON_FORCE_OBJECT);
		echo "data: {$JSON}\n\n";
	}
	}

	flush();

?>