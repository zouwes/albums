package com.daw.swapp.controller.albums;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.daw.swapp.model.albums.AddAlbumRequest;
import com.daw.swapp.model.albums.Album;
import com.daw.swapp.service.albums.AlbumsService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlbumsController {

    @Autowired
    private AlbumsService albumsService;

    @GetMapping("/albums")
    public String showAlbums(@RequestParam(required = false) String artists,
                             @RequestParam(required = false) Integer minDuration,
                             @RequestParam(required = false) Integer maxDuration,
                             Model model) {
        List<Album> albums = albumsService.listAlbumsByQuery(artists, minDuration, maxDuration);
        albumsService.setAlbumModel(model, albums, artists, minDuration, maxDuration);
    
        return "albums/home";
    }
    

    @GetMapping("/albums/add")
    public String showAddAlbumForm() {
        return "albums/addAlbum";
    }

    @PostMapping("/albums/addjson")
    public ResponseEntity<Object> addAlbumJson(@RequestBody AddAlbumRequest albumRequest) {
        try {
            return ResponseEntity.ok(albumsService.addAlbumJson(albumRequest));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding album: " + e.getMessage());
        }
    }

    @PostMapping("/albums/add")
    public ResponseEntity<Object> addAlbum(@RequestBody Album albumRequest) {
        try {
            return ResponseEntity.ok(albumsService.addAlbum(albumRequest));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding album: " + e.getMessage());
        }
    }

    @DeleteMapping("/albums/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable String id) {
        boolean deleted = albumsService.deleteAlbum(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
