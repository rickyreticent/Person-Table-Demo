-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2017 at 02:59 AM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 7.0.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pl`
--

-- --------------------------------------------------------

--
-- Table structure for table `koleksibuku`
--

CREATE TABLE `koleksibuku` (
  `kd_koleksi` varchar(33) NOT NULL,
  `kd_kategori` varchar(33) NOT NULL,
  `kd_penerbit` varchar(33) NOT NULL,
  `kd_penulis` varchar(33) NOT NULL,
  `judul` varchar(33) NOT NULL,
  `harga` int(33) NOT NULL,
  `stok` int(33) NOT NULL,
  `sinopsis` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `koleksibuku`
--

INSERT INTO `koleksibuku` (`kd_koleksi`, `kd_kategori`, `kd_penerbit`, `kd_penulis`, `judul`, `harga`, `stok`, `sinopsis`) VALUES
('123', '123123', '123', '123123', '123', 123, 123, '123');

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `PersonID` int(10) NOT NULL,
  `firstName` char(15) NOT NULL,
  `lastName` char(15) NOT NULL,
  `sport` char(15) NOT NULL,
  `years` char(5) NOT NULL,
  `vegetarian` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`PersonID`, `firstName`, `lastName`, `sport`, `years`, `vegetarian`) VALUES
(1, 'Kathy', 'Smith', 'Snowboarding', '2', 0),
(2, 'Kathy', 'Smith', 'Rowing', '2', 0),
(3, 'Joe', 'Doe', 'Rowing', '5', 1),
(4, 'Hey', 'You', 'Snowboarding', '21', 0),
(5, 'John', 'Frusciante', 'Drugging', '21', 1),
(6, 'David', 'Beckham', 'Rowing', '2', 0),
(7, 'John', 'Frusciante', 'Drugging', '21', 0),
(8, 'Ricky', 'Anggoro', 'Tak ade', '2', 0),
(9, 'Michael', 'Balzary', 'Tak ade', '2', 0),
(10, 'Micha', 'Kaaan', 'renang', '2', 1),
(19, 'Josh', 'Klinghoffer', 'Rowing', '3', 0),
(20, 'Klinghoffer', 'Josh', 'Swimming', '4', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`PersonID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `PersonID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
