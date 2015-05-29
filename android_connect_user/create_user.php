<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();
$responseOfGetIndex = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$SQLString="SELECT MAX(UserIndex)AS max_index FROM User ";
$responseOfGetIndex = mysql_query($SQLString) or die(mysql_error());//從ＵＳＥＲ資料表
//Max抓最大值 並令其為max_index
// check for empty result
if (mysql_num_rows($responseOfGetIndex) > 0) {

    
    $row = mysql_fetch_array($responseOfGetIndex);

     
        $UserIndex = intval($row["max_index"]);//String to Integer
    
        echo "index:" . $UserIndex."</br>";
        array_push($response["Users"], $Users);
        
   $newUserID=createUserId($UserIndex+1,10);//新增userID (index,總位數)
   echo "newUserId:" . $newUserID;
    // success
    $response["success"] = 1;

    //echo "USER的指標是" . $UserIndex."USER的TYPE是".get_type($UserIndex);
    //echo "USER的TYPE是".get_type($UserIndex);
    //string $UserIndexStr =$UserIndex;
    //echo "USER的指標STR是" . $UserIndexStr;

//echo  "加上一是".$UserIndex+1 ;
    
}

//echo  "加上一是".（int)$UserIndex+1 ;
// check for required fields
// if ( isset($_POST['UserName']) && isset($_POST['BeaconID'])) {
    
//     $UserID = $UserIndex+1;
//     $UserName = $_POST['UserName'];
//     $BeaconID = $_POST['BeaconID'];

//     // include db connect class
//     require_once __DIR__ . '/db_connect.php';

//     // connecting to db
//     $db = new DB_CONNECT();

//     // mysql inserting a new row
//     $result = mysql_query("INSERT INTO User(UserID, UserName, BeaconID) VALUES('$UserID', '$UserName', '$BeaconID')");

//     // check if row inserted or not
//     if ($result) {
//         // successfully inserted into database
//         $response["success"] = 1;
//         $response["message"] = "Product successfully created.";

//         // echoing JSON response
//         echo json_encode($response);
//     } else {
//         // failed to insert row
//         $response["success"] = 0;
//         $response["message"] = "Oops! An error occurred.";
        
//         // echoing JSON response
//         echo json_encode($response);
//     }
// } else {
//     // required field is missing
//     $response["success"] = 0;
//     $response["message"] = "Required field(s) is missing";

//     // echoing JSON response
//     echo json_encode($response);
// }

    function createUserId($index, $length)
    {
        $newUserID=(string)$index;

        while(strlen($newUserID)<$length){
            $newUserID="0". $newUserID;
        }
        
        return  $newUserID;
    }

?>