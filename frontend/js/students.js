document.addEventListener('DOMContentLoaded', () => {
    const studentList = document.getElementById('studentList');
    const studentForm = document.getElementById('studentForm');
    const studentModal = document.getElementById('studentModal');
    const addStudentBtn = document.getElementById('addStudentBtn');
    const closeModal = document.getElementById('closeModal');

    let isEditing = false;
    let currentStudentId = null;

    // Fetch and render students
    async function loadStudents() {
        try {
            const students = await apiCall('/students');
            studentList.innerHTML = '';
            students.forEach(student => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${student.studentId}</td>
                    <td>${student.name}</td>
                    <td>${student.registerNumber}</td>
                    <td>${student.departmentName || 'N/A'}</td>
                    <td>${student.email}</td>
                    <td class="action-btns">
                        <button class="btn btn-primary btn-sm edit-btn" data-id="${student.id}">Edit</button>
                        <button class="btn btn-danger btn-sm delete-btn" data-id="${student.id}" style="background: #ef4444; color: white;">Delete</button>
                    </td>
                `;
                studentList.appendChild(tr);
            });

            // Add event listeners to buttons
            document.querySelectorAll('.edit-btn').forEach(btn => {
                btn.addEventListener('click', () => openEditModal(btn.getAttribute('data-id')));
            });

            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', () => deleteStudent(btn.getAttribute('data-id')));
            });

        } catch (error) {
            console.error('Failed to load students', error);
        }
    }

    // Add Student
    studentForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const studentData = {
            name: document.getElementById('name').value,
            studentId: document.getElementById('studentId').value,
            registerNumber: document.getElementById('registerNumber').value,
            email: document.getElementById('email').value,
            year: parseInt(document.getElementById('year').value)
        };

        try {
            if (isEditing) {
                await apiCall(`/students/${currentStudentId}`, 'PUT', studentData);
            } else {
                await apiCall('/students', 'POST', studentData);
            }
            closeModalHandler();
            loadStudents();
        } catch (error) {
            alert('Error saving student: ' + error.message);
        }
    });

    // Delete Student
    async function deleteStudent(id) {
        if (confirm('Are you sure you want to delete this student?')) {
            try {
                await apiCall(`/students/${id}`, 'DELETE');
                loadStudents();
            } catch (error) {
                alert('Error deleting student');
            }
        }
    }

    // Modal logic
    addStudentBtn.addEventListener('click', () => {
        isEditing = false;
        document.getElementById('modalTitle').textContent = 'Add New Student';
        studentForm.reset();
        studentModal.style.display = 'block';
    });

    async function openEditModal(id) {
        isEditing = true;
        currentStudentId = id;
        document.getElementById('modalTitle').textContent = 'Edit Student';
        
        try {
            const student = await apiCall(`/students/${id}`);
            document.getElementById('name').value = student.name;
            document.getElementById('studentId').value = student.studentId;
            document.getElementById('registerNumber').value = student.registerNumber;
            document.getElementById('email').value = student.email;
            document.getElementById('year').value = student.year;
            studentModal.style.display = 'block';
        } catch (error) {
            alert('Error fetching student details');
        }
    }

    function closeModalHandler() {
        studentModal.style.display = 'none';
    }

    closeModal.addEventListener('click', closeModalHandler);
    window.onclick = (event) => {
        if (event.target == studentModal) closeModalHandler();
    };

    loadStudents();
});
