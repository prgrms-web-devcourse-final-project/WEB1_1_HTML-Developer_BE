package com.backend.allreva.artist.command;

import com.backend.allreva.artist.command.domain.Artist;
import com.backend.allreva.artist.exception.ArtistNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistCommandService {

    private final ArtistRepository artistRepository;

    public Artist getArtistById(String id) {
        return artistRepository.findById(id).orElseThrow(ArtistNotFoundException::new);
    }
}
