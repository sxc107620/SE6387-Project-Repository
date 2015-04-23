<?php
require_once 'google/appengine/api/cloud_storage/CloudStorageTools.php';
use google\appengine\api\cloud_storage\CloudStorageTools;

$fileName = 'gs://tempprofilepic/'.$_FILES['userfile']['name'];
echo $fileName."<br>";

$options = array('gs'=>array('acl'=>'public-read','Content-Type' => $_FILES['userfile']['type']));
$ctx = stream_context_create($options);

if (false == rename($_FILES['userfile']['tmp_name'], $fileName, $ctx)) {
  die('Could not rename.');
}

$object_public_url = CloudStorageTools::getPublicUrl($fileName, true);
echo $objectPublicUrl."<br>";
//header('Location:' .$objectPublicUrl); // redirects the user to the public uploaded file, comment any previous output (echo's).
?>