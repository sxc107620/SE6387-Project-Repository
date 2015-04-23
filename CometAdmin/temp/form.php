<?php
ini_set('display_errors', TRUE);

const BUCKET = 'boombatower-drupal.appspot.com';
$file_name = 'gs://' . BUCKET . '/profilePic.jpg';
$some_text = !empty($_POST['some_text']) ? $_POST['some_text'] : '';

require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;

$options = [ 'gs_bucket_name' => BUCKET ];
$upload_url = CloudStorageTools::createUploadUrl('/upload.php', $options);
?>
 
 
<form action="<?php echo $upload_url?>" enctype="multipart/form-data" method="post">
Files to upload: <br>
<input type="text" name="some_text" value="<?= $some_text; ?>">
<input type="file" name="uploaded_files" size="40">
<input type="submit" value="Send">
</form>
 
<?php

if (!empty($_FILES) && !empty($_FILES["uploaded_files"]['tmp_name'])) {
  var_dump($_FILES);
  $gs_name = $_FILES["uploaded_files"]['tmp_name'];
  move_uploaded_file($gs_name, $file_name);
}

if (file_exists($file_name)) {
  readfile($file_name);
}
?>