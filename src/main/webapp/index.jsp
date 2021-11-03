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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>AJAX</title>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    $(document).ready(
        getAllItems());


    function getAllItems() {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_todo/index.do',
            dataType: 'json'
        }).done(function (data) {
            for (var item of data) {
                $('#itemsTableBody tr:last').after(addNewRow(item));
            }
        }).fail(function (err) {
            console.log(err);
        });
    }

    function getItemsByDone(itemByDone) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/job4j_todo/index.do',
            dataType: 'json',
            data: {
                byDone: itemByDone
            }
        }).done(function (data) {
            var tableBody = document.getElementById('itemsTableBody');
            var tableBodyRows = tableBody.getElementsByTagName('tr');
            try {
                for (let x = tableBodyRows.length - 1; x > 0; x--) {
                    console.log(x);
                    console.log(tableBodyRows[1]);
                    tableBody.removeChild(tableBodyRows[1]);
                }
            } catch (err) {
                console.log(err);
            }
            let lol = 0;
            for (var item of data) {
                console.log(lol);
                $('#itemsTableBody tr:last').after(addNewRow(item));
            }
        }).fail(function (err) {
            console.log(err);
        });
    }

    function addNewItem() {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/job4j_todo/index.do',
            data: JSON.stringify({
                description: $('#inputDesc').val()
            }),
            dataType: 'json'
        }).done(function (item) {
            $('#itemsTableBody tr:last').after(addNewRow(item))
        }).fail(function (err) {
            console.log(err);
        });
    }

    function changeItem(id, isDone) {
        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/job4j_todo/index.do',
            data: JSON.stringify({
                id: id,
                done: isDone
            }),
            dataType: 'json'
        }).fail(function (err) {
            console.log(err);
        });
    }

    function addNewRow(item) {
        return `<tr><th>${item.id}</th><td>${item.description}</td><td>${item.created}</td><td>` + createCheckBox(item) + `</td></tr>`;
    }

    function createCheckBox(item) {
        return '<input type="checkbox"' +
            (item.done ? 'checked' : '')
            + ' id=' + 'chbx' + item.id + ' onchange="itemCheckBoxListener(' + item.id + ')">';
    }

    function itemCheckBoxListener(itemId) {
        let chbox = document.getElementById('chbx' + itemId);
        changeItem(itemId, chbox.checked);
    }

    function showByDoneCheckBoxListener() {
        let chbox = document.getElementById('byDoneCheckBox');
        getItemsByDone(chbox.checked);
    }

</script>

<body>

<form class="form-inline">
    <div class="form-group mb-2">
        <label for="staticTextDesc" class="sr-only">Email</label>
        <input type="text" readonly class="form-control-plaintext" id="staticTextDesc"
               value="New Item: ">
    </div>
    <div class="form-group mx-sm-3 mb-2">
        <label for="inputDesc" class="sr-only">Password</label>
        <input type="text" class="form-control" id="inputDesc" placeholder="Description">
    </div>
    <button type="submit" class="btn btn-primary mb-2" onclick="return addNewItem()">Confirm item</button>
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
    </tr>
    </thead>
    <tbody id="itemsTableBody">
    <tr></tr>
    </tbody>
</table>
</body>
</html>