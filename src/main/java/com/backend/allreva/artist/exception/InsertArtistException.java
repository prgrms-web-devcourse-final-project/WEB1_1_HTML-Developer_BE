package com.backend.allreva.artist.exception;

import com.backend.allreva.common.exception.CustomException;

public class InsertArtistException extends CustomException {
    public InsertArtistException() {
        super(ArtistErrorCode.ADD_ARTIST_FAIL);
    }
}
