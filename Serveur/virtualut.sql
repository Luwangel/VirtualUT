-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Mer 25 Décembre 2013 à 21:39
-- Version du serveur: 5.5.16
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `virtualut`
--

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `idCompte` int(11) NOT NULL AUTO_INCREMENT,
  `idSender` int(11) NOT NULL,
  `idReceiver` int(11) NOT NULL,
  `date` date NOT NULL,
  `montant` int(11) NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `valide` int(1) NOT NULL,
  PRIMARY KEY (`idCompte`),
  KEY `idSender` (`idSender`,`idReceiver`),
  KEY `idReceiver` (`idReceiver`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=18 ;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`idCompte`, `idSender`, `idReceiver`, `date`, `montant`, `libelle`, `valide`) VALUES
(1, 2, 1, '2013-12-09', 10, 'virement loyer', 1),
(2, 3, 1, '2013-12-11', 5, '', 0),
(3, 1, 2, '2013-12-09', 10, '', 0),
(4, 3, 2, '2013-12-09', 7, '', 0),
(5, 2, 3, '2013-12-09', 10, '', 0);

-- --------------------------------------------------------

--
-- Structure de la table `membre`
--

CREATE TABLE IF NOT EXISTS `membre` (
  `idMembre` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `credit` int(11) NOT NULL,
  `token` varchar(50) NOT NULL,
  `heure` int(11) NOT NULL,
  PRIMARY KEY (`idMembre`),
  UNIQUE KEY `login` (`login`,`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `membre`
--

INSERT INTO `membre` (`idMembre`, `nom`, `prenom`, `login`, `password`, `email`, `credit`, `token`, `heure`) VALUES
(1, 'pascal', 'perlo', 'messi_m', '1234', 'michel@gmail.com', 110, '5d6aca6fa11d39b08d7411950ce14319', 0),
(2, 'florenet', 'basel', 'brad_d', '1234', 'brad@gmail.com', 40, '5548297511d8267a79f0459f7051ee6e', 1388003756),
(3, 'selvain', 'pero', 'renaldo_6', '1234', 'renaldo@gmail.com', 50, '', 0);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `compte_ibfk_1` FOREIGN KEY (`idSender`) REFERENCES `membre` (`idMembre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `compte_ibfk_2` FOREIGN KEY (`idReceiver`) REFERENCES `membre` (`idMembre`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
