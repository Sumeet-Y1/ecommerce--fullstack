# 🛍️ AureumPicks - Premium E-Commerce Platform

## 🌟 Overview

**AureumPicks** is a premium e-commerce platform that provides a seamless shopping experience with modern authentication, beautiful UI/UX, and robust backend architecture. Built with enterprise-grade technologies and best practices.

### Why AureumPicks?

- 🔐 **Secure Authentication** - JWT-based auth with email OTP verification
- 📧 **Professional Email Templates** - Beautiful, branded email communications
- 🎨 **Premium UI/UX** - Luxury brand-inspired design with dark/light themes
- 🚀 **Production Ready** - Deployed on Railway with environment-based configuration
- 📱 **Responsive Design** - Works flawlessly on all devices
- ⚡ **High Performance** - Optimized database queries and caching

---

## ✨ Features

### 🔐 Authentication & Security
- User Registration with Email Verification (OTP)
- Secure Login with JWT Token Authentication
- Password Reset with OTP Verification
- BCrypt Password Encryption
- Protected API Endpoints with Spring Security
- CORS Configuration for Frontend Integration

### 🛒 E-Commerce Functionality
- Product Catalog with Advanced Filtering
- Shopping Cart Management
- Real-time Cart Updates
- Product Detail Views with Reviews
- Category-based Product Navigation
- Search Functionality

### 🎨 User Experience
- Day/Night Theme Toggle
- Premium Design Inspired by Luxury Brands
- Smooth Animations and Transitions
- Mobile-Responsive Layout
- Professional Email Templates with Brevo Integration

### 📧 Email System
- Welcome emails with OTP verification
- Password reset emails
- Professional HTML templates with golden gradient design
- Branded footer and headers
- Mobile-responsive email design

---

## 🛠️ Tech Stack

### Backend
| Technology | Version | Purpose |
|------------|---------|---------|
| **Spring Boot** | 4.0.1 | Main Framework |
| **Spring Security** | 7.0.2 | Authentication & Authorization |
| **Spring Data JPA** | 4.0.1 | Database ORM |
| **Hibernate** | 7.2.0 | ORM Implementation |
| **MySQL** | 8.0+ | Database |
| **JWT (jjwt)** | 0.12.3 | Token-based Authentication |
| **Brevo API** | Latest | Email Service |
| **OkHttp** | 4.12.0 | HTTP Client for Brevo |
| **Lombok** | Latest | Reduce Boilerplate Code |
| **Maven** | 3.6+ | Build Tool |

### Frontend
| Technology | Purpose |
|------------|---------|
| **HTML5** | Structure |
| **CSS3** | Styling with Custom Design System |
| **JavaScript (Vanilla)** | Dynamic Functionality |
| **Fetch API** | REST API Communication |

### DevOps & Deployment
| Tool | Purpose |
|------|---------|
| **Railway** | Cloud Hosting Platform |
| **GitHub** | Version Control |
| **Git** | Source Control |
| **Railway PostgreSQL** | Production Database |

---

## 🏗️ Architecture

### Project Structure

```
aureumpicks-ecommerce/
│
├── src/
│   ├── main/
│   │   ├── java/com/aureumpicks/ecommerce/
│   │   │   ├── config/              # Security & JWT Configuration
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── CorsConfig.java
│   │   │   │
│   │   │   ├── controller/          # REST API Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── CartController.java
│   │   │   │
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── SignupRequest.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── VerifyEmailRequest.java
│   │   │   │   ├── ForgotPasswordRequest.java
│   │   │   │   ├── ResetPasswordRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── MessageResponse.java
│   │   │   │   ├── CartRequest.java
│   │   │   │   └── CartResponse.java
│   │   │   │
│   │   │   ├── model/               # JPA Entity Classes
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   └── Cart.java
│   │   │   │
│   │   │   ├── repository/          # JPA Repositories
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   └── CartRepository.java
│   │   │   │
│   │   │   ├── service/             # Business Logic Layer
│   │   │   │   ├── UserService.java
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── EmailService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   └── CartService.java
│   │   │   │
│   │   │   ├── util/                # Utility Classes
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── OtpUtil.java
│   │   │   │
│   │   │   └── AureumPicksApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           └── index.html       # Frontend Application
│   │
│   └── test/                        # Unit Tests
│
├── pom.xml                          # Maven Dependencies
├── .gitignore
└── README.md
```

### Database Schema

```sql
-- Users Table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_verified BIT(1) DEFAULT 0,
    otp VARCHAR(6),
    otp_expiry DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products Table
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    category VARCHAR(100) DEFAULT 'Electronics',
    image_url VARCHAR(500),
    rating DECIMAL(2, 1) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Carts Table
CREATE TABLE carts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_product (user_id, product_id)
);
```

---

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java JDK 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Download](https://dev.mysql.com/downloads/mysql/)
- **Git** - [Download](https://git-scm.com/downloads)
- **Brevo Account** - [Sign up](https://www.brevo.com/)

### Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/Sumeet-Y1/ecommerce-fullstack.git
cd ecommerce-fullstack
```

#### 2. Create MySQL Database

```sql
CREATE DATABASE aureumpicks_db;
USE aureumpicks_db;
```

Run the SQL script to create tables:

```sql
-- Run the schema from Database Schema section above
```

Or let Spring Boot auto-create tables by setting `spring.jpa.hibernate.ddl-auto=update` in `application.properties`.

#### 3. Configure Application Properties

Create `src/main/resources/application.properties`:

```properties
# ===============================
# SERVER CONFIGURATION
# ===============================
server.port=8080

# ===============================
# DATABASE CONFIGURATION
# ===============================
spring.datasource.url=jdbc:mysql://localhost:3306/aureumpicks_db
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===============================
# JPA/HIBERNATE CONFIGURATION
# ===============================
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# ===============================
# JWT CONFIGURATION
# ===============================
jwt.secret=AureumPicksSecureSecretKeyMinimum32CharactersLongForProductionUse2024
jwt.expiration=86400000

# ===============================
# BREVO EMAIL CONFIGURATION
# ===============================
brevo.api.key=YOUR_BREVO_API_KEY
spring.mail.username=noreply.aureumpicks@gmail.com

# ===============================
# APPLICATION CONFIGURATION
# ===============================
app.name=AureumPicks
```

#### 4. Get Brevo API Key

1. Sign up at [Brevo](https://www.brevo.com/)
2. Go to **Settings** → **SMTP & API** → **API Keys**
3. Generate new API key
4. Copy and paste in `application.properties`
5. Verify sender email in Brevo Dashboard → **Senders**

#### 5. Install Dependencies

```bash
mvn clean install
```

#### 6. Run the Application

```bash
mvn spring-boot:run
```

The application will start at `http://localhost:8080`

#### 7. Access the Frontend

Open your browser and navigate to:
```
http://localhost:8080
```

---

## ⚙️ Configuration

### Environment Variables (Production)

For Railway or other cloud deployments, use environment variables:

```bash
# Database
DATABASE_URL=jdbc:mysql://host:port/database
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your-super-secure-jwt-secret-key-minimum-32-characters
JWT_EXPIRATION=86400000

# Brevo Email
BREVO_API_KEY=xkeysib-your-api-key
SPRING_MAIL_USERNAME=noreply.aureumpicks@gmail.com

# Application
APP_NAME=AureumPicks
```

### Update `application.properties` for Production

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
brevo.api.key=${BREVO_API_KEY}
spring.mail.username=${SPRING_MAIL_USERNAME}
app.name=${APP_NAME}
```

---

## 📚 API Documentation

### Base URL
```
Local: http://localhost:8080/api
Production: https://your-app.railway.app/api
```

### Authentication Endpoints

#### 1. User Signup
```http
POST /api/auth/signup
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securepassword123"
}

Response: 201 Created
{
  "message": "Signup successful! Please check your email for OTP verification."
}
```

#### 2. Verify Email
```http
POST /api/auth/verify-email
Content-Type: application/json

{
  "email": "user@example.com",
  "otp": "123456"
}

Response: 200 OK
{
  "message": "Email verified successfully! You can now login."
}
```

#### 3. Resend OTP
```http
POST /api/auth/resend-otp
Content-Type: application/json

{
  "email": "user@example.com"
}

Response: 200 OK
{
  "message": "OTP resent successfully! Please check your email."
}
```

#### 4. Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securepassword123"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "user@example.com",
  "message": "Login successful!"
}
```

#### 5. Forgot Password
```http
POST /api/auth/forgot-password
Content-Type: application/json

{
  "email": "user@example.com"
}

Response: 200 OK
{
  "message": "Password reset OTP sent to your email."
}
```

#### 6. Reset Password
```http
POST /api/auth/reset-password
Content-Type: application/json

{
  "email": "user@example.com",
  "otp": "123456",
  "newPassword": "newsecurepassword123"
}

Response: 200 OK
{
  "message": "Password reset successful! You can now login with your new password."
}
```

### Product Endpoints (Requires JWT Token)

#### 1. Get All Products
```http
GET /api/products/all
Authorization: Bearer {your-jwt-token}

Response: 200 OK
[
  {
    "id": 1,
    "name": "Asus TUF F16",
    "description": "High performance gaming laptop",
    "price": 89999.00,
    "stock": 15,
    "category": "Laptops",
    "imageUrl": "https://...",
    "rating": 4.5
  }
]
```

#### 2. Get Product by ID
```http
GET /api/products/{id}
Authorization: Bearer {your-jwt-token}

Response: 200 OK
{
  "id": 1,
  "name": "Asus TUF F16",
  "description": "High performance gaming laptop",
  "price": 89999.00,
  "stock": 15,
  "category": "Laptops",
  "imageUrl": "https://...",
  "rating": 4.5
}
```

#### 3. Get Products by Category
```http
GET /api/products/category/{category}
Authorization: Bearer {your-jwt-token}

Response: 200 OK
[...]
```

#### 4. Search Products
```http
GET /api/products/search?name={productName}
Authorization: Bearer {your-jwt-token}

Response: 200 OK
[...]
```
### Cart Endpoints (Requires JWT Token)

#### 1. Get User Cart
```http
GET /api/cart
Authorization: Bearer {your-jwt-token}

Response: 200 OK
[
  {
    "id": 1,
    "productId": 1,
    "productName": "Asus TUF F16",
    "productImage": "https://...",
    "productPrice": 89999.00,
    "quantity": 2,
    "totalPrice": 179998.00
  }
]
```

#### 2. Add to Cart
```http
POST /api/cart/add
Authorization: Bearer {your-jwt-token}
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}

Response: 201 Created
{
  "id": 1,
  "productId": 1,
  "productName": "Asus TUF F16",
  "productImage": "https://...",
  "productPrice": 89999.00,
  "quantity": 2,
  "totalPrice": 179998.00
}
```

#### 3. Update Cart Item
```http
PUT /api/cart/update/{cartId}?quantity={newQuantity}
Authorization: Bearer {your-jwt-token}

Response: 200 OK
{...}
```

#### 4. Remove from Cart
```http
DELETE /api/cart/remove/{cartId}
Authorization: Bearer {your-jwt-token}

Response: 200 OK
{
  "message": "Item removed from cart"
}
```

#### 5. Clear Cart
```http
DELETE /api/cart/clear
Authorization: Bearer {your-jwt-token}

Response: 200 OK
{
  "message": "Cart cleared successfully"
}
```

---

## 🚂 Deployment

### Deploy to Railway

#### 1. Prerequisites
- GitHub account
- Railway account ([Sign up](https://railway.app/))
- Push your code to GitHub

#### 2. Connect to Railway

1. Go to [Railway.app](https://railway.app/)
2. Click **New Project**
3. Select **Deploy from GitHub repo**
4. Choose your repository
5. Railway will auto-detect Spring Boot

#### 3. Add MySQL Database

1. In your Railway project, click **+ New**
2. Select **Database** → **MySQL**
3. Railway will provision a MySQL database

#### 4. Configure Environment Variables

Go to your service → **Variables** tab:

```bash
# Database (Auto-configured by Railway MySQL)
SPRING_DATASOURCE_URL=${DATABASE_URL}
SPRING_DATASOURCE_USERNAME=${MYSQLUSER}
SPRING_DATASOURCE_PASSWORD=${MYSQLPASSWORD}

# JWT
JWT_SECRET=AureumPicksSecureSecretKeyMinimum32CharactersLongForProductionUse2024
JWT_EXPIRATION=86400000

# Brevo
BREVO_API_KEY=xkeysib-your-brevo-api-key
SPRING_MAIL_USERNAME=noreply.aureumpicks@gmail.com

# App
APP_NAME=AureumPicks
```

#### 5. Deploy

Railway will automatically deploy your application. You'll get a URL like:
```
https://your-app-name.railway.app
```

#### 6. Test Your Deployment

Visit your Railway URL and test:
- User signup
- Email OTP verification
- Login
- Product browsing
- Cart functionality

---

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn jacoco:report
```

### Manual Testing Checklist

- [ ] User Registration
- [ ] Email OTP Verification
- [ ] Login with JWT
- [ ] Forgot Password
- [ ] Password Reset
- [ ] Browse Products
- [ ] Add to Cart
- [ ] Update Cart Quantity
- [ ] Remove from Cart
- [ ] Logout

---

## 🐛 Troubleshooting

### Common Issues

#### 1. Email Not Sending
- ✅ Verify Brevo API key is correct
- ✅ Check sender email is verified in Brevo
- ✅ Review Railway logs for email errors

#### 2. Database Connection Failed
- ✅ Verify MySQL is running
- ✅ Check database credentials
- ✅ Ensure database `aureumpicks_db` exists

#### 3. JWT Token Invalid
- ✅ Ensure JWT secret is at least 32 characters
- ✅ Check token expiration settings
- ✅ Clear browser localStorage and login again

#### 4. CORS Errors
- ✅ Check CORS configuration in `CorsConfig.java`
- ✅ Verify frontend URL is whitelisted

---

## 👨‍💻 Author

**Sumeet Yadav**

- GitHub: [@Sumeet-Y1](https://github.com/Sumeet-Y1)
- Email: sumeety202@gmail.com
- LinkedIn: linkedin.com/in/sumeet-backenddev
- Portfolio:sumeetyadav-dev.netlify.app

---

## 🙏 Acknowledgments

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Brevo API Documentation](https://developers.brevo.com/)
- [JWT.io](https://jwt.io/) for JWT documentation
- [Railway Documentation](https://docs.railway.app/)

---

## 📞 Support

If you have any questions or need help, feel free to:

- Open an [Issue](https://github.com/Sumeet-Y1/ecommerce-fullstack/issues)
- Email: sumeety202@gmail.com
- Star ⭐ this repository if you find it helpful!

---

## 🗺️ Roadmap

### Phase 1 (Current) ✅
- [x] User Authentication with JWT
- [x] Email OTP Verification
- [x] Product Catalog
- [x] Shopping Cart
- [x] Professional Email Templates
- [x] Railway Deployment

### Phase 2 (Upcoming) 🚧
- [ ] Payment Gateway Integration (Razorpay/Stripe)
- [ ] Order Management System
- [ ] Admin Dashboard
- [ ] Product Reviews & Ratings
- [ ] Wishlist Feature
- [ ] Order Tracking

### Phase 3 (Future) 🔮
- [ ] Mobile App (React Native)
- [ ] Advanced Analytics
- [ ] Recommendation Engine
- [ ] Multi-language Support
- [ ] Social Media Integration
- [ ] Customer Support Chat

---

### ⭐ Star this repository if you found it helpful!

Made with ❤️ by [Sumeet Yadav](https://github.com/Sumeet-Y1)

**#SpringBoot #Ecommerce #JWT #MySQL #Railway #Brevo**

