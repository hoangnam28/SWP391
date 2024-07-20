<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Order Details</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="mb-4">Order Details</h1>

            <div class="card">
                <div class="card-body">
                    <h3 class="card-title">Order Information</h3>
                    <p><strong>Order ID:</strong> ${order.id}</p>
                    <p><strong>Customer Full Name:</strong> ${order.receiverName}</p>
                    <p><strong>Gender:</strong> ${order.receiverGender}</p>
                    <p><strong>Email:</strong> ${order.receiverEmail}</p>
                    <p><strong>Mobile:</strong> ${order.receiverMobile}</p>
                    <p><strong>Address:</strong> ${order.receiverAddress}</p>
                    <p><strong>Order Date:</strong> ${order.createdAt}</p>
                    <p><strong>Total Cost:</strong> ${order.totalCost}</p>
                    <p><strong>Sale User ID:</strong> ${order.saleUserId}</p>
                    <p><strong>Sale User Name:</strong> ${order.saleUserName}</p>
                    <p><strong>Product:</strong> ${order.product}</p>
                    <p><strong>Product Thumbnail:</strong></p>
                    <img class="card-img-top" src="${pageContext.request.contextPath}/images/${order.productThumbnail}" alt="Product Thumbnail" style="max-width: 250px;">
                    <p><strong>Other Products:</strong> ${order.otherProducts}</p>
                    <p><strong>Quantity:</strong> ${order.quantity}</p>
                    <p><strong>Price:</strong> ${order.price}</p>
                    <p><strong>Total Price:</strong> ${order.totalPrice}</p>
                    <p><strong>Status:</strong> ${order.status}</p>
                    <p><strong>Update Date:</strong> ${order.updatedAt}</p>
                    <p><strong>Notes:</strong> ${order.notes}</p>
                </div>
            </div>

            <c:if test="${user.role == 'Sale Manager' || user.id == order.saleUserId}">
                <div class="card mt-4">
                    <div class="card-body">
                        <h3 class="card-title">Update Order Status</h3>
                        <form method="post" action="${pageContext.request.contextPath}/orderDetails">
                            <input type="hidden" name="id" value="${order.id}" />
                            <div class="form-group">
                                <label for="status">Status</label>
                                <select class="form-control" id="status" name="status">
                                    <option value="Submitted" ${order.status == 'Submitted' ? 'selected' : ''}>Submitted</option>
                                    <option value="Completed" ${order.status == 'Completed' ? 'selected' : ''}>Completed</option>
                                    <option value="Cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="notes">Notes</label>
                                <textarea class="form-control" id="notes" name="notes">${order.notes}</textarea>
                            </div>
                            <div class="form-group">
                                <label for="saleUserId">Assign to Sales User</label>
                                <select class="form-control" id="saleUserId" name="saleUserId">
                                    <c:forEach var="saleUser" items="${saleUsers}">
                                        <option value="${saleUser.id}" ${order.saleUserId == saleUser.id ? 'selected' : ''}>${saleUser.fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Update</button>
                        </form>
                    </div>
                </div>
            </c:if>
                 <a href="orders_list" class="btn btn-secondary mt-3">Back to Order List</a>
        </div>
    
        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>


