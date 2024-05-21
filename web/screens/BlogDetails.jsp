<%-- 
    Document   : postDetails
    Created on : May 20, 2024, 4:59:27 PM
    Author     : YOUR NAME
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${post.title}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blogDetails.css">
    </head>
    <body>
        <div class="container">
            <div class="main-content">
                <div class="blog-item">
                    <img class="thumbnail" src="${post.thumbnail}" alt="${post.title}">
                    <div>
                        <h2>${post.title}</h2>
                        <div class="post-meta">
                            <span>Author: ${post.authorId}</span> | 
                            <span>Updated: ${post.updatedAt}</span> | 
                            <span>Category: ${categories}</span>
                        </div>
                        <div class="post-details">
                            <p>${post.details}</p>
                        </div>
                    </div>
                </div>
            </div>
            <aside class="sidebar">
                <div class="search-box">
                    <h2>Search</h2>
                    <form action="searchPosts" method="get">
                        <input type="text" name="query" placeholder="Search posts...">
                        <button type="submit">Search</button>
                    </form>
                </div>
                <div class="categories">
                    <h3>Categories</h3>
                    <ul class="category-list">
                        <c:forEach var="category" items="${categories}">
                            <li><a href="${pageContext.request.contextPath}/categoryPost?category=${category}">${category}</a></li>
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
