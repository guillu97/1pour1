-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Client: sql301.hebergratuit.net
-- Généré le: Dim 11 Mars 2018 à 12:27
-- Version du serveur: 5.6.35-81.0
-- Version de PHP: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `heber_21526805_mobile`
--

-- --------------------------------------------------------

--
-- Structure de la table `CategorieProd`
--

CREATE TABLE IF NOT EXISTS `CategorieProd` (
  `NomCatProd` varchar(25) NOT NULL,
  PRIMARY KEY (`NomCatProd`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `CategorieProd`
--

INSERT INTO `CategorieProd` (`NomCatProd`) VALUES
('test'),
('test2');

-- --------------------------------------------------------

--
-- Structure de la table `CategorieServ`
--

CREATE TABLE IF NOT EXISTS `CategorieServ` (
  `NomCatServ` varchar(25) NOT NULL,
  PRIMARY KEY (`NomCatServ`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `CategorieServ`
--

INSERT INTO `CategorieServ` (`NomCatServ`) VALUES
('Coiffure'),
('Demenagement'),
('Manutention'),
('Massage'),
('test');

-- --------------------------------------------------------

--
-- Structure de la table `CategoriseP_C`
--

CREATE TABLE IF NOT EXISTS `CategoriseP_C` (
  `NomCatProd` varchar(25) NOT NULL,
  `ProduitId` int(11) NOT NULL,
  PRIMARY KEY (`NomCatProd`,`ProduitId`),
  KEY `FK_CategoriseP_C_ProduitId` (`ProduitId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `CategoriseS_C`
--

CREATE TABLE IF NOT EXISTS `CategoriseS_C` (
  `ServiceId` int(11) NOT NULL,
  `NomCatServ` varchar(25) NOT NULL,
  PRIMARY KEY (`ServiceId`,`NomCatServ`),
  KEY `FK_CategoriseS_C_NomCatServ` (`NomCatServ`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `DemandeU_P`
--

CREATE TABLE IF NOT EXISTS `DemandeU_P` (
  `DateMisenL` datetime DEFAULT NULL,
  `ProduitId` int(11) NOT NULL,
  `UsagerId` int(11) NOT NULL,
  PRIMARY KEY (`ProduitId`,`UsagerId`),
  KEY `FK_DemandeU_P_UsagerId` (`UsagerId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `DemandeU_P`
--

INSERT INTO `DemandeU_P` (`DateMisenL`, `ProduitId`, `UsagerId`) VALUES
('2018-03-02 23:13:28', 11, 11),
('2018-03-02 23:43:35', 16, 11),
('2018-03-02 21:29:02', 14, 11),
('2018-03-02 23:13:43', 18, 11),
('2018-03-02 23:43:44', 25, 11),
('2018-03-03 00:02:05', 17, 38);

-- --------------------------------------------------------

--
-- Structure de la table `DemandeU_S`
--

CREATE TABLE IF NOT EXISTS `DemandeU_S` (
  `DateMisenL` datetime DEFAULT NULL,
  `ServiceId` int(11) NOT NULL,
  `UsagerId` int(11) NOT NULL,
  PRIMARY KEY (`ServiceId`,`UsagerId`),
  KEY `FK_DemandeU_S_UsagerId` (`UsagerId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `DemandeU_S`
--

INSERT INTO `DemandeU_S` (`DateMisenL`, `ServiceId`, `UsagerId`) VALUES
('2018-03-02 00:00:00', 1, 12),
('2018-03-02 21:26:56', 1, 13),
('2018-03-02 22:49:47', 9, 11),
('2018-03-02 22:58:25', 3, 11),
('2018-03-02 22:59:29', 4, 11),
('2018-03-02 23:03:43', 6, 11),
('2018-03-02 23:52:14', 5, 11),
('2018-03-02 23:52:19', 16, 11),
('2018-03-02 23:58:36', 8, 11),
('2018-03-03 00:01:59', 10, 38);

-- --------------------------------------------------------

--
-- Structure de la table `Moderateur`
--

CREATE TABLE IF NOT EXISTS `Moderateur` (
  `ModId` int(11) NOT NULL AUTO_INCREMENT,
  `NumSecu` varchar(25) DEFAULT NULL,
  `IBAN` varchar(25) DEFAULT NULL,
  `Salaire` int(11) DEFAULT NULL,
  `Nom` varchar(25) DEFAULT NULL,
  `Prenom` varchar(25) DEFAULT NULL,
  `Adresse` varchar(25) DEFAULT NULL,
  `Ville` varchar(25) DEFAULT NULL,
  `AdresseMail` varchar(25) DEFAULT NULL,
  `Mdp` varchar(11) NOT NULL,
  `Telephone` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ModId`),
  UNIQUE KEY `NumSecu` (`NumSecu`),
  UNIQUE KEY `IBAN` (`IBAN`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Contenu de la table `Moderateur`
--

INSERT INTO `Moderateur` (`ModId`, `NumSecu`, `IBAN`, `Salaire`, `Nom`, `Prenom`, `Adresse`, `Ville`, `AdresseMail`, `Mdp`, `Telephone`) VALUES
(2, '1234567890123', '1234567890123', 5000, 'Me', 'Its', 'test', 'test', 'supermod@gmail.com', 'test12345', '0666666666'),
(3, '01', '01', 1, 'test', 'test', 'test', 'test', 'test@test', 'test', '01'),
(4, '$numSecu', '$iban', 0, '$nom', '$prenom', '$adresse', '$ville', '$adresseMail', '$password', '$telephone'),
(5, '02', '02', 1, 'test', 'test', 'test', 'test', 'test@test', 'ezfffdf', '0147502290'),
(6, '05', '05', 1, 'test', 'test', 'test', 'test', 'test@testtrty', 'tyzeie', '0147502291');

-- --------------------------------------------------------

--
-- Structure de la table `NoterDemander`
--

CREATE TABLE IF NOT EXISTS `NoterDemander` (
  `NoteEtoile` int(11) DEFAULT NULL,
  `LeNote` int(11) NOT NULL,
  `LeNoteur` int(11) NOT NULL,
  PRIMARY KEY (`LeNote`,`LeNoteur`),
  KEY `FK_NoterDemander_UsagerId_1` (`LeNoteur`,`LeNote`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `NoterProposeur`
--

CREATE TABLE IF NOT EXISTS `NoterProposeur` (
  `NoteEtoile` int(11) DEFAULT NULL,
  `LeNote` int(11) NOT NULL,
  `LeNoteur` int(11) NOT NULL,
  PRIMARY KEY (`LeNote`,`LeNoteur`),
  KEY `FK_NoterProposeur_UsagerId_1` (`LeNoteur`,`LeNote`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Produit`
--

CREATE TABLE IF NOT EXISTS `Produit` (
  `ProduitId` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(25) DEFAULT NULL,
  `Marque` varchar(25) DEFAULT NULL,
  `Description` varchar(25) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `DateMisenL` datetime DEFAULT NULL,
  `UsagerId` int(11) DEFAULT NULL,
  `DateValidation` datetime DEFAULT NULL,
  `ModId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ProduitId`),
  KEY `FK_Produit_UsagerId` (`UsagerId`),
  KEY `FK_Produit_ModId` (`ModId`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

--
-- Contenu de la table `Produit`
--

INSERT INTO `Produit` (`ProduitId`, `Nom`, `Marque`, `Description`, `Age`, `DateMisenL`, `UsagerId`, `DateValidation`, `ModId`) VALUES
(14, 'ProductName', 'marquetest', 'descript', 70, '2018-02-26 16:07:11', 11, NULL, NULL),
(11, 'ProductNameererzrez', 'Brand', 'Descriptionerzzzzzzzzzrez', 0, '2018-02-26 13:10:51', 11, NULL, NULL),
(12, 'ProductNameffd', 'Brand', 'Descriptionddd', 5787, '2018-02-26 14:05:37', 11, NULL, NULL),
(8, '', 'Brand', 'Description', 0, '2018-02-26 07:47:27', 0, NULL, NULL),
(18, 'ProductNamedsds', 'Branddsdsds', 'Description', 52255, '2018-02-26 20:06:09', 11, NULL, NULL),
(16, 'ProductName2', 'Brand', 'Mark', 0, '2018-02-26 16:07:40', 19, NULL, NULL),
(17, 'ProductName1', 'Brand', 'Description', 0, '2018-02-26 16:07:40', 11, NULL, NULL),
(19, 'ProductNamefgdfggf', 'Brandfgfgdgf', 'Descriptiongfdgfdgfd', 414141, '2018-02-26 20:07:45', 11, NULL, NULL),
(31, 'ProductName', 'Brand', 'Description', 95536, '2018-03-09 21:10:28', 38, NULL, NULL),
(24, 'ProductNameMe', 'Brand', 'Description', 2, '2018-02-27 14:45:19', 19, NULL, NULL),
(25, 'Waouh', 'Brand', 'Description', 3, '2018-02-27 14:46:52', 19, NULL, NULL),
(26, 'Again', 'Brand', 'Description', 2, '2018-02-27 14:47:18', 19, NULL, NULL),
(29, 'ProductNametest1', 'Brandtest1', 'Descriptiontest1', 1, '2018-02-27 17:05:48', 24, NULL, NULL),
(28, 'Crepier', 'Brand', 'Description', 2, '2018-02-27 14:48:28', 19, NULL, NULL),
(30, 'exa', 'vjk', 'le test', 1, '2018-02-27 17:23:12', 25, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `Service`
--

CREATE TABLE IF NOT EXISTS `Service` (
  `ServiceId` int(11) NOT NULL AUTO_INCREMENT,
  `Titre` varchar(25) DEFAULT NULL,
  `DateExecution` datetime DEFAULT NULL,
  `Description` varchar(25) DEFAULT NULL,
  `Lieu` varchar(25) DEFAULT NULL,
  `DateMisenL` datetime DEFAULT NULL,
  `UsagerId` int(11) DEFAULT NULL,
  `DateValidation` datetime DEFAULT NULL,
  `ModId` int(11) DEFAULT NULL,
  PRIMARY KEY (`ServiceId`),
  KEY `FK_Service_UsagerId` (`UsagerId`),
  KEY `FK_Service_ModId` (`ModId`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=33 ;

--
-- Contenu de la table `Service`
--

INSERT INTO `Service` (`ServiceId`, `Titre`, `DateExecution`, `Description`, `Lieu`, `DateMisenL`, `UsagerId`, `DateValidation`, `ModId`) VALUES
(3, 'test', '2018-02-22 00:00:00', 'test', 'test', '2018-02-22 00:00:00', 1, '2018-02-22 00:00:00', 1),
(4, 'Test', '2018-02-22 16:27:21', 'test', 'test', '2018-02-22 16:27:21', 1, '2018-02-22 16:27:21', 1),
(5, 'Test', '2018-03-22 00:00:00', 'test', 'test', '2018-02-22 16:31:32', 1, '2018-03-22 00:00:00', 1),
(6, 'Test', '2018-03-22 00:00:00', 'test', 'test', '2018-02-22 16:33:54', 1, '2018-03-22 00:00:00', 1),
(8, 'test2', '0000-00-00 00:00:00', 'test', 'test2', '2018-02-24 17:32:47', 11, NULL, NULL),
(9, 'test3', '0000-00-00 00:00:00', 'test3', 'test3', '2018-02-24 17:33:53', 11, NULL, NULL),
(10, 'tesadd1', '2018-01-24 18:07:00', 'testadd1', 'testadd1', '2018-02-24 19:09:11', 11, NULL, NULL),
(11, 'testadd2', '2018-01-26 10:10:00', 'testadd2', 'testadd2', '2018-02-24 19:10:43', 11, NULL, NULL),
(12, 'testadd3', '2018-01-28 06:00:00', 'testadd3', 'testadd3', '2018-02-24 19:15:03', 11, NULL, NULL),
(13, 'test26', '2018-10-09 18:15:00', 'test', 'test26', '2018-02-24 19:17:04', 11, NULL, NULL),
(14, 'test', '2018-02-22 12:00:00', 'test', 'test', '2018-02-24 19:18:10', 11, NULL, NULL),
(15, 'test', '2018-02-24 00:00:00', 'test', 'test', '2018-02-24 19:22:25', 11, NULL, NULL),
(16, 'test6', '0000-00-00 00:00:00', 'test6', 'test6', '2018-02-24 20:53:26', 11, NULL, NULL),
(17, 'tester', '2018-02-28 04:00:00', 'LOL', 'Waouh', '2018-02-25 16:00:42', 19, NULL, NULL),
(18, 'jjgg', '0000-00-00 00:00:00', 'x dkksk', 'bkcwb', '2018-02-25 16:01:02', 19, NULL, NULL),
(19, 'jekekrk', '0000-00-00 00:00:00', 'djeken', 'jdjdlen', '2018-02-25 16:01:21', 19, NULL, NULL),
(20, 'mon nouveau service', '2018-02-28 12:02:00', 'XPTDR', 'test', '2018-02-25 21:04:08', 11, NULL, NULL),
(21, 'test', '0000-00-00 00:00:00', 'test', 'test', '2018-02-25 21:04:34', 11, NULL, NULL),
(22, 'mon nouveau service test', '0000-00-00 00:00:00', 'Lol', 'test', '2018-02-25 21:06:08', 11, NULL, NULL),
(23, 'test10', '2018-02-23 17:00:00', 'test', 'test', '2018-02-26 20:01:40', 11, NULL, NULL),
(25, 'testService2', '0001-02-05 11:12:00', 'test', 'testService2', '2018-02-26 22:36:45', 11, NULL, NULL),
(26, 'testService3', '2018-02-28 17:49:00', 'test', 'testLELNew Test enco', '2018-02-26 22:41:06', 11, NULL, NULL),
(28, 'egfd', '0000-00-00 00:00:00', 'Waaah', 'vyd', '2018-02-26 22:55:18', 0, NULL, NULL),
(29, 'Antoine', '0000-00-00 00:00:00', 'mark', 'tou', '2018-02-26 22:55:43', 0, NULL, NULL),
(30, 'fbdl', '0000-00-00 00:00:00', 'dnldle', 'eklee', '2018-02-26 23:30:16', 0, NULL, NULL),
(31, 'Ahah', '0000-00-00 00:00:00', 'nmdmrkfvend', 'aorjej', '2018-02-27 14:50:56', 19, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `Usager`
--

CREATE TABLE IF NOT EXISTS `Usager` (
  `UsagerId` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(25) DEFAULT NULL,
  `Prenom` varchar(25) DEFAULT NULL,
  `AdresseMail` varchar(25) DEFAULT NULL,
  `Mdp` varchar(170) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `NumTel` varchar(20) DEFAULT NULL,
  `Adresse` varchar(25) DEFAULT NULL,
  `Ville` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`UsagerId`),
  UNIQUE KEY `AdresseMail` (`AdresseMail`),
  UNIQUE KEY `NumTel` (`NumTel`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=39 ;

--
-- Contenu de la table `Usager`
--

INSERT INTO `Usager` (`UsagerId`, `Nom`, `Prenom`, `AdresseMail`, `Mdp`, `NumTel`, `Adresse`, `Ville`) VALUES
(11, 'its', 'me', 'test@test', '6fec2a9601d5b3581c94f2150fc07fa3d6e45808079428354b868e412b76e6bb', '1', 'ici', ''),
(15, 'Surname123', 'Firstname123', 'Email123', 'pass123', '1234', 'Adresse', 'city'),
(17, 'Surname', 'Firstname', 'Email1', 'Password', '12', 'Adresse', 'city'),
(18, 'Surname', 'Firstname', 'itsme@mario.fr', 'password', '208', 'Adresse', 'city'),
(38, 'Vo', 'Antoine', 'siiscil@hotmail.fr', 'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9', '634336024', 'Le Grand', 'Noisy'),
(20, 'ghh', 'r', 'cc@hv.com', 'vPassword', '6000', 'gvvAdresse', 'cg'),
(21, 'Surname', 'Firstname', 'Email@mail', 'Password', '6354', 'Adresse', 'city'),
(22, 'Surnamedbkdndn', 'Firstnamefjd', 'Email@eb', 'Password', '635558', 'Adresse', 'city'),
(23, 'test1', 'test1', 'test2@test2', 'test', '564276', 'adresse', 'city'),
(24, 'Surname', 'Firstname', 'test3@test3', 'test12345', '658', 'Adresse', 'city'),
(25, 'Toi', 'Moi', 'tout@tout', 'ab4f63f9ac65152575886860dde480a1', '5666', 'Adresse', 'city'),
(29, 'Surname', 'Firstname', 'mac@mac', 'ab4f63f9ac65152575886860dde480a1', '65649494', 'Adresse', 'city'),
(30, 'Surname', 'Firstname', 'this@this', 'e807f1fcf82d132f9bb018ca6738a19f', '56564', 'Adresse', 'city'),
(32, 'Surname', 'Firstname', 'google@it', 'c822c1b63853ed273b89687ac505f9fa', '349', 'Adresse', 'city'),
(33, 'Surname', 'Firstname', 'marc@marc', 'ab4f63f9ac65152575886860dde480a1', '565356', 'Adresse', 'city'),
(34, 'Surname', 'Firstname', 'poverty@po', 'ab4f63f9ac65152575886860dde480a1', '656464', 'Adresse', 'city'),
(35, 'Surname', 'Firstname', 'look@look', 'ab4f63f9ac65152575886860dde480a1', '986464', 'Adresse', 'city'),
(37, 'Surname', 'Firstname', 'tearsera@gmail.com', 'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9', '49646464', 'Adresse', 'city');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
