# To-Do List API

This project is a RESTful API designed to manage to-do lists with user authentication.

## 📢 Requirements

The API includes the following features:

1. User registration
2. Login to generate an authentication token
3. CRUD operations to manage the to-do list
4. User authentication to access tasks
5. Proper validation and error handling
6. Security to protect data
7. Database integration for storing users and tasks
8. Pagination and filtering for tasks

## 📡 Technologies Used

- Language: Java 
- Framework: Spring Boot
- Database: MySQL
- Authentication: JWT

## Endpoints

### User Registration

**URL:** `POST /register`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@doe.com",
  "password": "password"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
}
```

### User Login

**URL:** `POST /login`

**Request Body:**
```json
{
  "email": "john@doe.com",
  "password": "password"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
}
```

### Create a Task

**URL:** `POST /todos`

**Headers:**
```
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "title": "Buy groceries",
  "description": "Buy milk, eggs, and bread"
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Buy milk, eggs, and bread"
}
```

### Update a Task

**URL:** `PUT /todos/{id}`

**Headers:**
```
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "title": "Buy groceries",
  "description": "Buy milk, eggs, bread, and cheese"
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Buy milk, eggs, bread, and cheese"
}
```

### Delete a Task

**URL:** `DELETE /todos/{id}`

**Headers:**
```
Authorization: Bearer <token>
```

**Response:**
- Status Code: `204`

### Get Tasks

**URL:** `GET /todos?page=1&limit=10`

**Headers:**
```
Authorization: Bearer <token>
```

**Response:**
```json
{
  "data": [
    {
      "id": 1,
      "title": "Buy groceries",
      "description": "Buy milk, eggs, bread"
    }
  ],
  "pagination": {
    "page": 1,
    "limit": 10,
    "total": 20
  }
}
```

## 🔐 Security

- Passwords are hashed before storing.
- User authentication via JWT tokens.
- Input validation to prevent malicious data.
- Proper error handling with appropriate HTTP status codes.

## ⚙ How to Run the Project

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Configure the database:
    - Open the `application.properties` file in the `src/main/resources` directory.
    - Update the following properties with your MySQL configuration:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/your_database
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
 
      # JWT Configuration(time in minutes)
      jwt.time.expiration.token=60
      jwt.time.expiration.refresh-token=120
      ```

3. Build the project:
   ```bash
   ./mvnw clean install
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

5. Test the endpoints using tools like Postman or cURL.

https://roadmap.sh/projects/todo-list-api
