<?php
ini_set('display_errors', TRUE);

use google\appengine\311243137457\cloud_storage\CloudStorageTools;

const BUCKET = 'effective-time-87319.appspot.com';
$file_name = 'gs://' . BUCKET . '/profilePic.jpg';
$some_text = !empty($_POST['some_text']) ? $_POST['some_text'] : '';



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