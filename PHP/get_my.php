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


 
// to get back later the json object
echo "JSON:";



// TODO: à remettre si GET->POST
if ( isset($_POST["UsagerId"]) ) {
//if ( isset($_GET["UsagerId"]) ) {

    $usagerId = $_POST["UsagerId"];
    //$usagerId = $_GET["UsagerId"];
    


    $sql = "SELECT * FROM Produit WHERE UsagerId = $usagerId";
    $result = $conn->query($sql);


    //initialization
    $response["success"] = 0;
    $response["checkProduits"] = 0;
    $response["checkServices"] = 0;

        
    if ($result) {

        // check for empty result
        if(mysqli_num_rows($result) > 0){

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
            $response["success"] = 1;
            $response["checkProduits"] = 1;
        }
    }

    // get all services from sercvices table

    $sql2 = "SELECT * FROM Service WHERE UsagerId = $usagerId";
    $result2 = $conn->query($sql2);



     
    
    if ($result2) {
        
        // check for empty result
        if(mysqli_num_rows($result2) > 0){

            $response["Services"] = array();
         
            // looping through all results
            // services node
            while ($row2 = mysqli_fetch_assoc($result2)) {

                // temp user array
                $service = array();
                $service["ServiceId"] = $row2["ServiceId"];
                $service["Titre"] = $row2["Titre"];
                $service["DateExecution"] = $row2["DateExecution"];
                $service["Description"] = $row2["Description"];
                $service["Lieu"] = $row2["Lieu"];
                $service["DateMisenL"] = $row2["DateMisenL"];
                $service["UsagerId"] = $row2["UsagerId"];
                $service["DateValidation"] = $row2["DateValidation"];
                $service["ModId"] = $row2["ModId"];
         
                // push single product into final response array
                array_push($response["Services"], $service);
            }
            $response["success"] = 1;
            $response["checkServices"] = 1;
            
        }
    }

    if($response["checkProduits"] == 0  && $response["checkServices"] == 0 ){
        $response["$success"] = 0;
        $response["$message"] = "No products and no services found";
    }

    echo json_encode($response);

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