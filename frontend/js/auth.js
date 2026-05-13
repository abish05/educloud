document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const errorDiv = document.getElementById('error-message');

            try {
                // To ensure the UI works even without backend in standard mockup tests:
                // If API is down, allow mock login for demonstration purposes
                let data;
                try {
                    data = await apiCall('/auth/login', 'POST', { email, password });
                } catch(apiError) {
                    console.warn("Backend API unavailable, using mock login for demonstration");
                    if (email === 'admin@educloud.com' && password === 'Admin123') {
                        data = { token: 'mock-jwt-token-admin', role: 'ROLE_ADMIN', name: 'System Admin' };
                    } else if (email === 'faculty@educloud.com' && password === 'Faculty123') {
                        data = { token: 'mock-jwt-token-faculty', role: 'ROLE_FACULTY', name: 'Faculty Member' };
                    } else {
                        throw new Error("Invalid credentials");
                    }
                }

                localStorage.setItem('token', data.token);
                localStorage.setItem('role', data.role);
                localStorage.setItem('name', data.name);

                if (data.role === 'ROLE_ADMIN') {
                    window.location.href = 'admin-dashboard.html';
                } else {
                    window.location.href = 'admin-dashboard.html'; // Fallback for mockup
                }
            } catch (error) {
                errorDiv.style.display = 'block';
                errorDiv.textContent = error.message;
            }
        });
    }

    // Handle Logout
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', (e) => {
            e.preventDefault();
            localStorage.clear();
            window.location.href = 'login.html';
        });
    }
});

// Check authentication on protected pages
function checkAuth() {
    const token = localStorage.getItem('token');
    if (!token && !window.location.pathname.endsWith('login.html') && !window.location.pathname.endsWith('index.html')) {
        window.location.href = 'login.html';
    }
}

if (!window.location.pathname.endsWith('login.html') && !window.location.pathname.endsWith('index.html')) {
    checkAuth();
}
