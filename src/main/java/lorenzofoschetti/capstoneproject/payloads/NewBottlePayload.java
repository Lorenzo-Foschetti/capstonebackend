package lorenzofoschetti.capstoneproject.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lorenzofoschetti.capstoneproject.enums.BottleCategory;

public record NewBottlePayload(
        BottleCategory bottleCategory,

        @NotEmpty
        String description,
        @NotNull
        double price,
        @NotNull
        int productionYear,
        @NotEmpty
        String name,
        @NotEmpty
        String collection

) {
}
