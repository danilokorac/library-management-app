import React from "react";
import Navbar from "./components/navbar/Navbar";
import BookCard from "./components/book-card/BookCard";
import SearchBar from "./components/search-bar/SearchBar";
import "./App.css";
import libraryImage from "./assets/images/library.jpg";

function App() {
  const dummyBooks = [
    {
      coverImage: libraryImage,
      title: "The Great Gatsby",
      rating: 4.2,
      stock: 5,
    },
    {
      coverImage: libraryImage,
      title: "1984",
      rating: 4.8,
      stock: 2,
    },
    {
      coverImage: libraryImage,
      title: "To Kill a Mockingbird",
      rating: 4.6,
      stock: 0,
    },
    {
      coverImage: libraryImage,
      title: "Pride and Prejudice",
      rating: 4.3,
      stock: 8,
    },
  ];
  return (
    <div className="app">
      <Navbar />
      <div className="main-content">
        <SearchBar />
        <div className="books-list">
          {dummyBooks.map((book, index) => (
            <BookCard key={index} book={book} />
          ))}
        </div>
      </div>
    </div>
  );
}

export default App;
