package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DimensionDto {
    @NotNull
    @Min(1)
    private Float width;

    @NotNull
    @Min(1)
    private Float height;

    @NotNull
    @Min(1)
    private Float depth;
}
