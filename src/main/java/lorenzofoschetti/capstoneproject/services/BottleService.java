package lorenzofoschetti.capstoneproject.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lorenzofoschetti.capstoneproject.entities.Bottle;
import lorenzofoschetti.capstoneproject.enums.BottleCategory;
import lorenzofoschetti.capstoneproject.exceptions.NotFoundException;
import lorenzofoschetti.capstoneproject.payloads.NewBottlePayload;
import lorenzofoschetti.capstoneproject.repositories.BottleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service

public class BottleService {

    @Autowired
    private BottleRepository bottleRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Bottle> getBottles(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return bottleRepository.findAll(pageable);
    }

    public Bottle save(NewBottlePayload body) {


        Bottle newBottle = new Bottle(body.bottleCategory(), body.description(), body.price(), body.productionYear(), body.name(), body.collection());
        newBottle.setUrlImage("https://drscdn.500px.org/photo/1059039074/m%3D900/v2?sig=537c65769aa761111e66850cfb2e525e1569bd5f67cf2fb77771cd876aae9414");

        return bottleRepository.save(newBottle);
    }

    public Bottle findById(UUID bottleId) {
        return this.bottleRepository.findById(bottleId).orElseThrow(() -> new NotFoundException(bottleId));
    }


    public Bottle findByIdAndUpdate(UUID userId, NewBottlePayload modifiedBottle) {
        Bottle found = this.findById(userId);
        found.setName(modifiedBottle.name());


        found.setCollection(modifiedBottle.collection());
        found.setPrice(modifiedBottle.price());
        found.setDescription(modifiedBottle.description());
        found.setProductionYear(modifiedBottle.productionYear());


        return this.bottleRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        Bottle found = this.findById(userId);
        this.bottleRepository.delete(found);
    }

    public Page<Bottle> filterByBottleCategory(int page, int size, BottleCategory bottleCategory) {
        Pageable pageable = PageRequest.of(page, size);
        BottleCategory bottleCategory1 = BottleCategory.VINOROSSO;
        switch (bottleCategory) {

            case VINOBIANCO: {
                bottleCategory1 = BottleCategory.VINOBIANCO;
                break;
            }
            case SPUMANTE: {
                bottleCategory1 = BottleCategory.SPUMANTE;
                break;
            }
            case CHAMPAGNE: {
                bottleCategory1 = BottleCategory.CHAMPAGNE;
                break;
            }
            case VINONATURALE: {
                bottleCategory1 = BottleCategory.VINONATURALE;
                break;
            }
            default:
                break;
        }
        return bottleRepository.filterByBottleCategory(bottleCategory1, pageable);
    }

    public String uploadAvatar(UUID id, MultipartFile file) throws IOException {
        Bottle bottle = findById(id);
        String imageUrl = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        bottle.setUrlImage(imageUrl);
        bottleRepository.save(bottle);
        return "immagine salvata!";
    }


}
