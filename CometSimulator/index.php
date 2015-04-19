<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="apple-touch-icon" href="apple-touch-icon.png">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="css/main.css">

</head>

<body>
<?php
	include ("./php/settings.inc.php");        // database settings
	include ("./php/connectdb.inc.php"); 
	
	function getCabs() { 
		$result = array();
		$getList = mysql_query("SELECT number, status, routeid FROM `shuttles` WHERE 1");
		mysql_data_seek($getList, 0);
		while ($row = mysql_fetch_assoc($getList)) {
			$routeName = mysql_query("SELECT rname FROM `routes` WHERE `routeid` = '".$row['routeid']."'");
			$route = mysql_fetch_row($routeName, 0) or trigger_error(mysql_error().$routeName);
			
			array_push($result, $row['number'], $row['status'], $route[0]);
		}
		return $result;
	}
	
	function getRoutes() {
		$result = array();
		$getRoutesList = mysql_query("SELECT rname, routeid FROM `routes`");
		mysql_data_seek($getRoutesList, 0);
		while ($row = mysql_fetch_assoc($getRoutesList)) {
		  array_push($result, $row['rname'], $row['routeid']);
		}
		return $result;
	}
	
?>
    <nav class="navbar-custom navbar-inverse navbar-static-top " role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Comet Simulator</a>
			</div>
		</div>
	</nav>
	
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="container side-collapse-container well">
      <div class="row">
		<div >
			<form class="form-horizontal" role="form">
			<?php
				$cabListStat = getCabs();
				$routeList = getRoutes();
				$cabStat = array("on-duty", "off-duty");
				for($i = 0; $i < count($cabListStat)/3; $i++) {
					echo "<div class='col-md-4 well'>";
					echo "<div class='form-group'><label class= 'col-sm-2 control-label'>Cab: </label>";
					echo "<div class='col-sm-10'>";
					echo "<input type='text' value=".$cabListStat[$i*3]." class='form-control cabList' disabled/>";
					echo "</div></div>";
					
					echo "<div class='form-group'><label class= 'col-sm-2 control-label'>Status: </label>";
					echo "<div class='col-sm-10'>";
					echo "<select class='form-control statusList'>";
						for($j = 0; $j < count($cabStat); $j++) {
							if($cabListStat[$i*3+1] == $cabStat[$j]) $sel = "selected"; else $sel = '';
							echo "<option $sel>$cabStat[$j]</option>";
						}
					echo "</select>";
					echo "</div></div>";
					
					echo "<div class='form-group'><label class= 'col-sm-2 control-label'>Route: </label>";
					echo "<div class='col-sm-10'>";
					echo "<select class='form-control routeList'>";
						for($k = 0; $k < count($routeList)/2; $k++) {
							if($cabListStat[$i*3+2] == $routeList[$k*2]) $sel = "selected"; else $sel = '';
							echo "<option value='".$routeList[$k*2+1]."' $sel>".$routeList[$k*2]."</option>";
						}
					echo "</select>";
					echo "</div></div>";
					echo "</div>";
				}
			?>
			</form>
		</div>
	  </div>
	  <div class="footer text-center">
		 <button type="button" class="btn btn-warning" id="run">Run</button>
		 <button type="button" class="btn btn-warning" id="stop">Stop</button>
	  </div>
	</div>



    <script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
    <script>
        window.jQuery || document.write('<script src="js/vendor/jquery-1.11.2.min.js"><\/script>')
    </script>

    <script src="js/vendor/bootstrap.min.js"></script>

    <script src="js/main.js"></script>
</body>

</html>