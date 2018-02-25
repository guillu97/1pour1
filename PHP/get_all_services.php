<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once "db_connect.php";
 
// connecting to db
$db = new DB();
$conn = $db->connect();


 
// get all products from products table

$sql = "SELECT * FROM Service";
$result = $conn->query($sql);


// to get back later the json object
echo "JSON:";
 
// check for empty result
if ($result) {
    // looping through all results
    // products node
    $response["Services"] = array();
 
    while ($row = mysqli_fetch_assoc($result)) {

        // temp user array
        $service = array();
        $service["ServiceId"] = $row["ServiceId"];
        $service["Titre"] = $row["Titre"];
        $service["DateExecution"] = $row["DateExecution"];
        $service["Description"] = $row["Description"];
        $service["Lieu"] = $row["Lieu"];
        $service["DateMisenL"] = $row["DateMisenL"];
        $service["UsagerId"] = $row["UsagerId"];
        $service["DateValidation"] = $row["DateValidation"];
        $service["ModId"] = $row["ModId"];
 
        // push single product into final response array
        array_push($response["Services"], $service);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}

// to get back later the json object
echo ":END";



?>