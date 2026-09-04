# Sunrise Dental Clinic System

A Java Swing application for managing dental clinic appointments, patients, and billing.

## How to run the project

1. Clone this repository to your computer and open the folder in NetBeans.
2. Make sure you have a local MySQL server running on port 3306 with the root password set to empty.
3. Open your MySQL client and create a database named `sunrisedentalclinic`.
4. Run these queries to set up the tables so the app works:

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    contactNumber VARCHAR(15),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(20)
);

CREATE TABLE patients (
    patientId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    contactNumber VARCHAR(15),
    address VARCHAR(255),
    medicalHistory TEXT
);

CREATE TABLE appointments (
    appointmentNumber INT AUTO_INCREMENT PRIMARY KEY,
    patientId INT,
    dentistName VARCHAR(100),
    treatmentType VARCHAR(100),
    appointmentDate DATE,
    appointmentTime VARCHAR(10),
    status VARCHAR(20),
    FOREIGN KEY (patientId) REFERENCES patients(patientId)
);

CREATE TABLE bills (
    billId INT AUTO_INCREMENT PRIMARY KEY,
    appointmentNo INT,
    consultationFee DECIMAL(10,2),
    treatmentCost DECIMAL(10,2),
    totalAmount DECIMAL(10,2),
    billDate DATE,
    FOREIGN KEY (appointmentNo) REFERENCES appointments(appointmentNumber)
);

INSERT INTO users (name, contactNumber, username, password, role) VALUES ('Default Admin', '000000', 'admin', 'admin123', 'Admin');
```

5. Make sure `mysql-connector-j-26.7.0.jar` is added to your NetBeans Libraries folder.
6. Click Clean and Build in NetBeans, then run the project.
7. You can log in with the username `admin` and password `admin123`.
