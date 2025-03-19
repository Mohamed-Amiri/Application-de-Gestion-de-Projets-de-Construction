<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Builderz - Construction Company</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600;700&family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        :root {
            --primary-color: #ffb703;
            --secondary-color: #023047;
            --dark-color: #001219;
            --light-color: #f8f9fa;
            --accent-color: #fb8500;
        }

        body {
            font-family: 'Roboto', sans-serif;
            overflow-x: hidden;
        }

        h1, h2, h3, h4, h5, h6 {
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
        }

        /* Header styles */
        .top-header {
            background-color: var(--primary-color);
            padding: 12px 0;
        }

        .company-name {
            font-size: 2.5rem;
            font-weight: 800;
            color: var(--dark-color);
            font-family: 'Montserrat', sans-serif;
            letter-spacing: -1px;
            margin-bottom: 0;
        }

        .contact-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .contact-info i {
            font-size: 1.2rem;
            color: var(--dark-color);
        }

        .contact-info-text {
            margin-bottom: 0;
        }

        .contact-info-text span {
            display: block;
            font-size: 0.8rem;
            color: var(--dark-color);
        }

        .contact-info-text strong {
            font-size: 0.9rem;
            color: var(--dark-color);
        }

        /* Navigation styles */
        .navbar {
            background-color: var(--secondary-color);
            padding: 15px 0;
        }

        .navbar-nav .nav-link {
            color: var(--light-color);
            font-weight: 500;
            padding: 0.5rem 1rem;
            transition: color 0.3s;
        }

        .navbar-nav .nav-link:hover {
            color: var(--primary-color);
        }

        .navbar-nav .nav-link.active {
            color: var(--primary-color);
        }

        .btn-quote {
            background-color: transparent;
            border: 2px solid var(--light-color);
            color: var(--light-color);
            padding: 8px 20px;
            border-radius: 0;
            transition: all 0.3s;
        }

        .btn-quote:hover {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            color: var(--dark-color);
        }

        /* Hero section styles */
        .hero-section {
            position: relative;
            height: 80vh;
            min-height: 500px;
            overflow: hidden;
        }

        .hero-bg {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }

        .hero-bg::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 18, 25, 0.5);
        }

        .hero-content {
            position: relative;
            z-index: 1;
            text-align: center;
            color: #fff;
            padding: 100px 0;
        }

        .hero-subtitle {
            font-size: 1.5rem;
            margin-bottom: 1rem;
        }

        .hero-title {
            font-size: 4rem;
            font-weight: 800;
            margin-bottom: 2rem;
        }

        .hero-btn {
            background-color: var(--primary-color);
            color: var(--dark-color);
            padding: 12px 30px;
            border: none;
            font-weight: 600;
            transition: all 0.3s;
        }

        .hero-btn:hover {
            background-color: var(--light-color);
            color: var(--dark-color);
        }

        .carousel-control-prev,
        .carousel-control-next {
            width: 50px;
            height: 50px;
            background-color: rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            top: 50%;
            transform: translateY(-50%);
            opacity: 1;
        }

        .carousel-control-prev {
            left: 20px;
        }

        .carousel-control-next {
            right: 20px;
        }

        /* Feature box styles */
        .feature-container {
            background-color: var(--secondary-color);
        }

        .feature-box {
            padding: 50px 30px;
            text-align: center;
            transition: all 0.3s;
        }

        .feature-box:hover {
            transform: translateY(-10px);
        }

        .feature-box.primary {
            background-color: var(--primary-color);
        }

        .feature-box.secondary {
            background-color: var(--secondary-color);
        }

        .feature-icon {
            font-size: 3rem;
            margin-bottom: 20px;
            color: var(--light-color);
        }

        .feature-title {
            font-size: 1.5rem;
            margin-bottom: 15px;
            color: var(--light-color);
        }

        .feature-text {
            color: rgba(255, 255, 255, 0.8);
            margin-bottom: 0;
        }

        /* Animation */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .animated {
            animation: fadeIn 1s ease-in-out;
        }

        /* Responsive */
        @media (max-width: 992px) {
            .company-name {
                font-size: 2rem;
            }

            .hero-title {
                font-size: 3rem;
            }
        }

        @media (max-width: 768px) {
            .contact-info {
                display: none;
            }

            .hero-title {
                font-size: 2.5rem;
            }
        }
    </style>
</head>
<body>

<%
    Connection con = null;
    try {
        ConnectToDB dbConnector = new ConnectToDB();
        con = dbConnector.getConnection();

        if (con != null) {
            out.println("<p>Database connection successful!</p>");
            // You can perform database operations here
            // For example, create a statement, execute a query, etc.
        } else {
            out.println("<p>Database connection failed.</p>");
        }

    } catch (Exception e) {
        out.println("<p>Error: " + e.getMessage() + "</p>");
        e.printStackTrace(new java.io.PrintWriter(out));  // Print the stack trace to the JSP output
    } finally {
        if (con != null) {
            try {
                con.close(); // Close the connection in the finally block
            } catch (SQLException e) {
                out.println("<p>Error closing connection: " + e.getMessage() + "</p>");
                e.printStackTrace(new java.io.PrintWriter(out));
            }
        }
    }
%>

<!-- Top Header -->
<div class="top-header">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-md-3">
                <h1 class="company-name">Builderz</h1>
            </div>
            <div class="col-md-3">
                <div class="contact-info">
                    <i class="fas fa-clock"></i>
                    <div class="contact-info-text">
                        <span>Opening Hour</span>
                        <strong>Mon - Fri, 8:00 - 9:00</strong>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="contact-info">
                    <i class="fas fa-phone-alt"></i>
                    <div class="contact-info-text">
                        <span>Call Us</span>
                        <strong>+012 345 6789</strong>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="contact-info">
                    <i class="fas fa-envelope"></i>
                    <div class="contact-info-text">
                        <span>Email Us</span>
                        <strong>info@example.com</strong>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg">
    <div class="container">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">HOME</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">ABOUT</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">SERVICE</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">TEAM</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">PROJECT</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        PAGES
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Blog</a></li>
                        <li><a class="dropdown-item" href="#">FAQ</a></li>
                        <li><a class="dropdown-item" href="#">Testimonials</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">CONTACT</a>
                </li>
            </ul>
            <button class="btn btn-quote">Get A Quote</button>
        </div>
    </div>
</nav>

<!-- Hero Section with Carousel -->
<div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active">
            <div class="hero-section">
                <div class="hero-bg" style="background-image: url('/api/placeholder/1200/800');"></div>
                <div class="container">
                    <div class="hero-content animated">
                        <h3 class="hero-subtitle">We Are Professional</h3>
                        <h1 class="hero-title">For Your Dream Project</h1>
                        <button class="btn hero-btn">GET A QUOTE</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="carousel-item">
            <div class="hero-section">
                <div class="hero-bg" style="background-image: url('/api/placeholder/1200/800');"></div>
                <div class="container">
                    <div class="hero-content">
                        <h3 class="hero-subtitle">Quality & Innovation</h3>
                        <h1 class="hero-title">Building The Future</h1>
                        <button class="btn hero-btn">OUR PROJECTS</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="carousel-item">
            <div class="hero-section">
                <div class="hero-bg" style="background-image: url('/api/placeholder/1200/800');"></div>
                <div class="container">
                    <div class="hero-content">
                        <h3 class="hero-subtitle">Experience & Excellence</h3>
                        <h1 class="hero-title">Trusted Construction</h1>
                        <button class="btn hero-btn">LEARN MORE</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<!-- Feature Boxes -->
<div class="feature-container">
    <div class="row g-0">
        <div class="col-lg-4">
            <div class="feature-box secondary">
                <i class="fas fa-hard-hat feature-icon"></i>
                <h3 class="feature-title">Expert Worker</h3>
                <p class="feature-text">Our team consists of highly skilled professionals with years of experience in the construction industry, ensuring top-quality workmanship.</p>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="feature-box primary">
                <i class="fas fa-building feature-icon"></i>
                <h3 class="feature-title">Quality Work</h3>
                <p class="feature-text">We are committed to delivering the highest quality construction services, using premium materials and following industry best practices.</p>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="feature-box secondary">
                <i class="fas fa-headset feature-icon"></i>
                <h3 class="feature-title">24/7 Support</h3>
                <p class="feature-text">Our customer support team is available around the clock to address any concerns or questions you may have about your project.</p>
            </div>
        </div>
    </div>
</div>

<!-- About Section -->
<section class="about-section py-5">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-6">
                <div class="about-img">
                    <img src="/api/placeholder/600/400" alt="About Builderz" class="img-fluid">
                </div>
            </div>
            <div class="col-lg-6">
                <div class="about-content">
                    <h6 class="text-primary fw-bold">ABOUT US</h6>
                    <h2 class="mb-4">Building Dreams Into Reality Since 1995</h2>
                    <p>Builderz has been at the forefront of the construction industry for over 25 years, delivering exceptional projects across residential, commercial, and industrial sectors.</p>
                    <p>Our company philosophy is built on integrity, innovation, and excellence. We combine traditional craftsmanship with cutting-edge technology to create buildings that stand the test of time.</p>
                    <div class="row mt-4">
                        <div class="col-md-6">
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle text-primary me-2"></i>
                                <span>Licensed & Insured</span>
                            </div>
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle text-primary me-2"></i>
                                <span>On-Time Completion</span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle text-primary me-2"></i>
                                <span>Cost Transparency</span>
                            </div>
                            <div class="d-flex align-items-center mb-3">
                                <i class="fas fa-check-circle text-primary me-2"></i>
                                <span>Eco-Friendly Solutions</span>
                            </div>
                        </div>
                    </div>
                    <button class="btn btn-primary mt-3">READ MORE</button>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Footer -->
<footer class="bg-dark text-light py-5">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6 mb-4 mb-lg-0">
                <h4 class="text-uppercase mb-4">Builderz</h4>
                <p>We're dedicated to providing the highest quality construction services to our clients, with a focus on innovation, sustainability, and customer satisfaction.</p>
                <div class="social-icons">
                    <a href="#" class="me-2"><i class="fab fa-facebook-f"></i></a>
                    <a href="#" class="me-2"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="me-2"><i class="fab fa-linkedin-in"></i></a>
                    <a href="#"><i class="fab fa-instagram"></i></a>
                </div>
            </div>
            <div class="col-lg-3 col-md-6 mb-4 mb-lg-0">
                <h5 class="text-uppercase mb-4">Quick Links</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><a href="#" class="text-light">Home</a></li>
                    <li class="mb-2"><a href="#" class="text-light">About Us</a></li>
                    <li class="mb-2"><a href="#" class="text-light">Services</a></li>
                    <li class="mb-2"><a href="#" class="text-light">Projects</a></li>
                    <li><a href="#" class="text-light">Contact Us</a></li>
                </ul>
            </div>
            <div class="col-lg-3 col-md-6 mb-4 mb-lg-0">
                <h5 class="text-uppercase mb-4">Services</h5>
                <ul class="list-unstyled">
                    <li class="mb-2"><a href="#" class="text-light">Building Construction</a></li>
                    <li class="mb-2"><a href="#" class="text-light">House Renovation</a></li>
                    <li class="mb-2"><a href="#" class="text-light">Architecture Design</a></li>
                    <li class="mb-2"><a href="#" class="text-light">Interior Design</a></li>
                    <li><a href="#" class="text-light">Consultation</a></li>
                </ul>
            </div>
            <div class="col-lg-3 col-md-6">
                <h5 class="text-uppercase mb-4">Contact Us</h5>
                <div class="mb-3">
                    <i class="fas fa-map-marker-alt me-2"></i>
                    <span>123 Construction Street, Building City, Country</span>
                </div>
                <div class="mb-3">
                    <i class="fas fa-phone-alt me-2"></i>
                    <span>+012 345 6789</span>
                </div>
                <div class="mb-3">
                    <i class="fas fa-envelope me-2"></i>
                    <span>info@example.com</span>
                </div>
            </div>
        </div>
    </div>
</footer>
<div class="copyright bg-secondary py-3">
    <div class="container">
        <div class="row">
            <div class="col-md-6 text-center text-md-start">
                <p class="mb-0 text-light">© 2025 Builderz. All rights reserved.</p>
            </div>
            <div class="col-md-6 text-center text-md-end">
                <p class="mb-0 text-light">Designed by <a href="#" class="text-primary">YourName</a></p>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
<!-- Custom JS -->
<script>
    // Add animation to elements when they come into view
    document.addEventListener('DOMContentLoaded', function() {
        // Initialize Bootstrap components
        var dropdownElementList = [].slice.call(document.querySelectorAll('.dropdown-toggle'));
        var dropdownList = dropdownElementList.map(function (dropdownToggleEl) {
            return new bootstrap.Dropdown(dropdownToggleEl);
        });

        // Initialize the carousel
        var carousel = new bootstrap.Carousel(document.getElementById('heroCarousel'), {
            interval: 5000,
            ride: 'carousel'
        });

        // Animate elements on scroll
        const animateOnScroll = function() {
            const elements = document.querySelectorAll('.feature-box, .about-content, .about-img');

            elements.forEach(element => {
                const elementPosition = element.getBoundingClientRect().top;
                const viewportHeight = window.innerHeight;

                if (elementPosition < viewportHeight - 100) {
                    element.classList.add('animated');
                }
            });
        }

        // Run on page load
        animateOnScroll();

        // Run on scroll
        window.addEventListener('scroll', animateOnScroll);

        // Smooth scrolling for navigation links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();

                const targetId = this.getAttribute('href');
                if (targetId === '#') return;

                const targetElement = document.querySelector(targetId);
                if (targetElement) {
                    targetElement.scrollIntoView({
                        behavior: 'smooth'
                    });
                }
            });
        });

        // Add sticky navbar on scroll
        window.addEventListener('scroll', function() {
            const navbar = document.querySelector('.navbar');
            if (window.scrollY > 100) {
                navbar.classList.add('sticky-top');
                navbar.style.backgroundColor = 'rgba(2, 48, 71, 0.95)';
                navbar.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
            } else {
                navbar.classList.remove('sticky-top');
                navbar.style.backgroundColor = '';
                navbar.style.boxShadow = '';
            }
        });
    });
</script>
</body>
</html>