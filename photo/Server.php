<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>
</head>
<body>
	<?php


		$DataBase="cmapp";//資料庫
		$server="cmapp.nado.tw";//server IP\DNS
		$username="mobileUser";//mysql username
		$passwd="ntustmobile";//mysql password

		$Connect=mysql_connect($server,$username,$passwd) or die("Wrong!");//建立連線
		//echo "Sucesses!!<br>";
		mysql_select_db($DataBase,$Connect);//設定連線資料庫
		$result=mysql_query("SELECT * FROM User") or die('query error' . mysql_error());//下SQL抓資料
		
		$userData = array();//存取抓到的資料

	

		while($row =mysql_fetch_array($result)){//當有資料時 一次抓一筆
			$userData[]=array(
		    'id'=>$row['UserID'],//抓欄位為UserID的資料
		    'name'=>$row['UserName']//抓欄位為UserName的資料
		    );

		}
		echo json_encode($userData);//將資料印出
		
		mysql_free_result($result);//清空暫存
		mysql_close($Connect);//關閉連線
	?>


</body>
</html>