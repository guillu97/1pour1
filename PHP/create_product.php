
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
 

/*
ProduitId
Nom
Marque
Description
Age
DateMisenL
UsagerId
DateValidation
ModId

*/


// check for required fields


if (isset($_POST['Nom']) && isset($_POST['Marque']) && isset($_POST['Description']) && isset($_POST['Age']) && isset($_POST['UsagerId'])) {

    $nom = $_POST['Nom'];
    $marque = $_POST['Marque'];
    $description = $_POST['Description'];
    $age = $_POST['Age'];
    $usagerId = $_POST['UsagerId'];


    // get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    date_default_timezone_set('Europe/Paris');
    $dateMisenL = date("Y-m-d H:i:s");
 
   
 


    // mysqli inserting a new row

    $sql = "INSERT INTO Produit(Nom, Marque, Description, Age, DateMisenL, UsagerId ) VALUES('$nom', '$marque', '$description', '$age', '$dateMisenL' ,'$usagerId')";
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