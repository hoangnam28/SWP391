
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Customers List</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
                align-items: center; /* Căn giữa nội dung theo chiều ngang */
            }

            .sidebar a {
                padding: 10px 15px;
                text-decoration: none;
                font-size: 18px;
                color: white;
                display: block;
                text-align: center; /* Căn giữa văn bản trong link */
                width: 100%;
            }

            .sidebar a:hover {
                background-color: #575757;
            }

            .avatar-img {
                width: 200px;
                height: 200px;
                object-fit: cover;
                border-radius: 50%; /* Tùy chọn: Làm cho avatar tròn */
                margin-bottom: 20px; /* Khoảng cách dưới hình ảnh */
            }

            .main-content {
                margin-left: 260px;
                padding: 20px;
            }

            .toggle-password {
                cursor: pointer;
                position: absolute;
                right: 10px;
                top: 35px;
            }

            .form-group {
                position: relative;
                margin-bottom: 1.5rem;
            }

        </style>
    </head>
    <body>
        <div class="sidebar">
            <a class="navbar-brand" href="./home_page">
                <img style="width: 200px"src="images/logo.png" class="logo">
            </a>
            <h2 class="text-center text-white">Welcome ${sessionScope.user.fullName}</h2>
            <img src="${pageContext.request.contextPath}/uploads/${user.avatar}" alt="Avatar" class="avatar-img">
            <a href="home_page">
                <h3 class="fas fa-home"></h3> Home
                <a href="customers_list">Customers List</a>
                <a href="prouduct_list">Product List</a>
            </a>
        </div>


        <div class="main-content">
            <div class="container mt-4">
                <h1>Customers List</h1>
                <form method="get" action="customers_list" class="mb-3">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" name="search" value="${param.search}" class="form-control" placeholder="Search by full name, email, mobile">
                        </div>
                        <div class="col-md-2">
                            <select name="status" class="form-control">
                                <option value="">All Statuses</option>
                                <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
                                <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <select name="sort" class="form-control">
                                <option value="full_name" ${param.sort == 'full_name' ? 'selected' : ''}>Sort by Name</option>
                                <option value="email" ${param.sort == 'email' ? 'selected' : ''}>Sort by Email</option>
                                <option value="mobile" ${param.sort == 'mobile' ? 'selected' : ''}>Sort by Mobile</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </div>
                    </div>
                </form>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Full Name</th>
                            <th scope="col">Gender</th>
                            <th scope="col">Email</th>
                            <th scope="col">Mobile</th>
                            <th scope="col">Address</th>
                            <th scope="col">Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="customer" items="${customers}">
                            <tr>
                                <td>${customer.fullName}</td>
                                <td>${customer.gender}</td>
                                <td>${customer.email}</td>
                                <td>${customer.mobile}</td>
                                <td>${customer.address}</td>
                                <td>
                                    <button class="btn btn-sm btn-toggle-status ${customer.status == 'active' ? 'btn-success' : 'btn-secondary'}" 
                                            data-id="${customer.id}" data-status="${customer.status}">
                                        ${customer.status}
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>

                <!-- JSP code -->

                <!-- Pagination links -->
                <c:if test="${noOfPages > 1}">
                    <nav>
                        <ul class="pagination justify-content-center">
                            <c:forEach begin="1" end="${noOfPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="customers_list?page=${i}&search=${param.search}&status=${param.status}&sort=${param.sort}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>


                <button class="btn btn-success mb-3" data-toggle="modal" data-target="#addModal">Add New Customer</button>
            </div>
        </div>

        <!-- Modal for Adding Customer -->
        <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">Add New Customer</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="addCustomerForm" action="customers_list" method="post" onsubmit="return validatePassword()">
                            <input type="hidden" name="action" value="add"/>
                            <div class="form-group">
                                <label for="add_full_name">Full Name:</label>
                                <input type="text" id="add_full_name" name="full_name" class="form-control" required/>
                            </div>
                            <div class="form-group">
                                <label for="add_gender">Gender:</label>
                                <select id="add_gender" name="gender" class="form-control">
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="add_email">Email:</label>
                                <input type="email" id="add_email" name="email" class="form-control" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" required placeholder="Enter email"/>
                                <div class="invalid-feedback">
                                    Please enter a valid email address.
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add_mobile">Mobile:</label>
                                <input type="text" id="add_mobile" name="mobile" class="form-control" pattern="^0\d{9}$" required placeholder="Enter mobile number starting with 0 and 10 digits"/>
                                <div class="invalid-feedback">
                                    Please enter a valid mobile number starting with 0 and having 10 digits.
                                </div>
                            </div>


                            <div class="form-group">
                                <label for="add_address">Address:</label>
                                <input type="text" id="add_address" name="address" class="form-control" required/>
                            </div>
                            <div class="form-group">
                                <label for="add_status">Status:</label>
                                <select id="add_status" name="status" class="form-control">
                                    <option value="active">Active</option>
                                    <option value="inactive">Inactive</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="add_password">Password:</label>
                                <input type="password" id="add_password" name="password" class="form-control" required/>
                                <i class="fas fa-eye toggle-password" onclick="togglePassword('add_password', this)"></i>
                            </div>
                            <div class="form-group">
                                <label for="confirm_password">Confirm Password:</label>
                                <input type="password" id="confirm_password" name="confirm_password" class="form-control" required/>
                                <i class="fas fa-eye toggle-password" onclick="togglePassword('confirm_password', this)"></i>
                            </div>
                            <button type="submit" class="btn btn-primary">Add Customer</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- jQuery and Bootstrap Bundle (includes Popper) -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
        <script>
                                    $(document).ready(function () {
                                        $('.btn-toggle-status').on('click', function () {
                                            var button = $(this);
                                            var customerId = button.data('id');
                                            var currentStatus = button.data('status');
                                            var newStatus = currentStatus === 'active' ? 'inactive' : 'active';

                                            console.log('Customer ID:', customerId); // Kiểm tra giá trị customerId
                                            console.log('Current Status:', currentStatus); // Kiểm tra giá trị currentStatus
                                            console.log('New Status:', newStatus); // Kiểm tra giá trị newStatus

                                            $.ajax({
                                                url: 'customers_list', // URL của Servlet xử lý
                                                method: 'post',
                                                data: {id: customerId, status: newStatus, action: 'update_status'},
                                                success: function (response) {
                                                    var responseObj = JSON.parse(response);
                                                    console.log(responseObj); // Kiểm tra phản hồi từ server
                                                    if (responseObj.success) {
                                                        button.data('status', newStatus);
                                                        button.removeClass('btn-success btn btn-danger');
                                                        button.addClass(newStatus === 'active' ? 'btn-success' : 'btn btn-danger');
                                                        button.text(newStatus);
                                                    } else {
                                                        alert('Failed to update status');
                                                    }
                                                },
                                                error: function (xhr, status, error) {
                                                    console.log('Error: ' + error); // Kiểm tra lỗi
                                                    alert('Failed to update status');
                                                }
                                            });
                                        });
                                    });
                                    function validatePassword() {
                                        var password = document.getElementById("add_password").value;
                                        var confirmPassword = document.getElementById("confirm_password").value;
                                        if (password !== confirmPassword) {
                                            alert("Passwords do not match.");
                                            return false;
                                        }
                                        return true;
                                    }
                                    function togglePassword(inputId, icon) {
                                        const input = document.getElementById(inputId);
                                        const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
                                        input.setAttribute('type', type);

                                        icon.classList.toggle('fa-eye-slash');
                                    }
                                    // Validation feedback for email and mobile
                                    $(document).ready(function () {
                                        $('#addCustomerForm').submit(function () {
                                            var form = this;
                                            if (form.checkValidity() === false) {
                                                event.preventDefault();
                                                event.stopPropagation();
                                            }
                                            form.classList.add('was-validated');
                                        });
                                    });

        </script>
    </body>
</html>
