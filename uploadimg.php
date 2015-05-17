
<?php
echo $_FILES['image']['name'] . '<br/>';


//ini_set('upload_max_filesize', '10M');
//ini_set('post_max_size', '10M');
//ini_set('max_input_time', 300);
//ini_set('max_execution_time', 300);
if(is_dir("./photo/")){
	$target_path = "./photo/";

	$target_path = $target_path . basename($_FILES['image']['name']);
	//$target_path = $target_path . "test.jpg";


	try {
	    //throw exception if can't move the file
	    
	    if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path)) {
	        throw new Exception('Could not move file');
	    }else{
	    	chmod($target_path, 0777);
	    }
	    $website = isset($_POST['website']) ? $_POST['website'] : '';
	    

	    echo "The file " . basename($_FILES['image']['name']) .
	    " has been uploaded //webSite:".$website;
	} catch (Exception $e) {
	    die('File did not upload: ' . $e->getMessage());
	}
}else{
	echo "dir isn't exist";
}


?>

