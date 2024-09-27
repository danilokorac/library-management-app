import React, { useEffect, useState } from "react";
import axios from "axios";
import "./BookCard.css";
import BookCard from "./BookCard";

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const response = await axios.get("/book/getAllBooks");
        setBooks(response.data);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }
    };

    fetchBooks();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="book-list">
      {books.map((book) => (
        <BookCard
          key={book.id}
          coverImage={require("../../assets/images/book-cover.jpg")}
          title={book.title}
          rating={book.starRating}
          quantity={book.quantity}
        />
      ))}
    </div>
  );
};

export default BookList;
