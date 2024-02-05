-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 03 Lut 2024, 14:05
-- Wersja serwera: 10.4.6-MariaDB
-- Wersja PHP: 7.1.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `bazazpo`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `grades`
--

CREATE TABLE `grades` (
                          `id` int(11) NOT NULL,
                          `name` tinytext NOT NULL,
                          `value` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `students`
--

CREATE TABLE `students` (
                            `ind` int(11) NOT NULL,
                            `department` tinytext NOT NULL,
                            `name` tinytext NOT NULL,
                            `surname` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `student_grades`
--

CREATE TABLE `student_grades` (
                                  `id` int(11) NOT NULL,
                                  `id_grade` int(11) DEFAULT NULL,
                                  `id_student` int(11) NOT NULL,
                                  `id_subject` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `subjects`
--

CREATE TABLE `subjects` (
                            `id` int(11) NOT NULL,
                            `name` tinytext NOT NULL,
                            `subject_manager` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `grades`
--
ALTER TABLE `grades`
    ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `students`
--
ALTER TABLE `students`
    ADD PRIMARY KEY (`ind`);

--
-- Indeksy dla tabeli `student_grades`
--
ALTER TABLE `student_grades`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `STUDENT_SUBJECT_UNIQUE` (`id_subject`,`id_student`),
    ADD KEY `FK_GRADE` (`id_grade`),
    ADD KEY `FK_STUDENT` (`id_student`);

--
-- Indeksy dla tabeli `subjects`
--
ALTER TABLE `subjects`
    ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `grades`
--
ALTER TABLE `grades`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `student_grades`
--
ALTER TABLE `student_grades`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `subjects`
--
ALTER TABLE `subjects`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;


ALTER TABLE `subjects` ADD UNIQUE `UNIQUE_SUBJECT_NAME` (`name`);

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `student_grades`
--
ALTER TABLE `student_grades`
    ADD CONSTRAINT `FK_GRADE` FOREIGN KEY (`id_grade`) REFERENCES `grades` (`id`),
    ADD CONSTRAINT `FK_STUDENT` FOREIGN KEY (`id_student`) REFERENCES `students` (`ind`) ON DELETE CASCADE,
    ADD CONSTRAINT `FK_SUBJECT` FOREIGN KEY (`id_subject`) REFERENCES `subjects` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
