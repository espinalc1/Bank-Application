document.getElementById("loginForm").addEventListener("submit", fetchLogin);

function fetchLogin() {
    alert("Welcome to the Login Page!");
    console.log("Reached Beginning of Fetch Login");
    // console.log(obj);
    let form = document.getElementById("loginForm")
    let _user = {
        "username": form.elements['username'].value,
        "password": form.elements['password'].value,
    }

    let url = "http://localhost:8080/ERS/user/login";

    let response = fetch(url, {
        method: "POST",
        body: JSON.stringify(_user)
    }).then((response) => {
        if ((response.status < 300) && (response.status >= 200)) {
            console.log("resources folder");
            window.location.href = "http://localhost:8080/ERS/manager";
        } else {
            document.getElementById("message").innerHTML = "Please Try Again!";
        }
    }).catch(e => log.error(e));


}



/*     {
        console.log("Getting a response");
        if (response.status >= 200 && response.status < 400) {
            window.location.href = "http://localhost:8080/ERS";
        } else {
            document.getElementById("message").innerHTML = "Login Unsuccessful";
        } */