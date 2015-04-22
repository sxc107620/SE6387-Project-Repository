<?php
	include ("settings.inc.php");        // database settings
	include ("connectdb.inc.php"); 
	include ("array.php");
	

	
	if(isset($_REQUEST['c']) && $_REQUEST['c']!= "") {
		$cabList = $_REQUEST['c'];
		$status = $_REQUEST['s'];
		$routeNo = $_REQUEST['r'];
		
		for($i = 0; $i < count($cabList); $i++) {
			//srand(mktime());
			$rand = mt_rand(0, count($route[$routeNo[$i]]));
			$lt = $route[$routeNo[$i]][$rand][0];
			$lg = $route[$routeNo[$i]][$rand][1];
			setcookie($cabList[$i] , $rand, time()+3600);
			
			mysql_query("UPDATE `shuttles` SET `status`='".$status[$i]."', `routeid`='".$routeNo[$i]."', `latitude`='".$lt."', `longitude`='".$lg."' WHERE `number` = '".$cabList[$i]."'");
		}
	}
?>