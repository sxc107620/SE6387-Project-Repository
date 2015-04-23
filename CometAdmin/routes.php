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
		if($_SESSION['type'] == 'driver') header('Location: driver.php');
		if(isset($_SESSION['uName'])) {
			include ("./php/includes/settings.inc.php");        // database settings
			include ("./php/includes/connectdb.inc.php"); 
			include ("./php/includes/sql.php");
			if(isset($_REQUEST['n']) && $_REQUEST['n']!="") {
				$points = str_replace("\\", "\\\\", $_REQUEST['p']); 
				$line = str_replace("\\", "\\\\", $_REQUEST['l']); 
				$curve = str_replace("\\", "\\\\", $_REQUEST['c']); 
				newRoute($_REQUEST['n'],  $_REQUEST['s'], $points, $line,  $curve);
			}
			if(isset($_REQUEST['i']) && $_REQUEST['i']!="") {
				deleteRoute($_REQUEST['i']);
			}
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
			
			<div class="modal fade" id="modalCreateRouteDialog" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
				<?php
					include ("modal_box_map_dialog.php"); 
				?>
			</div>
			
			<div class="modal fade" id="modalConfirm" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
				<?php
					include ("modal_box.php"); 
				?>
			</div>
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
								$dataAttr = "data-rid='".$routeList[$colums*$i+1]."' data-rname='".$routeList[$colums*$i+2]."' data-color='".$routeList[$colums*$i+3]."' data-savepoints='".$routeList[$colums*$i+4]."' data-lines='".$routeList[$colums*$i+5]."' data-curves='".$routeList[$colums*$i+6]."' ";
								if($i == 0) $active = "active"; else $active = "";
								echo "<div class='tab-pane $active' id='".$i."'>";
								echo "<p>The ".$routeList[$colums*$i+2]." route with its save boarding points:</p>";
								echo "<div id='".$i."_Map' class='routeMap' ".$dataAttr."></div>";
								echo "<a href='#modalCreateRouteDialog' data-toggle='modal' class='btn pull-right'>Create New Route</a>";
								echo "<a href='#modalConfirm' data-toggle='modal' class='btn pull-right'>Delete Route</a>";
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
        
        <?php
			include ("js.php"); 
		?>
    </body>
</html>
