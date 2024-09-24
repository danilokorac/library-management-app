package com.project.library_management_be.dto;

import com.project.library_management_be.model.Genre;
import lombok.Data;

@Data
public class BookDTO {

    private String title;
    private Genre bookGenre;
    private String starRating;
    private String stock;
    private int quantity;
}
