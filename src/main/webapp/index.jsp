<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="jsFuncs.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>AJAX</title>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    $(document).ready(
        godBlessJs());
</script>

<body>
<c:if test="${sessionScope.user == null}">
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/security/login.jsp">Войти</a>
    </li>
</c:if>

<c:if test="${sessionScope.user != null}">
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"> <c:out
                value="${sessionScope.user.name}"/> | Выйти</a>
    </li>
</c:if>

<div class="container pt-1">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header" style="font-weight: bold; font-size: larger">
                Форма для создания записи
            </div>
            <div class="card-body">
                <form class="form-inline">
                    <div class="form-group row">
                        <label for="staticTextDesc" class="sr-only">Email</label>
                        <input type="text" readonly class="form-control-plaintext" id="staticTextDesc"
                               value="New Item: ">
                    </div>
                    <div class="col-sm-5">
                        <label for="optionCats"></label>
                        <select id="optionCats" class="form-control" name="optionCats" required multiple>

                        </select>
                    </div>
            </div>
        </div>
        <div class="form-group mx-sm-3 mb-2">
            <label for="inputDesc" class="sr-only">Password</label>
            <input type="text" class="form-control" id="inputDesc" required placeholder="Description">
        </div>

        <button type="submit" class="btn btn-primary mb-2" onclick="return addNewItem()">Confirm item</button>
    </div>
</div>
</form>

<form class="form-inline">
    <div class="form-group mx-sm-3 mb-2">
        <label for="addCategory" class="sr-only">Password</label>
        <input type="text" class="form-control" id="addCategory" required placeholder="new Category">
    </div>
    <button type="submit" class="btn btn-primary mb-2" onclick="return addNewCategory()">Confirm category
    </button>
</form>

<div class="form-check">
    <input class="form-check-input" type="checkbox" value="" id="byDoneCheckBox"
           onchange="showByDoneCheckBoxListener()">
    <label class="form-check-label" for="byDoneCheckBox">
        Only Undone
    </label>
</div>
<table class="table" id="itemsTable">
    <thead>
    <tr>
        <th>#</th>
        <th>Description</th>
        <th>Created</th>
        <th>Done</th>
        <th>Owner</th>
        <th>Categories</th>
    </tr>
    </thead>
    <tbody id="itemsTableBody">
    <tr></tr>
    </tbody>
</table>
</body>
</html>