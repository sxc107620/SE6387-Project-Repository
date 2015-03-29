<?php
	$pageName = basename($_SERVER['PHP_SELF']);
?>
<ul class="list-unstyled side-menu">
                    <li <?php if($pageName == "dashboard.php") echo "class='active'" ?>>
                        <a class="sa-side-home" href="dashboard.php">
                            <span class="menu-item">Dashboard</span>
                        </a>
                    </li>
					<li <?php if($pageName == "statistics.php") echo "class='active'" ?>>
                        <a class="sa-side-chart" href="statistics.php">
                            <span class="menu-item">Statistics</span>
                        </a>
                    </li>
                    <li <?php if($pageName == "users.php") echo "class='active'" ?>>
                        <a class="sa-side-typography" href="users.php">
                            <span class="menu-item">User Manager</span>
                        </a>
                    </li>
                    <li <?php if($pageName == "routes.php") echo "class='active'" ?>>
                        <a class="sa-side-widget" href="routes.php">
                            <span class="menu-item">Route Manager</span>
                        </a>
                    </li>
                    <li <?php if($pageName == "track.php") echo "class='active'" ?>>
                        <a class="sa-side-table" href="track.php">
                            <span class="menu-item animated">Track Shuttles</span>
                        </a>
                    </li>
                </ul>