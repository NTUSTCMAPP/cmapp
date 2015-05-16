<?php

/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['UserID']) && isset($_POST['UserName']) && isset($_POST['BeaconID']) ) {
    
    $UserID = $_POST['UserID'];
    $UserName = $_POST['UserName'];
    $BeaconID = $_POST['BeaconID'];
    //$description = $_POST['description'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("UPDATE User SET UserName = '$UserName', BeaconID = '$BeaconID' WHERE UserID = $UserID");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "User successfully updated.";
        
        // echoing JSON response
        echo json_encode($response);
    } else {
        
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>
