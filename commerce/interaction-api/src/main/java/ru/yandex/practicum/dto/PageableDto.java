package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableDto {
    @NotNull
    @Min(0)
    Integer page;

    @NotNull
    @Min(0)
    Integer size;

    @NotNull
    @NotBlank
    String sort;
}
