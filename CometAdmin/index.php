<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie9"><![endif]-->
    
<head>
<?php
	include ("head.php"); 
?>
</head>
<?php
session_start();
if(isset($_SESSION['uName'])) header('Location: dashboard.php');
?>
    <body id="skin-blur-violate">
        <section id="login">
            <header>
                <h1>ADMIN PORTAL</h1>
                <p>Please login using your credentials.</p>
            </header>
<?php
error_reporting(0);
function rand_string( $length ) {
    $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    return substr(str_shuffle($chars),0,$length);
}
if(isset($_POST['submit'])){
	include ("./php/includes/settings.inc.php");        // database settings
	include ("./php/includes/connectdb.inc.php"); 
	include ("./php/includes/sql.php"); 
	$userid = $_POST['uid'];
    $pwd = $_POST['passwd'];
	$type = getUserType($userid);
	if($type == "admin" || $type == "superadmin") {
	$attempts = getAttempts($userid);
	if($attempts < 2) {
	if(md5($pwd) == getPwd($userid)) {
	session_start();
	$_SESSION['uName'] = $userid;
	$_SESSION['type'] = $type;
	$_SESSION['email'] = getEmail($userid);
	header('Location: dashboard.php');
	updateAttempt($userid, 0);
	} else {
	$attempts = $attempts + 1;
	updateAttempt($userid, $attempts);
	?>
	<div class="alert alert-info alert-danger">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		<i class="fa fa-info-circle"></i>  <strong>Check your password.</strong> You have <?php echo 3-$attempts; ?> attempt(s) left.
	</div>
	<?php
	}
	} else {
	$randomPass = rand_string(8);
	changePass($userid, md5($randomPass));
	$to = getEmail($userid);
	$subject = "Your new password for SHAS";
	mail($to,$subject,$randomPass);
	$attempts = 0;
	updateAttempt($userid, $attempts);
	?>
	<div class="alert alert-info alert-danger">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		<i class="fa fa-info-circle"></i>  <strong>Account has frozen. New password has been mailed to your email id</strong><br/><?php echo "For demo purpose your new password is ".$randomPass; ?>
	</div>
	<?php
	}
} else {
	?>
	<div class="alert alert-info alert-danger">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		<i class="fa fa-info-circle"></i>  <strong>Invalid Username</strong><br/>
	</div>
	<?php
}
}
?>
        
            <div class="clearfix"></div>
            
            <!-- Login -->
            <form method="post" action='index.php' class="box tile animated active" id="box-login">
                <h2 class="m-t-0 m-b-15">Login</h2>
                <input type="text" class="login-control m-b-10" name="uid" placeholder="Username">
                <input type="password" class="login-control m-b-10" name="passwd" placeholder="Password">
                
				<input type="submit" value="Sign in" name="submit" class="btn btn-sm m-r-5"/>
                
                <small>
                    <a class="box-switcher" data-switch="box-reset" href="#">Forgot Password?</a>
                </small>
            </form>
 
            
            <!-- Forgot Password -->
            <form class="box animated tile" id="box-reset">
                <h2 class="m-t-0 m-b-15">Reset Password</h2>
                <p>Please enter the email address which was used to create the account.</p>
                <input type="email" class="login-control m-b-20" placeholder="Email Address">    

                <button class="btn btn-sm m-r-5">Reset Password</button>

                <small><a class="box-switcher" data-switch="box-login" href="#">Already have an Account?</a></small>
            </form>
        </section>                      
        
        <?php
				include ("js.php"); 
		?>
    </body>
</html>
