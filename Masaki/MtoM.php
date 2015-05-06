<?php
//設定網頁的type及編碼，因為Android中使用了utf-8，所以這裡一定要設定，
//否則回傳的中文資料會變成亂碼
header("Content-Type:text/html; charset=utf-8");
$MyData1 = $_POST["MyData1"];
$MyData2 = $_POST["MyData2"];
$MyData3 = $_POST["MyData3"];
//$MyData4 = $_POST["MyData4"];
echo "123".$MyData1 ." ". $MyData2." ". $MyData3;
//可能這裏使用空格隔開，回傳一串字元回到app分析



//同時可以配合JSON使用，效果會更佳更方便。
//讓.net頁面輸出JSON字串，再從Android端把從HttpPost接收到的回傳值
//丟進JSONObject或是JSONArrayList分析處理
//就可以進行一種類似XML分析處理一般的動作
//減少還要寫一堆分析字串的程式。
?>