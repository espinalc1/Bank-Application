var btn = document.getElementById('loginBtn');
btn.addEventListener('click', getLoginInfo());

var url = "localhost:8080/ERS/user/login";

function getLoginInfo() {
    console.log("Inside of getLoginInfo");
    let myForm = document.getElementById("loginForm");
    let formData = new FormData(myForm);
    console.log(formData);
    console.log(JSON.stringify(formData))
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log("Finished logging in");
        } else {
            let child = document.createElement("p");
            child.innerHtml = "<p> This didn't work. Please try again! </p>";
            let loginContainer = document.getElementById("loginContainer").appendChild(child);
        }
        // need to do some process if something goes wrong!
    }

    xhr.open("POST", url);

    xhr.send(formData);

}