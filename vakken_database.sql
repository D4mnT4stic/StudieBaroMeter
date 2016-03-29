-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Gegenereerd op: 29 mrt 2016 om 14:42
-- Serverversie: 10.1.10-MariaDB
-- PHP-versie: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `android_db`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `vakken`
--

CREATE TABLE `vakken` (
  `name` varchar(255) NOT NULL,
  `ects` int(11) NOT NULL,
  `grade` double NOT NULL,
  `period` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `vakken`
--

INSERT INTO `vakken` (`name`, `ects`, `grade`, `period`) VALUES
('IARCH', 3, 1, 1),
('IIBPM', 3, 1, 1),
('IHBO', 3, 1, 1),
('IOPR1', 4, 1, 1),
('INET', 3, 1, 2),
('IWDR', 3, 1, 2),
('IRDB', 3, 1, 2),
('IIBUI', 3, 1, 2),
('IPRODAM', 2, 1, 2),
('IPROMEDT', 2, 1, 2),
('IMUML', 3, 1, 3),
('IOPR2', 4, 1, 3),
('IFIT', 3, 1, 3),
('IPOFIT', 2, 1, 3),
('IPOSE', 2, 1, 3),
('IIPXXXX', 10, 1, 4),
('IPROV', 3, 1, 4),
('ICOMMP', 3, 1, 4),
('ISLP', 1, 1, 4);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
