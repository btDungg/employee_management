-- Tạo dữ liệu mẫu cho bảng Employee
-- create database testmysql
-- drop database testmysql
use testmysql

-- Kiểm tra dữ liệu
SELECT * FROM Employee;
SELECT * FROM HR;
SELECT * FROM Manager;
SELECT * from Point_Change;
SELECT * FROM Exchange_Point;

SHOW TABLES;

INSERT INTO Employee (id, user_id, employee_name, point, manager_id, type)
VALUES
    (1, 101, 'John Doe', 100, 1, 'HR'),
    (2, 102, 'Jane Smith', 150, 2, 'Manager'),
    (3, 103, 'Tom Brown', 90, 1, 'HR'),
    (4, 104, 'Alice Johnson', 120, 2, 'Manager'),
    (5, 105, 'Michael Green', 110, 3, 'Employee'),
    (6, 106, 'Emily White', 95, 3, 'Employee'),
    (7, 107, 'Chris Black', 80, 3, 'Employee');



-- Tạo dữ liệu mẫu cho bảng Manager
INSERT INTO Manager (bonus_employee_point,id)
VALUES
    (10,2),
    (20,1);

-- Chèn dữ liệu vào bảng PointChange
INSERT INTO Point_Change (id, user_id, amount, change_date, reason, employee_id)
VALUES 
    (1, 101, 10, '2024-10-01', 'Bonus for project completion', 1),
    (2, 102, 15, '2024-10-05', 'Referral bonus', 2),
    (3, 103, 20, '2024-10-10', 'Achievement award', 3),
    (4, 104, 5, '2024-10-11', 'Quarterly performance', 4),
    (5, 105, 7, '2024-10-12', 'Attendance reward', 5),
    (6, 106, 12, '2024-10-13', 'Client feedback bonus', 6),
    (7, 107, 25, '2024-10-14', 'Annual target achieved', 7),
    (8, 108, 8, '2024-10-15', 'Customer service excellence', 5),
    (9, 109, 9, '2024-10-16', 'Sales target exceeded', 4),
    (10, 110, 6, '2024-10-17', 'Peer recognition', 2);


-- Chèn dữ liệu vào bảng ExchangePoint
INSERT INTO Exchange_Point (id, reserved_money)
VALUES 
(1, 150.00),
(2, 200.00),
(3, 150.00),
(4, 125.00),
(5, 80.00);





