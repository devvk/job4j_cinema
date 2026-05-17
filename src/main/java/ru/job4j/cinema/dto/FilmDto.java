package ru.job4j.cinema.dto;

import java.util.Objects;

public class FilmDto {

    private int id;
    private String name;
    private String description;
    private int year;
    private int minimalAge;
    private int durationInMinutes;
    private String genreName;
    private String filePath;

    public FilmDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmDto filmDto = (FilmDto) o;
        return id == filmDto.id && year == filmDto.year && minimalAge == filmDto.minimalAge
                && durationInMinutes == filmDto.durationInMinutes && Objects.equals(name, filmDto.name)
                && Objects.equals(description, filmDto.description)
                && Objects.equals(genreName, filmDto.genreName)
                && Objects.equals(filePath, filmDto.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, year, minimalAge, durationInMinutes, genreName, filePath);
    }

    @Override
    public String toString() {
        return "FilmDto{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", year=" + year
                + ", minimalAge=" + minimalAge
                + ", durationInMinutes=" + durationInMinutes
                + ", genreName='" + genreName + '\''
                + ", filePath='" + filePath + '\''
                + '}';
    }
}
