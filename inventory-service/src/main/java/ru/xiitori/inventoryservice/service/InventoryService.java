package ru.xiitori.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xiitori.inventoryservice.model.Inventory;
import ru.xiitori.inventoryservice.repository.InventoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isStock(String scuCode) {
        Optional<Inventory> optional = inventoryRepository.findByScuCode(scuCode);
        return optional.isPresent();
    }
}
