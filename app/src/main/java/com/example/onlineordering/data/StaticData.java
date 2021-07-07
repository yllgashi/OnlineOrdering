package com.example.onlineordering.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onlineordering.data.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class StaticData {
    public static ArrayList<Product> products = new ArrayList<Product>(
            Arrays.asList(new Product("1", "Buston", "I mire", 2),
                    new Product("2", "Pjeper", "I mire", 3))
    );
}
