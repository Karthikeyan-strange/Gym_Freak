-- ===================================================================
-- Gym Membership & Workout Management System
-- Local MySQL Database Script (Compatible with MySQL Workbench)
-- ===================================================================

CREATE DATABASE IF NOT EXISTS gym_management_db;
USE gym_management_db;

-- -----------------------------------------------------
-- Table 1: members
-- -----------------------------------------------------
DROP TABLE IF EXISTS exercises;
DROP TABLE IF EXISTS workout_plans;
DROP TABLE IF EXISTS memberships;
DROP TABLE IF EXISTS trainers;
DROP TABLE IF EXISTS members;

CREATE TABLE members (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    gender VARCHAR(20),
    age INT,
    height DOUBLE,
    weight DOUBLE,
    join_date DATE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Table 2: trainers
-- -----------------------------------------------------
CREATE TABLE trainers (
    trainer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    experience INT DEFAULT 0,
    salary DOUBLE,
    joining_date DATE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Table 3: memberships
-- -----------------------------------------------------
CREATE TABLE memberships (
    membership_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    plan_name VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PAID',
    membership_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_memberships_member FOREIGN KEY (member_id) 
        REFERENCES members(member_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Table 4: workout_plans
-- -----------------------------------------------------
CREATE TABLE workout_plans (
    plan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    trainer_id BIGINT NOT NULL,
    plan_name VARCHAR(100) NOT NULL,
    goal VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_workout_plans_member FOREIGN KEY (member_id) 
        REFERENCES members(member_id) ON DELETE CASCADE,
    CONSTRAINT fk_workout_plans_trainer FOREIGN KEY (trainer_id) 
        REFERENCES trainers(trainer_id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- Table 5: exercises
-- -----------------------------------------------------
CREATE TABLE exercises (
    exercise_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_id BIGINT NOT NULL,
    exercise_name VARCHAR(100) NOT NULL,
    muscle_group VARCHAR(50) NOT NULL,
    sets INT DEFAULT 3,
    reps INT DEFAULT 12,
    rest_seconds INT DEFAULT 60,
    weight DOUBLE DEFAULT 0,
    instructions TEXT,
    CONSTRAINT fk_exercises_plan FOREIGN KEY (plan_id) 
        REFERENCES workout_plans(plan_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample Seed Data for Testing in MySQL Workbench
INSERT INTO members (name, email, phone, gender, age, height, weight, join_date) VALUES
('John Doe', 'john.doe@example.com', '9876543210', 'Male', 28, 178.0, 75.5, '2026-01-15'),
('Jane Smith', 'jane.smith@example.com', '9876543211', 'Female', 25, 165.0, 58.0, '2026-02-01'),
('Michael Johnson', 'michael.j@example.com', '9876543212', 'Male', 32, 182.0, 84.0, '2026-02-10'),
('Emily Davis', 'emily.d@example.com', '9876543213', 'Female', 29, 168.0, 62.0, '2026-03-01'),
('Alex Brown', 'alex.b@example.com', '9876543214', 'Other', 24, 172.0, 68.0, '2026-03-15');

INSERT INTO trainers (name, email, phone, specialization, experience, salary, joining_date) VALUES
('David Miller', 'david.trainer@gym.com', '9123456780', 'Weight Training & Bodybuilding', 6, 45000.0, '2025-01-10'),
('Sarah Connor', 'sarah.trainer@gym.com', '9123456781', 'CrossFit & HIIT', 4, 40000.0, '2025-03-15'),
('Robert Lee', 'robert.trainer@gym.com', '9123456782', 'Strength & Conditioning', 8, 55000.0, '2024-06-01');

INSERT INTO memberships (member_id, plan_name, amount, start_date, end_date, payment_status, membership_status) VALUES
(1, 'Yearly', 12000.0, '2026-01-15', '2027-01-15', 'PAID', 'ACTIVE'),
(2, 'Quarterly', 3500.0, '2026-02-01', '2026-05-01', 'PAID', 'ACTIVE'),
(3, 'Monthly', 1200.0, '2026-01-01', '2026-02-01', 'PAID', 'EXPIRED'),
(4, 'Half-Yearly', 6500.0, '2026-03-01', '2026-09-01', 'PAID', 'ACTIVE'),
(5, 'Monthly', 1200.0, '2026-03-15', '2026-04-15', 'PENDING', 'ACTIVE');

INSERT INTO workout_plans (member_id, trainer_id, plan_name, goal, start_date, end_date, description, status) VALUES
(1, 1, 'Hypertrophy Muscle Builder', 'Muscle Gain', '2026-01-20', '2026-04-20', '4-day split targeting major muscle hypertrophy.', 'ACTIVE'),
(2, 2, 'Fat Burn & Core', 'Weight Loss', '2026-02-05', '2026-05-05', 'High-intensity interval routines with active recovery.', 'ACTIVE');

INSERT INTO exercises (plan_id, exercise_name, muscle_group, sets, reps, rest_seconds, weight, instructions) VALUES
(1, 'Barbell Bench Press', 'Chest', 4, 10, 90, 60.0, 'Keep scapula retracted and drive through feet.'),
(1, 'Incline Dumbbell Press', 'Chest', 3, 12, 60, 22.5, 'Slight arch in back, controlled eccentric phase.'),
(1, 'Barbell Squat', 'Legs', 4, 8, 120, 80.0, 'Break at hips and knees simultaneously to parallel.'),
(2, 'Kettlebell Swings', 'Full Body', 4, 20, 45, 16.0, 'Hinge at the hips, explode with glutes and hamstrings.'),
(2, 'Plank to Pushup', 'Abs', 3, 15, 30, 0.0, 'Engage core tight, minimize hip swaying.');
