package com.kuehnenagel.city.pojo.csv;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class CityCsvDto {

    @CsvBindByName(column = "id")
    private Long id;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "photo")
    private String photoUri;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityCsvDto that = (CityCsvDto) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CityCsvDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photoUri='" + photoUri + '\'' +
                '}';
    }
}
