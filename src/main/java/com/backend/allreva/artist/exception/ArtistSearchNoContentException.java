package com.backend.allreva.artist.exception;

import com.backend.allreva.common.exception.CustomException;

public class ArtistSearchNoContentException extends CustomException {
    public ArtistSearchNoContentException() {
        super(ArtistErrorCode.ARTIST_SEARCH_NO_CONTENT);
    }
}
