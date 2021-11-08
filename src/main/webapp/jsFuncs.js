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
