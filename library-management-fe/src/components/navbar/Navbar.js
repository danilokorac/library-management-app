import "./Navbar.css";
import libraryImage from "../../assets/images/library.jpg";
const Navbar = ({
  allBooks,
  setDisplayedBooks,
  switchScreen,
  isLoggedIn,
  user,
  handleLogout,
}) => {
  // Filter and display books based on selection
  const handlePopularClick = () => {
    const sortedBooks = [...allBooks].sort((a, b) => a.quantity - b.quantity);
    setDisplayedBooks(sortedBooks);
  };

  const handleTopRatedClick = () => {
    const sortedBooks = [...allBooks].sort(
      (a, b) => b.starRating - a.starRating
    );
    setDisplayedBooks(sortedBooks);
  };

  const handleCurrentlyAvailableClick = () => {
    setDisplayedBooks(allBooks);
  };

  const handleGenreClick = (genre) => {
    const filteredBooks = allBooks.filter((book) => book.bookGenre === genre);
    setDisplayedBooks(filteredBooks);
  };

  const getTopGenres = () => {
    const genreCount = {};

    // Count occurrences of each genre
    allBooks.forEach((book) => {
      genreCount[book.bookGenre] = (genreCount[book.bookGenre] || 0) + 1;
    });

    // Convert to an array of [genre, count] pairs
    const genreArray = Object.entries(genreCount);

    // Sort by count in descending order and take the top 3
    const sortedGenres = genreArray
      .sort((a, b) => b[1] - a[1]) // Sort by count
      .slice(0, 3); // Get top 3

    return sortedGenres.map(([genre]) => genre); // Extract the genre names
  };

  const topGenres = getTopGenres();

  return (
    <div className="navbar">
      <img
        src={libraryImage}
        alt="Library Logo"
        className="library-logo"
        onClick={() => switchScreen("books")}
      />

      <button className="nav-btn" onClick={handlePopularClick}>
        Popular
      </button>
      <button className="nav-btn" onClick={handleTopRatedClick}>
        Top Rated
      </button>
      <button className="nav-btn" onClick={handleCurrentlyAvailableClick}>
        Currently Available
      </button>

      <div className="genres">
        <h3>Top 3 Genres</h3>
        {topGenres.map((genre, index) => (
          <button
            key={index}
            className="genre-btn"
            onClick={() => handleGenreClick(genre)}
          >
            {genre}
          </button>
        ))}
      </div>

      {/* Authentication buttons */}
      <div className="auth-buttons">
        {isLoggedIn ? (
          // If the user is registered and logged in, display user info and logout button
          <div className="user-info">
            <span className="user-icon">👤</span>
            <span className="user-name">{user?.username || "User"}</span>
            <br />
            <button
              className="auth-btn profile-btn"
              onClick={() => switchScreen("user-dashboard")}
            >
              View Profile
            </button>
            <br />
            {user?.role === "EMPLOYEE" && (
              <button
                className="auth-btn admin-btn"
                onClick={() => switchScreen("admin-panel")}
              >
                Dashboard
              </button>
            )}
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
