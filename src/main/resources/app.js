function startGame() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/startGame", true);
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            document.getElementById("gameStatus").innerHTML = `Game started. Game ID: ${xhr.responseText}`;
        } else {
            console.error("Request failed: ", xhr.statusText);
        }
    };
    xhr.onerror = function () {
        console.error("Request failed.");
    };
    xhr.send();
}

function makeMove() {
    const gameId = document.getElementById("gameId").value;
    const fuelRate = document.getElementById("fuelRate").value;
    const xhr = new XMLHttpRequest();
    xhr.open("POST", `/makeMove?gameId=${gameId}&fuelRate=${fuelRate}`, true);
    xhr.onload = function () {
        if (xhr.status >= 200 && xhr.status < 300) {
            document.getElementById("gameStatus").innerHTML = `Move made. Response: ${xhr.responseText}`;
        } else {
            console.error("Request failed: ", xhr.statusText);
        }
    };
    xhr.onerror = function () {
        console.error("Request failed.");
    };
    xhr.send();
}