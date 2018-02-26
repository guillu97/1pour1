<?php
	class Secu
	{
		//In utiliser a chaque fois qu'on rentre un string dans la DB
		//ex :  Secu::antiinjection($_POST['name']);
		public static function antiinjection($string){

			//String est un entier ?
			if(ctype_digit($string))
			{
				//Si string entier alors on force le string a devenir un int ==> plus de probleme d'injection
				$string = intval($string);
			}
			//Pour les autres types
			else
			{
				//On echape les lettres sensibles
				$string = mysql_real_escape_string($string);

				//On evite une surcharge du serveur par attaque de %
				$string = addcslashes($string, '%_');
			}
			return $string;
		}


		//Out utiliser a chaque fois qu'on sors des data de la database
		//ex:  echo Secu::antihtml($_GET['name']);
		public static function antihtml($string){
			//On enleve tous les liens vers l'exterieurs(on escape) ==> impossibilite de telecharger un fichier sur le serveur  donc pas de virus
			return htmlentities($string);
		}

		//
		public static function listeBlanchePays(){
			$liste_pays = array(
				'France',
				'Canada',
				'Italie',


			);
		}
	}

	echo Secu::antiinjection("DELETE * FROM Here;");
?>