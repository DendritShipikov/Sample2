package com.toto.sample2.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Min;

public class BookData {

    private Long id;
    
    @NotEmpty(message = "Title can't be empty")
    private String title;

    private String authors;
    
    @Min(value = 1, message = "Number of pages can't be less than 1")
    private int pages;
    
    private int amount;

    private Long userId;

    public Long getId() { return id; }
    
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
    
    public String getAuthors() { return authors; }

    public void setAuthors(String authors) { this.authors = authors; }

    public int getPages() { return pages; }

    public void setPages(int pages) { this.pages = pages; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }
    
    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

}