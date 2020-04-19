<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, com.cotede.todolist.*" %>
<!DOCTYPE html>
<html>

<head>
    <title>To-do list</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
    <link rel="stylesheet" type="text/css"
        href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css'>
    <link rel="stylesheet" type="text/css" href='assets/style.css'>
</head>
<body>
    <div class="page-content page-container" id="page-content">
        <div class="padding">
            <div class="row container d-flex justify-content-center">
                <div class="col-lg-12">
                    <div class="card px-3">
                        <div class="card-body">
                            <h4 class="card-title">Awesome JSP Todo list</h4>
                            <div class="add-items d-flex"> <input type="text" class="form-control todo-list-input"
                                    placeholder="What do you need to do today?"> <button
                                    class="add btn btn-primary font-weight-bold todo-list-add-btn">Add</button> </div>
                            <div class="list-wrapper">
                                <ul class="d-flex flex-column-reverse todo-list">
	                                <c:forEach var="ticket" items="${ALL_TICKETS}">
	                                    <li class="${ticket.isChecked()? "completed":""}">
	                                        <div class="form-check">
												<label class="form-check-label">
													<input class="checkbox" type="checkbox" data-id="${ticket.id}" ${ticket.isChecked()?'checked':''}>
													${ticket.value}
													<i class="input-helper"></i>
												</label>
											</div>
											<i class="remove mdi mdi-close-circle-outline" data-id="${ticket.id}"></i>
	                                    </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="assets/main.js"></script>
</body>

</html>