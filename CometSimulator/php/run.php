<?php
	include ("settings.inc.php");        // database settings
	include ("connectdb.inc.php"); 
	include ("array.php");
	
	
	if(isset($_REQUEST['r']) && $_REQUEST['r']!= "") {
		$list = $_REQUEST['r'];
		for($i = 0; $i < count($list); $i++) {
			$index = $_COOKIE[$list[$i][0]];
			$routeN = $list[$i][1];
			if(count($route[$routeN]) == ($index-2)) $index = 0; else $index++;
			
			mysql_query("UPDATE `shuttles` SET `latitude`='".$route[$routeN][$index][0]."', `longitude`='".$route[$routeN][$index][1]."' WHERE `number` = '".$list[$i][0]."'");
			setcookie($list[$i][0] , $index, time()+3600);
		}
	}
	
	?>