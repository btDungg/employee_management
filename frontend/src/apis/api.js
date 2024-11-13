import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/points';

export const getListEmployeesPoints = async (employeeId) => {
    const response = await axios.get(`${BASE_URL}/employee/${employeeId}`);
    return response.data;
};

export const fetchAllEmployeePoints = async () => {
    const response = await axios.get(`${BASE_URL}/test`);
    return response.data;
};



export const getPointChanges = async (id) => {
    const response = await axios.get(`${BASE_URL}/${id}/pointChanges`);
    return response.data;
};

export const getEmployeeRoleById = async (id) => {
    const response = await axios.get(`http://localhost:8080/api/employees/${id}/role`);
    return response.data;
};

export const increaseEmployeePoints = async (employeePointDto) => {
    const response = await axios.post(`${BASE_URL}/increase`, employeePointDto);
    return response.data;
};

export const getManagerBonusPointsById = async (id) => {
    const response = await axios.get(`http://localhost:8080/api/employees/${id}/bonus`);
    return response.data;
};

export const increasePointsByManager = async (managerId, employeePointDto) => {
    const response = await axios.post(`${BASE_URL}/increaseByManager/${managerId}`, employeePointDto);
    return response.data;
};



//lấy danh sách employee cho role thích hơp
export const getEmployeeWithBaseRole = async () => {
  const token = localStorage.getItem('token'); // Lấy token từ localStorage
  const response = await axios.get('http://localhost:8080/api/points/employees/point', {
    headers: {
      Authorization: `Bearer ${token}`, // Gửi token trong header
    },
  });
  return response.data;

};

//lấy chi tiết cho role thích hợp
export const getEmployeePoints = async (id) => {
  const token = localStorage.getItem('token'); // Lấy token từ localStorage
  const response = await axios.get(`http://localhost:8080/api/points/employee/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`, // Gửi token trong header
    },
  });
  return response.data;
};


export const getEmployeeProfile = async () => {
  const token = localStorage.getItem('token'); // Lấy token từ localStorage
  const response = await axios.get('http://localhost:8080/api/employees/me/profile', {
    headers: {
      Authorization: `Bearer ${token}`, // Gửi token trong header
    },
  });
  return response.data;
};




const login = async (email, password) => {
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', { email, password });
      const token = response.data.token;
      localStorage.setItem('token', token); // Lưu token vào localStorage
    } catch (error) {
      console.error("Login error:", error);
    }
  };