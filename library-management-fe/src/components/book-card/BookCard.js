import React from "react";
import "./BookCard.css";

const BookCard = ({ book }) => {
  return (
    <div className="book-card">
      <img src={book.coverImage} alt={book.title} className="book-cover" />
      <h3 className="book-title">{book.title}</h3>
      <p className="book-rating">Rating: {book.rating} ★</p>
      <p
        className={`book-stock ${book.stock > 0 ? "in-stock" : "out-of-stock"}`}
      >
        {book.stock > 0 ? "In Stock" : "Out of Stock"}
      </p>
      <p className="book-quantity">Quantity: {book.stock}</p>
    </div>
  );
};

export default BookCard;
