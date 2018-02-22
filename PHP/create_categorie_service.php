
<?php
 
/*
 * Following code will create a new categorie service row
 * All categorie service details are read from HTTP Post Request
 */



 
// array for JSON response
$response = array();

 // include db connect class
require_once "db_connect.php";
    
// connecting to db
$db = new DB();
$conn = $db->connect();

/*

NomCatServ

*/


// to get back later the json object
echo "JSON:";
 
// check for required fields

//TODO: à remettre lorsque GET -> POST
//if ( isset($_POST['NomCatServ']) ){

if ( isset($_GET['NomCatServ']) ){


//TODO: à remettre lorsque GET -> POST
/*
    $nomCatServ = $_POST['NomCatServ'];

    */

    $nomCatServ = $_GET['NomCatServ'];
    



 
    /*
    echo " " . $nomCatServ;
    */

    // mysqli inserting a new row

    //INSERT INTO CategorieServ(NomCatServ ) VALUES('test')

    $sql = "INSERT INTO CategorieServ(NomCatServ) VALUES('$nomCatServ')";
    $result = $conn->query($sql);

 
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