


<?php

// include db connect class
require_once "db_connect.php";

// connecting to db
$db = new DB();
$conn = $db->connect();

// define variables and set to empty values
$email = $passwordl = "";

$displayForm = 0;

if ($_SERVER["REQUEST_METHOD"] == "POST") {
	
  $email = test_input($_POST["email"]);
  $password = test_input($_POST["password"]);


  if( $email != "" && $password != "" ){
  	// SELECT * FROM Moderateur WHERE AdresseMail = 'supermod@gmail.com' AND Mdp = 'test12345'
  		$sql = "SELECT * FROM Moderateur WHERE AdresseMail = '$email' and Mdp = '$password'";
    	$result = $conn->query($sql);
    	

    	if ($result) {

    		// check for empty result
    		if ( mysqli_num_rows($result) > 0) {

    			$displayForm = 1;
    		}
    		else{
    			echo "failed email and/or password (1)";
    		}
    	}
    	else{
    		echo "failed email and/or password (2)";
    	}

  }
  else{
  	echo "email == \"\" and password = \"\" ";
  }


}


function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}


echo "<h2>Your Input:</h2>";
echo $email;
echo "<br>";
echo $password;

if($displayForm){

?>

<h2>Add a new moderator</h2>
<form method="post" action="/add_moderator.php">  
NumSecu: <input type="text" name="NumSecu" required>
<br><br>
IBAN: <input type="text" name="IBAN" required>
<br><br>
Salaire: <input type="text" name="Salaire" required>
<br><br>
Nom: <input type="text" name="Nom" required>
<br><br>
Prenom:<input type="text" name="Prenom" required>
<br><br>
Adresse:<input type="text" name="Adresse" required>
<br><br>
Ville:<input type="text" name="Ville" required>
<br><br>
AdresseMail:<input type="email" name="AdresseMail" required>
<br><br>
Password:<input type="password" name="Password" required>
<br><br>
Telephone:<input type="tel" pattern="^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,5})|(\(?\d{2,6}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$" name="Telephone" required>
<br><br>
<input type="submit" name="submit" value="Submit">  
</form>


<?php
}
?>

