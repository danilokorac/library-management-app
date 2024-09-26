import React, { useState } from "react";
import Navbar from "./components/navbar/Navbar";
import SearchBar from "./components/search-bar/SearchBar";
import "./App.css";
import RegisterForm from "./components/auth/RegisterForm";
import LoginForm from "./components/auth/LoginForm";
import BookList from "./components/book-card/BookList";

function App() {
  const [activeScreen, setActiveScreen] = useState("books");
  const switchScreen = (screen) => {
    setActiveScreen(screen);
  };
  return (
    <div className="app">
      <Navbar switchScreen={switchScreen} />
      <div className="main-content">
        {activeScreen === "books" && (
          <>
            <SearchBar /> <BookList />
          </>
        )}
        {activeScreen === "login" && <LoginForm />}
        {activeScreen === "register" && <RegisterForm />}
      </div>
    </div>
  );
}

export default App;
