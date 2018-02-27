
<?php
 
/*
 * Following code will update a service information
 * A service is identified by service id 
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

ServiceId
Titre
DateExecution
Description
Lieu
DateMisenL
UsagerId
DateValidation
ModId

*/


// check for required fields

// TODO : à remettre lorsque GET -> POST

if ( isset($_POST['ServiceId']) && isset($_POST['DateExecution']) && isset($_POST['Description']) && isset($_POST['Lieu']) && isset($_POST['UsagerId']) ) {
 
 

    $serviceId = $_POST['ServiceId'];
    //$titre = $_POST['Titre'];
    $dateExecution = $_POST['DateExecution'];
    $description = $_POST['Description'];
    $lieu = $_POST['Lieu'];
    //$dateMisenL = $_POST['DateMisenL'];
    $UsagerId = $_POST['UsagerId'];
    //$dateValidation = $_POST['DateValidation'];
    //$modId = $_POST['ModId'];
    
/*
if ( isset($_GET['ServiceId']) && isset($_GET['Titre']) && isset($_GET['DateExecution']) && isset($_GET['Description']) && isset($_GET['Lieu']) && isset($_GET['DateMisenL']) && isset($_GET['UsagerId']) && isset($_GET['DateValidation']) && isset($_GET['ModId']) ) {

    $serviceId = Secu::antiinjection($_GET['ServiceId']);
    $titre = Secu::antiinjection($_GET['Titre']);
    $dateExecution = Secu::antiinjection($_GET['DateExecution']);
    $description = Secu::antiinjection($_GET['Description']);
    $lieu = Secu::antiinjection($_GET['Lieu']);
    $dateMisenL = Secu::antiinjection($_GET['DateMisenL']);
    $UsagerId = Secu::antiinjection($_GET['UsagerId']);
    $dateValidation = Secu::antiinjection($_GET['DateValidation']);
    $modId = Secu::antiinjection($_GET['ModId']);
*/


    // get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    /*
    date_default_timezone_set('Europe/Paris');
    $date = date("Y-m-d H:i:s");
    */
 

 
    // mysql update row with matched serviceId
    //$sql = "UPDATE Service SET ServiceId = $serviceId, Lieu = '$lieu', Description = '$description', DateExecution ='$dateExecution' WHERE ServiceId = $serviceId";

    //$result = $conn->query($sql);
 
    $stmt = $conn->prepare("UPDATE Service SET Lieu = ?, Description = ?, DateExecution = ? WHERE ServiceId = ?");
    $stmt->bind_param('sssi', $lieu, $description, $dateExecution, $serviceId);
    $result = $stmt->execute();
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Service successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
        // failed update
        $response["success"] = 0;
        $response["message"] = "Service failed update";
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