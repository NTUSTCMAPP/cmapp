
<?php
echo $_FILES['image']['name'] . '<br/>';


//ini_set('upload_max_filesize', '10M');
//ini_set('post_max_size', '10M');
//ini_set('max_input_time', 300);
//ini_set('max_execution_time', 300);
require_once __DIR__ . '/db_connect.php';

if(is_dir("./photo/")){
	$db = new DB_CONNECT();
	$UserIndex = basename($_FILES['image']['name']);
	echo $UserIndex;
	$fileName="";
	$target_path = "./photo/";

	$result = mysql_query("SELECT UserID FROM User WHERE UserIndex = $UserIndex");
	if (mysql_num_rows($result) > 0) {

    
  	  	$row = mysql_fetch_array($result);
		$fileName =$row["UserID"];
		$target_path = $target_path . $fileName.".jpg";

    }else{

		$target_path = $target_path . basename($_FILES['image']['name']).".jpg";
		$fileName= basename($_FILES['image']['name']);
    }
        //String to Integer
	

	
	//$target_path = $target_path . "test.jpg";


	try {
	    //throw exception if can't move the file
	    
	    if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path)) {
	        throw new Exception('Could not move file');
	    }else{
	    	chmod($target_path, 0777);
	    }
	   

	    echo "The file " . $fileName .
	    " has been uploaded"
	} catch (Exception $e) {
	    die('File did not upload: ' . $e->getMessage());
	}
}else{
	echo "dir isn't exist";
}


?>

