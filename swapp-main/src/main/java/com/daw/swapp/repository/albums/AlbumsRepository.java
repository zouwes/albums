package com.daw.swapp.repository.albums;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.daw.swapp.model.albums.Album;

public interface AlbumsRepository extends MongoRepository<Album, String> {
    List<Album> findByArtistsContainingIgnoreCase(String artist);

    List<Album> findByDurationBetween(int minDuration, int maxDuration);

    List<Album> findByArtistsContainingIgnoreCaseAndDurationBetween(
            String artist, Integer minDuration, Integer maxDuration);
}
