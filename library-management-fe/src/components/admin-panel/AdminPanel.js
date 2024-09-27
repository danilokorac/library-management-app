import React, { useState, useEffect } from "react";
import apiClient from "../../api/axiosConfig";
import "./AdminPanel.css"; // Ensure you have relevant styles
import { formatToReadableDate } from "../../utils/dateUtils";

const AdminPanel = () => {
  const [selectedTable, setSelectedTable] = useState("Books"); // Default table to display
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 8; // Number of items to display per page

  useEffect(() => {
    const fetchData = async () => {
      try {
        let endpoint = "";
        switch (selectedTable) {
          case "Books":
            endpoint = "/book/getAllBooks";
            break;
          case "Users":
            endpoint = "/user/getAllUsers";
            break;
          case "Borrowings":
            endpoint = "/borrowing/getAllBorrowings";
            break;
          default:
            endpoint = "/book/getAllBooks";
        }

        const response = await apiClient.get(endpoint);
        setData(response.data); // Set the data for the selected table
      } catch (error) {
        console.error(`Error fetching ${selectedTable} data:`, error);
        setError(`Failed to load ${selectedTable} data. Please try again.`);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [selectedTable]);

  // Calculate total pages
  const totalPages = Math.ceil(data.length / itemsPerPage);

  // Get current items for the table
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = data.slice(startIndex, startIndex + itemsPerPage);

  // Handle pagination actions
  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage((prevPage) => prevPage + 1);
    }
  };

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage((prevPage) => prevPage - 1);
    }
  };

  // Function to handle table switching
  const handleTableSwitch = (tableName) => {
    setSelectedTable(tableName);
    setCurrentPage(1); // Reset to first page when switching tables
    setData([]); // Clear current data when switching tables
    setLoading(true); // Set loading to true to show loading state while fetching new data
    setError(null); // Clear any existing errors
  };

  // Placeholder functions for Update and Delete actions
  const handleUpdate = (itemId) => {
    alert(`Update action for ${selectedTable} with ID: ${itemId}`);
  };

  const handleDelete = (itemId) => {
    alert(`Delete action for ${selectedTable} with ID: ${itemId}`);
  };

  return (
    <div className="admin-panel">
      {/* Top buttons for selecting different tables */}
      <div className="admin-panel-buttons">
        <button onClick={() => handleTableSwitch("Books")}>Books</button>
        <button onClick={() => handleTableSwitch("Users")}>Users</button>
        <button onClick={() => handleTableSwitch("Borrowings")}>
          Borrowings
        </button>
      </div>

      {/* Loading and error states */}
      {loading ? (
        <div>Loading {selectedTable} data...</div>
      ) : error ? (
        <div className="error">{error}</div>
      ) : (
        // Dynamic table based on selected data
        <table className="admin-panel-table">
          <thead>
            <tr>
              {selectedTable === "Borrowings" ? (
                <>
                  <th>Username</th>
                  <th>Book Title</th>
                  <th>Borrow Date</th>
                  <th>Return Date</th>
                  <th>Comments</th>
                  <th>Debt</th>
                </>
              ) : selectedTable === "Users" ? (
                <>
                  <th>Username</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Membership Type</th>
                </>
              ) : (
                <>
                  <th>Book Title</th>
                  <th>Genre</th>
                  <th>Star Rating</th>
                  <th>Stock</th>
                  <th>Quantity</th>
                </>
              )}
              <th>Actions</th> {/* Column for Update/Delete buttons */}
            </tr>
          </thead>
          <tbody>
            {currentItems.map((item, index) => (
              <tr key={index}>
                {selectedTable === "Borrowings" ? (
                  <>
                    <td>{item.user?.username || "N/A"}</td>{" "}
                    {/* Accessing username */}
                    <td>{item.book?.title || "N/A"}</td> {/* Accessing title */}
                    <td>{formatToReadableDate(item.borrowStartDate)}</td>
                    <td>{formatToReadableDate(item.borrowEndDate)}</td>
                    <td>{item.comments}</td>
                    <td>{item.debtAmount}</td>
                  </>
                ) : selectedTable === "Users" ? (
                  <>
                    <td>{item.username}</td>
                    <td>{item.firstName}</td>
                    <td>{item.lastName}</td>
                    <td>{item.email}</td>
                    <td>{item.role}</td>
                    <td>{item.membershipType}</td>
                  </>
                ) : (
                  <>
                    <td>{item.title}</td>{" "}
                    {/* Assuming 'title' exists in book data */}
                    <td>{item.bookGenre}</td>
                    <td>{item.starRating} ⭐</td>
                    <td>{item.stock}</td>
                    <td>{item.quantity}</td>
                  </>
                )}
                <td>
                  <button
                    className="update-button"
                    onClick={() => handleUpdate(item.id)}
                  >
                    Update
                  </button>
                  <button
                    className="delete-button"
                    onClick={() => handleDelete(item.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {/* Pagination Controls */}
      <div className="pagination">
        <button onClick={handlePrevPage} disabled={currentPage === 1}>
          Previous
        </button>
        <span>
          Page {currentPage} of {totalPages}
        </span>
        <button onClick={handleNextPage} disabled={currentPage === totalPages}>
          Next
        </button>
      </div>
    </div>
  );
};

export default AdminPanel;
