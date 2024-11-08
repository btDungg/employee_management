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

export const getEmployeePoints = async (id) => {
    const response = await axios.get(`${BASE_URL}/${id}`);
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