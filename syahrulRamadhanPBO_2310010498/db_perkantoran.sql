-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 11, 2025 at 11:50 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_perkantoran`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `id` varchar(20) NOT NULL,
  `kode` varchar(20) DEFAULT NULL,
  `nama` varchar(60) DEFAULT NULL,
  `keterangan` varchar(2000) DEFAULT NULL,
  `stok_awal` varchar(20) DEFAULT NULL,
  `stok` varchar(20) DEFAULT NULL,
  `satuan_id` varchar(20) DEFAULT NULL,
  `rekomendasi` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `admin_view` varchar(20) DEFAULT NULL,
  `staf_view` varchar(20) DEFAULT NULL,
  `tanggal_update` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE `pesanan` (
  `id` varchar(20) NOT NULL,
  `marketing_id` varchar(20) DEFAULT NULL,
  `staf_id` varchar(20) DEFAULT NULL,
  `barang_id` varchar(20) DEFAULT NULL,
  `permintaan` varchar(20) DEFAULT NULL,
  `pengiriman` varchar(20) DEFAULT NULL,
  `tanggal_minta` varchar(50) DEFAULT NULL,
  `tanggal_kirim` varchar(50) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `rekomendasi` varchar(20) DEFAULT NULL,
  `status_rekomendasi` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `riwayat`
--

CREATE TABLE `riwayat` (
  `id` varchar(20) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `barang_id` varchar(20) DEFAULT NULL,
  `jenis` varchar(20) DEFAULT NULL,
  `qty` varchar(20) DEFAULT NULL,
  `keterangan` varchar(2000) DEFAULT NULL,
  `tanggal` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `satuan`
--

CREATE TABLE `satuan` (
  `id` varchar(20) NOT NULL,
  `kode` varchar(20) DEFAULT NULL,
  `nama` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `riwayat`
--
ALTER TABLE `riwayat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `satuan`
--
ALTER TABLE `satuan`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
