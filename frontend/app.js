// Utility functions for modals
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    } else {
        console.error(`Modal with ID ${modalId} not found`);
    }
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'block';
    } else {
        console.error(`Modal with ID ${modalId} not found`);
    }
}

// Clear form fields utility
function clearFormFields(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
    }
}

// Signup form handler
document.querySelector('#signupModal form').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const newUsername = document.querySelector('#new-username').value.trim();
    const newPassword = document.querySelector('#new-password').value;
    const email = document.querySelector('#email').value.trim();

    try {
        const response = await axios.post('http://localhost:8080/api/users/signup', {
            username: newUsername,
            password: newPassword,
            email
        });

        if (response.data.success) {
            alert("Sign-up successful! Please log in.");
            closeModal('signupModal');
            openModal('loginModal');
            clearFormFields('signupModal');
        } else {
            alert("Sign-up failed: " + (response.data.message || "Unknown error"));
        }
    } catch (error) {
        console.error("Signup error:", error);
        const errorMsg = error.response?.data?.message || "Signup failed. Please try again.";
        alert(errorMsg);
    }
});

// Login form handler (if you need it)
document.querySelector('#loginModal form')?.addEventListener('submit', async function(e) {
    e.preventDefault();
    console.log("Login form submitted");

    const username = document.querySelector('#username').value.trim();
    const password = document.querySelector('#password').value;

    try {
        const response = await axios.post('http://localhost:8080/api/users/login', 
            { username, password }, 
            { withCredentials: true });

        console.log("Login response:", response);

        if (response.status === 200) {
            if (response.data.success || response.data.username) {
                alert("Login successful!");
                closeModal('loginModal');
                // Redirect or update UI as needed
                window.location.href = '/dashboard'; // Example redirect
            } else {
                throw new Error("Invalid credentials");
            }
        } else {
            throw new Error(response.data.message || "Login failed");
        }
    } catch (error) {
        console.error("Login error:", error);
        alert(error.response?.data?.message || "Login failed. Please try again.");
    }
});