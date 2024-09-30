import React, { useState, useEffect } from "react";
import apiClient from "../../api/axiosConfig";
import "./AdminPanel.css";
import { formatToReadableDate } from "../../utils/dateUtils";
import ModalComponent from "../common/ModalComponent";

const AdminPanel = () => {
  const [selectedTable, setSelectedTable] = useState("Books"); // Default table to display
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Model state management
  const [isUpdateModalOpen, setUpdateModalOpen] = useState(false);
  const [isDeleteModalOpen, setDeleteModalOpen] = useState(false);
  const [currentItem, setCurrentItem] = useState(null);
  const [updatedItem, setUpdatedItem] = useState({});

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
  const handleUpdate = (item) => {
    setCurrentItem(item);
    setUpdatedItem(item);
    setUpdateModalOpen(true);
  };

  const handleDelete = (item) => {
    console.log("Deleting item:", item);
    setCurrentItem(item);
    setDeleteModalOpen(true);
  };

  const handleUpdateConfirm = async () => {
    try {
      let endpoint = "";
      switch (selectedTable) {
        case "Books":
          endpoint = `/book/updateBook?id=${currentItem.id}`;
          break;
        case "Users":
          endpoint = `/user/updateUser?id=${currentItem.id}`;
          break;
        case "Borrowings":
          endpoint = `/borrowing/updateBorrowing?id=${currentItem.id}`;
          break;
        default:
          return;
      }

      const response = await apiClient.put(endpoint, updatedItem);

      setData((previousData) =>
        previousData.map((item) =>
          item.id === currentItem.id ? response.data : item
        )
      );

      setUpdateModalOpen(false);
    } catch (error) {
      console.error("Error updating item: ", error);
      setError("Failed to update the item, please try again!");
    }
  };

  const handleDeleteConfirm = async () => {
    try {
      let endpoint = "";
      switch (selectedTable) {
        case "Books":
          endpoint = `/book/deleteBook?id=${currentItem.id}`;
          break;
        case "Users":
          endpoint = `/user/deleteUser?id=${currentItem.id}`;
          break;
        case "Borrowings":
          endpoint = `/borrowing/deleteBorrowing?id=${currentItem.id}`;
          break;
        default:
          return;
      }

      await apiClient.delete(endpoint);

      setData((previousData) =>
        previousData.filter((item) => item.id !== currentItem.id)
      );

      setDeleteModalOpen(false);
    } catch (error) {
      console.error("Error deleting item: ", error);
      setError("Failed to delete the item, please try again!");
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedItem((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
    <div className="admin-panel">
      <div className="admin-panel-buttons">
        <button onClick={() => handleTableSwitch("Books")}>Books</button>
        <button onClick={() => handleTableSwitch("Users")}>Users</button>
        <button onClick={() => handleTableSwitch("Borrowings")}>
          Borrowings
        </button>
      </div>

      {loading ? (
        <div>Loading {selectedTable} data...</div>
      ) : error ? (
        <div className="error">{error}</div>
      ) : (
        <table className="admin-panel-table">
          <thead>
            <tr>
              {selectedTable === "Borrowings" && (
                <>
                  <th>Username</th>
                  <th>Book Title</th>
                  <th>Borrow Date</th>
                  <th>Return Date</th>
                  <th>Comments</th>
                  <th>Debt</th>
                </>
              )}
              {selectedTable === "Users" && (
                <>
                  <th>Username</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Membership Type</th>
                </>
              )}
              {selectedTable === "Books" && (
                <>
                  <th>Book Title</th>
                  <th>Genre</th>
                  <th>Star Rating</th>
                  <th>Stock</th>
                  <th>Quantity</th>
                </>
              )}
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {currentItems.map((item, index) => (
              <tr key={index}>
                {selectedTable === "Borrowings" && (
                  <>
                    <td>{item.user?.username || "N/A"}</td>
                    <td>{item.book?.title || "N/A"}</td>
                    <td>{formatToReadableDate(item.borrowStartDate)}</td>
                    <td>{formatToReadableDate(item.borrowEndDate)}</td>
                    <td>{item.comments}</td>
                    <td>{item.debtAmount}</td>
                  </>
                )}
                {selectedTable === "Users" && (
                  <>
                    <td>{item.username}</td>
                    <td>{item.firstName}</td>
                    <td>{item.lastName}</td>
                    <td>{item.email}</td>
                    <td>{item.role}</td>
                    <td>{item.membershipType}</td>
                  </>
                )}
                {selectedTable === "Books" && (
                  <>
                    <td>{item.title}</td>
                    <td>{item.bookGenre}</td>
                    <td>{item.starRating} ⭐</td>
                    <td>{item.stock}</td>
                    <td>{item.quantity}</td>
                  </>
                )}
                <td>
                  <button
                    className="update-button"
                    onClick={() => handleUpdate(item)}
                  >
                    Update
                  </button>
                  <button
                    className="delete-button"
                    onClick={() => handleDelete(item)}
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

      {/* Modal components */}
      {isUpdateModalOpen && (
        <ModalComponent
          isOpen={isUpdateModalOpen}
          onClose={() => setUpdateModalOpen(false)}
          title={`Update ${selectedTable.slice(0, -1)}: ${
            currentItem.title ||
            currentItem.username ||
            currentItem.user.username + " borrowing"
          }`}
          onConfirm={handleUpdateConfirm}
        >
          <form>
            {/* Input fields for item properties */}
            {selectedTable === "Books" && (
              <>
                <label>
                  Title:
                  <input
                    type="text"
                    name="title"
                    value={updatedItem.title || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Genre:
                  <input
                    type="text"
                    name="bookGenre"
                    value={updatedItem.bookGenre || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Stock:
                  <input
                    type="text"
                    name="stock"
                    value={updatedItem.stock || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Quantity:
                  <input
                    type="number"
                    name="quantity"
                    value={updatedItem.quantity || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Star Rating:
                  <input
                    type="number"
                    name="starRating"
                    value={updatedItem.starRating || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
              </>
            )}
            {selectedTable === "Users" && (
              <>
                <label>
                  First Name:
                  <input
                    type="text"
                    name="firstName"
                    value={updatedItem.firstName || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Last Name:
                  <input
                    type="text"
                    name="lastName"
                    value={updatedItem.lastName || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Email:
                  <input
                    type="email"
                    name="email"
                    value={updatedItem.email || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Role:
                  <input
                    type="text"
                    name="role"
                    value={updatedItem.role || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Membership Type:
                  <input
                    type="text"
                    name="membershipType"
                    value={updatedItem.membershipType || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
              </>
            )}
            {selectedTable === "Borrowings" && (
              <>
                <label>
                  Username:
                  <input
                    type="text"
                    name="username"
                    value={updatedItem.user?.username || ""}
                    onChange={handleInputChange}
                    readOnly // Optional, if we don't want to allow changing the username
                  />
                </label>
                <br />
                <label>
                  Book Title:
                  <input
                    type="text"
                    name="bookTitle"
                    value={updatedItem.book?.title || ""}
                    onChange={handleInputChange}
                    readOnly // Optional, if we don't want to allow changing the book title
                  />
                </label>
                <br />
                <label>
                  Borrow Date:
                  <input
                    type="date"
                    name="borrowStartDate"
                    value={updatedItem.borrowStartDate || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Return Date:
                  <input
                    type="date"
                    name="borrowEndDate"
                    value={updatedItem.borrowEndDate || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
                <br />
                <label>
                  Comments:
                  <textarea
                    name="comments"
                    value={updatedItem.comments || ""}
                    onChange={handleInputChange}
                  />
                </label>
                <br />
                <label>
                  Debt:
                  <input
                    type="number"
                    name="debtAmount"
                    value={updatedItem.debtAmount || ""}
                    onChange={handleInputChange}
                    required
                  />
                </label>
              </>
            )}
          </form>
        </ModalComponent>
      )}

      {isDeleteModalOpen && (
        <ModalComponent
          isOpen={isDeleteModalOpen}
          onClose={() => setDeleteModalOpen(false)}
          title={`Delete ${selectedTable.slice(0, -1)}: ${
            currentItem.title || currentItem.username
          }`}
          onConfirm={handleDeleteConfirm}
        >
          <div>
            Are you sure you want to delete this {selectedTable.slice(0, -1)}?
          </div>
        </ModalComponent>
      )}
    </div>
  );
};

export default AdminPanel;
