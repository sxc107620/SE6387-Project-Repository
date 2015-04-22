<?php
	function getPwd($userid) {
		$getPass = mysql_query("SELECT upass FROM `users` WHERE `uname` = '".$userid."'");
		$result = mysql_fetch_row($getPass, 0) or trigger_error(mysql_error().$getPass);
		return $result[0];
	}
	
	function getAttempts($userid) {
		$getPass = mysql_query("SELECT attempts FROM `users` WHERE `uname` = '".$userid."'");
		$result = mysql_fetch_row($getPass, 0) or trigger_error(mysql_error().$getPass);
		return $result[0];
	}
	
	function getUserType($userid) { 
		$getPass = mysql_query("SELECT type FROM `users` WHERE `uname` = '".$userid."'");
		$result = mysql_fetch_row($getPass, 0) or trigger_error(mysql_error().$getPass);
		return $result[0];
	}
	
	function getEmail($userid) {
		$getPass = mysql_query("SELECT email FROM `users` WHERE `uname` = '".$userid."'");
		$result = mysql_fetch_row($getPass, 0) or trigger_error(mysql_error().$getPass);
		return $result[0];
	}
	
	function updateAttempt($userid, $attempt) {
		mysql_query("UPDATE `users` SET `attempts`='$attempt' WHERE `uname` = '".$userid."'");
	}
	
	function changePass($userid, $pwd) {
		mysql_query("UPDATE `users` SET `upass`='$pwd' WHERE `uname` = '".$userid."'");
	}
	
	function forgetPass($email, $pwd) {
		mysql_query("UPDATE `users` SET `upass`='$pwd' WHERE `email` = '".$email."'");
	}
	
	function getUsers($type) { 
		$result = array();
		$getList = mysql_query("SELECT uname, email, joindate FROM `users` WHERE `type` = '".$type."'");
		mysql_data_seek($getList, 0);
		while ($row = mysql_fetch_assoc($getList)) {
		  array_push($result, $row['uname'], $row['email'], $row['joindate']);
		}
		return $result;
	}
	
	function newUser($user, $pwd, $mail, $status) {
		mysql_query("INSERT INTO `users` ( `uname`, `upass`, `email`, `type`) VALUES ( '$user', '$pwd', '$mail', '$status')");
	}
	
	function newShuttle($num, $shuttle) {
		mysql_query("INSERT INTO `shuttles` ( `number`, `type`) VALUES ( '$num', '$shuttle')");
	}
	
	function deleteUser($mail, $status) {
		mysql_query('DELETE FROM `users` WHERE `type` = "'.$status.'" AND `email` = "'.$mail.'"');
	}
	
	function deleteShuttle($num) {
		mysql_query('DELETE FROM `shuttles` WHERE `number` = "'.$num.'"');
	}
	
	function getCabs() { 
		$result = array();
		$getList = mysql_query("SELECT number, status, type, latitude, longitude, capacity, routeid, totalpassengers FROM `shuttles` WHERE 1");
		mysql_data_seek($getList, 0);
		while ($row = mysql_fetch_assoc($getList)) {
			$routeName = mysql_query("SELECT rname FROM `routes` WHERE `routeid` = '".$row['routeid']."'");
			$route = mysql_fetch_row($routeName, 0) or trigger_error(mysql_error().$routeName);
			
			array_push($result, $row['number'], $row['status'], $row['type'], $row['latitude'], $row['longitude'], $route[0], $row['capacity'], $row['totalpassengers']);
		}
		return $result;
	}
	
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
	
	function newRoute($name, $color, $points, $line, $curve) {
		mysql_query("INSERT INTO `routes` (`rname`, `color`, `savepoints`, `lines`, `curves`) VALUES ( '$name', '$color', '$points', '$line', '$curve')");
	}
	
	function deleteRoute($id) {
		mysql_query('DELETE FROM `routes` WHERE `routeid` = "'.$id.'"');
	}
	
	function getDriverHours($month) {
		$result = array();
		$getList = mysql_query("SELECT username, SUM( totaltime /3600000 ) AS totaltime FROM statistics_driver_time WHERE MONTH(date) = '$month' GROUP BY username");
		mysql_data_seek($getList, 0);
		while ($row = mysql_fetch_assoc($getList)) {
		  array_push($result, $row['username'], $row['totaltime']);
		}
		return $result;
	}
	
	function getShuttleUsage() {
		$result = array();
		$getList = mysql_query("SELECT number, totalpassengers FROM shuttles");
		mysql_data_seek($getList, 0);
		while ($row = mysql_fetch_assoc($getList)) {
		  array_push($result, $row['number'], $row['totalpassengers']);
		}
		return $result;
	}
	
	function getData($route, $from, $to) {
		$result = array();
		$getList = mysql_query("SELECT FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(timestamp) / 3600)*3600) AS timestamp, SUM(totalpassengers) AS totalpassengers FROM statistics_passengers, routes WHERE statistics_passengers.routeid = routes.routeid AND routes.rname = '$route' AND timestamp > '$from' AND timestamp < '$to' GROUP BY FLOOR(UNIX_TIMESTAMP(timestamp) / 3600)");
		mysql_data_seek($getList, 0);
		while ($row = mysql_fetch_assoc($getList)) {
		  array_push($result, $row['timestamp'], $row['totalpassengers']);
		}
		return $result;
	}
?>