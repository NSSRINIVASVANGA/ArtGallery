package com.example.artgallery.repository;

import com.example.artgallery.model.*;
import java.util.*;

public interface ArtistRepository {
    ArrayList<Artist> getArtists();

    Artist addArtist(Artist artist);

    Artist getArtistById(int artistId);

    Artist updateArtist(int artistId, Artist artist);

    void deleteArtist(int artistId);

    List<Gallery> getArtistGalleries(int artistId);

    List<Art> getArtistArts(int artistId);
}