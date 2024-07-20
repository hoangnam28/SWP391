<%-- 
    Document   : header
    Created on : May 17, 2024, 10:13:07 AM
    Author     : YOUR NAM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <style>
        .password-container {
            position: relative;
        }
        .password-container input {
            padding-right: 40px; /* Đảm bảo ô nhập liệu có khoảng trống đủ để chứa nút hiển thị mật khẩu */
        }
        .password-container .toggle-password {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
        }
        .avatar-img {
            width: 200px;
            height: 200px;
            object-fit: cover;
            border-radius: 50%; /* Tùy chọn: Làm cho avatar tròn */

        </style>
    </head>
    <body>
        <jsp:include page="../common/search.jsp"></jsp:include>

            <header id="header" class="site-header">

                <div class="top-info border-bottom d-none d-md-block">
                    <div class="container-fluid">
                        <div class="row g-0">
                            <div class="col-md-4">
                                <p class="fs-6 my-2 text-center">Need any help? Call us <a href="#">112233344455</a></p>
                            </div>
                            <div class="col-md-4 border-start border-end">
                                <p class="fs-6 my-2 text-center">Summer sale discount off 60% off! <a class="text-decoration-underline" href="./products">Shop Now</a></p>
                            </div>
                            <div class="col-md-4">
                                <p class="fs-6 my-2 text-center">2-3 business days delivery & free returns</p>
                            </div>
                        </div>
                    </div>
                </div>

                <nav id="header-nav" class="navbar navbar-expand-lg py-3">
                    <div class="container">
                        <a class="navbar-brand" href="./home_page">
                            <img src="images/logo.png" class="logo">
                        </a>
                        <button class="navbar-toggler d-flex d-lg-none order-3 p-2" type="button" data-bs-toggle="offcanvas" data-bs-target="#bdNavbar" aria-controls="bdNavbar" aria-expanded="false" aria-label="Toggle navigation">
                            <svg class="navbar-icon">
                            <use xlink:href="#navbar-icon"></use>
                            </svg>
                        </button>
                        <div class="offcanvas offcanvas-end" tabindex="-1" id="bdNavbar" aria-labelledby="bdNavbarOffcanvasLabel">
                            <div class="offcanvas-header px-4 pb-0">
                                <a class="navbar-brand" href="index.html">
                                    <img src="images/logo.png" class="logo" alt="Logo" style="width: 165px;
                                         height: 39px;">
                                </a>
                                <button type="button" class="btn-close btn-close-black" data-bs-dismiss="offcanvas" aria-label="Close" data-bs-target="#bdNavbar"></button>
                            </div>
                            <div class="offcanvas-body">
                                <ul id="navbar" class="navbar-nav text-uppercase justify-content-start justify-content-lg-center align-items-start align-items-lg-center flex-grow-1">
                                    <li class="nav-item">
                                        <a id="home-link" class="nav-link me-4" href="./home_page">Home</a>
                                    </li>
                                    <li class="nav-item">
                                        <a id="about-link" class="nav-link me-4" href="#">About</a>
                                    </li>
                                    <li class="nav-item">
                                        <a id="shop-link" class="nav-link me-4" href="./products">Shop</a>
                                    </li>
                                    <li class="nav-item">
                                        <a id="blogs-link" class="nav-link me-4" href="./blogs">Blogs</a>
                                    </li>

                                    <li class="nav-item dropdown">
                                        <a class="nav-link me-4 dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">Pages</a>
                                        <ul class="dropdown-menu animate slide border">
                                            <li>
                                                <a href="about.html" class="dropdown-item fw-light">About <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                            <li>
                                                <a href="shop.html" class="dropdown-item fw-light">Shop <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                            <li>
                                                <a href="single-product.html" class="dropdown-item fw-light">Single Product <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                            <li>
                                                <a href="cart.html" class="dropdown-item fw-light">Cart <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                            <li>
                                                <a href="checkout.html" class="dropdown-item fw-light">Checkout <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                            <li>
                                                <a href="blog.html" class="dropdown-item fw-light">Blog <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                            <li>
                                                <a href="single-post.html" class="dropdown-item fw-light">Single Post <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                            <li>
                                                <a href="#" class="dropdown-item fw-light">Contact <span class="badge bg-primary">Pro</span></a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li class="nav-item">
                                    <c:choose>
                                        <c:when test="${sessionScope.role == 'Marketing'}">
                                            <a class="nav-link me-4" href="customers_list">Customers List</a>
                                        </c:when>
                                        <c:when test="${sessionScope.role == 'Sale'}">
                                            <a class="nav-link me-4" href="orders_list">Orders List</a>
                                        </c:when>
                                        <c:when test="${sessionScope.role == 'Admin'}">
                                            <a class="nav-link me-4" href="admin_list">Users List</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="nav-link me-4" href="#">Contact</a>
                                        </c:otherwise>
                                    </c:choose>
                                </li>

                            </ul>
                            <div class="user-items d-flex">
                                <ul class="d-flex justify-content-end list-unstyled mb-0">
                                    <li class="search-item pe-3">
                                        <a href="#" class="search-button">
                                            <svg class="search">
                                            <use xlink:href="#search"></use>
                                            </svg>
                                        </a>
                                    </li>
                                    <li class="pe-3">
                                        <c:if test="${sessionScope.user == null}">
                                            <a href="#" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                                <svg class="user">
                                                <use xlink:href="#user"></use>
                                                </svg>
                                            </a>
                                        </c:if>
                                        <c:if test="${sessionScope.user != null}">
                                            <div class="dropdown btn-group">
                                                <button type="button" class="btn dropdown-toggle" role="button" data-bs-toggle="dropdown">
                                                    Welcome ${sessionScope.user.fullName} <span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <li><a class="dropdown-item" href="history-order">My order</a></li>
                                                    <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#editProfileModal">User Profile</a></li>
                                                    <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#changePasswordModal">Change Password</a></li>
                                                    <li><a class="dropdown-item" href="logout">Logout</a></li>
                                                </ul>
                                            </div>
                                        </c:if>
                                    </li>

                                    <!-- Modal for Sign In / Register -->
                                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <!-- Modal Header -->
                                                <div class="modal-header border-bottom-0">
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <!-- Modal Body -->
                                                <div class="modal-body">
                                                    <div class="tabs-listing">
                                                        <nav>
                                                            <div class="nav nav-tabs d-flex justify-content-center" id="nav-tab" role="tablist">
                                                                <button class="nav-link text-uppercase active" id="nav-sign-in-tab" data-bs-toggle="tab" data-bs-target="#nav-sign-in" type="button" role="tab" aria-controls="nav-sign-in" aria-selected="true">Sign In</button>
                                                                <button class="nav-link text-uppercase" id="nav-register-tab" data-bs-toggle="tab" data-bs-target="#nav-register" type="button" role="tab" aria-controls="nav-register" aria-selected="false">Register</button>
                                                            </div>
                                                        </nav>
                                                        <div class="tab-content" id="nav-tabContent">
                                                            <!-- Sign In Tab -->
                                                            <!-- Sign In Tab -->
                                                            <div class="tab-pane fade active show" id="nav-sign-in" role="tabpanel" aria-labelledby="nav-sign-in-tab">
                                                                <form id="loginForm" method="post">
                                                                    <div class="form-group py-3">
                                                                        <label class="mb-2" for="sign-in">Username or email address *</label>
                                                                        <input type="text" minlength="2" name="email" placeholder="Your Email" class="form-control w-100 rounded-3 p-3" required>
                                                                    </div>
                                                                    <div class="form-group pb-3">
                                                                        <label class="mb-2" for="sign-in">Password *</label>
                                                                        <input type="password" minlength="2" name="password" placeholder="Your Password" class="form-control w-100 rounded-3 p-3" required>
                                                                        <span class="text-danger" id="loginError"></span>
                                                                    </div>
                                                                    <label class="py-3">
                                                                        <input type="checkbox" class="d-inline">
                                                                        <span class="label-body">Remember me</span>
                                                                        <span class="label-body"><a href="enterotp">Forgot Password</a></span>
                                                                    </label>
                                                                    <button type="submit" class="btn btn-dark w-100 my-3">Login</button>
                                                                </form>
                                                            </div>

                                                            <script>
                                                                document.getElementById('loginForm').addEventListener('submit', function (event) {
                                                                    event.preventDefault();

                                                                    const formData = new URLSearchParams(new FormData(this));
                                                                    const loginError = document.getElementById('loginError');

                                                                    fetch('login', {
                                                                        method: 'POST',
                                                                        headers: {
                                                                            'Content-Type': 'application/x-www-form-urlencoded'
                                                                        },
                                                                        body: formData
                                                                    })
                                                                            .then(response => {
                                                                                if (!response.ok) {
                                                                                    throw new Error('Network response was not ok');
                                                                                }
                                                                                return response.json();
                                                                            })
                                                                            .then(data => {
                                                                                if (data.success) {
                                                                                    window.location.href = data.redirectUrl;
                                                                                } else {
                                                                                    loginError.textContent = data.error;
                                                                                }
                                                                            })
                                                                            .catch(error => {
                                                                                loginError.textContent = 'There was a problem with the login request.';
                                                                                console.error('There was an error:', error);
                                                                            });
                                                                });
                                                            </script>
                                                            <!-- Register Tab -->
                                                            <div class="tab-pane fade" id="nav-register" role="tabpanel" aria-labelledby="nav-register-tab">
                                                                <form id="registrationForm">
                                                                    <div class="form-group">
                                                                        <label for="fullName">Full Name:</label>
                                                                        <input type="text" class="form-control" id="fullName" name="fullName" required>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="gender">Gender:</label>
                                                                        <select class="form-control" id="gender" name="gender" required>
                                                                            <option value="Male">Male</option>
                                                                            <option value="Female">Female</option>
                                                                            <option value="Other">Other</option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="email">Email:</label>
                                                                        <input type="email" class="form-control" id="email" name="email" required>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="mobile">Mobile:</label>
                                                                        <input type="text" class="form-control" id="mobile" name="mobile" required>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label for="address">Address:</label>
                                                                        <input type="text" class="form-control" id="address" name="address" required>
                                                                    </div>
                                                                    <div class="form-group password-container">
                                                                        <label for="password">Password:</label>
                                                                        <input type="password" class="form-control" id="password" name="password" required>
                                                                        <span class="toggle-password" onclick="togglePassword('password', 'togglePasswordIcon')">
                                                                            <img src="https://img.icons8.com/ios-glyphs/30/000000/visible.png" alt="Show/Hide Password" id="togglePasswordIcon"/>
                                                                        </span>
                                                                    </div>
                                                                    <div class="form-group password-container">
                                                                        <label for="confirmPassword">Confirm Password:</label>
                                                                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                                                                        <span class="toggle-password" onclick="togglePassword('confirmPassword', 'toggleConfirmPasswordIcon')">
                                                                            <img src="https://img.icons8.com/ios-glyphs/30/000000/visible.png" alt="Show/Hide Password" id="toggleConfirmPasswordIcon"/>
                                                                        </span>
                                                                    </div>
                                                                    <button type="submit" class="btn btn-primary">Register</button>
                                                                </form>
                                                                <div class="alert alert-info mt-3" id="registerMessage" style="display:none;"></div>
                                                            </div>

                                                            <script>
                                                                document.getElementById('registrationForm').addEventListener('submit', function (event) {
                                                                    event.preventDefault();

                                                                    const formData = new URLSearchParams(new FormData(this));
                                                                    const registerMessage = document.getElementById('registerMessage');

                                                                    fetch('register', {
                                                                        method: 'POST',
                                                                        headers: {
                                                                            'Content-Type': 'application/x-www-form-urlencoded'
                                                                        },
                                                                        body: formData
                                                                    })
                                                                            .then(response => {
                                                                                if (!response.ok) {
                                                                                    throw new Error('Network response was not ok');
                                                                                }
                                                                                return response.json();
                                                                            })
                                                                            .then(data => {
                                                                                registerMessage.style.display = 'block';
                                                                                registerMessage.textContent = data.message;
                                                                                if (data.success) {
                                                                                    registerMessage.classList.remove('alert-danger');
                                                                                    registerMessage.classList.add('alert-success');
                                                                                } else {
                                                                                    registerMessage.classList.remove('alert-success');
                                                                                    registerMessage.classList.add('alert-danger');
                                                                                }
                                                                            })
                                                                            .catch(error => {
                                                                                registerMessage.style.display = 'block';
                                                                                registerMessage.textContent = 'There was a problem with the registration request.';
                                                                                registerMessage.classList.remove('alert-success');
                                                                                registerMessage.classList.add('alert-danger');
                                                                                console.error('There was an error:', error);
                                                                            });
                                                                });

                                                                function togglePassword(fieldId, iconId) {
                                                                    var passwordField = document.getElementById(fieldId);
                                                                    var passwordIcon = document.getElementById(iconId);
                                                                    if (passwordField.type === 'password') {
                                                                        passwordField.type = 'text';
                                                                        passwordIcon.src = 'https://img.icons8.com/ios-glyphs/30/000000/invisible.png';
                                                                    } else {
                                                                        passwordField.type = 'password';
                                                                        passwordIcon.src = 'https://img.icons8.com/ios-glyphs/30/000000/visible.png';
                                                                    }
                                                                }
                                                            </script>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Modal for Edit Profile -->
                                    <div class="modal fade" id="editProfileModal" tabindex="-1" aria-labelledby="editProfileModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header border-bottom-0">
                                                    <h5 class="modal-title" id="editProfileModalLabel">Your Profile</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="alert alert-danger" id="editProfileError" style="display: none;"></div>
                                                    <div class="alert alert-success" id="editProfileSuccess" style="display: none;"></div>
                                                    <c:if test="${not empty user.avatar}">
                                                        <img src="${pageContext.request.contextPath}/uploads/${user.avatar}" alt="Avatar" class="avatar-img">
                                                    </c:if>
                                                    <form id="editProfileForm" enctype="multipart/form-data">
                                                        <div class="form-group d-none">
                                                            <label for="fullName">Full Name:</label>
                                                            <input type="text" class="form-control" id="id" name="id" value="${user.id}" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="fullName">Full Name:</label>
                                                            <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullName}" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="gender">Gender:</label>
                                                            <select class="form-control" id="gender" name="gender" required>
                                                                <option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
                                                                <option value="Female" ${user.gender == 'Female' ? 'selected' : ''}>Female</option>
                                                                <option value="Other" ${user.gender == 'Other' ? 'selected' : ''}>Other</option>
                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="mobile">Mobile:</label>
                                                            <input type="text" class="form-control" id="mobile" name="mobile" value="${user.mobile}" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="address">Address:</label>
                                                            <input type="text" class="form-control" id="address" name="address" value="${user.address}" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="avatar">Avatar:</label>
                                                            <input type="file" class="form-control" id="avatar" name="avatar" accept="image/*" >
                                                        </div>
                                                        <button type="submit" class="btn btn-primary">Save Changes</button>
                                                    </form>
                                                    <c:if test="${not empty message}">
                                                        <div class="alert alert-info mt-3">${message}</div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        document.getElementById('editProfileForm').addEventListener('submit', function (event) {
                                            event.preventDefault();

                                            const formData = new FormData(this);
                                            const editProfileError = document.getElementById('editProfileError');
                                            const editProfileSuccess = document.getElementById('editProfileSuccess');

                                            fetch('editProfile', {
                                                method: 'POST',
                                                body: formData
                                            })
                                                    .then(response => {
                                                        if (!response.ok) {
                                                            throw new Error('Network response was not ok');
                                                        }
                                                        return response.json();
                                                    })
                                                    .then(data => {
                                                        if (data.success) {
                                                            editProfileSuccess.style.display = 'block';
                                                            editProfileSuccess.textContent = data.message;
                                                            editProfileError.style.display = 'none';

                                                            // Redirect to home page after 5 seconds
                                                            setTimeout(() => {
                                                                window.location.href = './home_page';
                                                            }, 5000);
                                                        } else {
                                                            editProfileError.style.display = 'block';
                                                            editProfileError.textContent = data.message;
                                                            editProfileSuccess.style.display = 'none';
                                                        }
                                                    })
                                                    .catch(error => {
                                                        editProfileError.style.display = 'block';
                                                        editProfileError.textContent = 'There was a problem with the edit profile request.';
                                                        editProfileSuccess.style.display = 'none';
                                                        console.error('There was an error:', error);
                                                    });
                                        });
                                    </script>


                                    <!-- Modal for Change Password -->
                                    <div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header border-bottom-0">
                                                    <h5 class="modal-title" id="changePasswordModalLabel">Change Password</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="alert alert-danger" id="changePassError" style="display: none;"></div>
                                                    <div class="alert alert-success" id="changePassSuccess" style="display: none;"></div>
                                                    <form id="changePassForm">
                                                        <div class="form-group py-3">
                                                            <label class="mb-2" for="oldPassword">Current Password *</label>
                                                            <input type="password" minlength="2" name="oldPassword" id="oldPassword" placeholder="Your Old Password" class="form-control w-100 rounded-3 p-3" required>
                                                        </div>
                                                        <div class="form-group pb-3">
                                                            <label class="mb-2" for="newPassword">New Password *</label>
                                                            <input type="password" minlength="2" name="newPassword" id="newPassword" placeholder="Your New Password" class="form-control w-100 rounded-3 p-3" required>
                                                        </div>
                                                        <div class="form-group pb-3">
                                                            <label class="mb-2" for="confirmPassword">Confirm Password *</label>
                                                            <input type="password" minlength="2" name="confirmPassword" id="confirmPassword" placeholder="Confirm Your New Password" class="form-control w-100 rounded-3 p-3" required>
                                                        </div>
                                                        <button type="submit" name="submit" class="btn btn-dark w-100 my-3">Change Password</button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        document.getElementById('changePassForm').addEventListener('submit', function (event) {
                                            event.preventDefault();

                                            const formData = new URLSearchParams(new FormData(this));
                                            const changePassError = document.getElementById('changePassError');
                                            const changePassSuccess = document.getElementById('changePassSuccess');

                                            fetch('changepass', {
                                                method: 'POST',
                                                headers: {
                                                    'Content-Type': 'application/x-www-form-urlencoded'
                                                },
                                                body: formData
                                            })
                                                    .then(response => {
                                                        if (!response.ok) {
                                                            throw new Error('Network response was not ok');
                                                        }
                                                        return response.json();
                                                    })
                                                    .then(data => {
                                                        if (data.success) {
                                                            changePassSuccess.style.display = 'block';
                                                            changePassSuccess.textContent = data.message;
                                                            changePassError.style.display = 'none';

                                                            // Redirect to home page after 5 seconds
                                                            setTimeout(() => {
                                                                window.location.href = './home_page';
                                                            }, 5000);
                                                        } else {
                                                            changePassError.style.display = 'block';
                                                            changePassError.textContent = data.message;
                                                            changePassSuccess.style.display = 'none';
                                                        }
                                                    })
                                                    .catch(error => {
                                                        changePassError.style.display = 'block';
                                                        changePassError.textContent = 'There was a problem with the change password request.';
                                                        changePassSuccess.style.display = 'none';
                                                        console.error('There was an error:', error);
                                                    });
                                        });
                                    </script>



                                    <script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
                                    <script type='text/javascript' src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>
                                    <script>
                                        function validateForm() {
                                            var newPassword = document.getElementById("newPassword").value;
                                            var confirmPassword = document.getElementById("confirmPassword").value;
                                            if (newPassword !== confirmPassword) {
                                                alert("New Password and Confirm Password do not match.");
                                                return false;
                                            }
                                            return true;
                                        }
                                    </script>

                                    <li class="wishlist-dropdown dropdown pe-3">
                                        <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">
                                            <svg class="wishlist">
                                            <use xlink:href="#heart"></use>
                                            </svg>
                                        </a>
                                        <div class="dropdown-menu animate slide dropdown-menu-start dropdown-menu-lg-end p-3">
                                            <h4 class="d-flex justify-content-between align-items-center mb-3">
                                                <span class="text-primary">Your wishlist</span>
                                                <span class="badge bg-primary rounded-pill">2</span>
                                            </h4>
                                            <ul class="list-group mb-3">
                                                <li class="list-group-item bg-transparent d-flex justify-content-between lh-sm">
                                                    <div>
                                                        <h5>
                                                            <a href="single-product.html">Iphone 15 pro max</a>
                                                        </h5>
                                                        <small>Special discounted price.</small>
                                                        <a href="#" class="d-block fw-medium text-capitalize mt-2">Add to cart</a>
                                                    </div>
                                                    <span class="text-primary">$2000</span>
                                                </li>
                                                <li class="list-group-item bg-transparent d-flex justify-content-between lh-sm">
                                                    <div>
                                                        <h5>
                                                            <a href="single-product.html">Apple Watch (2nd Gen)</a>
                                                        </h5>
                                                        <small>Professional apple watch.</small>
                                                        <a href="#" class="d-block fw-medium text-capitalize mt-2">Add to cart</a>
                                                    </div>
                                                    <span class="text-primary">$400</span>
                                                </li>
                                                <li class="list-group-item bg-transparent d-flex justify-content-between">
                                                    <span class="text-uppercase"><b>Total (USD)</b></span>
                                                    <strong>$1470</strong>
                                                </li>
                                            </ul>
                                            <div class="d-flex flex-wrap justify-content-center">
                                                <a href="#" class="w-100 btn btn-dark mb-1" type="submit">Add all to cart</a>
                                                <a href="cart.html" class="w-100 btn btn-primary" type="submit">View cart</a>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="cart-dropdown dropdown">
                                        <a href="./CartDetailController" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">
                                            <svg class="cart">
                                                <use xlink:href="#cart"></use>
                                            </svg>
                                            <span class="fs-6 fw-light">(${getNumberOfCart.getNumberOfCart(sessionScope.user_id != null ? sessionScope.user_id : 0)})</span>
                                        </a>
                                        <div class="dropdown-menu animate slide dropdown-menu-start dropdown-menu-lg-end p-3">
                                            <h4 class="d-flex justify-content-between align-items-center mb-3">
                                                <span class="text-primary">Your cart</span>
                                                <span class="badge bg-primary rounded-pill">${getNumberOfCart.getNumberOfCart(sessionScope.user_id != null ? sessionScope.user_id : 0)}</span>
                                            </h4>

                                            <div class="d-flex flex-wrap justify-content-center">
                                                <a href="./CartDetailController" class="w-100 btn btn-dark mb-1" type="submit">View Cart</a>
                                                <a href="checkout.html" class="w-100 btn btn-primary" type="submit">Go to checkout</a>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>

        </header>
        <section id="billboard" class="position-relative d-flex align-items-center py-5 bg-light-gray" style="background-image: url(images/banner-image-bg.jpg);
                 background-size: cover;
                 background-repeat: no-repeat;
                 background-position: center;">
            <div class="position-absolute end-0 pe-0 pe-xxl-5 me-0 me-xxl-5 swiper-next main-slider-button-next">
                <svg class="chevron-forward-circle d-flex justify-content-center align-items-center border rounded-3 p-2" width="55" height="55">
                <use xlink:href="#alt-arrow-right-outline"></use>
                </svg>
            </div>
            <div class="position-absolute start-0 ps-0 ps-xxl-5 ms-0 ms-xxl-5 swiper-prev main-slider-button-prev">
                <svg class="chevron-back-circle d-flex justify-content-center align-items-center border rounded-3 p-2" width="55" height="55">
                <use xlink:href="#alt-arrow-left-outline"></use>
                </svg>
            </div>
            <div class="swiper main-swiper">
                <div class="swiper-wrapper d-flex align-items-center">
                    <div class="swiper-slide">
                        <div class="container">
                            <div class="row d-flex flex-column-reverse flex-md-row align-items-center">
                                <div class="col-md-5 offset-md-1">
                                    <div class="banner-content">
                                        <h2>GoPro hero9 Black</h2>
                                        <p>Limited stocks available. Grab it now!</p>
                                        <a href="./products" class="btn mt-3">Shop Collection</a>
                                    </div>
                                </div>
                                <div class="col-md-6 text-center">
                                    <div class="image-holder">
                                        <img src="images/banner-image.png" class="img-fluid" alt="banner">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="container">
                            <div class="row d-flex flex-column-reverse flex-md-row align-items-center">
                                <div class="col-md-5 offset-md-1">
                                    <div class="banner-content">
                                        <h2>Iphone 15 Pro Max</h2>
                                        <p>Discount available. Grab it now!</p>
                                        <a href="./products" class="btn mt-3">Shop Product</a>
                                    </div>
                                </div>
                                <div class="col-md-6 text-center">
                                    <div class="image-holder">
                                        <img src="images/banner-image1.png" class="img-fluid" alt="banner">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="swiper-slide">
                        <div class="container">
                            <div class="row d-flex flex-column-reverse flex-md-row align-items-center">
                                <div class="col-md-5 offset-md-1">
                                    <div class="banner-content">
                                        <h2>Macbook Collection</h2>
                                        <p>Limited stocks available. Grab it now!</p>
                                        <a href="./products" class="btn mt-3">Shop Collection</a>
                                    </div>
                                </div>
                                <div class="col-md-6 text-center">
                                    <div class="image-holder">
                                        <img src="images/banner-image2.png" class="img-fluid" alt="banner">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const currentPath = window.location.pathname;
            const navLinks = {
                '/home_page': document.getElementById('home-link'),
                '/about': document.getElementById('about-link'),
                '/products': document.getElementById('shop-link'),
                '/blogs': document.getElementById('blogs-link')
            };

            Object.keys(navLinks).forEach(path => {
                if (currentPath.includes(path)) {
                    navLinks[path].classList.add('active');
                } else {
                    navLinks[path].classList.remove('active');
                }
            });
        });

    </script>