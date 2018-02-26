
<?php
 
/*
 * Following code will create a new categorie row
 * All service details are read from HTTP Post Request
 */



 
// array for JSON response
$response = array();

 // include db connect class
require_once "db_connect.php";
    
// connecting to db
$db = new DB();
$conn = $db->connect();

/*

NomCatProd

*/


// to get back later the json object
echo "JSON:";
 
// check for required fields

//TODO: à remettre lorsque GET -> POST
//if ( isset($_POST['NomCatProd']) ){

if ( isset($_GET['NomCatProd']) ){


//TODO: à remettre lorsque GET -> POST
/*
    $nomCatProd = $_POST['NomCatProd'];

    */

    $nomCatProd = $_GET['NomCatProd'];
    



 
    /*
    echo " " . $nomCatProd;
    */

    // mysqli inserting a new row

    //INSERT INTO CategorieProd(NomCatProd ) VALUES('test')

    $stmt = $link->prepare("INSERT INTO CategorieProd(NomCatProd) VALUES (?)");
    $stmt->bind_param("s", $nomCatProd);
    $stmt->execute();

 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database

        $response["success"] = 1;

        $response["message"] = "Categorie successfully created.";
          
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