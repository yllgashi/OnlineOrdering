package com.example.onlineordering.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.onlineordering.api.model.Order;
import com.example.onlineordering.api.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.O)
public class StaticData {
    public static ArrayList<Product> products = new ArrayList<Product>(
            Arrays.asList(new Product("1", "Buston", "I mire", 2),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3),
                    new Product("2", "Pjeper", "I mire", 3))
    );

//    public static ArrayList<Order> arrivedOrders = new ArrayList<Order>(
//            Arrays.asList(new Order("1", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()))
//    );
//
//    public static ArrayList<Order> requestedOrders = new ArrayList<Order>(
//            Arrays.asList(new Order("3", LocalDate.now(), products, LocalDate.now()),
//                    new Order("4", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now()),
//                    new Order("2", LocalDate.now(), products, LocalDate.now())
//            ));
}
