package com.backend.allreva.artist.exception;

import com.backend.allreva.common.exception.CustomException;

public class ArtistNotFoundException extends CustomException {

    public ArtistNotFoundException() {
        super(ArtistErrorCode.ARTIST_NOT_FOUND);
    }
}
