function validatePassword() {
    let password = document.getElementById("password").value;
    let errorMessage = document.getElementById("password-error");

    // Password validation conditions
    let hasUpperCase = /[A-Z]/.test(password);
    let hasNumber = /[0-9]/.test(password);
    let hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
    let isValidLength = password.length >= 8;

    if (!hasUpperCase || !hasNumber || !hasSpecialChar || !isValidLength) {
        errorMessage.innerText = "Password must contain at least 8 characters, one uppercase letter, one number, and one special character.";
        return false;  // Prevent form submission
    }

    errorMessage.innerText = ""; // Clear error message if valid
    return true;
}
function addMessage(){
	if(validatePassword()){
	setTimeout(function()
	   { let message=document.querySelector(".success-message");
	          message.textContent="Succesfully Added New User";
      },3000);
}
}