function appendNumber(num) {
    document.getElementById("amount").value += num;
}

function clearInput() {
    document.getElementById("amount").value = "";
}

function deleteLast() {
    let input = document.getElementById("amount");
    input.value = input.value.slice(0, -1);
}



      
       