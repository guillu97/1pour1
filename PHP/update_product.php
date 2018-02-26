
<?php
 
/*
 * Following code will update a product information
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

// à remettre lorsque GET <-> POST

if ( isset($_POST['ProduitId'])  && isset($_POST['Marque']) && isset($_POST['Age']) && isset($_POST['Description'])  ) {
 
 

    $produitId = $_POST['ProduitId'];
    //$nom = $_POST['Nom'];
    $marque = $_POST['Marque'];
    $description = $_POST['Description'];
    $age = $_POST['Age'];
    //$dateMisenL = $_POST['DateMisenL'];
    //$UsagerId = $_POST['UsagerId'];
    //$dateValidation = $_POST['DateValidation'];
    //$modId = $_POST['ModId'];
    
/*
if ( isset($_GET['ProduitId']) && isset($_GET['Nom']) && isset($_GET['Marque']) && isset($_GET['Description']) && isset($_GET['Age']) && isset($_GET['DateMisenL']) && isset($_GET['UsagerId']) && isset($_GET['DateValidation']) && isset($_GET['ModId']) ) {

    $produitId = $_GET['ProduitId'];
    $nom = $_GET['Nom'];
    $marque = $_GET['Marque'];
    $description = $_GET['Description'];
    $age = $_GET['Age'];
    $dateMisenL = $_GET['DateMisenL'];
    $usagerId = $_GET['UsagerId'];
    $dateValidation = $_GET['DateValidation'];
    $modId = $_GET['ModId'];
    */



    // get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    /*
    date_default_timezone_set('Europe/Paris');
    $date = date("Y-m-d H:i:s");
    */
 

 
    // mysql update row with matched pid
    $sql = "UPDATE Produit SET  Marque = '$marque', Description = '$description', Age = $age WHERE ProduitId = $produitId";
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