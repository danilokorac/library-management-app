import React from "react";
import "./Navbar.css";
import libraryImage from "../../assets/images/library.jpg";
const Navbar = () => {
  return (
    <div className="navbar">
      <img src={libraryImage} alt="Library Logo" className="library-logo" />

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

      <div className="auth-buttons">
        <button className="auth-btn">Login</button>
        <button className="auth-btn">Register</button>
      </div>
    </div>
  );
};

export default Navbar;
