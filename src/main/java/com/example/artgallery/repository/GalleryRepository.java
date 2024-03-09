package com.example.artgallery.repository;

import com.example.artgallery.model.*;
import java.util.*;

public interface GalleryRepository {

    ArrayList<Gallery> getGalleries();

    Gallery addGallery(Gallery gallery);

    Gallery getGalleryById(int galleryId);

    Gallery updateGallery(int galleryId, Gallery gallery);

    void deleteGallery(int galleryId);

    List<Artist> getGalleryArtists(int galleryId);
}