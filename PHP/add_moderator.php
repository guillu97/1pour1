
<!DOCTYPE HTML>  
<html>
<head>
</head>
<body>  

<?php

// include db connect class
require_once "db_connect.php";

// connecting to db
$db = new DB();
$conn = $db->connect();

// define variables and set to empty values


$numSecu = $iban = $salaire = $nom = $prenom = $adresse = $ville = $adresseMail = $password = $telephone = "";


if ($_SERVER["REQUEST_METHOD"] == "POST") {

  	
  $numSecu = test_input($_POST["NumSecu"]);
  $iban = test_input($_POST["IBAN"]);
  $salaire = test_input($_POST["Salaire"]);
  $nom = test_input($_POST["Nom"]);
  $prenom = test_input($_POST["Prenom"]);
  $adresse = test_input($_POST["Adresse"]);
  $ville = test_input($_POST["Ville"]);
  $adresseMail = test_input($_POST["AdresseMail"]);
  $mdp = test_input($_POST["Password"]);
  $telephone = test_input($_POST["Telephone"]);

  //echo "  test : " . $numSecu . " " . $iban . " " . $salaire . " ". $nom . " " .$prenom . " ". $adresse . " " . $ville ." " . $adresseMail ." " . $mdp . " " .$telephone;



//$numSecu = $iban = $salaire = $nom = $prenom = $adresse = $ville = $adresseMail = $password = $telephone = "";
  if($numSecu != "" && $iban != "" && $salaire != "" && $nom != "" && $prenom != "" && $adresse != "" && $ville != "" && $adresseMail != "" && $mdp != "" && $telephone != ""){

  	// SELECT * FROM Moderateur WHERE AdresseMail = 'supermod@gmail.com' AND Mdp = 'test12345'
  		$sql = "INSERT INTO Moderateur(NumSecu, IBAN, Salaire, Nom, Prenom, Adresse, Ville, AdresseMail, Mdp, Telephone ) VALUES('$numSecu', '$iban', $salaire ,'$nom', '$prenom', '$adresse', '$ville', '$adresseMail', '$mdp', '$telephone')";
    	$result = $conn->query($sql);

    	if ($result) {
    		echo "Moderator added to the databe";
    	}
    	else{
    		echo "error : failed to add the moderator (2)";
    	}

  }
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

?>










<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

</body>
</html>