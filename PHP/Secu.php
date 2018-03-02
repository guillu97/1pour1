<?php
	class Secu
	{
		public static function cryptage($string) {
			return hash('sha256', $string);
		}
	}
?>