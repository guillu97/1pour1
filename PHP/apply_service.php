
<?php
 
/*
 * Following code apply for a service
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

if ( isset($_POST["UsagerId"]) && isset($_POST["ServiceId"]) ){
//if ( isset($_GET['UsagerId']) && isset($_GET['ServiceId']) ){

   
    $usagerId = $_POST["UsagerId"];
    $serviceId = $_POST["ServiceId"];
    
    

    
/*
    $usagerId = $_GET["UsagerId"];
    $serviceId = $_GET["ServiceId"];
*/

     // get the date in year-month-day hour:min:second     i.e.  2018-02-06 12:52:46
    date_default_timezone_set('Europe/Paris');
    $dateMisenL = date("Y-m-d H:i:s");
 


    $stmt = $conn->prepare("INSERT INTO DemandeU_S(DateMisenL, ServiceId, UsagerId ) VALUES(?,?,?)");
    $stmt->bind_param('sii',$dateMisenL, $serviceId, $usagerId);
    $result = $stmt->execute();
    // check if row inserted or not
    if ($result) {
        // successfully applied
        $response["success"] = 1;
        $response["message"] = "Service successfully applied.";
 
        // echoing JSON response
        echo json_encode($response);

    } else {
 
        // failed applied
        $response["success"] = 0;
        $response["message"] = "Service failed applied";
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