package com.ecommerce.image;

import com.ecommerce.image.dto.CreateUrlRequest;
import com.ecommerce.image.dto.CreateUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/url")
    public ResponseEntity<CreateUrlResponse> createUrl(final CreateUrlRequest request){
        CreateUrlResponse response = imageService.createUrl(request);
        return ResponseEntity.ok(response);
    }
}
