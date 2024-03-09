package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ArtJpaService implements ArtRepository {
    @Autowired
    public ArtJpaRepository artJpaRepository;

    @Autowired
    public ArtistJpaRepository artistJpaRepository;

    @Override
    public ArrayList<Art> getArts() {
        List<Art> artsList = artJpaRepository.findAll();
        ArrayList<Art> arts = new ArrayList<>(artsList);
        return arts;
    }

    @Override
    public Art addArt(Art art) {
        try {
            Artist artist = art.getArtist();
            int artistId = artist.getArtistId();
            Artist newArtist = artistJpaRepository.findById(artistId).get();
            art.setArtist(newArtist);
            Art savedArt = artJpaRepository.save(art);
            return savedArt;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Art getArtById(int artId) {
        try {
            Art art = artJpaRepository.findById(artId).get();
            return art;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Art updateArt(int artId, Art art) {
        try {
            Art newArt = artJpaRepository.findById(artId).get();
            if (art.getArtTitle() != null) {
                newArt.setArtTitle(art.getArtTitle());
            }
            if (art.getTheme() != null) {
                newArt.setTheme(art.getTheme());
            }
            if (art.getArtist() != null) {
                Artist artist = art.getArtist();
                int artistId = artist.getArtistId();
                Artist newArtist = artistJpaRepository.findById(artistId).get();
                newArt.setArtist(newArtist);
            }
            Art savedArt = artJpaRepository.save(newArt);
            return savedArt;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArt(int artId) {
        try {
            artJpaRepository.deleteById(artId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Artist getArtArtist(int artId) {
        try {
            Art art = artJpaRepository.findById(artId).get();
            return art.getArtist();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}