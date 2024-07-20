<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Product Management</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <style>
            .filter-form, .sort-form {
                margin-bottom: 20px;
            }
            .product-table th, .product-table td {
                text-align: center;
            }
            .product-table img {
                width: 50px;
                height: 50px;
            }
            .product-actions {
                display: flex;
                justify-content: center;
            }
            .product-actions a {
                margin: 0 5px;
            }
            .btn-add-product {
                margin-bottom: 20px;
            }
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
        <div class="container mt-5">
            <h1>Product Management</h1>

            <!-- Filter Form -->
            <div class="filter-form">
                <form action="prouduct_list" method="get">
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control" name="search" placeholder="Search by title" value="${param.search}">
                        </div>
                        <div class="col">
                            <select class="form-control" name="category">
                                <option value="">All Categories</option>
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.id}" ${param.category == category.id ? 'selected' : ''}>${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-primary">Filter</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Sort Form -->
            <div class="sort-form">
                <form action="prouduct_list" method="get">
                    <input type="hidden" name="search" value="${param.search}">
                    <input type="hidden" name="category" value="${param.category}">
                    <div class="form-row">
                        <div class="col">
                            <select class="form-control" name="sortBy">
                                <option value="title" ${param.sortBy == 'title' ? 'selected' : ''}>Sort by Title</option>
                                <option value="category_id" ${param.sortBy == 'category_id' ? 'selected' : ''}>Sort by Category</option>
                                <option value="original_price" ${param.sortBy == 'original_price' ? 'selected' : ''}>Sort by List Price</option>
                                <option value="sale_price" ${param.sortBy == 'sale_price' ? 'selected' : ''}>Sort by Sale Price</option>
                            </select>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-secondary">Sort</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Add New Product Button -->
            <button type="button" class="btn btn-success btn-add-product" data-toggle="modal" data-target="#addProductModal">Add New Product</button>

            <!-- Product Table -->
            <table class="table table-striped product-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Thumbnail</th>
                        <th>Title</th>
                        <th>Category</th>
                        <th>List Price</th>
                        <th>Sale Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.id}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty product.thumbnail}">
                                        <img src="${pageContext.request.contextPath}/images/${product.thumbnail}" alt="${product.title}">
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Không hiển thị gì cả nếu product.thumbnail null hoặc rỗng -->
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${product.title}</td>
                            <td>${product.categoryName}</td>
                            <td>${product.originalPrice}</td>
                            <td>${product.salePrice}</td>
                            <td class="product-actions">

                                <a href="editProduct?id=${product.id}" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#editProductModal" onclick="editProduct(${product.id}, '${product.title}', '${product.description}', '${product.thumbnail}', ${product.categoryId}, ${product.originalPrice}, ${product.salePrice}, ${product.stock})">Edit</a>
                                <form action="deleteProduct" method="post" style="display: inline;">
                                    <input type="hidden" name="id" value="${product.id}">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- Pagination Controls -->
            <nav>
                <ul class="pagination">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${i == page ? 'active' : ''}">
                            <a class="page-link" href="prouduct_list?page=${i}&search=${param.search}&category=${param.category}&sortBy=${param.sortBy}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
            </div>

        <!-- Add Product Modal -->
        <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addProductModalLabel">Add New Product</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="addProduct" method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="title">Title</label>
                                <input type="text" class="form-control" id="title" name="title" required>
                            </div>
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                            </div>
                            <div class="form-group">
                                <label for="thumbnail">Thumbnail</label>
                                <input type="file" class="form-control" id="thumbnail" name="thumbnail" required>
                            </div>
                            <div class="form-group">
                                <label for="categoryId">Category</label>
                                <select class="form-control" id="categoryId" name="categoryId" required>
                                    <option value="" disabled selected>Select a category</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.id}">${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="originalPrice">Original Price</label>
                                <input type="number" step="0.01" class="form-control" id="originalPrice" name="originalPrice" required>
                            </div>
                            <div class="form-group">
                                <label for="salePrice">Sale Price</label>
                                <input type="number" step="0.01" class="form-control" id="salePrice" name="salePrice" required>
                            </div>
                            <div class="form-group">
                                <label for="stock">Stock</label>
                                <input type="number" class="form-control" id="stock" name="stock" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Add Product</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Edit Product Modal -->
        <div class="modal fade" id="editProductModal" tabindex="-1" role="dialog" aria-labelledby="editProductModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editProductModalLabel">Edit Product</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="editProduct" method="post" enctype="multipart/form-data">
                            <input type="hidden" id="editProductId" name="id">
                            <div class="form-group">
                                <label for="editTitle">Title</label>
                                <input type="text" class="form-control" id="editTitle" name="title" required>
                            </div>
                            <div class="form-group">
                                <label for="editDescription">Description</label>
                                <textarea class="form-control" id="editDescription" name="description" rows="3" required></textarea>
                            </div>
                            <div class="form-group">
                                <label for="editThumbnail">Thumbnail</label>
                                <input type="file" class="form-control" id="editThumbnail" name="thumbnail">
                            </div>
                            <div class="form-group">
                                <label for="editCategoryId">Category</label>
                                <select class="form-control" id="editCategoryId" name="categoryId" required>
                                    <option value="" disabled>Select a category</option>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.id}">${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="editOriginalPrice">Original Price</label>
                                <input type="number" step="0.01" class="form-control" id="editOriginalPrice" name="originalPrice" required>
                            </div>
                            <div class="form-group">
                                <label for="editSalePrice">Sale Price</label>
                                <input type="number" step="0.01" class="form-control" id="editSalePrice" name="salePrice" required>
                            </div>
                            <div class="form-group">
                                <label for="editStock">Stock</label>
                                <input type="number" class="form-control" id="editStock" name="stock" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script>
                                    function editProduct(id, title, description, thumbnail, categoryId, originalPrice, salePrice, stock) {
                                        $('#editProductId').val(id);
                                        $('#editTitle').val(title);
                                        $('#editDescription').val(description);
                                        $('#editCategoryId').val(categoryId);
                                        $('#editOriginalPrice').val(originalPrice);
                                        $('#editSalePrice').val(salePrice);
                                        $('#editStock').val(stock);
                                    }
        </script>
    </body>
</html>


