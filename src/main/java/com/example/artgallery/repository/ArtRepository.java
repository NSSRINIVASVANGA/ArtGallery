package com.example.artgallery.repository;

import com.example.artgallery.model.*;

import java.util.*;

public interface ArtRepository {
    ArrayList<Art> getArts();

    Art addArt(Art art);

    Art getArtById(int artId);

    Art updateArt(int artId, Art art);

    void deleteArt(int artId);

    Artist getArtArtist(int artId);
}