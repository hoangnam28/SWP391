<%-- 
    Document   : SliderList
    Created on : May 27, 2024, 12:28:11 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
            * {
                box-sizing: border-box;
            }

            #myInput {
                background-image: url('/css/searchicon.png');
                background-position: 10px 10px;
                background-repeat: no-repeat;
                width: 100%;
                font-size: 16px;
                padding: 12px 20px 12px 40px;
                border: 1px solid #ddd;
                margin-bottom: 12px;
            }

            #myTable {
                border-collapse: collapse;
                width: 100%;
                border: 1px solid #ddd;
                font-size: 18px;
            }

            #myTable th, #myTable td {
                text-align: center;
                padding: 12px;
            }

            #myTable tr {
                border-bottom: 1px solid #ddd;
            }

            #myTable tr.header, #myTable tr:hover {
                background-color: #f1f1f1;
            }
        </style>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    </head>
    <body>

        <h2>Slider Lists</h2>

        <form action="Slider-List">
            <input type="text" id="myInput" name="title" value="${requestScope.text}" placeholder="Search for names.." title="Type in a name">
            <select name="status">
                <option value="all" ${requestScope.status == 'all' ? 'selected' : ''}>All</option>
                <option value="Active" ${requestScope.status == 'Active' ? 'selected' : ''}>Active</option>
                <option value="Inactive" ${requestScope.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
            </select>
            <button type="submit">Search</button>
        </form>

        <c:if test="${requestScope.list != null}">
            <table id="myTable">
                <tr class="header">
                    <th>title</th>
                    <th>Image</th>
                    <th>Back Link</th>
                    <th colspan="2">Action</th>
                </tr>
                <c:forEach items="${requestScope.list}" var="item">
                    <tr>
                        <td>${item.title}</td>
                        <td><img src="${item.image}" alt="alt"/></td>
                        <td>${item.backlink}</td>
                        <td>
                            <a href="updateStatus?id=${item.id}&status=${item.status}">
                                <c:if test="${item.status == 'Active'}">
                                    <i class='fa fa-eye'></i>
                                </c:if>
                                <c:if test="${item.status == 'Inactive'}">
                                    <i class='fa fa-eye-slash'></i>
                                </c:if>
                            </a>
                        </td>
                        <td>
                            <a href="updateSlider?id=${item.id}">Edit</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${requestScope.list == null}">
            <h2>No data</h2>
        </c:if>

    </body>
</html>

