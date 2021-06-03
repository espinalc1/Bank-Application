function openView(view) {
    let views = document.getElementsByClassName('page');
    let len = views.length;
    for (let index = 0; index < len; index++) {
        views[index].style.display = "none";
    }

    document.getElementById(view).style.display = "block";
}

document.getElementById('accountInfoBtn').addEventListener('click', function () { alert("Getting Account Info") })


document.getElementById('pendingReimsBtn').addEventListener('click', function () {
    alert("Getting Pending Reimbursements");
    let tableBody = getAndCleanTableBody("pendingReims");

    let url = 'http://localhost:8080/ERS/manager/pendingReims';

    let pendingBtns = getDataAndPopulateTable(url, tableBody, "pendingReim", function () {
        alert("Successfully updated this request");
    });
})


document.getElementById('seeAllEmployeesBtn').addEventListener('click', function () {
    let tableBody = getAndCleanTableBody("allEmployeesTable");

    let url = 'http://localhost:8080/ERS/manager/allEmployees';

    let employeeBtns = getDataAndPopulateTable(url, tableBody, "employee", getEmployeeItem);

    // could have employee json return the name of the button class to look out for
    // also return the id
    // then open when the buttons
});

// used as a callable
function getEmployeeItem() {
    openView("employeeReims");
    let tableBody = getAndCleanTableBody("employeeReimsTable");

    let rowId = event.target.id;
    console.log("emp id: " + rowId);

    let url = 'http://localhost:8080/ERS/manager/employeeReims/' + rowId;

    let empReimBtns = getDataAndPopulateTable(url, tableBody, "empReim", function () { alert("Can only See!") });

};

function getAndCleanTableBody(tableId) {
    // alert("Getting All Employees");
    let tableBody = document.getElementById(tableId).children[0];
    // select all tr elements besides the first one, iterate and delete each one to refresh
    // console.log(table.tagName, table.children[0].tagName, table.children[0].children[0].tagName, table.children[0].children[0].children[0].tagName);
    // console.log(table.children[0].children[0].tagName);
    let rows = tableBody.children;
    // console.log(rows);
    // console.log(rows[0].children[0].tagName);
    // console.log(rows[1].children[0].tagName);
    Object.values(rows).forEach((row) => {
        // let check = row.children[0].tagName;
        // // console.log(check);
        // if (check == "TD") {
        tableBody.removeChild(row);
        // console.log("removed :" + row);
        // };
    })

    return tableBody;
}

// also returns buttons with information
function getDataAndPopulateTable(url, tableBody, itemName, btnCallable) {
    let itemBtns = fetch(url, {
        method: "GET",
        mode: "cors",
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
    }).then((response) => {
        console.log(response);
        return response.json();
    }
    ).then((data) => {
        console.log("reached second then statement");
        let itemBtnsClassName = populateTable(data, tableBody, itemName);

        return document.getElementsByClassName(itemBtnsClassName);
    }).then((btns) => {
        Object.values(btns).forEach(
            btn => btn.addEventListener('click', btnCallable)
        );
        return btns;
    }).catch((error) => console.log(error));
    return itemBtns;
}

// data should be Object Array format such as JSON
// and gets item buttons class name for later use 
function populateTable(data, tableBody, itemName) {
    let itemBtnsClassName = "";

    console.log("Data keys: " + Object.keys(data))
    let obj = data[0];
    // create a header row
    let genericRow = document.createElement("tr");
    Object.keys(obj).forEach(colName => {
        // add a column to each row with obj keys
        let genericCol = document.createElement("th");
        genericCol.innerHTML = colName;
        genericRow.appendChild(genericCol);
    })
    // an additional col for future buttons
    let btnCol = document.createElement("th");
    btnCol.innerHTML = "Action";
    genericRow.appendChild(btnCol);
    tableBody.appendChild(genericRow);

    // creates a row of data and a button for submission purposes
    data.forEach(rowData => {
        console.log("JSON object keys :" + Object.values(rowData));

        let row = document.createElement("tr");
        // grab individual cells of data and place them in the table
        Object.values(rowData).forEach(cellData => {
            console.log("Cell Data: " + cellData);
            let cell = document.createElement("td");
            cell.innerHTML = cellData;
            row.appendChild(cell);
        })

        let btnCell = document.createElement("td");
        let btn = document.createElement("button");
        btn.setAttribute("id", rowData.id);

        itemBtnsClassName = `select${itemName}Btns`;
        btn.setAttribute("class", itemBtnsClassName);
        btn.innerHTML = "Select";
        btnCell.appendChild(btn);
        row.appendChild(btnCell);
        tableBody.appendChild(row);
    })
    return itemBtnsClassName;
}

document.getElementById('resolvedReimsBtn').addEventListener('click', function () { alert("Getting All Resolved Reimbursements") });