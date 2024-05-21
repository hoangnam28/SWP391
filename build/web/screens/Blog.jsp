<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blog.css">
    </head>
    <body>
        <!-- Include header.jsp from screens folder -->
        <jsp:include page="/common/header.jsp"></jsp:include>
        <div class="container">
            <div class="main-content">
                <ul id="blog-list">
                    <c:forEach var="post" items="${postList}">
                        <li class="blog-item">
                            <img src="${post.thumbnail}" alt="${post.title}">
                            <div>
                                <h2><a href="${pageContext.request.contextPath}/post_details?id=${post.id}">${post.title}</a></h2>
                                <p>${post.briefInfo}</p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <div id="pagination">
                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <a href="${pageContext.request.contextPath}/blogs?page=${i}">
                            <button <c:if test="${i == currentPage}">disabled</c:if>>${i}</button>
                        </a>
                    </c:forEach>
                </div>
            </div>
            <aside class="sidebarblog">
                <div class="search-box-blog">
                    <h2>Search</h2>
                    <input type="text" id="search-input" placeholder="Search posts...">
                </div>
                <div class="categories">
                    <h3>Categories</h3>
                    <ul class="category-list">
                        <c:forEach var="category" items="${categories}">
                            <li><a href="categoryPosts?category=${category}">${category}</a></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="latest-posts">
                    <h2>Latest Posts</h2>
                    <ul id="latest-posts-list">
                        <li><a href="#">Latest Post 1</a></li>
                        <li><a href="#">Latest Post 2</a></li>
                        <li><a href="#">Latest Post 3</a></li>
                    </ul>
                </div>
                <div class="contacts">
                    <h2>Contact</h2>
                    <ul>
                        <li>Email: example@example.com</li>
                        <li>Phone: (123) 456-7890</li>
                        <li><a href="#">Contact Form</a></li>
                    </ul>
                </div>
            </aside>
        </div>
    </body>
</html>
