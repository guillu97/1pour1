
<?php
 
/*
 * Following code register an user in the database
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

if (isset($_POST['Prenom']) && isset($_POST['Nom']) && isset($_POST['AdresseMail']) && isset($_POST['NumTel']) && isset($_POST['Ville']) && isset($_POST['Adresse']) && isset($_POST['Mdp'])){
 //if (isset($_GET['Prenom']) && isset($_GET['Nom']) && isset($_GET['AdresseMail']) && isset($_GET['NumTel']) && isset($_GET['Ville']) && isset($_GET['Adresse']) && isset($_GET['Mdp'])){


    $prenom = $_POST['Prenom'];
    $nom = $_POST['Nom'];
    $adresseMail = $_POST['AdresseMail'];
    $numTel = $_POST['NumTel'];
    $ville = $_POST['Ville'];
    $adresse = $_POST['Adresse'];
    $mdp = $_POST['Mdp']);
    

/*
    $prenom = $_GET['Prenom'];
    $nom = $_GET['Nom'];
    $adresseMail = $_GET['AdresseMail'];
    $numTel = $_GET['NumTel'];
    $ville = $_GET['Ville'];
    $adresse = $_GET['Adresse'];
    $mdp = $_GET['Mdp'];
*/




 
    // mysql select the user to test if he is already in the database
    $sql = "SELECT * FROM Usager WHERE AdresseMail = '$adresseMail';";
    $result = $conn->query($sql);

    $sqlNum = "SELECT * FROM Usager WHERE NumTel = $numTel;";
    $resultNum = $conn->query($sqlNum);
 
    // check if row inserted or not
    if ($result && $resultNum) {
        


        if ( mysqli_num_rows($result) > 0 || mysqli_num_rows($resultNum) > 0) {
            // the user is already in the database
            $response["success"] = 2;
            $response["message"] = "The user is already in the database";
 
            // echoing JSON response
            echo json_encode($response);
        }
        else {
 
            // there we register the user

            // mysql add the user to database
            $stmt = $link->prepare("INSERT INTO Usager (Nom, Prenom, AdresseMail, Mdp, NumTel, Adresse, Ville) VALUES (?,?,?,?,?,?,?)");
            $stmt->bind_param("ssssiss", $nom, $prenom, $adresseMail, $mdp, $numTel, $adresse, $ville);
            $stmt->execute();


            if($result2){
                $response["success"] = 1;
                $response["message"] = "The user has been successfully added to the database";

                // echoing JSON response
                echo json_encode($response);
            }
            else{
                $response["success"] = 0;
                $response["message"] = "Failed to register the user in the database";

                // echoing JSON response
                echo json_encode($response);

            }
        }


    } else {
 
        // failed to register
        $response["success"] = 0;
        $response["message"] = "failed to register";

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