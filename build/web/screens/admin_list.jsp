<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User List</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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
            }
            .sidebar a {
                padding: 10px 15px;
                text-decoration: none;
                font-size: 18px;
                color: white;
                display: block;
            }
            .sidebar a:hover {
                background-color: #575757;
            }
            .main-content {
                margin-left: 260px;
                padding: 20px;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
            <h2 class="text-center text-white">Welcome ${sessionScope.user.fullName}</h2>
            <a href="home_page">Home</a>


        </div>

        <div class="main-content">
            <div class="container mt-4">
                <h1>User List</h1>
                <form method="get" action="admin_list" class="mb-3">
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
                                <option value="role" ${param.sort == 'role' ? 'selected' : ''}>Sort by Role</option>
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
                            <th scope="col">Role</th>
                            <th scope="col">Status</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.fullName}</td>
                                <td>${user.gender}</td>
                                <td>${user.email}</td>
                                <td>${user.mobile}</td>
                                <td>${user.address}</td>
                                <td>${user.role}</td>
                                <td>
                                    <button class="btn btn-sm btn-toggle-status ${user.status == 'active' ? 'btn-success' : 'btn-secondary'}" data-id="${user.id}" data-status="${user.status}">
                                        ${user.status}
                                    </button>
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-danger btn-delete" data-id="${user.id}">Delete</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <c:if test="${noOfPages > 1}">
                    <nav>
                        <ul class="pagination justify-content-center">
                            <c:forEach begin="1" end="${noOfPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="admin_list?page=${i}&search=${param.search}&status=${param.status}&sort=${param.sort}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>

                <button class="btn btn-success mb-3" data-toggle="modal" data-target="#addModal">Add New User</button>
            </div>
        </div>


        <!-- Modal for Adding User -->
        <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">Add New User</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!-- Error message display -->
                        <c:if test="${not empty errorMsg}">
                            <div class="alert alert-danger">${errorMsg}</div>
                        </c:if>
                        <form id="addUserForm" action="admin_list" method="post" onsubmit="return validatePassword()">
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
                                <input type="email" id="add_email" name="email" class="form-control" required/>
                            </div>
                            <div class="form-group">
                                <label for="add_mobile">Mobile:</label>
                                <input type="text" id="add_mobile" name="mobile" class="form-control" required/>
                            </div>
                            <div class="form-group">
                                <label for="add_address">Address:</label>
                                <input type="text" id="add_address" name="address" class="form-control" required/>
                            </div>
                            <div class="form-group">
                                <label for="add_role">Role:</label>
                                <select id="add_role" name="role" class="form-control">
                                    <option value="Customer">Customer</option>
                                    <option value="Admin">Admin</option>
                                    <option value="Sale">Sale</option>
                                    <option value="Marketing">Marketing</option>
                                </select>
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
                                <div class="input-group">
                                    <input type="password" id="add_password" name="password" class="form-control" required/>
                                    <div class="input-group-append">
                                        <span class="input-group-text" onclick="togglePasswordVisibility('add_password', this)">
                                            <i class="fa fa-eye"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirm_password">Confirm Password:</label>
                                <div class="input-group">
                                    <input type="password" id="confirm_password" name="confirm_password" class="form-control" required/>
                                    <div class="input-group-append">
                                        <span class="input-group-text" onclick="togglePasswordVisibility('confirm_password', this)">
                                            <i class="fa fa-eye"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary">Add User</button>
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
                                                    var userId = button.data('id');
                                                    var currentStatus = button.data('status');
                                                    var newStatus = currentStatus === 'Active' ? 'Inactive' : 'Active';

                                                    $.ajax({
                                                        url: 'admin_list',
                                                        method: 'post',
                                                        data: {id: userId, status: newStatus, action: 'update_status'},
                                                        success: function (response) {
                                                            var responseObj = JSON.parse(response);
                                                            if (responseObj.success) {
                                                                button.data('status', newStatus);
                                                                button.removeClass('btn-success btn-secondary');
                                                                button.addClass(newStatus === 'Active' ? 'btn-success' : 'btn-secondary');
                                                                button.text(newStatus);
                                                            } else {
                                                                alert('Failed to update status');
                                                            }
                                                        }
                                                    });
                                                });

                                                $('.btn-delete').on('click', function () {
                                                    var button = $(this);
                                                    var userId = button.data('id');

                                                    if (confirm('Are you sure you want to delete this user?')) {
                                                        $.ajax({
                                                            url: 'admin_list',
                                                            method: 'post',
                                                            data: {id: userId, action: 'delete'},
                                                            success: function (response) {
                                                                var responseObj = JSON.parse(response);
                                                                if (responseObj.success) {
                                                                    button.closest('tr').remove();
                                                                } else {
                                                                    alert('Failed to delete user');
                                                                }
                                                            }
                                                        });
                                                    }
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
                                            function togglePasswordVisibility(inputId, icon) {
                                                var input = document.getElementById(inputId);
                                                var iconElement = icon.querySelector('i');
                                                if (input.type === "password") {
                                                    input.type = "text";
                                                    iconElement.classList.remove('fa-eye');
                                                    iconElement.classList.add('fa-eye-slash');
                                                } else {
                                                    input.type = "password";
                                                    iconElement.classList.remove('fa-eye-slash');
                                                    iconElement.classList.add('fa-eye');
                                                }
                                            }
            <c:if test="${not empty errorMsg}">
                                            $(document).ready(function () {
                                                $('#addModal').modal('show');
                                            });
            </c:if>
        </script>

    </body>
</html>

