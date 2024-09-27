import React, { useState } from "react";
import Navbar from "./components/navbar/Navbar";
import SearchBar from "./components/search-bar/SearchBar";
import "./App.css";
import RegisterForm from "./components/auth/RegisterForm";
import LoginForm from "./components/auth/LoginForm";
import BookList from "./components/book-card/BookList";
import UserDashboard from "./components/user-dashboard/UserDashboard";

function App() {
  const [activeScreen, setActiveScreen] = useState("books");
  const [user, setUser] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const switchScreen = (screen) => {
    setActiveScreen(screen);
  };

  const handleLogout = () => {
    setUser(null);
    setIsLoggedIn(false);
    switchScreen("books"); // Redirect to the books screen after logout
  };

  return (
    <div className="app">
      <Navbar
        switchScreen={switchScreen}
        isLoggedIn={isLoggedIn}
        user={user}
        handleLogout={handleLogout}
      />
      <div className="main-content">
        {activeScreen === "books" && (
          <>
            <SearchBar /> <BookList />
          </>
        )}
        {activeScreen === "register" && (
          <RegisterForm
            setUser={setUser}
            setIsLoggedIn={setIsLoggedIn} // Update login state on successful registration
            switchScreen={switchScreen}
          />
        )}
        {activeScreen === "login" && (
          <LoginForm
            setUser={setUser}
            setIsLoggedIn={setIsLoggedIn} // Update login state on successful login
            switchScreen={switchScreen}
          />
        )}
        {activeScreen === "user-dashboard" && <UserDashboard user={user} />}
      </div>
    </div>
  );
}

export default App;
