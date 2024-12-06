package com.backend.allreva.artist.command;

import com.backend.allreva.artist.command.domain.Artist;
import com.backend.allreva.member.command.application.dto.MemberArtistRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtistCommandService {

    private final ArtistRepository artistRepository;

    public void saveIfNotExist(final List<MemberArtistRequest> artists) {
        List<String> ids = artists.stream()
                .map(MemberArtistRequest::spotifyArtistId)
                .toList();

        List<Artist> existingEntities = artistRepository
                .findAllById(ids);

        Set<String> existingIds = existingEntities.stream()
                .map(Artist::getId)
                .collect(Collectors.toSet());

        List<Artist> list = artists.stream()
                .filter(artist -> !existingIds.contains(artist.spotifyArtistId()))
                .map(MemberArtistRequest::to)
                .toList();

        artistRepository.saveAll(list);
    }

}
