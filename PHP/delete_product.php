
<?php
 
/*
 * Following code will delete a product from table
 * A product is identified by product id (pid)
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


//TODO: à remettre si GET<->POST
if (isset($_POST['ProduitId'])) {
//if (isset($_GET['ProduitId'])) {

    $pid = Secu::antiinjection($_POST['ProduitId']);
	//$pid = $_GET['ProduitId'];
 

 

 
    // mysqli delete row with matched pid
    $sql = "DELETE FROM Produit WHERE ProduitId = $pid";
    $result = $conn->query($sql);

 
    // check if row deleted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Product successfully deleted";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
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