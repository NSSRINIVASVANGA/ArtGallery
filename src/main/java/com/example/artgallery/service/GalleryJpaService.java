package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class GalleryJpaService implements GalleryRepository {
    @Autowired
    public GalleryJpaRepository galleryJpaRepository;

    @Autowired
    public ArtistJpaRepository artistJpaRepository;

    @Override
    public ArrayList<Gallery> getGalleries() {
        List<Gallery> galleriesList = galleryJpaRepository.findAll();
        ArrayList<Gallery> galleries = new ArrayList<>(galleriesList);
        return galleries;
    }

    @Override
    public Gallery addGallery(Gallery gallery) {
        try {
            List<Integer> artistIds = new ArrayList<>();
            for (Artist artist : gallery.getArtists()) {
                artistIds.add(artist.getArtistId());
            }

            List<Artist> artistsList = artistJpaRepository.findAllById(artistIds);
            gallery.setArtists(artistsList);

            for (Artist artist : artistsList) {
                artist.getGalleries().add(gallery);
            }

            Gallery savedGallery = galleryJpaRepository.save(gallery);
            artistJpaRepository.saveAll(artistsList);

            return savedGallery;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Gallery getGalleryById(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            return gallery;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Gallery updateGallery(int galleryId, Gallery gallery) {
        try {
            Gallery newGallery = galleryJpaRepository.findById(galleryId).get();
            if (gallery.getGalleryName() != null) {
                newGallery.setGalleryName(gallery.getGalleryName());
            }
            if (gallery.getLocation() != null) {
                newGallery.setLocation(gallery.getLocation());
            }
            if (gallery.getArtists() != null) {

                List<Artist> oldArtistsList = newGallery.getArtists();

                for (Artist artist : oldArtistsList) {
                    artist.getGalleries().remove(newGallery);
                }

                artistJpaRepository.saveAll(oldArtistsList);

                List<Integer> artistsIds = new ArrayList<>();

                for (Artist artist : gallery.getArtists()) {
                    artistsIds.add(artist.getArtistId());
                }

                List<Artist> newArtistsList = artistJpaRepository.findAllById(artistsIds);

                for (Artist artist : newArtistsList) {
                    artist.getGalleries().add(newGallery);
                }

                artistJpaRepository.saveAll(newArtistsList);
                newGallery.setArtists(newArtistsList);
            }
            Gallery savedGallery = galleryJpaRepository.save(newGallery);
            return savedGallery;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteGallery(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            List<Artist> artistsList = gallery.getArtists();
            for (Artist artist : artistsList) {
                artist.getGalleries().remove(gallery);
            }
            artistJpaRepository.saveAll(artistsList);
            galleryJpaRepository.deleteById(galleryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Artist> getGalleryArtists(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            return gallery.getArtists();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}