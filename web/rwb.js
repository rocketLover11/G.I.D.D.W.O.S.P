function hamburger() {
    var x = document.getElementById("topbar");
    if (x === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}