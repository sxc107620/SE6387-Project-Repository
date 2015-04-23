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
                    <h3 class="block-title">Comet Admin</h3>
                    <p>Comet Admin is a system that can be used to maintain the users of the Transport Department. The admin can create new routes and delete exisiting once based on the need. The statistics page lets the admin to keep 
					track of how many hours a driver has served and what routes have been frequently been used. The tracking page lets the admin to find out the location of a cab in real time, whether its on-duty or not.</p>
					<p>The following demo shows the admin how to use the portal.</p>
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
