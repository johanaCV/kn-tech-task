package com.kuehnenagel.city.util.photo;

public interface PhotoConverterStrategy<T> {

    /**
     * Converts the photo to a specific format
     * i.e converts the photo itself to a byte array or convert the encoded photo's url to a decoded url
     * @param photoUrl encoded url pointing to an image/photo
     * @return the reference to the photo or the photo itself, converted
     */
    T convert(String photoUrl);

}
