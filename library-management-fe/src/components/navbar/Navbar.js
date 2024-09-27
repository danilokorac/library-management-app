import React from "react";
import "./Navbar.css";
import libraryImage from "../../assets/images/library.jpg";
const Navbar = ({ switchScreen, isLoggedIn, user, handleLogout }) => {
  return (
    <div className="navbar">
      <img
        src={libraryImage}
        alt="Library Logo"
        className="library-logo"
        onClick={() => switchScreen("books")}
      />

      <button className="nav-btn">Popular</button>
      <button className="nav-btn">Top Rated</button>
      <button className="nav-btn">Currently Available</button>

      <div className="genres">
        <h3>Genres:</h3>
        <button className="genre-btn">Fiction</button>
        <button className="genre-btn">Non-Fiction</button>
        <button className="genre-btn">Fantasy</button>
        <button className="genre-btn">Sci-Fi</button>
      </div>

      {/* Authentication buttons */}
      <div className="auth-buttons">
        {isLoggedIn ? (
          // If the user is registered and logged in, display user info and logout button
          <div className="user-info">
            <span className="user-icon">👤</span>
            <span className="user-name">{user?.username || "User"}</span>
            <button className="auth-btn logout-btn" onClick={handleLogout}>
              Logout
            </button>
          </div>
        ) : (
          // If not registered or logged in, show Login and Register buttons
          <>
            <button className="auth-btn" onClick={() => switchScreen("login")}>
              Login
            </button>
            <button
              className="auth-btn"
              onClick={() => switchScreen("register")}
            >
              Register
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default Navbar;
