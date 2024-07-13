package ru.xiitori.inventoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.xiitori.inventoryservice.model.Inventory;
import ru.xiitori.inventoryservice.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{scu-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isStock(@PathVariable("scu-code") String scuCode) {
        return inventoryService.isStock(scuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isStock(@RequestBody List<String> scuCodes) {
        for (String scuCode : scuCodes) {
            if (!inventoryService.isStock(scuCode)) {
                return false;
            }
        }

        return true;
    }
}
