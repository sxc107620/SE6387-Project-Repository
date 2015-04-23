   var TheUploadURL = "#";

    $.post("gcsprocess.php",'',function(uploadurl){
		
		 TheUploadURL = ""+uploadurl+"";
    });