<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    
<head>
<?php
	include ("head.php"); 
?>
    </head>
    <body>
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
		
		<!-- Modal Default -->
		<?php 
			include('modal_edit_profile.php'); 
		?>
            
            <!-- Sidebar -->
            <aside id="sidebar">
                
                <!-- Sidbar Widgets -->
                <?php
					include ("side_widget.php"); 
				?>
                
                <!-- Side Menu -->
                <?php
					include ("menu_driver.php"); 
				?>
                
                
            </aside>
        
            <!-- Content -->
            <section id="content" class="container">
                
                <!-- Breadcrumb -->
                <?php
					include ("breadcrumb.php"); 
				?>
                
                <article id="paragraph" class="block-area">
                    <h3 class="block-title">Statistics</h3>
					<div class="tile tab-content">
                    <p>The following chart lists the amount of hours you have driven based on months:</p>
					<div class="list-group block" id="driverHours">
						<div class="tile">
							<h2 class="tile-title h31">Driver Pie Chart</h2>
							<div class="tile-config input-group">
								<div class="input-group date w200" id="pickerDriver">
								<input type="text" class="form-control pull-right h30" id="queryDateDriver" disabled="">
								<span class="input-group-addon add-on">
									<span class="glyphicon glyphicon-calendar"></span>
								</span>
								</div>
							</div>
							<div class="p-10">
									<div id="driver-chart" data-uname=<?php echo $_SESSION['uName']; ?> class="main-chart" style="height: 250px; width: 100%; padding: 0px; position: relative;"></div>
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
		<script src="js/charts/jquery.flot.tooltip.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment-with-locales.js"></script>
		<script src="js/datetimepicker.min.js"></script> <!-- Date time picker -->
		<script src="js/driver_charts.js"></script> <!-- All the above chart related functions -->
    </body>
</html>
