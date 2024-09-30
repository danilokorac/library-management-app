import React from "react";
import "./SearchBar.css";

const SearchBar = () => {
  return (
    <div className="search-bar">
      <input
        type="text"
        placeholder="Search books..."
        className="search-input"
      />
      <button className="search-btn">Search</button>
    </div>
  );
};

export default SearchBar;
