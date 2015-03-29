<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    
<head>
<?php
	include ("head.php"); 
?>
    </head>
    <body id="skin-blur-violate">
	<?php
				session_start();
				if(isset($_SESSION['uName'])) {
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
                
                <div class="block-area">
                    <h3 class="block-title">Driver and Shuttle Statistics</h3>
                    
                    <div class="tile">
                        <h2 class="tile-title">Shuttle Line Chart</h2>
                        <div class="tile-config dropdown">
                            <a data-toggle="dropdown" href="#" class="tooltips tile-menu" title="Options"></a>
                            <ul class="dropdown-menu pull-right text-right">
                                <li><a href="#">Refresh</a></li>
                                <li><a href="#">Settings</a></li>
                            </ul>
                        </div>
                        <div class="p-10">
                            <div id="line-chart" class="main-chart" style="height: 250px"></div>
                        </div>
                    </div>
                    
                  
                    
                    <div class="tile">
                        <h2 class="tile-title">Driver Bar Chart</h2>
                        <div class="tile-config dropdown">
                            <a data-toggle="dropdown" href="#" class="tooltips tile-menu" title="Options"></a>
                            <ul class="dropdown-menu pull-right text-right">
                                <li><a href="#">Refresh</a></li>
                                <li><a href="#">Settings</a></li>
                            </ul>
                        </div>
                        <div class="p-10">
                            <div id="bar-chart" class="main-chart" style="height: 250px"></div>
                        </div>
                    </div>
                
  
                    <!-- Pies -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="tile">
                                <h2 class="tile-title"> DriverPie Chart</h2>
                                <div class="tile-config dropdown">
                                    <a data-toggle="dropdown" href="#" class="tooltips tile-menu" title="Options"></a>
                                    <ul class="dropdown-menu pull-right text-right">
                                        <li><a href="#">Refresh</a></li>
                                        <li><a href="#">Settings</a></li>
                                    </ul>
                                </div>
                                <div class="p-10">
                                    <div id="pie-chart" class="main-chart" style="height: 300px"></div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-md-6">
                            <div class="tile">
                                <h2 class="tile-title">Driver Donut Chart</h2>
                                <div class="tile-config dropdown">
                                    <a data-toggle="dropdown" href="#" class="tooltips tile-menu" title="Options"></a>
                                    <ul class="dropdown-menu pull-right text-right">
                                        <li><a href="#">Refresh</a></li>
                                        <li><a href="#">Settings</a></li>
                                    </ul>
                                </div>
                                <div class="p-10">
                                    <div id="donut-chart" class="main-chart" style="height: 300px"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <hr class="whiter m-t-20 m-b-20" />

                <div class="block-area">
                    <h3 class="block-title">Driver Work Chart</h3>
                    
                    <!-- Easy Pie charts -->
                    <div class="tile text-center">
                        <div class="tile-dark p-10">
                            <div class="pie-chart-tiny" data-percent="86">
                                <span class="percent"></span>
                                <span class="pie-title">Driver 1 <i class="m-l-5 fa fa-retweet"></i></span>
                            </div>
                            <div class="pie-chart-tiny" data-percent="23">
                                <span class="percent"></span>
                                <span class="pie-title">Driver 2 <i class="m-l-5 fa fa-retweet"></i></span>
                            </div>
                            <div class="pie-chart-tiny" data-percent="57">
                                <span class="percent"></span>
                                <span class="pie-title">Driver 3 <i class="m-l-5 fa fa-retweet"></i></span>
                            </div>
                            <div class="pie-chart-tiny" data-percent="34">
                                <span class="percent"></span>
                                <span class="pie-title">Driver 4 <i class="m-l-5 fa fa-retweet"></i></span>
                            </div>
                            <div class="pie-chart-tiny" data-percent="81">
                                <span class="percent"></span>
                                <span class="pie-title">Driver 5 <i class="m-l-5 fa fa-retweet"></i></span>
                            </div>
                        </div>
                    </div>
                </div>
                
                <hr class="whiter m-t-20 m-b-20" />
                
               
                
              
                <br/><br/><br/>
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
        
        <!-- Javascript Libraries -->
        <!-- jQuery -->
        <script src="js/jquery.min.js"></script> <!-- jQuery Library -->
        <script src="js/jquery-ui.min.js"></script> <!-- jQuery UI -->
        <script src="js/jquery.easing.1.3.js"></script> <!-- jQuery Easing - Requirred for Lightbox -->
        
        <!-- Bootstrap -->
        <script src="js/bootstrap.min.js"></script>
        
        <!-- Charts -->
        <script src="js/charts/jquery.flot.js"></script> <!-- Flot Main -->
        <script src="js/charts/jquery.flot.time.js"></script> <!-- Flot sub -->
        <script src="js/charts/jquery.flot.animator.min.js"></script> <!-- Flot sub -->
        <script src="js/charts/jquery.flot.resize.min.js"></script> <!-- Flot sub - for repaint when resizing the screen -->
        <script src="js/charts/jquery.flot.orderBar.js"></script> <!-- Flot Bar chart -->
        <script src="js/charts/jquery.flot.pie.min.js"></script> <!-- Flot Pie chart -->
 
        <script src="js/sparkline.min.js"></script> <!-- Sparkline - Tiny charts -->
        <script src="js/easypiechart.js"></script> <!-- EasyPieChart - Animated Pie Charts -->
        <script src="js/charts.js"></script> <!-- All the above chart related functions -->
        
        <!-- Map -->
        <script src="js/maps/jvectormap.min.js"></script> <!-- jVectorMap main library -->
        <script src="js/maps/usa.js"></script> <!-- USA Map for jVectorMap -->
        <script src="js/maps/world.js"></script> <!-- World Map for jVectorMap -->
        
        <!-- UX -->
        <script src="js/scroll.min.js"></script> <!-- Custom Scrollbar -->
        
        <!-- Other -->
        <script src="js/calendar.min.js"></script> <!-- Calendar -->
        <script src="js/feeds.min.js"></script> <!-- News Feeds -->
        
        
        <!-- All JS functions -->
        <script src="js/functions.js"></script>
    </body>
</html>
