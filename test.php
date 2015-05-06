<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>
</head>
<body>
	<?php

		$DB="cmapp";
		//$con=mssql_connect("localhost","mobileUser","ntustmobile") or die("Wrong!");
		$link =mysql_connect("localhost","mobileUser","ntustmobile") or die("Wrong!");
		echo "Sucesses!!";
		$sql="SELECT * FROM USER";
		$result=execute_sql($DB,$sql,$link);

		while($row =mysql_fetch_array($result,MYSQL_BOTH)){
			echo $row["UserId"]."<br>";
			echo $row["UserNAme"]."<br>";

		}
		mysql_free_result($result);
		mysql_close($link);
	?>


</body>
</html>