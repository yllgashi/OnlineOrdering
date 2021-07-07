package com.example.onlineordering.data;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.onlineordering.data.model.Order;
import com.example.onlineordering.data.model.Product;
import com.example.onlineordering.data.model.Warehouse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@RequiresApi(api = Build.VERSION_CODES.O)
public class StaticData {
    public static ArrayList<Product> products = new ArrayList<Product>(
            Arrays.asList(new Product("1", "Buston", "I mire", 2),
                    new Product("2", "Pjeper", "I mire", 3))
    );

    public static ArrayList<Warehouse> warehouses = new ArrayList<Warehouse>(
            Arrays.asList(new Warehouse("1", "Center", "Prishtine"))
    );

    public static ArrayList<Order> arrivedOrders = new ArrayList<Order>(
            Arrays.asList(new Order("1", LocalDate.now(), products, LocalDate.now(), warehouses.get(0)),
                    new Order("2", LocalDate.now(), products, LocalDate.now(), warehouses.get(0)))
    );

    public static ArrayList<Order> requestedOrders = new ArrayList<Order>(
            Arrays.asList(new Order("3", LocalDate.now(), products, LocalDate.now(), warehouses.get(0)),
                    new Order("4", LocalDate.now(), products, LocalDate.now(), warehouses.get(0)))
    );
}
