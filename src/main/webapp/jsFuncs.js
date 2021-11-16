function godBlessJs() {
    getAllItems();
    getAllCategories();
}

function addNewCategory() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/category.do',
        data: JSON.stringify({
            name: $('#addCategory').val(),
        }),
    }).done(function (cat) {
        let optionCats = document.getElementById('optionCats');
        optionCats.options.add(new Option(cat.name, cat.id));
    }).fail(function (err) {
        console.log(err);
    });
}

function getAllCategories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_todo/category.do',
        dataType: 'json'
    }).done(function (cats) {
        let optionCats = document.getElementById('optionCats');
        let index = 0;
        for (var cat of cats) {
            optionCats.options[index] = new Option(cat.name, cat.id);
            index++;
        }
    }).fail(function (err) {
        console.log(err);
    });
}

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
    let optionCats = document.getElementById('optionCats');
    let description = document.getElementById('inputDesc');
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/job4j_todo/index.do',
        data: {
            "cats": getSelectedIndexes(optionCats),
            "description": description.value
        },
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
    return `<tr><th>${item.id}</th><td>${item.description}</td><td>${item.created}</td><td>` + createCheckBox(item) + `</td><td>${item.user.email}</td><td>` + getCatsFromItem(item) + `</td></tr>`;
}

function getCatsFromItem(item) {
    let result = '';
    for (let cat of item.categories) {
        result += cat.name;
        result += ', ';
    }
    return result;
}

function addNewOpt(cat) {
    return '<option value=\'<c:out value="${cat.id}"/>\'>${cat.name}</option>\n' +
        ' </';
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

function getSelectedIndexes(oListbox) {
    var arrIndexes = new Array;
    for (var i = 0; i < oListbox.options.length; i++) {
        if (oListbox.options[i].selected)
            arrIndexes.push(oListbox.options[i].value);
    }
    return arrIndexes;
};
