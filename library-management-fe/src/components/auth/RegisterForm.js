import React, { useState } from "react";
import axios from "axios";
import "./RegisterForm.css";

const RegisterForm = ({ setUser, switchScreen }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
    email: "",
    membershipType: "FREE",
  });
  const [error, setError] = useState(null);
  const [isRegistered, setIsRegistered] = useState(false); // State to track registration success

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("/auth/register", formData);
      if (
        response.data === "User successfully created and added to the database!"
      ) {
        setIsRegistered(true);
      } else {
        setError("Registration failed. Please try again.");
      }
    } catch (error) {
      console.error(
        "Registration failed:",
        error.response ? error.response.data : error.message
      );
      setError("Registration failed. Please try again.");
    }
  };

  return (
    <div className="register-form">
      <h2>Register</h2>
      {error && <p className="error">{error}</p>}
      {isRegistered ? (
        <div className="success-card">
          <p>You've successfully registered! Head on to log in.</p>
          <button onClick={() => switchScreen("login")}>Log In</button>
        </div>
      ) : (
        <form onSubmit={handleSubmit}>
          <label>
            Username:
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Password:
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            First Name:
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Last Name:
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Email:
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Membership Type:
            <select
              name="membershipType"
              value={formData.membershipType}
              onChange={handleChange}
            >
              <option value="FREE">FREE</option>
              <option value="STANDARD">STANDARD</option>
              <option value="PREMIUM">PREMIUM</option>
              <option value="PREMIUM_PLUS">PREMIUM_PLUS</option>
            </select>
          </label>
          <button type="submit">Register</button>
        </form>
      )}
    </div>
  );
};

export default RegisterForm;
