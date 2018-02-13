
<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// to get back later the json object
echo "JSON:";


// check for required fields

if (isset($_POST['productID']) && isset($_POST['name']) && isset($_POST['price']) && isset($_POST['description'])) {
 

    $pid = $_POST['productID'];
    $name = $_POST['name'];
    $price = $_POST['price'];
    $description = $_POST['description'];


    // get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    date_default_timezone_set('Europe/Paris');
    $date = date("Y-m-d H:i:s");
 
    // include db connect class
    require_once "db_connect.php";
 
    // connecting to db
    $db = new DB();
    $conn = $db->connect();
 
    // mysql update row with matched pid
    $sql = "UPDATE products SET name = '$name', price = '$price', description = '$description', updated_at = '$date' WHERE productID = $pid";
    $result = $conn->query($sql);
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
        // failed update
        $response["success"] = 0;
        $response["message"] = "Product failed update";
    }

} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

// to get back later the json object
echo "JSON:";

?>