package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.yandex.practicum.dto.product.CreateUpdateProductDto;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    Product productToCreateUpdateProductDto(CreateUpdateProductDto product);

    List<ProductDto> productsToProductDtoItems(List<Product> products);
}
