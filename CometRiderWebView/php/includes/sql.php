<?php
	
	function getRoutes() {
		$result = array();
		$getList = mysql_query("SELECT * FROM `routes` WHERE 1");
		mysql_data_seek($getList, 0);
		$column = mysql_query("SELECT COUNT( * ) FROM information_schema.columns WHERE table_name =  'routes'");
		$col = mysql_fetch_row($column, 0) or trigger_error(mysql_error().$column);
		array_push($result, $col[0]);
		while ($row = mysql_fetch_assoc($getList)) {
		  array_push($result, $row['routeid'], $row['rname'], $row['color'], $row['savepoints'], $row['lines'], $row['curves']);
		}
		return $result;
	}
	
	function interest($id, $lt, $lg) {
		echo "INSERT INTO `interests` ( `routeid`, `latitude`, `longitude`) VALUES ( '$id', '$lt', '$lg')";
		mysql_query("INSERT INTO `interests` ( `routeid`, `latitude`, `longitude`) VALUES ( '$id', '$lt', '$lg')");
	}
?>