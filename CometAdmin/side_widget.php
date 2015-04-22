<div class="side-widgets overflow" tabindex="5000" style="overflow: hidden; outline: none;">
                    <!-- Profile Menu -->
                    <div class="text-center s-widget m-b-25 dropdown open" id="profile-menu">
                        <?php
							if (!isset($_SESSION)) {
								session_start();
							}
							$src = "./uploads/".$_SESSION['uName']."/profilePic.jpg";
							if(!file_exists($src)) $src = "img/profile-pic.jpg";
						?>
                            <img class="profile-pic" src=<?php echo $src; ?> alt="">
                        
                        <ul class="dropdown-menu profile-menu">
                            <li><a data-toggle='modal' href="#modalEditProfile">
							<?php
							
							if(isset($_SESSION['uName'])) 
							echo ucfirst($_SESSION['uName'])."'s";
						?>

							Profile</a></li>
                            <li><a href=<?php echo "'logout.php'"; ?>>Sign Out</a></li>
                        </ul>
                    </div>
                    
                    <!-- Calendar -->
                    <div class="s-widget m-b-25">
                    <div id="sidebar-calendar" class="fc fc-ltr"></div>
					</div>
                    
                    <!-- Feeds -->
                    <div class="s-widget m-b-25">
                        <h2 class="tile-title">
                           News Feeds
                        </h2>
                        
                        <div class="s-widget-body">
							<div id="news-feed"></div>
						</div>
                    </div>
                    
                    
                </div>