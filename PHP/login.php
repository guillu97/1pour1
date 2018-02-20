
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


// check for required fields

if (isset($_POST['AdresseMail']) && isset($_POST['Mdp'])) {
 

    $adresseMail = $_POST['AdresseMail'];
    $mdp = $_POST['Mdp'];



 

 
    // mysql update row with matched pid
    $sql = "SELECT * FROM Usager WHERE AdresseMail = '$adresseMail' and Mdp = '$mdp'";
    $result = $conn->query($sql);
 
    // check if row inserted or not
    if ($result) {
    	if ( mysqli_num_rows($result) > 0) {
	        // successfully updated
	        $response["success"] = 1;
	        $response["message"] = "successfully loged in.";
 
		    // echoing JSON response
		    echo json_encode($response);
		}
		else {
 
	        // failed update
	        $response["success"] = 0;
	        $response["message"] = " 1 failed to login";

	        // echoing JSON response
        	echo json_encode($response);
    	}
    } else {
 
        // failed update
        $response["success"] = 0;
        $response["message"] = " 2 failed to login";

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