package lorenzofoschetti.capstoneproject.controllers;

import lorenzofoschetti.capstoneproject.entities.Bottle;
import lorenzofoschetti.capstoneproject.enums.BottleCategory;
import lorenzofoschetti.capstoneproject.exceptions.BadRequestException;
import lorenzofoschetti.capstoneproject.payloads.NewBottlePayload;
import lorenzofoschetti.capstoneproject.services.BottleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/bottles")
public class BottleController {
    @Autowired
    private BottleService bottleService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Bottle> getAllBottles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestParam(defaultValue = "name") String sortBy) {
        return this.bottleService.getBottles(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Bottle saveBottle(@RequestBody @Validated NewBottlePayload body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return (this.bottleService.save(body));
    }

    @GetMapping("/{bottleId}")

    public Bottle findById(@PathVariable UUID bottleId) {
        return this.bottleService.findById(bottleId);
    }

    @PatchMapping("/{bottleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Bottle findByIdAndUpdate(@PathVariable UUID bottleId, @RequestBody NewBottlePayload body) {
        return this.bottleService.findByIdAndUpdate(bottleId, body);
    }

    @DeleteMapping("/{bottleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID bottleId) {
        this.bottleService.findByIdAndDelete(bottleId);
    }

    @GetMapping("/bottlecategory/{bottleCategory}")
    Page<Bottle> filterByName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "30") int size, @PathVariable BottleCategory bottleCategory) {
        return bottleService.filterByBottleCategory(page, size, bottleCategory);
    }

    @PatchMapping("/{bottleId}/avatar")
    public ResponseEntity<Object> uploadAvatar(@PathVariable UUID bottleId, @RequestParam("avatar") MultipartFile image) throws IOException {
        try {
            String response = bottleService.uploadAvatar(bottleId, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", response));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }

    }

}
