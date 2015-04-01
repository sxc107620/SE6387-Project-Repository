<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    
<head>
<?php
	include ("head.php"); 
?>
    </head>
    <body id="skin-blur-violate">
	<?php
	error_reporting(0);
	session_start();
	if(isset($_SESSION['uName'])) {
		include ("./php/includes/settings.inc.php");        // database settings
		include ("./php/includes/connectdb.inc.php"); 
		include ("./php/includes/sql.php");
		if(isset($_REQUEST['uname']) && $_REQUEST['uname']!="") {
			newUser($_REQUEST['uname'], md5($_REQUEST['p']), $_REQUEST['mail'], $_REQUEST['t']);
		}
		if(isset($_REQUEST['n']) && $_REQUEST['n']!="") {
			newShuttle($_REQUEST['n'],  $_REQUEST['shuttle']);
		}
		if(isset($_REQUEST['status']) && $_REQUEST['status']!="") {
			deleteUser($_REQUEST['e'], $_REQUEST['status']);
		}
		if(isset($_REQUEST['snum']) && $_REQUEST['snum']!="") {
			deleteShuttle($_REQUEST['snum']);
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
		<div class="modal fade" id="modalDetails" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
			<?php
				include ("modal_box.php"); 
			?>
		</div>
		<div class="modal fade" id="modalCreate" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
			<?php
				include ("modal_box.php"); 
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
                
                <article id="paragraph" class="block-area">
                    <h3 class="block-title">Modify Entities</h3>
                    <div class="tab-container tile media">
                        <ul class="tab pull-left tab-vertical nav nav-tabs" style="height: auto;">
                            <li class="active"><a href="#admin">Admin</a></li>
                            <li class=""><a href="#driver">Driver</a></li>
                            <li class=""><a href="#shuttle">Shuttle</a></li>
                        </ul>
                          
                        <div class="tab-content media-body">
                            <div class="tab-pane active" id="admin">
							<p>The list of admins available in the system:</p>
                                <div class="list-group block scrollList overflow" id="adminList">
									<?php
										$adminList = getUsers("admin");
										for($i = 0; $i < count($adminList)/3; $i++) {
											echo "<a data-toggle='modal' data-email='".$adminList[3*$i+1]."' data-type='admin' data-date='".$adminList[3*$i+2]."' data-name='".$adminList[3*$i]."' href='#modalDetails' class='list-group-item'>".ucfirst($adminList[3*$i])."</a>";
										}
									?>
								</div>
								<a data-toggle='modal' href="#modalCreate" data-type='admin' class="btn pull-right">Create New Admin</a>
                            </div>
                            <div class="tab-pane" id="driver">
                            <p>The list of drivers available in the system:</p>
                                <div class="list-group block scrollList overflow" id="driverList">
									<?php
										$driverList = getUsers("driver");
										for($i = 0; $i < count($driverList)/3; $i++) {
											echo "<a data-toggle='modal' data-email='".$driverList[3*$i+1]."' data-type='driver' data-date='".$driverList[3*$i+2]."' data-name='".$driverList[3*$i]."' href='#modalDetails' class='list-group-item'>".ucfirst($driverList[3*$i])."</a>";
										}
									?>
								</div>
								<a data-toggle='modal' href="#modalCreate" data-type='driver' class="btn pull-right">Create New Driver</a>
							</div>
                            <div class="tab-pane" id="shuttle">
                            <p>The list of cabs available in the system:</p>
                                <div class="list-group block scrollList overflow" id="cabList">
									<?php
										$cabList = getCabs();
										for($i = 0; $i < count($cabList)/8; $i++) {
											echo "<a data-toggle='modal' data-name='".$cabList[8*$i]."' data-status='".$cabList[8*$i+1]."' data-type='".$cabList[8*$i+2]."' data-latitude='".$cabList[8*$i+3]."' data-longitude='".$cabList[8*$i+4]."' data-route='".$cabList[8*$i+5]."' data-capacity='".$cabList[8*$i+6]."' data-passengers='".$cabList[8*$i+7]."' href='#modalDetails' class='list-group-item'>".$cabList[8*$i]."</a>";
										}
									?>
								</div>
								<a data-toggle='modal' href="#modalCreate" data-type='shuttle' class="btn pull-right">Create New Shuttle</a>    
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
    </body>
</html>
