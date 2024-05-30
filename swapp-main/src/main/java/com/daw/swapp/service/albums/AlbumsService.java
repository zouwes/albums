package com.daw.swapp.service.albums;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.daw.swapp.model.albums.AddAlbumRequest;
import com.daw.swapp.model.albums.Album;
import com.daw.swapp.repository.albums.AlbumsRepository;

@Service
public class AlbumsService {

    @Autowired
    private AlbumsRepository albumsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Album> listAlbumsByCustomQuery(String artist, Integer minDuration, Integer maxDuration) {
        Integer minDurationParam = Optional.ofNullable(minDuration).orElse(0);
        Integer maxDurationParam = Optional.ofNullable(maxDuration).orElse(Integer.MAX_VALUE);

        Query query = new Query();

        if (artist != null && !artist.isEmpty()) {
            query.addCriteria(Criteria.where("artists").regex(artist, "i"));
        }
        if (minDuration != null || maxDuration != null) {
            query.addCriteria(Criteria.where("duration").gte(minDurationParam).lte(maxDurationParam));
        }

        return mongoTemplate.find(query, Album.class);
    }

    public List<Album> listAlbumsByQuery(String artist, Integer minDuration, Integer maxDuration) {
        Integer minDurationParam = Optional.ofNullable(minDuration).orElse(0);
        Integer maxDurationParam = Optional.ofNullable(maxDuration).orElse(Integer.MAX_VALUE);

        if (artist != null && (minDuration != null || maxDuration != null)) {
            return albumsRepository.findByArtistsContainingIgnoreCaseAndDurationBetween(artist,
                    minDurationParam, maxDurationParam);
        } else if (artist != null && minDuration == null && maxDuration == null) {
            return albumsRepository.findByArtistsContainingIgnoreCase(artist);
        } else if (minDuration != null || maxDuration != null) {
            return albumsRepository.findByDurationBetween(minDurationParam, maxDurationParam);
        } else {
            return albumsRepository.findAll();
        }
    }

    public List<Album> listAlbums() {
        return albumsRepository.findAll();
    }

    public List<Album> listAlbumsByArtistContains(String artist) {
        return albumsRepository.findByArtistsContainingIgnoreCase(artist);
    }

    public List<Album> listAlbumsByDurationRange(int minDuration, int maxDuration) {
        return albumsRepository.findByDurationBetween(minDuration, maxDuration);
    }

    public Document addAlbumJson(AddAlbumRequest albumRequest) {
        Document document = Document.parse(albumRequest.getAlbumInfo());
        return mongoTemplate.insert(document, "albums");
    }

    public Album addAlbum(Album albumRequest) {
        return albumsRepository.save(albumRequest);
    }

    public void setAlbumModel(Model model, List<Album> albums, String artist, Integer minDuration,
            Integer maxDuration) {
        model.addAttribute("albums", albums);
        if (artist != null && !artist.isEmpty()) {
            model.addAttribute("searchArtist", artist);
        }
        if (minDuration != null) {
            model.addAttribute("searchMinDuration", minDuration);
        }
        if (maxDuration != null) {
            model.addAttribute("searchMaxDuration", maxDuration);
        }
    }

    public boolean deleteAlbum(String id) {
        if (albumsRepository.existsById(id)) {
            albumsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
