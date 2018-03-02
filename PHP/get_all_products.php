
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



$sql = "SELECT * FROM Produit WHERE ProduitId NOT IN ( SELECT DISTINCT ProduitId FROM DemandeU_P);";
$result = $conn->query($sql);


// to get back later the json object
echo "JSON:";
 
// check for empty result
if ($result) {
    // looping through all results
    // products node
    $response["Produits"] = array();
 
    while ($row = mysqli_fetch_assoc($result)) {

        // temp user array
        $product = array();
        $product["ProduitId"] = $row["ProduitId"];
        $product["Nom"] = $row["Nom"];
        $product["Marque"] = $row["Marque"];
        $product["Description"] = $row["Description"];
        $product["Age"] = $row["Age"];
        $product["DateMisenL"] = $row["DateMisenL"];
        $product["DateValidation"] = $row["DateValidation"];
 
        // push single product into final response array
        array_push($response["Produits"], $product);
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