# Gym Membership & Workout Management System - Backend

This repository contains the complete Spring Boot REST API backend and MySQL database configuration for the **Gym Membership & Workout Management System**.

---

## 🛠️ Technology Stack

- **Java**: 17
- **Framework**: Spring Boot 3.2.5
- **ORM / Persistence**: Spring Data JPA & Hibernate
- **Database**: Local MySQL (Workbench compatible)
- **Validation**: Jakarta Bean Validation (`jakarta.validation.constraints.*`)
- **API Architecture**: RESTful Services with DTO pattern & Global Exception Handling
- **No Lombok**: Written strictly using explicit constructors, getters, and setters

---

## 📁 Project Structure

```
D:\Gym
├── pom.xml                                 # Maven dependencies & build configuration
├── mysql_workbench_schema.sql              # MySQL DDL & Seed Data script
├── README.md                               # Project documentation & API guide
└── src
    └── main
        ├── java/com/gym
        │   ├── GymApplication.java         # Spring Boot Entry Point
        │   ├── config
        │   │   └── CorsConfig.java         # CORS setup (allows http://localhost:5173)
        │   ├── controller                  # REST API Controllers
        │   │   ├── MemberController.java
        │   │   ├── TrainerController.java
        │   │   ├── MembershipController.java
        │   │   ├── WorkoutPlanController.java
        │   │   ├── ExerciseController.java
        │   │   └── DashboardController.java
        │   ├── dto                         # Data Transfer Objects & Validation
        │   │   ├── MemberRequestDTO.java
        │   │   ├── MemberResponseDTO.java
        │   │   ├── TrainerRequestDTO.java
        │   │   ├── TrainerResponseDTO.java
        │   │   ├── MembershipRequestDTO.java
        │   │   ├── MembershipRenewDTO.java
        │   │   ├── MembershipResponseDTO.java
        │   │   ├── WorkoutPlanRequestDTO.java
        │   │   ├── WorkoutPlanResponseDTO.java
        │   │   ├── ExerciseRequestDTO.java
        │   │   ├── ExerciseResponseDTO.java
        │   │   ├── DashboardStatsDTO.java
        │   │   ├── ExpiryNotificationDTO.java
        │   │   └── ErrorResponse.java
        │   ├── entity                      # JPA Entities (No Lombok, explicit getters/setters)
        │   │   ├── Member.java
        │   │   ├── Trainer.java
        │   │   ├── Membership.java
        │   │   ├── WorkoutPlan.java
        │   │   └── Exercise.java
        │   ├── exception                   # Custom Exceptions & Global Handler
        │   │   ├── BadRequestException.java
        │   │   ├── DuplicateResourceException.java
        │   │   ├── ResourceNotFoundException.java
        │   │   └── GlobalExceptionHandler.java
        │   ├── repository                  # Spring Data JPA Repositories
        │   │   ├── MemberRepository.java
        │   │   ├── TrainerRepository.java
        │   │   ├── MembershipRepository.java
        │   │   ├── WorkoutPlanRepository.java
        │   │   └── ExerciseRepository.java
        │   └── service                     # Business Logic & Service Implementation
        │       ├── MemberService.java & impl/MemberServiceImpl.java
        │       ├── TrainerService.java & impl/TrainerServiceImpl.java
        │       ├── MembershipService.java & impl/MembershipServiceImpl.java
        │       ├── WorkoutPlanService.java & impl/WorkoutPlanServiceImpl.java
        │       ├── ExerciseService.java & impl/ExerciseServiceImpl.java
        │       └── DashboardService.java & impl/DashboardServiceImpl.java
        └── resources
            └── application.properties      # MySQL connection & server properties
```

---

## 🗄️ Database Setup (MySQL Workbench)

1. Open **MySQL Workbench** and connect to your local MySQL server (`localhost:3306`).
2. Open the file `D:\Gym\mysql_workbench_schema.sql` (`File` -> `Open SQL Script...`).
3. Click the **Execute (Lightning icon)** button to execute the entire script.
4. It will:
   - Create `gym_management_db`
   - Create tables: `members`, `trainers`, `memberships`, `workout_plans`, `exercises`
   - Insert realistic sample records for instant testing.
5. In `src/main/resources/application.properties`, verify that the username and password match your local MySQL configuration:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/gym_management_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```

---

## 🚀 Running the Backend

From the command line inside `D:\Gym`:

```bash
mvn clean spring-boot:run
```

Or open `D:\Gym` in **IntelliJ IDEA**, **Eclipse**, or **VS Code**, and run `GymApplication.java`.

The backend will start on **`http://localhost:8080`**.

---

## 🔌 REST API Endpoints

### 1. Dashboard API
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/dashboard/stats` | Returns total counts, recent registrations, and expiring memberships |

### 2. Member APIs
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/members` | Add a new member |
| `GET` | `/api/members` | Get all members (supports `?query=` search) |
| `GET` | `/api/members/{id}` | Get member by ID |
| `PUT` | `/api/members/{id}` | Update member details |
| `DELETE` | `/api/members/{id}` | Delete member |
| `GET` | `/api/members/search?query={name/email/phone}` | Search members |

### 3. Trainer APIs
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/trainers` | Add a new trainer |
| `GET` | `/api/trainers` | Get all trainers (supports `?query=` search) |
| `GET` | `/api/trainers/{id}` | Get trainer by ID |
| `PUT` | `/api/trainers/{id}` | Update trainer details |
| `DELETE` | `/api/trainers/{id}` | Delete trainer |
| `GET` | `/api/trainers/search?query={term}` | Search trainers |
| `GET` | `/api/trainers/specialization/{specialization}` | Filter trainers by specialization |

### 4. Membership APIs
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/memberships` | Create a membership for a member |
| `GET` | `/api/memberships` | View all memberships |
| `GET` | `/api/memberships/{id}` | View membership details |
| `PUT` | `/api/memberships/{id}` | Update membership details |
| `DELETE` | `/api/memberships/{id}` | Delete membership |
| `POST` | `/api/memberships/{id}/renew` | Renew membership (creates new record, preserves history) |
| `GET` | `/api/memberships/active` | View all active memberships |
| `GET` | `/api/memberships/expired` | View all expired memberships |
| `GET` | `/api/memberships/member/{memberId}` | View memberships for a specific member |

### 5. Workout Plan APIs
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/workout-plans` | Create a workout plan |
| `GET` | `/api/workout-plans` | View all workout plans |
| `GET` | `/api/workout-plans/{id}` | View workout plan by ID |
| `PUT` | `/api/workout-plans/{id}` | Update workout plan |
| `DELETE` | `/api/workout-plans/{id}` | Delete workout plan |
| `GET` | `/api/workout-plans/member/{memberId}` | Get workout plans by member |
| `GET` | `/api/workout-plans/trainer/{trainerId}` | Get workout plans by trainer |

### 6. Exercise APIs
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/exercises` | Add an exercise to a workout plan |
| `GET` | `/api/exercises` | View all exercises |
| `GET` | `/api/exercises/{id}` | View exercise by ID |
| `PUT` | `/api/exercises/{id}` | Update exercise details |
| `DELETE` | `/api/exercises/{id}` | Delete exercise |
| `GET` | `/api/exercises/plan/{planId}` | View exercises belonging to a workout plan |

---

## ⚡ React Frontend Integration (Axios Services)

Configure Axios in your React application (`src/services/api.js`):

```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
```

### Example Service Files:
- **`memberService.js`**:
  ```javascript
  import api from './api';

  export const getMembers = (query = '') => api.get(`/members${query ? `?query=${query}` : ''}`);
  export const getMemberById = (id) => api.get(`/members/${id}`);
  export const createMember = (data) => api.post('/members', data);
  export const updateMember = (id, data) => api.put(`/members/${id}`, data);
  export const deleteMember = (id) => api.delete(`/members/${id}`);
  ```

- **`membershipService.js`**:
  ```javascript
  import api from './api';

  export const getMemberships = () => api.get('/memberships');
  export const getActiveMemberships = () => api.get('/memberships/active');
  export const getExpiredMemberships = () => api.get('/memberships/expired');
  export const createMembership = (data) => api.post('/memberships', data);
  export const renewMembership = (id, data) => api.post(`/memberships/${id}/renew`, data);
  export const deleteMembership = (id) => api.delete(`/memberships/${id}`);
  ```
