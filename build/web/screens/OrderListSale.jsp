<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Order List</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <style>
            .sidebar {
                height: 100%;
                width: 250px;
                position: fixed;
                z-index: 1;
                top: 0;
                left: 0;
                background-color: #111;
                overflow-x: hidden;
                padding-top: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .sidebar a {
                padding: 10px 15px;
                text-decoration: none;
                font-size: 18px;
                color: white;
                display: block;
                text-align: center;
                width: 100%;
            }

            .sidebar a:hover {
                background-color: #575757;
            }

            .avatar-img {
                width: 200px;
                height: 200px;
                object-fit: cover;
                border-radius: 50%;
                margin-bottom: 20px;
            }

            .main-content {
                margin-left: 270px;
                padding: 20px;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <a class="navbar-brand" href="./home_page">
                <img style="width: 200px" src="images/logo.png" class="logo">
            </a>
            <h2 class="text-center text-white">Welcome ${sessionScope.user.fullName}</h2>
            <img src="${pageContext.request.contextPath}/uploads/${sessionScope.user.avatar}" alt="Avatar" class="avatar-img">
            <a href="home_page">
                <h3 class="fas fa-home"></h3> Home
            </a>
        </div>
        <div class="main-content">
            <div class="container">
                <h1 class="mb-4">Order List</h1>
                <form method="get" action="orders_list">
                    <div class="form-row">
                        <div class="form-group col-md-3">
                            <label for="fromDate">From Date</label>
                            <input type="date" class="form-control" id="fromDate" name="fromDate" value="${param.fromDate}">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="toDate">To Date</label>
                            <input type="date" class="form-control" id="toDate" name="toDate" value="${param.toDate}">
                        </div>
                        <div class="form-group col-md-3">
                            <label for="status">Status</label>
                            <select class="form-control" id="status" name="status">
                                <option value="">All</option>
                                <option value="Submitted" ${param.status == 'Submitted' ? 'selected' : ''}>Submitted</option>
                                <option value="Completed" ${param.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                <option value="Canceled" ${param.status == 'Canceled' ? 'selected' : ''}>Canceled</option>
                                <!-- Add other statuses as needed -->
                            </select>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="search">Search</label>
                            <input type="text" class="form-control" id="search" name="search" placeholder="Order ID or Customer Name" value="${param.search}">
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary mb-2">Filter</button>
                    <div class="filter">
                    
                    <a href="orders_list" class="clear-filter">Clear Filter</a>
                </div>
                </form>


                <table class="table table-bordered">
                    <thead class="thead-light">
                        <tr>
                            <th><a href="orders_list?sortField=id&sortOrder=${sortOrder eq 'ASC' ? 'DESC' : 'ASC'}">Order ID</a></th>
                            <th><a href="orders_list?sortField=created_at&sortOrder=${sortOrder eq 'ASC' ? 'DESC' : 'ASC'}">Ordered Date</a></th>
                            <th><a href="orders_list?sortField=customer_name&sortOrder=${sortOrder eq 'ASC' ? 'DESC' : 'ASC'}">Customer Name</a></th>
                            <th>Product</th>
                            <th>Other Products</th>
                            <th><a href="orders_list?sortField=total_cost&sortOrder=${sortOrder eq 'ASC' ? 'DESC' : 'ASC'}">Total Cost</a></th>
                            <th><a href="orders_list?sortField=status&sortOrder=${sortOrder eq 'ASC' ? 'DESC' : 'ASC'}">Status</a></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td><a href="orderDetails?id=${order.id}">${order.id}</a></td>
                                <td>${order.createdAt}</td>
                                <td>${order.receiverName}</td>
                                <td>${order.totalCost}</td>
                                <td>${order.status}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>



                <nav>
                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage - 1}&pageSize=${pageSize}&sortField=${sortField}&sortOrder=${sortOrder}&fromDate=${fromDate}&toDate=${toDate}&saleName=${saleName}&status=${status}&search=${search}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}&pageSize=${pageSize}&sortField=${sortField}&sortOrder=${sortOrder}&fromDate=${fromDate}&toDate=${toDate}&saleName=${saleName}&status=${status}&search=${search}">${i}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${currentPage + 1}&pageSize=${pageSize}&sortField=${sortField}&sortOrder=${sortOrder}&fromDate=${fromDate}&toDate=${toDate}&saleName=${saleName}&status=${status}&search=${search}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>