package com.popcornpalace.moviebookingsystem.util.response;

public record MovieSummary(Long id, String title, String genre, int releaseYear, double rating) {}
