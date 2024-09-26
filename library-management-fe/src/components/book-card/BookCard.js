import React from "react";
import "./BookCard.css";

const BookCard = ({ coverImage, title, rating, inStock, quantity }) => {
  return (
    <div className="book-card">
      <img src={coverImage} alt={title} className="book-cover" />
      <h3 className="book-title">{title}</h3>
      <p className="book-rating">Rating: {rating} ★</p>
      <p className={`book-stock ${quantity > 0 ? "in-stock" : "out-of-stock"}`}>
        {quantity > 0 ? "In Stock" : "Out of Stock"}
      </p>
      <p className="book-quantity">Quantity: {quantity}</p>
    </div>
  );
};

export default BookCard;
