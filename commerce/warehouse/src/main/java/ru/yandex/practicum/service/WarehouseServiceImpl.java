package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.cart.CartDto;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.WarehouseDto;
import ru.yandex.practicum.entity.Dimension;
import ru.yandex.practicum.entity.Warehouse;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.repository.DimensionRepository;
import ru.yandex.practicum.repository.WarehouseRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WarehouseServiceImpl implements WarehouseService {
    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    private final WarehouseRepository warehouseRepository;
    private final DimensionRepository dimensionRepository;

    private Warehouse getWarehouseByProductId(String productId) {
        return warehouseRepository.findByProductId(productId).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    @Transactional
    public WarehouseDto create(NewProductInWarehouseRequest body) {
        Dimension dimension = Dimension.builder()
                .width(body.getDimension().getWidth())
                .height(body.getDimension().getHeight())
                .depth(body.getDimension().getDepth())
                .build();

        Dimension savedDimension = dimensionRepository.save(dimension);

        Warehouse warehouse = WarehouseMapper.INSTANCE.newProductInWarehouseRequestToWarehouse(body);
        warehouse.setDimension(savedDimension);
        return WarehouseMapper.INSTANCE.warehouseToWarehouseDto(warehouseRepository.save(warehouse));
    }

    @Override
    @Transactional
    public BookedProductsDto check(CartDto body) {
        List<Warehouse> products = warehouseRepository.findByProductIdIn(body.getProducts().keySet());

        if (products.isEmpty()) {
            throw new ProductInShoppingCartLowQuantityInWarehouse("Product not found");
        }

        float deliveryWeight = 0F;
        float deliveryVolume = 0F;
        boolean fragile = false;

        for (Warehouse product : products) {
            if (body.getProducts().get(product.getProductId()) > product.getQuantity()) {
                throw new ProductInShoppingCartLowQuantityInWarehouse("Product with id " + product.getProductId() + " is low quantity");
            }
            deliveryWeight += product.getWeight() * body.getProducts().get(product.getProductId());
            deliveryVolume += (product.getDimension().getWidth() * product.getDimension().getHeight() * product.getDimension().getDepth()) * body.getProducts().get(product.getProductId());
            if (Boolean.TRUE.equals(product.getFragile())) {
                fragile = true;
            }
        }

        return new BookedProductsDto(deliveryWeight, deliveryVolume, fragile);
    }

    @Override
    @Transactional
    public void add(AddProductToWarehouseRequest body) {
        Warehouse found = getWarehouseByProductId(body.getProductId());
        found.setQuantity(found.getQuantity() + body.getQuantity());
        warehouseRepository.save(found);
    }

    @Override
    public AddressDto getAddress() {
        return AddressDto.builder()
                .country(CURRENT_ADDRESS)
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }
}
