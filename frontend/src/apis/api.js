import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/points';
const AUTH_URL = 'http://localhost:8080/api/auth';
const EMPLOYEE_URL = 'http://localhost:8080/api/employees';

const getAuthHeaders = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Token is missing');
    }
    return { Authorization: `Bearer ${token}` };
};

export const getListEmployeesPoints = async (employeeId) => {
    const response = await axios.get(`${BASE_URL}/employee/${employeeId}`);
    return response.data;
};

export const modifyPoints = async (modifyPoint) => {
    try {
        const response = await axios.post(`${BASE_URL}/modify-points`, modifyPoint, {
            headers: getAuthHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Error modifying points:', error);
        throw error;
    }
};

export const getMyPoint = async () => {
    const response = await axios.get(`${BASE_URL}`, {
	            headers: getAuthHeaders(),
	        });
    return response.data;
};

export const getPointChanges = async () => {
    const response = await axios.get(`${BASE_URL}/pointChanges`, {
		            headers: getAuthHeaders(),
		        });
    return response.data;
};

export const getEmployeeRoleById = async (id) => {
    const response = await axios.get(`${EMPLOYEE_URL}/${id}/role`);
    return response.data;
};

export const getManagerBonusPointsById = async () => {
    try {
        // Fetch the employee profile
        const profile = await getEmployeeProfile();

        // Check if the employee is a manager
        if (profile.type.toLowerCase() === 'manager') {
            // Fetch the manager's bonus points
            const response = await axios.get(`${EMPLOYEE_URL}/${profile.id}/bonus`, {
                headers: getAuthHeaders(),
            });
            return response.data;
        } else {
            throw new Error('Access denied: Only managers can access bonus points.');
        }
    } catch (error) {
        console.error('Error fetching manager bonus points:', error);
        throw error;
    }
};


export const getEmployeeWithBaseRole = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/employees/point`, {
            headers: getAuthHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching employee list based on role:', error);
        throw error;
    }
};

export const getEmployeePoints = async (id) => {
    try {
        const response = await axios.get(`${BASE_URL}/employee/${id}`, {
            headers: getAuthHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching employee points:', error);
        throw error;
    }
};

export const getEmployeeProfile = async () => {
    try {
        const response = await axios.get(`${EMPLOYEE_URL}/me/profile`, {
            headers: getAuthHeaders(),
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching employee profile:', error);
        throw error;
    }
};

export const login = async (email, password) => {
    try {
        const response = await axios.post(`${AUTH_URL}/login`, { email, password });
        const token = response.data.token;
        localStorage.setItem('token', token);
    } catch (error) {
        console.error('Login error:', error);
        throw error;
    }
};

export const logout =  () => {
    try {
        //  axios.post(`${AUTH_URL}/logout`);
        localStorage.removeItem('token');
    } catch (error) {
        console.error('Logout error:', error);
        throw error;
    }
};

export const getEmployeeById = async (id) => {
    try {
        const response = await axios.get(`${EMPLOYEE_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching employee data:', error);
        throw error;
    }
};

