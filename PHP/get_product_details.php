
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
//if (isset($_GET["ProduitId"])) {

if (isset($_POST["ProduitId"])) {
	
    //$pid = $_GET['ProduitId'];

    $pid = $_POST['ProduitId'];
 
    // get a product from Produit table
    $sql = "SELECT *FROM Produit WHERE ProduitId = $pid";
    $result = $conn->query($sql);

    if ($result) {
        // check for empty result
        if ( mysqli_num_rows($result) > 0) {

            $result = mysqli_fetch_assoc($result);
            $product = array();
            $product["ProduitId"] = $result["ProduitId"];
            $product["Nom"] = $result["Nom"];
            $product["Marque"] = $result["Marque"];
            $product["Age"] = $result["Age"];
            $product["Description"] = $result["Description"];
            //$product["created_at"] = $result["created_at"];
            //$product["updated_at"] = $result["updated_at"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["Produit"] = array();
 
            array_push($response["Produit"], $product);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "1 No product found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "2 No product found";
 
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