

-- Inserting data into `organization`
INSERT INTO `organization` (`id`, `name`, `manager_id`) VALUES 
(1, 'tech', NULL),
(2, 'media', NULL),
(3, 'human', NULL);

-- Inserting data into `employee`
INSERT INTO `employee` (`id`, `date_of_birth`, `age`, `avatar`, `business_title`, `citizenship_status`, `city_of_birth`, `country_of_birth`, `email_company`, `ethnicty`, `gender`, `hire_date`, `job`, `job_profile`, `location`, `marital`, `employee_name`, `password`, `point`, `primary_nationality`, `region_of_birth`, `religion`, `time_type`, `type`, `organization_id`) VALUES
(1, '1989-01-02 00:00:00', 35, 'NULL', 'Manager', 'Citizen', 'city_of_birth', 'country_of_birth', 'dung@gmail.com', 'ethnicty', _binary '', '2024-12-11 00:00:00', 'job', 'job_profile', 'HCM', 'yes', 'dung', '$2a$10$iy4h8J464wc2V2PKaHHqCup9HBJKWrTJFdQo/bwSJNLCwJlQMq532', 100, 'primary_nationality', 'region_of_birth', 'religion', 'time_type', 'Manager', 1),
(2, '1984-10-01 00:00:00', 40, 'NULL', 'Manager', 'Citizen', 'city_of_birth', 'country_of_birth', 'duy@gmail.com', 'ethnicty', _binary '', '2024-12-11 00:00:00', 'job', 'job_profile', 'HCM', 'yes', 'duy', '$2a$10$N3AO8ZUDDqjBn86RshP.3ObFnDtwOEFTBaTIwEDNAwNwB2ca8i16W', 100, 'primary_nationality', 'region_of_birth', 'religion', 'time_type', 'Manager', 2),
(3, '1999-10-12 00:00:00', 25, 'NULL', 'HR', 'Citizen', 'city_of_birth', 'country_of_birth', 'duy1@gmail.com', 'ethnicty', _binary '', '2024-12-11 00:00:00', 'job', 'job_profile', 'HCM', 'yes', 'duy1', '$2a$10$v/D8OChiaXYEFV5C2dmseu3Gy0cvoChhf7YyLeG.5JvjoDMFkC0XC', 20, 'primary_nationality', 'region_of_birth', 'religion', 'time_type', 'HR', 2),
(4, '1999-12-11 00:00:00', 25, 'NULL', 'Employee', 'Citizen', 'city_of_birth', 'country_of_birth', 'emp1@gmail.com', 'ethnicty', _binary '\0', '2024-12-11 00:00:00', 'job', 'job_profile', 'HCM', 'yes', 'emp1', '$2a$10$/fStTn9K8LnryCC9jgBZaeLIoir3C4erTZTD8R8vPcQRcT29DnCFe', 1, 'primary_nationality', 'region_of_birth', 'religion', 'time_type', 'Employee', 2),
(5, '2004-02-05 00:00:00', 20, 'NULL', 'Employee', 'Citizen', 'city_of_birth', 'country_of_birth', 'emp2@gmail.com', 'ethnicty', _binary '\0', '2024-12-11 00:00:00', 'job', 'job_profile', 'HCM', 'yes', 'emp2', '$2a$10$WatANUZecGzrvWF.zAgOSOTz3DF132YJjKMR0UjQ/.DpLu/Sk0XSK', 1, 'primary_nationality', 'region_of_birth', 'religion', 'time_type', 'Employee', 2),
(6, '2000-09-10 00:00:00', 24, 'NULL', 'Employee', 'Citizen', 'city_of_birth', 'country_of_birth', 'emp3@gmail.com', 'ethnicty', _binary '\0', '2024-12-11 00:00:00', 'job', 'job_profile', 'HCM', 'yes', 'emp3', '$2a$10$vfa9qqMZ9LuIiITJuwAQN.qeIpgCq8fMDZFnYUHljp8Cel1upmyCG', 2, 'primary_nationality', 'region_of_birth', 'religion', 'time_type', 'Employee', 1),
(7, '2002-08-07 00:00:00', 22, 'NULL', 'Employee', 'Citizen', 'city_of_birth', 'country_of_birth', 'null@gmail.com', 'ethnicty', _binary '', '2024-12-11 00:00:00', 'job', 'job_profile', 'HCM', 'no', 'empnull', '$2a$10$Z2KJk5ZkSchtYYP0D3eGTudMqHvGcF7Po5CGd47AZanDC7ZkOsEGm', 20, 'primary_nationality', 'region_of_birth', 'religion', 'time_type', 'Employee', 1);


UPDATE `organization` 
SET `manager_id` = 1
Where id =1;


UPDATE `organization` 
SET `manager_id` = 2
Where id =2;
-- Inserting data into `email`
INSERT INTO `email` (`id`, `email`, `usage_type`, `employee_id`) VALUES
(1, 'dung@gmail.com', 'Home(Primary)', 1),
(2, 'dung1@gmail.com', 'Home', 1),
(3, 'Duy@gmail.com', 'Home(Primary)', 2),
(4, 'Duy2@gmail.com', 'Home', 2),
(5, 'DungCongty@gmail.com', 'Business', 1),
(6, 'Duy@Congty.com', 'Business', 2),
(7, 'Nhanvien@gmail.com', 'Business', 3),
(8, 'Nhanvien4@gmail.com', 'Business', 4),
(9, 'Nhanvien5@gmail.com', 'Business', 5),
(10, 'Nhanvien6@gmail.com', 'Business', 6),
(11, 'Nhanvien6@canhan.com', 'Home', 6);

-- Inserting data into `address`
INSERT INTO `address` (`id`, `address_full`, `effective_date`, `usage_type`, `employee_id`) VALUES
(1, 'HCM', '2024-12-12 00:00:00', 'Home(Primary)', 1),
(2, 'DakLak', '2000-01-01 00:00:00', 'Home', 1),
(3, 'NgheAn', '2000-01-01 00:00:00', 'Home(Primary)', 2),
(4, 'HCM', '2024-12-12 00:00:00', 'Home', 2),
(5, 'HaNoi', '2024-12-12 00:00:00', 'Home', 3),
(6, 'LongAn', '2024-12-12 00:00:00', 'Home', 4),
(7, 'QuangNGai', '2024-12-12 00:00:00', 'Home', 6);
