<?php
header('Content-Type: text/event-stream');
header('Cache-Control: no-cache');
include ("./includes/settings.inc.php");        // database settings
include ("./includes/connectdb.inc.php"); 
include ("./includes/sql.php");

getLocation();