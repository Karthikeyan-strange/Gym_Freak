# Project: Gym Membership & Workout Management System

## 1. Project Overview

Develop a Gym Membership & Workout Management System using React for the frontend and Spring Boot for the backend.

The application will help gym administrators manage members, trainers, memberships, workout plans and exercises. It should provide a simple dashboard where users can manage gym operations through CRUD functionality.

**Technology Stack**

**Frontend (My Responsibility)**
- React.js
- Vite
- JavaScript
- HTML and CSS
- Axios for API integration
- React Router for navigation
- Bootstrap or Tailwind CSS for styling

**Backend (Friend's Responsibility)**
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST APIs
- Postman for API testing
- DTOs
- Bean Validation
- Global Exception Handling

## 2. Main Modules and Entities

The application consists of five main entities.

### 1. Member
Stores gym member information.

Fields:
- memberId (Primary Key)
- name
- email
- phone
- gender
- age
- height
- weight
- joinDate
- createdAt

Functionalities:
- Add new members
- View all members
- View member by ID
- Update member details
- Delete members
- Search members by name or email

### 2. Trainer
Stores gym trainer information.

Fields:
- trainerId (Primary Key)
- name
- email
- phone
- specialization
- experience
- salary
- joiningDate
- createdAt

Functionalities:
- Add trainers
- View trainers
- Update trainer details
- Delete trainers
- Search trainers by specialization

### 3. Membership
Manages gym membership purchases, payments and expiry dates.

Fields:
- membershipId (Primary Key)
- memberId (Foreign Key)
- planName
- amount
- startDate
- endDate
- paymentStatus
- membershipStatus
- createdAt

Membership plans:
- Monthly
- Quarterly
- Half-Yearly
- Yearly

Membership statuses:
- ACTIVE
- EXPIRED
- CANCELLED

Payment statuses:
- PAID
- PENDING
- FAILED

Functionalities:
- Create memberships for members
- View membership details
- Update memberships
- Delete memberships
- Renew expired or expiring memberships
- View active memberships
- View expired memberships
- Track membership expiry

Important business rule:
Every renewal should create a new membership record instead of overwriting the previous membership history. A member should generally have only one active membership at a time. Membership validity must also consider payment status and start/end dates.

### 4. WorkoutPlan
Stores workout plans assigned to members by trainers.

Fields:
- planId (Primary Key)
- memberId (Foreign Key)
- trainerId (Foreign Key)
- planName
- goal
- startDate
- endDate
- description
- status
- createdAt

Workout goals:
- Muscle Gain
- Weight Loss
- Strength
- Endurance
- General Fitness

Workout statuses:
- ACTIVE
- COMPLETED
- CANCELLED

Functionalities:
- Create workout plans
- Assign plans to members
- Assign trainers to plans
- View all workout plans
- Update workout plans
- Delete workout plans
- View workout plans by member
- View workout plans by trainer

### 5. Exercise
Stores individual exercises included in workout plans.

Fields:
- exerciseId (Primary Key)
- planId (Foreign Key)
- exerciseName
- muscleGroup
- sets
- reps
- restSeconds
- weight
- instructions

Muscle groups:
- Chest
- Back
- Shoulders
- Biceps
- Triceps
- Legs
- Abs
- Full Body

Functionalities:
- Add exercises to workout plans
- View all exercises
- Update exercises
- Delete exercises
- View exercises belonging to a particular workout plan

## 3. Database Relationships

Use the following relationships:

1. Member → Membership: One-to-Many
2. Member → WorkoutPlan: One-to-Many
3. Trainer → WorkoutPlan: One-to-Many
4. WorkoutPlan → Exercise: One-to-Many

The corresponding child entities should use Many-to-One relationships.

Each workout plan belongs to one member and one trainer. Each exercise belongs to one workout plan.

## 4. REST API Endpoints

The backend should expose the following REST APIs.

### Member APIs
- POST /api/members
- GET /api/members
- GET /api/members/{id}
- PUT /api/members/{id}
- DELETE /api/members/{id}

### Trainer APIs
- POST /api/trainers
- GET /api/trainers
- GET /api/trainers/{id}
- PUT /api/trainers/{id}
- DELETE /api/trainers/{id}

### Membership APIs
- POST /api/memberships
- GET /api/memberships
- GET /api/memberships/{id}
- PUT /api/memberships/{id}
- DELETE /api/memberships/{id}
- POST /api/memberships/{id}/renew
- GET /api/memberships/active
- GET /api/memberships/expired

### Workout Plan APIs
- POST /api/workout-plans
- GET /api/workout-plans
- GET /api/workout-plans/{id}
- PUT /api/workout-plans/{id}
- DELETE /api/workout-plans/{id}
- GET /api/workout-plans/member/{memberId}
- GET /api/workout-plans/trainer/{trainerId}

### Exercise APIs
- POST /api/exercises
- GET /api/exercises
- GET /api/exercises/{id}
- PUT /api/exercises/{id}
- DELETE /api/exercises/{id}
- GET /api/exercises/plan/{planId}

## 5. Frontend Requirements (React)

The frontend should have a clean, modern and responsive gym management dashboard.

### Main Pages

1. Dashboard
   - Total members
   - Total trainers
   - Active memberships
   - Expired memberships
   - Active workout plans
   - Recent registrations
   - Membership expiry notifications

2. Members Management
   - Display members in a table
   - Add member form
   - Edit member details
   - Delete member
   - Search and filter members
   - View member details

3. Trainers Management
   - Display trainer details
   - Add trainer
   - Edit trainer
   - Delete trainer
   - Search trainers
   - Filter by specialization

4. Membership Management
   - Display all memberships
   - Create membership
   - Edit membership
   - Renew membership
   - Filter active, expired and cancelled memberships
   - Display expiry dates and payment statuses

5. Workout Plans
   - Display workout plans
   - Create workout plan
   - Assign a member
   - Assign a trainer
   - Edit and delete plans
   - View plans by member or trainer

6. Exercise Management
   - Display exercises
   - Add exercises to a workout plan
   - Edit exercises
   - Delete exercises
   - View exercises by workout plan

### Frontend Navigation

The dashboard sidebar should contain:

- Dashboard
- Members
- Trainers
- Memberships
- Workout Plans
- Exercises

### UI Design Requirements

- Modern gym management dashboard.
- Clean sidebar navigation.
- Responsive layout.
- Consistent colors and typography.
- Cards for dashboard statistics.
- Tables for displaying records.
- Modal or separate forms for adding and editing records.
- Confirmation dialogs before deleting records.
- Loading indicators and error messages.
- Success notifications after CRUD operations.
- Search and filter functionality.

## 6. Frontend and Backend Integration

The React frontend should communicate with the Spring Boot backend through REST APIs using Axios.

Backend base URL:

http://localhost:8080

Frontend development URL:

http://localhost:5173

Create a centralized Axios configuration file and separate API service files for:

- memberService.js
- trainerService.js
- membershipService.js
- workoutPlanService.js
- exerciseService.js

The frontend must use the actual backend API responses. Do not use hardcoded or mock data once the backend APIs are integrated.

Both developers should agree on request DTOs, response DTOs, JSON field names, date formats and error response structures before integration.

## 7. Development Rules

- Keep the application modular.
- Use separate components for each module.
- Keep API calls separate from UI components.
- Use reusable form components wherever possible.
- Implement proper frontend form validation.
- Handle backend errors properly.
- Use DTOs in the backend.
- Avoid Lombok in the backend; use explicit constructors, getters and setters.
- Implement proper validation and global exception handling.
- Use meaningful HTTP status codes.
- Avoid unnecessary complexity in the first version.

## 8. Project Development Goal

The main objective is to build a complete CRUD-based full-stack application that demonstrates:

- React frontend development
- Spring Boot REST API development
- MySQL database design
- One-to-Many and Many-to-One relationships
- DTO implementation
- API integration using Axios
- Form validation
- Exception handling
- Membership renewal and expiry business logic
- Search, sorting and filtering

JWT authentication and role-based access can be added as a later enhancement after the core application is completed.