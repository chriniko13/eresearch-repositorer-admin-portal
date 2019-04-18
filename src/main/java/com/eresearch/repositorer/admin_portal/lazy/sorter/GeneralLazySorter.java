package com.eresearch.repositorer.admin_portal.lazy.sorter;

import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.Comparator;

public class GeneralLazySorter<T> implements Comparator<T> {

    private String sortField;
    private SortOrder sortOrder;

    public GeneralLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(T t1, T t2) {
        try {
            Field declaredField = t1.getClass().getDeclaredField(this.sortField);
            declaredField.setAccessible(true);

            Object value1 = declaredField.get(t1);
            Object value2 = declaredField.get(t2);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
}