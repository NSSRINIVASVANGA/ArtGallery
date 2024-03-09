package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ArtistJpaService implements ArtistRepository {
    @Autowired
    public ArtistJpaRepository artistJpaRepository;

    @Autowired
    public GalleryJpaRepository galleryJpaRepository;

    @Autowired
    public ArtJpaRepository artJpaRepository;

    @Override
    public ArrayList<Artist> getArtists() {
        List<Artist> artistsList = artistJpaRepository.findAll();
        ArrayList<Artist> artists = new ArrayList<>(artistsList);
        return artists;
    }

    @Override
    public Artist addArtist(Artist artist) {
        try {
            List<Integer> galleryIds = new ArrayList<>();

            for (Gallery gallery : artist.getGalleries()) {
                galleryIds.add(gallery.getGalleryId());
            }

            List<Gallery> newGalleriesList = galleryJpaRepository.findAllById(galleryIds);

            if (galleryIds.size() != newGalleriesList.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            artist.setGalleries(newGalleriesList);

            return artistJpaRepository.save(artist);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist getArtistById(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist updateArtist(int artistId, Artist artist) {
        try {
            Artist newArtist = artistJpaRepository.findById(artistId).get();
            if (artist.getArtistName() != null) {
                newArtist.setArtistName(artist.getArtistName());
            }
            if (artist.getGenre() != null) {
                newArtist.setGenre(artist.getGenre());
            }
            if (artist.getGalleries() != null) {
                List<Integer> galleryIds = new ArrayList<>();
                for (Gallery gallery : artist.getGalleries()) {
                    galleryIds.add(gallery.getGalleryId());
                }

                List<Gallery> newGalleriesList = galleryJpaRepository.findAllById(galleryIds);

                if (newGalleriesList.size() != galleryIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                newArtist.setGalleries(newGalleriesList);

            }
            return artistJpaRepository.save(newArtist);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArtist(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            List<Art> arts = artJpaRepository.findByArtist(artist);
            for (Art art : arts) {
                art.setArtist(null);
            }
            artistJpaRepository.deleteById(artistId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Gallery> getArtistGalleries(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artist.getGalleries();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Art> getArtistArts(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artJpaRepository.findByArtist(artist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}