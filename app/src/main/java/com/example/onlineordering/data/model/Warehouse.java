package com.example.onlineordering.data.model;

public class Warehouse {
    private String warehouseId;
    private String warehouseName;
    private String warehouseAddress;

    public Warehouse(String warehouseId, String warehouseName, String warehouseAddress) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.warehouseAddress = warehouseAddress;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }
}
