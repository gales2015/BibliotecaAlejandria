-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-04-2015 a las 12:19:52
-- Versión del servidor: 5.5.27
-- Versión de PHP: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `alejandria`
--
CREATE DATABASE `alejandria` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `alejandria`;

--
-- Volcado de datos para la tabla `ejemplar`
--

INSERT INTO `ejemplar` (`id`, `libro_id`) VALUES
(1, 1),
(3, 1),
(2, 2),
(4, 3),
(5, 4),
(6, 4);

--
-- Volcado de datos para la tabla `libro`
--

INSERT INTO `libro` (`isbn`, `autor`, `categoria`, `titulo`) VALUES
(1, 'Mao Tse-Tung', 'Propaganda', 'Libro rojo'),
(2, 'Tolstoi', 'Novela', 'Guerra y Paz'),
(3, 'Sun Tzu', 'Estrategia militar', 'El arte de la guerra'),
(4, 'Adolf Hitler', 'Autobiografía', 'Mein Kampf');

--
-- Volcado de datos para la tabla `prestamo`
--

INSERT INTO `prestamo` (`id`, `fecha_devolucion`, `fecha_fin`, `fecha_inicio`, `ejemplar_id`, `usuario_id`) VALUES
(1, '2015-04-23 12:02:22', '2015-05-23 11:57:52', '2015-04-23 11:57:52', 2, 1);

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `apellidos`, `direccion`, `dni`, `email`, `nombre`) VALUES
(1, 'Magno', 'Gran vía de Pella', '12345678A', 'alejando.magno@gmail.com', 'Alejandro'),
(2, 'el Huno', 'C/ Mongolia s/n', '12121212F', 'un.atila@hotmail.com', 'Atila'),
(3, 'Hitler', 'C/ Polonia', '19391945F', 'mis.recetas.cocina@yahoo.es', 'Adolf');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
