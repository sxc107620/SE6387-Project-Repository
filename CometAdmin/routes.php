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
		
			<!-- Modal Default -->
			<div class="modal fade" id="modalCreateRoute" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
				<?php
					include ("modal_box_map.php"); 
				?>
			</div>
            
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
                <?php
					$routeList = getRoutes();
					$colums = $routeList[0];
					$rows = (count($routeList)-1)/$colums;
				?>
                <article id="paragraph" class="block-area">
                    <h3 class="block-title" id="routeCount" data-count=<?php echo $rows; ?>>Route Lists</h3>
                    <div class="tab-container tile media">
                        <ul class="tab pull-left tab-vertical nav nav-tabs" style="height: auto;">
							<?php
							for($i = 0; $i < $rows; $i++) {
								if($i == 0) $active = "class='active'"; else $active = "";
								echo "<li ".$active." ><a class='mapTabs' href='#".$i."'>".$routeList[$colums*$i+2]."</a></li>";
							}
							?>
                        </ul>
                          
                        <div class="tab-content media-body">
							<?php
							for($i = 0; $i < $rows; $i++) {
								$dataAttr = "data-rid='".$routeList[$colums*$i+1]."' data-rname='".$routeList[$colums*$i+2]."' data-color='".$routeList[$colums*$i+3]."' data-focus='".$routeList[$colums*$i+4]."' data-waypoints='".$routeList[$colums*$i+5]."' ";
								if($i == 0) $active = "active"; else $active = "";
								echo "<div class='tab-pane $active' id='".$i."'>";
								echo "<p>The ".$routeList[$colums*$i+2]." route with its save boarding points:</p>";
								echo "<div id='".$i."_Map' class='routeMap' ".$dataAttr."></div>";
								echo "<a href='#modalCreateRoute' data-toggle='modal' class='btn pull-right'>Create New Route</a>";
								echo "</div>";
							} 
							?>
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
        
        <!-- Javascript Libraries -->
        <!-- jQuery -->
        <script src="js/jquery.min.js"></script> <!-- jQuery Library -->
        <script src="js/jquery-ui.min.js"></script> <!-- jQuery UI -->
        <script src="js/jquery.easing.1.3.js"></script> <!-- jQuery Easing - Requirred for Lightbox -->
        
        <!-- Bootstrap -->
        <script src="js/bootstrap.min.js"></script>
        
        <!-- Map -->
        <script src="http://maps.googleapis.com/maps/api/js"></script>
        
        <!-- UX -->
        <script src="js/scroll.min.js"></script> <!-- Custom Scrollbar -->
        
        <!-- Other -->
        <script src="js/calendar.min.js"></script> <!-- Calendar -->
        <script src="js/feeds.min.js"></script> <!-- News Feeds -->
        
        
        <!-- All JS functions -->
        <script src="js/functions.js"></script>
    </body>
</html>
