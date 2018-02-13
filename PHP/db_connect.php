
<?php
 
/**
 * A class file to connect to database
 */
class DB {
 
    // constructor
    /*function __construct() {
        // connecting to database
        $this->connect();
    }*/
 
    // destructor
    function __destruct() {
        // closing db connection
        $this->close();
    }
 
    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        require_once "db_config.php";
 
        // Connecting to mysql database
        $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
        // Check connection
        if (mysqli_connect_errno())
          {
            die("Failed to connect to the database" . mysqli_connect_error());
          }
          else
            echo "Connected to the database";
        return $con;
    }
 
    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
        mysqli_close();
    }
 
}

?>

