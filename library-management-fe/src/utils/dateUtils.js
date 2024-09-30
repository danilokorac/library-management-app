import moment from "moment/moment";

/**
 * Format date to a given format.
 *
 * @param {Date | string} date - The date to be formatted.
 * @param {string} format - The format in which to return the date.
 * @returns {string} - The formatted date string.
 *
 */
export const formatDate = (date, format = "DD-MM-YYYY") => {
  if (!date) return "Invalid Date";

  return moment(date).format(format);
};

/**
 * Example function to format a date to a readable format like 'DD MMMM YYYY'.
 *
 * @param {Date | string} date - The date to format.
 * @returns {string} - The formatted date string.
 */
export const formatToReadableDate = (date) => {
  return formatDate(date, "DD MMMM YYYY"); // Example: "27 September 2024"
};
