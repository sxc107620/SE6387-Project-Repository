<?php
	$pageName = basename($_SERVER['PHP_SELF']);
?>
<ul class="list-unstyled side-menu">
					<li <?php if($pageName == "driver.php") echo "class='active'" ?>>
                        <a class="sa-side-chart" href="driver.php">
                            <span class="menu-item">Statistics</span>
                        </a>
                    </li>
                </ul>