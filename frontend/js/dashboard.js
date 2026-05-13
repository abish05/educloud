document.addEventListener('DOMContentLoaded', async () => {
    
    // Set username
    const nameDisplay = document.getElementById('userNameDisplay');
    if (nameDisplay) {
        nameDisplay.textContent = localStorage.getItem('name') || 'User';
    }

    try {
        // Try fetching actual stats
        const stats = await apiCall('/dashboard/stats');
        document.getElementById('stat-students').textContent = stats.totalStudents;
        document.getElementById('stat-faculty').textContent = stats.totalFaculty;
        document.getElementById('stat-attendance').textContent = stats.averageAttendance + '%';
        document.getElementById('stat-marks').textContent = stats.averageMarks;
    } catch (e) {
        console.warn("Using mock stats for dashboard");
    }

    // Init Chart
    const ctx = document.getElementById('performanceChart');
    if (ctx) {
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                datasets: [{
                    label: 'Average Attendance %',
                    data: [82, 85, 88, 86, 90, 89],
                    borderColor: '#6366f1',
                    tension: 0.4,
                    fill: true,
                    backgroundColor: 'rgba(99, 102, 241, 0.1)'
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    }
                }
            }
        });
    }
});
