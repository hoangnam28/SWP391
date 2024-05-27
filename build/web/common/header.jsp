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
</head>
<body>
    <jsp:include page="../common/search.jsp"></jsp:include>


<jsp:include page="../common/search.jsp"></jsp:include>

    <header id="header" class="site-header">

        <div class="top-info border-bottom d-none d-md-block">
            <div class="container-fluid">
                <div class="row g-0">
                    <div class="col-md-4">
                        <p class="fs-6 my-2 text-center">Need any help? Call us <a href="#">112233344455</a></p>
                    </div>
                    <div class="col-md-4 border-start border-end">
                        <p class="fs-6 my-2 text-center">Summer sale discount off 60% off! <a class="text-decoration-underline" href="shop.html">Shop Now</a></p>
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
                            <img src="images/logo.png" class="logo" alt="Logo" style="width: 165px; height: 39px;">
                        </a>
                        <button type="button" class="btn-close btn-close-black" data-bs-dismiss="offcanvas" aria-label="Close" data-bs-target="#bdNavbar"></button>
                    </div>
                    <div class="offcanvas-body">
                        <ul id="navbar" class="navbar-nav text-uppercase justify-content-start justify-content-lg-center align-items-start align-items-lg-center flex-grow-1">
                            <li class="nav-item">
                                <a class="nav-link me-4 active" href="./home_page">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link me-4" href="about.html">About</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link me-4" href="shop.html">Shop</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link me-4" href="./blogs">Blogs</a>
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
                                        <a href="contact.html" class="dropdown-item fw-light">Contact <span class="badge bg-primary">Pro</span></a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link me-4" href="contact.html">Contact</a>
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
                                        <button type="button" class="btn dropdown-toggle" role="button" data-bs-toggle="dropdown" href="">
                                            Welcome ${sessionScope.user.fullName} <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                            
                                            <li><a class="dropdown-item" href="logout">logout</a></li>
                                        </ul>
                                    </c:if>
                                    <!-- Modal -->
                                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                                         aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header border-bottom-0">
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="tabs-listing">
                                                        <nav>
                                                            <div class="nav nav-tabs d-flex justify-content-center" id="nav-tab" role="tablist">
                                                                <button class="nav-link text-uppercase active" id="nav-sign-in-tab" data-bs-toggle="tab" data-bs-target="#nav-sign-in" type="button" role="tab" aria-controls="nav-sign-in" aria-selected="true">Sign In</button>
                                                                <button class="nav-link text-uppercase" id="nav-register-tab" data-bs-toggle="tab" data-bs-target="#nav-register" type="button" role="tab" aria-controls="nav-register" aria-selected="false">Register</button>
                                                            </div>
                                                        </nav>
                                                        <div class="tab-content" id="nav-tabContent">
                                                            <div class="tab-pane fade active show" id="nav-sign-in" role="tabpanel" aria-labelledby="nav-sign-in-tab">
                                                                <form action="login" method="post">
                                                                    <div class="form-group py-3">
                                                                        <label class="mb-2" for="sign-in">Username or email address *</label>
                                                                        <input type="text" minlength="2" name="username" placeholder="Your Username" class="form-control w-100 rounded-3 p-3" value="${requestScope.username}" required>
                                                                    </div>
                                                                    <div class="form-group pb-3">
                                                                        <label class="mb-2" for="sign-in">Password *</label>
                                                                        <input type="password" minlength="2" name="password" placeholder="Your Password" class="form-control w-100 rounded-3 p-3" value="${requestScope.password}" required>
                                                                    </div>
                                                                    <label class="py-3">
                                                                        <input type="checkbox" required="" class="d-inline">
                                                                        <span class="label-body">Remember me</span>
                                                                        <span class="label-body"><a href="#" class="fw-bold">Forgot Password</a></span>
                                                                    </label>
                                                                    <button type="submit" name="submit" class="btn btn-dark w-100 my-3">Login</button>
                                                                </form>
                                                            </div>
                                                            <div class="tab-pane fade" id="nav-register" role="tabpanel" aria-labelledby="nav-register-tab">
                                                                <div class="form-group py-3">
                                                                    <label class="mb-2" for="register">Your email address *</label>
                                                                    <input type="text" minlength="2" name="username" placeholder="Your Email Address" class="form-control w-100 rounded-3 p-3" required>
                                                                </div>
                                                                <div class="form-group pb-3">
                                                                    <label class="mb-2" for="sign-in">Password *</label>
                                                                    <input type="password" minlength="2" name="password" placeholder="Your Password" class="form-control w-100 rounded-3 p-3" required>
                                                                </div>
                                                                <label class="py-3">
                                                                    <input type="checkbox" required="" class="d-inline">
                                                                    <span class="label-body">I agree to the <a href="#" class="fw-bold">Privacy Policy</a></span>
                                                                </label>
                                                                <button type="submit" name="submit" class="btn btn-dark w-100 my-3">Register</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
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
                                    <a href="cart.html" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">
                                        <svg class="cart">
                                        <use xlink:href="#cart"></use>
                                        </svg><span class="fs-6 fw-light">(02)</span>
                                    </a>
                                    <div class="dropdown-menu animate slide dropdown-menu-start dropdown-menu-lg-end p-3">
                                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                                            <span class="text-primary">Your cart</span>
                                            <span class="badge bg-primary rounded-pill">2</span>
                                        </h4>
                                        <ul class="list-group mb-3">
                                            <li class="list-group-item bg-transparent d-flex justify-content-between lh-sm">
                                                <div>
                                                    <h5>
                                                        <a href="single-product.html">IPad (9th Gen)</a>
                                                    </h5>
                                                    <small>High quality in good price.</small>
                                                </div>
                                                <span class="text-primary">$870</span>
                                            </li>
                                            <li class="list-group-item bg-transparent d-flex justify-content-between lh-sm">
                                                <div>
                                                    <h5>
                                                        <a href="single-product.html">Drone with camera</a>
                                                    </h5>
                                                    <small>Professional drone with camera.</small>
                                                </div>
                                                <span class="text-primary">$600</span>
                                            </li>
                                            <li class="list-group-item bg-transparent d-flex justify-content-between">
                                                <span class="text-uppercase"><b>Total (USD)</b></span>
                                                <strong>$1470</strong>
                                            </li>
                                        </ul>
                                        <div class="d-flex flex-wrap justify-content-center">
                                            <a href="cart.html" class="w-100 btn btn-dark mb-1" type="submit">View Cart</a>
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
    <section id="billboard" class="position-relative d-flex align-items-center py-5 bg-light-gray" style="background-image: url(images/banner-image-bg.jpg); background-size: cover; background-repeat: no-repeat; background-position: center;">
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
                                    <a href="shop.html" class="btn mt-3">Shop Collection</a>
                                </div>
                            </div>
                            <div class="col-md-6 text-center">
                                <div class="image-holder">
                                    <img src="images/banner-image.png" class="img-fluid" alt="banner">
                                </div>
                            </li>
                            <li class="cart-dropdown dropdown">
                                <a href="cart.html" class="dropdown-toggle" data-bs-toggle="dropdown" role="button" aria-expanded="false">
                                    <svg class="cart">
                                    <use xlink:href="#cart"></use>
                                    </svg><span class="fs-6 fw-light">(02)</span>
                                </a>
                                <div class="dropdown-menu animate slide dropdown-menu-start dropdown-menu-lg-end p-3">
                                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                                        <span class="text-primary">Your cart</span>
                                        <span class="badge bg-primary rounded-pill">2</span>
                                    </h4>
                                    <ul class="list-group mb-3">
                                        <li class="list-group-item bg-transparent d-flex justify-content-between lh-sm">
                                            <div>
                                                <h5>
                                                    <a href="single-product.html">IPad (9th Gen)</a>
                                                </h5>
                                                <small>High quality in good price.</small>
                                            </div>
                                            <span class="text-primary">$870</span>
                                        </li>
                                        <li class="list-group-item bg-transparent d-flex justify-content-between lh-sm">
                                            <div>
                                                <h5>
                                                    <a href="single-product.html">Drone with camera</a>
                                                </h5>
                                                <small>Professional drone with camera.</small>
                                            </div>
                                            <span class="text-primary">$600</span>
                                        </li>
                                        <li class="list-group-item bg-transparent d-flex justify-content-between">
                                            <span class="text-uppercase"><b>Total (USD)</b></span>
                                            <strong>$1470</strong>
                                        </li>
                                    </ul>
                                    <div class="d-flex flex-wrap justify-content-center">
                                        <a href="cart.html" class="w-100 btn btn-dark mb-1" type="submit">View Cart</a>
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
<section id="billboard" class="position-relative d-flex align-items-center py-5 bg-light-gray" style="background-image: url(images/banner-image-bg.jpg); background-size: cover; background-repeat: no-repeat; background-position: center;">
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
                                <a href="shop.html" class="btn mt-3">Shop Collection</a>
                            </div>
                        </div>
                        <div class="col-md-6 text-center">
                            <div class="image-holder">
                                <img src="images/banner-image.png" class="img-fluid" alt="banner">
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
                                    <a href="shop.html" class="btn mt-3">Shop Product</a>
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
                                    <a href="shop.html" class="btn mt-3">Shop Collection</a>
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
