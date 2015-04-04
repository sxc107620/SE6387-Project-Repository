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

    <!--[if lt IE 9]>
            <script src="js/vendor/html5-3.6-respond-1.4.2.min.js"></script>
        <![endif]-->
</head>

<body>
    <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
    <nav class="navbar-custom navbar-inverse navbar-fixed-top " role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">CometRide</a>
				<button data-toggle="collapse-side" data-target=".side-collapse" data-target-2=".side-collapse-container" type="button" class="navbar-toggle pull-right"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
            </div>
			<div class="navbar-inverse side-collapse in overflow" id="routeList">
			  <nav role="navigation" class="navbar-collapse">
				<ul class="nav navbar-nav nav-list">
				<?php
					include ("./php/includes/settings.inc.php");        // database settings
					include ("./php/includes/connectdb.inc.php"); 
					include ("./php/includes/sql.php");
					if(isset($_REQUEST['i']) && $_REQUEST['i']!="") {
						interest($_REQUEST['i'], $_REQUEST['l'], $_REQUEST['lg']);
					}
					$routeList = getRoutes();
					$colums = $routeList[0];
					$rows = (count($routeList)-1)/$colums;
					for($i = 0; $i < $rows; $i++) {
						if($i == 0) $active = "class='active'"; else $active = "";
						$dataAttr = "data-rid='".$routeList[$colums*$i+1]."' data-rname='".$routeList[$colums*$i+2]."' data-color='".$routeList[$colums*$i+3]."' data-savepoints='".$routeList[$colums*$i+4]."' data-lines='".$routeList[$colums*$i+5]."' data-curves='".$routeList[$colums*$i+6]."' ";
						echo "<li ".$active." ><a class='routeChange' href='#' ".$dataAttr.">".$routeList[$colums*$i+2]."</a></li>";
					}
				?>
				</ul>
			  </nav>
			</div>
        </div>

    </nav>
    <!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="container side-collapse-container">
      <div class="row">
		<div id="map-container"></div>
	  </div>
	  <div class="footer interest">
		 <button type="submit" class="btn btn-default">I'm interested</button>
	  </div>
	</div>



    <script src="https://code.jquery.com/jquery-2.1.3.min.js"></script>
    <script>
        window.jQuery || document.write('<script src="js/vendor/jquery-1.11.2.min.js"><\/script>')
    </script>

    <script src="js/vendor/bootstrap.min.js"></script>
	<script src="js/vendor/scroll.min.js"></script>
    <script src="http://maps.googleapis.com/maps/api/js?v=3&sensor=false&libraries=geometry"></script>
    <script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/infobox/src/infobox.js"></script>

    <script src="js/main.js"></script>
</body>

</html>