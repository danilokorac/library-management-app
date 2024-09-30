import React, { useState } from "react";
import "./LoginForm.css";

const LoginForm = ({ setUser, setIsLoggedIn, switchScreen }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        throw new Error("Login failed. Please check your credentials.");
      }

      const data = await response.json();
      localStorage.setItem("token", data.accessToken);

      // Fetch user details after successful login
      const authenticatedUserData = await fetch("/user/me", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${data.accessToken}`,
        },
      });

      if (!authenticatedUserData.ok) {
        throw new Error("Failed to fetch user details.");
      }

      const userData = await authenticatedUserData.json();
      console.log("Fetched user data:", userData);

      setUser(userData);
      setIsLoggedIn(true);
      switchScreen("books");
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div className="login-form">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </label>
        <label>
          Password:
          <input
            type="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </label>
        <button type="submit">Login</button>
        {error && <p className="error-message">{error}</p>}{" "}
        {/* Display error if any */}
      </form>
    </div>
  );
};

export default LoginForm;
