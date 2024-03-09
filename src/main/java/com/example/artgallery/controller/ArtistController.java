package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;
import com.example.artgallery.service.*;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class ArtistController {
    @Autowired
    public ArtistJpaService artistJpaService;

    @GetMapping("/galleries/artists")
    public ArrayList<Artist> getArtists() {
        return artistJpaService.getArtists();
    }

    @PostMapping("/galleries/artists")
    public Artist addArtist(@RequestBody Artist artist) {
        return artistJpaService.addArtist(artist);
    }

    @GetMapping("/galleries/artists/{artistId}")
    public Artist getArtistById(@PathVariable("artistId") int artistId) {
        return artistJpaService.getArtistById(artistId);
    }

    @PutMapping("/galleries/artists/{artistId}")
    public Artist updateArtist(@PathVariable("artistId") int artistId, @RequestBody Artist artist) {
        return artistJpaService.updateArtist(artistId, artist);
    }

    @DeleteMapping("/galleries/artists/{artistId}")
    public void deleteArtist(@PathVariable("artistId") int artistId) {
        artistJpaService.deleteArtist(artistId);
    }

    @GetMapping("/artists/{artistId}/galleries")
    public List<Gallery> getArtistGalleries(@PathVariable("artistId") int artistId) {
        return artistJpaService.getArtistGalleries(artistId);
    }

    @GetMapping("/artists/{artistId}/arts")
    public List<Art> getArtistArts(@PathVariable("artistId") int artistId) {
        return artistJpaService.getArtistArts(artistId);
    }
}