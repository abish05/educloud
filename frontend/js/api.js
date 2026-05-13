const API_BASE_URL = 'http://localhost:8080/api';

async function apiCall(endpoint, method = 'GET', data = null) {
    const token = localStorage.getItem('token');
    
    const headers = {
        'Content-Type': 'application/json'
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        method: method,
        headers: headers,
    };

    if (data) {
        config.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);
        
        if (response.status === 401) {
            localStorage.clear();
            window.location.href = 'login.html';
            throw new Error('Unauthorized');
        }

        const responseData = await response.json();
        
        if (!response.ok) {
            throw new Error(responseData.error || 'API Request Failed');
        }

        return responseData;
    } catch (error) {
        console.error('API Error:', error);
        throw error;
    }
}
