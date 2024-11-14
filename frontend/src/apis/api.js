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




// Lấy danh sách employee cho role thích hợp
export const getEmployeeWithBaseRole = async () => {
  const token = localStorage.getItem('token'); // Lấy token từ localStorage

  // Kiểm tra nếu không có token thì ném lỗi
  if (!token) {
    throw new Error('Token is missing');
  }

  try {
    // Gửi request với token trong header Authorization
    const response = await axios.get('http://localhost:8080/api/points/employees/point', {
      headers: {
        Authorization: `Bearer ${token}`, // Gửi token trong header
      },
    });
    
    // Kiểm tra dữ liệu trả về có hợp lệ không
    if (!response.data) {
      throw new Error('No data returned');
    }

    return response.data;
  } catch (error) {
    // Xử lý lỗi khi request không thành công
    console.error('Error fetching employee list based on role:', error);
    // Ném lại lỗi để xử lý ở nơi gọi hàm
    throw error; 
  }
};

// Lấy chi tiết điểm cho employee dựa trên id và role thích hợp
export const getEmployeePoints = async (id) => {
  const token = localStorage.getItem('token'); // Lấy token từ localStorage

  // Kiểm tra nếu không có token thì ném lỗi
  if (!token) {
    throw new Error('Token is missing');
  }

  try {
    // Gửi request với token trong header Authorization
    const response = await axios.get(`http://localhost:8080/api/points/employee/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`, // Gửi token trong header
      },
    });
    
    // Kiểm tra dữ liệu trả về có hợp lệ không
    if (!response.data) {
      throw new Error('No data returned');
    }

    return response.data;
  } catch (error) {
    // Xử lý lỗi khi request không thành công
    console.error('Error fetching employee points:', error);
    // Ném lại lỗi để xử lý ở nơi gọi hàm
    throw error; 
  }
};


export const getEmployeeProfile = async () => {
  const token = localStorage.getItem('token'); // Lấy token từ localStorage

  // Kiểm tra nếu không có token thì ném lỗi
  if (!token) {
    throw new Error('Token is missing');
  }

  try {
    // Gửi request với token trong header Authorization
    const response = await axios.get('http://localhost:8080/api/employees/me/profile', {
      headers: {
        Authorization: `Bearer ${token}`, // Gửi token trong header
      },
    });

    // Kiểm tra dữ liệu trả về có hợp lệ không
    if (!response.data) {
      throw new Error('No data returned');
    }

    return response.data; // Trả về dữ liệu hồ sơ của người dùng
  } catch (error) {
    // Xử lý lỗi khi request không thành công
    console.error('Error fetching employee profile:', error);
    // Ném lại lỗi để xử lý ở nơi gọi hàm
    throw error; 
  }
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

  export const logout =  () => {
    try {
       axios.post('http://localhost:8080/api/auth/logout');  // Gọi API logout
      localStorage.removeItem('token');  // Xóa token khỏi localStorage
    } catch (error) {
      console.error("Logout error:", error);
    }
  };

