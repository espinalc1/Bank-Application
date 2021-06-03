function openView(view) {
    let views = document.getElementsByClassName('page');
    let len = views.length;
    for (let index = 0; index < len; index++) {
        views[index].style.display = "none";
    }

    document.getElementById(view).style.display = "block";
}

document.getElementById('accountInfoBtn').addEventListener('click', function () { alert("Getting Account Info") })

document.getElementById('pendingReimsBtn').addEventListener('click', pendingReimsView);

document.getElementById('logout').addEventListener('click', (event) => logout(event));

function logout(event) {
    event.preventDefault();
    alert("Logging Out");
    let url = "http://localhost:8080/ERS/user/logout";

    let response = fetch(
        url,
        {
            method: "POST",
            headers: {
                "mode": "core",
            },
        }
    ).then((response) => {
        if ((response.status < 300) && (response.status >= 200)) {
            console.log("logged out!!");
            window.location.href = "http://localhost:8080/ERS/user/login";
        }
    }
    ).catch(e => console.error(e))
}

function pendingReimsView() {
    alert("Getting Pending Reimbursements");
    let tableBody = getAndCleanTableBody("pendingReims");

    let url = 'http://localhost:8080/ERS/manager/pendingReims';

    let buttonsClassName = getDataAndPopulateTable(
        url,
        tableBody,
        "pendingReim",
        "accept",
        accept,
        true,
        "reject",
        reject
    );

    /*     url,
        tableBody,
        itemName,
        action,
        btnCallable,
        secondButton = false,
        action2 = "",
        secondBtnCallable = ""
     */

}

// when figure out CORS issues and token issue, can send token here as well
function accept() {
    alert("accepted");
    let id = event.target.id;
    let url = "http://localhost:8080/ERS/manager/acceptReim/" + id;
    let status = fetch(url, {

        method: "GET",
        mode: "cors",

    }).then((response) => {
        console.log(response.status);
        return pendingReimsView();
    }).catch((error) => console.log(error));
    return status;
}

function reject() {
    alert("rejected");
    let id = event.target.id;
    let url = "http://localhost:8080/ERS/manager/rejectReim/" + id;
    let status = fetch(url, {

        method: "GET",
        mode: "cors",

    }).then((response) => {
        console.log(response.status);
        return pendingReimsView();
    }
    ).catch((error) => console.log(error));
    return status;
}


document.getElementById('seeAllEmployeesBtn').addEventListener('click', function () {
    let tableBody = getAndCleanTableBody("allEmployeesTable");

    let url = 'http://localhost:8080/ERS/manager/allEmployees';

    let employeeBtns = getDataAndPopulateTable(
        url,
        tableBody,
        "employee",
        "See Details",
        getEmployeeItem,
    );
    /*     url,
            tableBody,
            itemName,
            action,
            btnCallable,
            secondButton = false,
            action2 = "",
            secondBtnCallable = ""
         */
});

// used as a callable
function getEmployeeItem() {
    openView("employeeReims");
    let tableBody = getAndCleanTableBody("employeeReimsTable");

    let rowId = event.target.id;
    console.log("emp id: " + rowId);

    let url = 'http://localhost:8080/ERS/manager/employeeReims/' + rowId;

    let empReimBtns = getDataAndPopulateTable(
        url,
        tableBody,
        "empReim",
        "accept",
        accept,
        true,
        "reject",
        reject,
    );

    /*     url,
        tableBody,
        itemName,
        action,
        btnCallable,
        secondButton = false,
        action2 = "",
        secondBtnCallable = ""
     */

};

document.getElementById('resolvedReimsBtn').addEventListener('click', function () {
    let tableBody = getAndCleanTableBody("resolvedReimsTable");

    let url = 'http://localhost:8080/ERS/manager/resolvedReims';

    let employeeBtns = getDataAndPopulateTable(
        url,
        tableBody,
        "resolvedReim", // item anem
        "Cool!", // action name in string
        function () { alert("Cool!") }, // btn callable
    );
    /*     url,
            tableBody,
            itemName,
            action,
            btnCallable,
            secondButton = false,
            action2 = "",
            secondBtnCallable = ""
         */
});

// step 1
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

// step 2
// also returns buttons with information
function getDataAndPopulateTable(url,
    tableBody,
    itemName,
    action,
    btnCallable,
    secondButton = false,
    action2 = "",
    secondButtonCallable = "",
) {
    let itemBtns = fetch(url, {

        method: "GET",
        mode: "cors",
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },

    }).then((response) => {

        console.log(response);
        return response.json();

    }).then((data) => {

        console.log("reached second then statement");
        let itemBtnsClassName = populateTable(
            data,
            tableBody,
            itemName,
            action,
            btnCallable,
            secondButton,
            action2,
            secondButtonCallable
        );

        return itemBtnsClassName;

    }).catch((error) => console.log(error));
    return itemBtns;
}

// used by the above function - no need to access directly - should I "encapsulate/hide"
// data should be Object Array format such as JSON
// and gets item buttons class name for later use 
function populateTable(
    data,
    tableBody,
    itemName,
    action,
    btnCallable,
    secondButton = false,
    action2 = "",
    secondButtonCallable = ""
) {
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

    // in case there are two buttons needed
    if (secondButton) {
        let btnCol2 = document.createElement("th");
        btnCol2.innerHTML = "Action";
        genericRow.appendChild(btnCol2);
    }

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

        // first button
        let btnCell = document.createElement("td");
        let btn = document.createElement("button");
        btn.setAttribute("id", rowData.id);

        itemBtnsClassName = `${action}${itemName}Btns`;

        btn.setAttribute("class", itemBtnsClassName);
        btn.innerHTML = "Select";

        btn.addEventListener('click', btnCallable);
        btnCell.appendChild(btn);
        row.appendChild(btnCell);

        // incase second button is needed
        if (secondButton) {
            let btnCell2 = document.createElement("td");
            let btn2 = document.createElement("button");

            btn2.setAttribute("id", rowData.id);
            itemBtnsClassName2 = `${action2}${itemName}Btns`;

            btn2.setAttribute("class", itemBtnsClassName2);
            btn2.innerHTML = action2;
            btn2.addEventListener('click', secondButtonCallable);
            btnCell2.appendChild(btn2);
            row.appendChild(btnCell2);
        }
        tableBody.appendChild(row);
    })
    return itemBtnsClassName;
}
