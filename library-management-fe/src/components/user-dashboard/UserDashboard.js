import React, { useState, useEffect } from "react";
import axios from "axios";
import "./UserDashboard.css"; // Assuming you'll have a CSS file for styling

const UserDashboard = ({ user }) => {
  // State to hold user and borrowing data
  const [userData, setUserData] = useState(null);
  const [borrowings, setBorrowings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Function to fetch the user borrowings and debt information
    const fetchBorrowings = async () => {
      const token = localStorage.getItem("token");
      try {
        const response = await axios.get(
          `/borrowing/getAllBorrowingsAndDebtByUserUsername?username=${user.username}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const borrowingsData = response.data;

        if (borrowingsData.length > 0) {
          // Extract user data from the first borrowing record
          setUserData(borrowingsData[0].user);
        }
        setBorrowings(borrowingsData);
      } catch (error) {
        console.error("Error fetching borrowings:", error);
        setError("Failed to load borrowing data. Please try again.");
      } finally {
        setLoading(false);
      }
    };

    // Fetch borrowings on component mount
    fetchBorrowings();
  }, [user.username]);

  // Show loading state
  if (loading) {
    return <div>Loading...</div>;
  }

  // Show error state
  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
    <div className="user-dashboard">
      {userData && (
        <div className="user-profile">
          <h2>User Info</h2>
          <p>
            <strong>Username:</strong> {userData.username}
          </p>
          <p>
            <strong>First Name:</strong> {userData.firstName}
          </p>
          <p>
            <strong>Last Name:</strong> {userData.lastName}
          </p>
          <p>
            <strong>Email:</strong> {userData.email}
          </p>
          <p>
            <strong>Role:</strong> {userData.role}
          </p>
          <p>
            <strong>Membership Type:</strong> {userData.membershipType}
          </p>
        </div>
      )}

      <h2>Borrowed Books</h2>
      {borrowings.length > 0 ? (
        <table className="borrowings-table">
          <thead>
            <tr>
              <th>Book Title</th>
              <th>Genre</th>
              <th>Borrow Start Date</th>
              <th>Borrow End Date</th>
              <th>Comments</th>
              <th>Debt Amount</th>
            </tr>
          </thead>
          <tbody>
            {borrowings.map((borrowing, index) => (
              <tr key={index}>
                <td>{borrowing.book.title}</td>
                <td>{borrowing.book.bookGenre}</td>
                <td>{borrowing.borrowStartDate}</td>
                <td>{borrowing.borrowEndDate}</td>
                <td>{borrowing.comments}</td>
                <td>{borrowing.debtAmount}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No borrowings found for this user.</p>
      )}
    </div>
  );
};

export default UserDashboard;
