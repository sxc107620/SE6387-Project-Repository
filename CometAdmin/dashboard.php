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
                    <h3 class="block-title">Paragraph</h3>
                    <p>Pellentesque lacinia sagittis libero et feugiat. Etiam volutpat adipiscing tortor non luctus. Vivamus venenatis vitae metus et aliquet. Praesent vitae justo purus. In hendrerit lorem nisl, ac lacinia urna aliquet non. Quisque nisi tellus, rhoncus quis est sit amet, lacinia euismod nunc. Aenean nec nibh velit. Fusce quis ante in nisl molestie fringilla. Nunc vitae ante id magna feugiat condimentum. Maecenas sit amet urna massa.</p>
                    <p>Integer eu lectus sollicitudin, hendrerit est ac, sollicitudin nisl. Quisque viverra sodales lectus nec ultrices. Fusce elit dolor, dignissim a nunc id, varius suscipit turpis. Cras porttitor turpis vitae leo accumsan molestie. Morbi vitae luctus leo. Sed nec scelerisque magna, et adipiscing est. Proin lobortis lectus eu sem ullamcorper, commodo malesuada quam fringilla. Curabitur ac nunc dui. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sagittis enim eu est lacinia, ut egestas ligula imperdiet.</p>
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
