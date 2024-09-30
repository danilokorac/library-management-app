import axios from "axios";

// Create an Axios instance with a base URL and authorization header
const apiClient = axios.create({
  baseURL: "", // Replace with your base API URL
  headers: {
    "Content-Type": "application/json",
  },
});

// Interceptor to attach token to every request
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Interceptor to handle response errors
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      console.error("API Error:", error.response.data);
    } else if (error.request) {
      console.error("No response from API:", error.request);
    } else {
      console.error("Error:", error.message);
    }
    return Promise.reject(error);
  }
);

export default apiClient;
