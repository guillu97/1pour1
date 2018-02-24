
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

//if (isset($_GET["AdresseMail"]) && isset($_GET["Mdp"])) {
if (isset($_POST["AdresseMail"]) && isset($_POST["Mdp"])) {
    
    /*
    $adresseMail = $_GET["AdresseMail"];
    $mdp = $_GET["Mdp"];
    */

    $adresseMail = $_POST["AdresseMail"];
    $mdp = $_POST["Mdp"];
 
    // get the user's info from usager table
    //SELECT * FROM Usager WHERE AdresseMail = 'test@test' AND Mdp = 'test12345';
    $sql = "SELECT * FROM Usager WHERE AdresseMail = '$adresseMail' AND Mdp = '$mdp';";
    $result = $conn->query($sql);

    if ($result) {
        // check for empty result
        if ( mysqli_num_rows($result) > 0) {

            $result = mysqli_fetch_assoc($result);
            $user = array();
            $user["UsagerId"] = $result["UsagerId"];
            $user["Nom"] = $result["Nom"];
            $user["Prenom"] = $result["Prenom"];
            $user["AdresseMail"] = $result["AdresseMail"];
            $user["Mdp"] = $result["Mdp"];
            $user["NumTel"] = $result["NumTel"];
            $user["Adresse"] = $result["Adresse"];
            $user["Ville"] = $result["Ville"];

            //$product["created_at"] = $result["created_at"];
            //$product["updated_at"] = $result["updated_at"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["Utilisateur"] = array();
 
            array_push($response["Utilisateur"], $user);
 
            // echoing JSON response
            echo json_encode($response);

        } else {
            // no infos found
            $response["success"] = 0;
            $response["message"] = "No infos found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no infos found
        $response["success"] = 0;
        $response["message"] = "No infos found";
 
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