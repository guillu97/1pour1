
<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */



 
// array for JSON response
$response = array();

 // include db connect class
require_once "db_connect.php";
	
// connecting to db
$db = new DB();
$conn = $db->connect();


// to get back later the json object
echo "JSON:";
 
// check for required fields

if (isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])) {

    $name = $_POST['name'];
    $price = $_POST['price'];
    $description = $_POST['description'];


    // get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    date_default_timezone_set('Europe/Paris');
    $date = date("Y-m-d H:i:s");
 
   
 


    // mysqli inserting a new row

    $sql = "INSERT INTO products(name, price, description, created_at) VALUES('$name', '$price', '$description', '$date')";
    $result = $conn->query($sql);

 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database

        $response["success"] = 1;

        $response["message"] = "Product successfully created.";
          
        // echoing JSON response
        echo json_encode($response);

    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }


} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

// to get back later the json object
echo ":END";

?>