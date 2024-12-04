import axios from 'axios';

// Tạo một instance của axios với cấu hình mặc định
const axiosClient = axios.create({
    baseURL: 'http://localhost:8080/api/',
    timeout: 5000, //(ms)
    headers: {
        'Content-Type': 'application/json',
    },
});

// Đảm bảo axiosClient được export để có thể sử dụng trong các component khác
export default axiosClient;