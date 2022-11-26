package com.kuehnenagel.city.entity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name="city")
public class City {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, name = "name", columnDefinition="VARCHAR(100)")
    private String name;

    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    private Byte[] photo;

    @Lob
    @Column(name = "photo_uri", columnDefinition="CLOB")
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

    public Byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(Byte[] photo) {
        this.photo = photo;
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
        City city = (City) o;
        return id.equals(city.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", photoUri='" + photoUri + '\'' +
                '}';
    }
}
