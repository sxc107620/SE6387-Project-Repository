<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    
<head>
<?php
	include ("head.php"); 
?>
    </head>
    <body>
	<?php
	error_reporting(0);
	session_start();
	if(isset($_SESSION['uName'])) {
		include ("./php/includes/settings.inc.php");        // database settings
		include ("./php/includes/connectdb.inc.php"); 
		include ("./php/includes/sql.php");
		?>
        <header id="header" class="media">
            <?php
				include ("header.php"); 
			?>
        </header>
        
        <div class="clearfix"></div>
		
		
        
        <section id="main" class="p-relative" role="main">
            
            <!-- Sidebar -->
            <aside id="sidebar">
                
                <!-- Sidbar Widgets -->
                <?php
					include ("side_widget.php"); 
				?>
                
                <!-- Side Menu -->
                <?php
					include ("menu.php"); 
				?>
                
                
            </aside>
        
            <!-- Content -->
            <section id="content" class="container">
                
                <!-- Breadcrumb -->
                <?php
					include ("breadcrumb.php"); 
				?>
                
                <article id="paragraph" class="block-area">
                    <h3 class="block-title">Chart Entities</h3>
                    <div class="tab-container tile media">
                        <ul class="tab pull-left tab-vertical nav nav-tabs" id="statTabs" style="height: auto;">
                            <li class="active"><a href="#driver">Drivers</a></li>
                            <li class=""><a href="#route">Routes</a></li>
							<li class=""><a href="#shuttle">Shuttles</a></li>
                        </ul>
                          
                        <div class="tab-content media-body">
                            <div class="tab-pane active" id="driver">
							<p>The following chart lists the amount of hours the driver has driven based on months:</p>
                                <div class="list-group block" id="driverHours">
									<div class="tile">
										<h2 class="tile-title h31">Driver Bar Chart</h2>
										<div class="tile-config input-group">
											<div class="input-group date w200" id="pickerDriver">
											<input type='text' class="form-control pull-right h30" id="queryDateDriver" disabled />
											<span class="input-group-addon add-on">
												<span class="glyphicon glyphicon-calendar"></span>
											</span>
											</div>
										</div>
										<div class="p-10">
											<div id="driver-chart" class="main-chart" style="height: 250px; width: 100%; padding: 0px; position: relative;"></div>
										</div>
									</div>
								</div>
                            </div>
                            <div class="tab-pane" id="route">
                            <p>The following chart lists the number of passengers who have taken the cabs in 24 hour based on route:</p>
                                <div class="list-group block" id="routeList">
									<div class="tile">
										<h2 class="tile-title h31">Route Bar Chart</h2>
										<div class="tile-config input-group w420">
										<select class="form-control input-sm m-b-10 pull-right w200 h30" id="routeSel">
											<option selected="true" style="display:none;">Select route</option>
											<?php
												$routeList = getRoutes();
												$colums = $routeList[0];
												$rows = (count($routeList)-1)/$colums;
												for($i = 0; $i < $rows; $i++) echo "<option>".$routeList[$colums*$i+2]."</option>";
											?>
										</select>
										<div class="input-group date w200" id="picker">
										<input type='text' class="form-control pull-right h30" id="queryDate" disabled />
										<span class="input-group-addon add-on">
											<span class="glyphicon glyphicon-calendar"></span>
										</span>
										</div>
										</div>
										<div class="p-10">
											<div id="route-chart" class="main-chart" style="height: 250px; width: 100%; padding: 0px; position: relative;"></div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="shuttle">
							<p>The following chart lists the number of passengers who have taken the cabs in the past 30 days:</p>
                                <div class="list-group block" id="shuttleUsageList">
									<?php
										$shuttleUsageList = getShuttleUsage();
										$shuttleList = '';
										$shuttleCapList = '';
										for($i = 0; $i < count($shuttleUsageList)/2; $i++) {
											$shuttleList .= (string)$shuttleUsageList[2*$i];
											if($i != (count($shuttleUsageList)/2)-1) $shuttleList .= ";";
											$shuttleCapList .= (string)$shuttleUsageList[2*$i+1];
											if($i != (count($shuttleUsageList)/2)-1) $shuttleCapList .= ";";
										}
									?>
									<div class="tile">
										<h2 class="tile-title h31">Shuttle Bar Chart</h2>
										<div class="p-10">
											<div id="shuttle-chart" class="main-chart" data-names=<?php echo $shuttleList; ?> data-cap=<?php echo $shuttleCapList; ?> style="height: 250px; width: 100%; padding: 0px; position: relative;"></div>
										</div>
									</div>
								</div>
                            </div>
                        </div>
                        
                    </div>
					<br>
                </article> 
                <hr class="whiter m-t-20 m-b-20" />
            </section>
        </section>
		
		<?php
				} else {
					?>
					<section id="error-page" class="tile">
						<h1 class="m-b-10">ERROR 401</h1>
						<p>Sorry, but this page needs an authorized credentials to be viewed.</p>
						<a class="underline" href="index.php">Back to login page</a> 
					</section>
					<?php
				}
		?>
        
        <?php
			include ("js.php"); 
		?>
		<!-- Charts -->
        <script src="js/charts/jquery.flot.js"></script> <!-- Flot Main -->
        <script src="js/charts/jquery.flot.time.js"></script> <!-- Flot sub -->
        <script src="js/charts/jquery.flot.animator.min.js"></script> <!-- Flot sub -->
        <script src="js/charts/jquery.flot.resize.min.js"></script> <!-- Flot sub - for repaint when resizing the screen -->
        <script src="js/charts/jquery.flot.orderBar.js"></script> <!-- Flot Bar chart -->
        <script src="js/charts/jquery.flot.pie.min.js"></script> <!-- Flot Pie chart -->
		<script src="js/charts/jquery.flot.categories.js"></script> <!-- Flot print names -->
		<script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
		<script src="js/datetimepicker.min.js"></script> <!-- Date time picker -->
		<script src="js/charts.js"></script> <!-- All the above chart related functions -->
    </body>
</html>
