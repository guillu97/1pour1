
<?php
 
/*
 * Following code will create a new service row
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

Titre
DateExecution
Description
Lieu
DateMisenL
UsagerId
DateValidation
ModId

*/


// to get back later the json object
echo "JSON:";
 
// check for required fields

//TODO: à remettre lorsque GET -> POST
if (isset($_POST['Titre']) && isset($_POST['DateExecution']) && isset($_POST['Description']) && isset($_POST['Lieu']) && isset($_POST['UsagerId']) ){

//if (isset($_GET['Titre']) && isset($_GET['DateExecution']) && isset($_GET['Description']) && isset($_GET['Lieu']) && isset($_GET['UsagerId'])){


//TODO: à remettre lorsque GET -> POST

    $titre = $_POST['Titre'];
    $dateExecution = $_POST['DateExecution'];
    $description = $_POST['Description'];
    $lieu = $_POST['Lieu'];

	// get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    date_default_timezone_set('Europe/Paris');
    $dateMisenL = date("Y-m-d H:i:s");

    $usagerId = $_POST['UsagerId'];
    //$dateValidation = $_POST['DateValidation'];
    //$modId = $_POST['ModId'];
    

/*
    $titre = $_GET['Titre'];

	/*date_default_timezone_set('Europe/Paris');
    $dateExecution = date("Y-m-d H:i:s");

    $dateExecution = $_GET['DateExecution'];

    $description = $_GET['Description'];
    $lieu = $_GET['Lieu'];

    // get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    date_default_timezone_set('Europe/Paris');
    $dateMisenL = date("Y-m-d H:i:s");

    $usagerId = $_GET['UsagerId'];

    /*date_default_timezone_set('Europe/Paris');
    $dateValidation = date("Y-m-d H:i:s");

    $dateValidation = $_GET['DateValidation'];

    $modId = $_GET['ModId'];
    */



 
   	/*
   	echo " " . $titre . " " . $dateExecution . " " . $description . " " . $lieu . " " . $dateMisenL . " " . $usagerId . " " . $dateValidation . " " . $modId;
   	*/


    // mysqli inserting a new row

    //INSERT INTO Service(Titre, DateExecution, Description, Lieu, DateMisenL, UsagerId, DateValidation, ModId ) VALUES('test', '2018-02-22 00-00-00', 'test', 'test' , '2018-02-22 00-00-00', 1 , '2018-02-22 00-00-00', 1)

    $sql = "INSERT INTO Service(Titre, DateExecution, Description, Lieu, DateMisenL, UsagerId ) VALUES('$titre', '$dateExecution', '$description', '$lieu', '$dateMisenL', '$usagerId')";
    $result = $conn->query($sql);

 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database

        $response["success"] = 1;

        $response["message"] = "Service successfully created.";
          
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