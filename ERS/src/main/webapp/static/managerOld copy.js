function openView(view) {
    let views = document.getElementsByClassName('page');
    let len = views.length;
    for (let index = 0; index < len; index++) {
        views[index].style.display = "none";
    }

    document.getElementById(view).style.display = "block";
}

document.getElementById('accountInfoBtn').addEventListener('click', function () { alert("Getting Account Info") })
document.getElementById('pendingReimsBtn').addEventListener('click', function () { alert("Getting Pending Reimbursements") })





/* Required Information:
1. URL
2. Callable Function
3. Table to populate
4. Table's corresponding buttons
 */


document.getElementById('seeAllEmployeesBtn').addEventListener('click', function () {
    // alert("Getting All Employees");
    let tableBody = document.getElementById('allEmployeesTable').children[0];
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
    // skip the first row (since it's the heading row)

    let employeeJSON = fetch('http://localhost:8080/ERS/manager/allEmployees', {
        method: "GET",
        mode: "cors",
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
    }).then((response) =>
        response.json()
    ).then((data) => {
        console.log("Data keys: " + Object.keys(data))
        let obj = data[0];
        // create a row
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
            btn.setAttribute("class", "selectItemBtns");
            btn.innerHTML = "Select";
            btnCell.appendChild(btn);
            row.appendChild(btnCell);
            tableBody.appendChild(row);
        })
        return document.getElementsByClassName("selectItemBtns");
    }).then((btns) => {
        Object.values(btns).forEach(
            btn => btn.addEventListener('click', getItem)
        )
    }).catch((error) => console.log(error));

    
});

function getItem() {
    openView("employeeReims");
    let rowId = event.target.id;
    console.log("emp id: " + rowId);

};


document.getElementById('resolvedReimsBtn').addEventListener('click', function () { alert("Getting All Resolved Reimbursements") });