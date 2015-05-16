<?php

/*
 * Following code will delete a product from table
 * A product is identified by product id (pid)

 原本是ＰＩＤ，改成傳送ＵＳＥＲＩＤ
 
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['UserID'])) {
    $UserID = $_POST['UserID'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysql_query("DELETE FROM User WHERE UserID = $UserID");//在ＵＳＥＲ資料表中
    
    // check if row deleted or not
    if (mysql_affected_rows() > 0) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "User successfully deleted";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No User found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>