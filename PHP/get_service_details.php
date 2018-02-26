<?php
 
/*
 * Following code will get single product details
 * A product is identified by product id (productID)
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

// check for post data

//if GET <-> POST à remettre
//if (isset($_GET["ServiceId"])) {

if (isset($_POST["ServiceId"])) {
	
    //$serviceId = $_GET['ServiceId'];

    $serviceId = $_POST['ServiceId'];
 
    // get a service from Service table
    $sql = "SELECT *FROM Service WHERE ServiceId = $serviceId";
    $result = $conn->query($sql);

    if ($result) {
        // check for empty result
        if ( mysqli_num_rows($result) > 0) {

            $result = mysqli_fetch_assoc($result);
            $service = array();
            $service["ServiceId"] = $result["ServiceId"];
            $service["Titre"] = $result["Titre"];
            $service["Lieu"] = $result["Lieu"];
            $service["Description"] = $result["Description"];
            $service["DateExecution"] = $result["DateExecution"];
            //$product["created_at"] = $result["created_at"];
            //$product["updated_at"] = $result["updated_at"];

            // success
            $response["success"] = 1;
 
            // user node
            $response["Service"] = array();
 
            array_push($response["Service"], $service);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "1 No service found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "2 No service found";
 
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

// to get back later the json object
echo ":END";


?>