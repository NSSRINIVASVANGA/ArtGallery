package com.example.artgallery.repository;

import com.example.artgallery.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface GalleryJpaRepository extends JpaRepository<Gallery, Integer> {

}