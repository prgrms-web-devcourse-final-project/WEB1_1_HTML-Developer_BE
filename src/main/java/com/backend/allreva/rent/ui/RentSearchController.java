package com.backend.allreva.rent.ui;

import com.backend.allreva.common.dto.Response;
import com.backend.allreva.rent.query.application.RentDocumentService;
import com.backend.allreva.rent.query.application.dto.RentThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/rents")
@RequiredArgsConstructor
public class RentSearchController {
    private final RentDocumentService rentDocumentService;

    @GetMapping("/")
    public Response<List<RentThumbnail>> searchRentThumbnail(@RequestParam final String query) {
        return Response.onSuccess(
                rentDocumentService.searchRentThumbnails(query)
        );
    }
}
