import "./BookCard.css";
import BookCard from "./BookCard";

const BookList = ({ books }) => {
  if (books.length === 0) {
    return <div>No books available.</div>;
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
