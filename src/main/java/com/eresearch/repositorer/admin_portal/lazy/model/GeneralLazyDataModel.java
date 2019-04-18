package com.eresearch.repositorer.admin_portal.lazy.model;

import com.eresearch.repositorer.admin_portal.lazy.sorter.GeneralLazySorter;
import lombok.Getter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Getter
public class GeneralLazyDataModel<T> extends LazyDataModel<T> {

    // Note: this in a real life application should be a persistence storage, such as a database.
    private final List<T> datasource;
    private final Field idField;

    public GeneralLazyDataModel(List<T> datasource, Field idField) {
        this.datasource = datasource;
        this.idField = idField;
        idField.setAccessible(true);
    }

    @Override
    public T getRowData(String rowKey) {
        for (T elem : datasource) {
            try {
                if (idField.get(elem).equals(rowKey)) {
                    return elem;
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("GeneralLazyDataModel#getRowData");
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(T t) {
        try {
            return idField.get(t);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("GeneralLazyDataModel#getRowKey");
        }
    }

    @Override
    public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        final List<T> data = new ArrayList<>();

        filter(filters, data);

        if (multiSortMeta != null && !multiSortMeta.isEmpty()) {
            sort(multiSortMeta, data);
        }

        return paginate(first, pageSize, data);
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        final List<T> data = new ArrayList<>();

        filter(filters, data);

        if (sortField != null) {
            sort(sortField, sortOrder, data);
        }

        return paginate(first, pageSize, data);
    }

    private void filter(Map<String, Object> filters, List<T> data) {

        for (T elem : datasource) {
            boolean match = true;

            if (filters != null && !filters.isEmpty()) {

                for (final String filterProperty : filters.keySet()) {

                    try {
                        final Object filterValue = filters.get(filterProperty);

                        Field declaredField = elem.getClass().getDeclaredField(filterProperty);
                        declaredField.setAccessible(true);

                        String fieldValue = String.valueOf(declaredField.get(elem));

                        if (filterValue == null || fieldValue.contains(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }

                    } catch (Exception e) {
                        match = false;

                        if (e instanceof NoSuchFieldException) {
                            if (e.getMessage().equals("globalFilter")) {
                                match = true;
                            }
                        }
                    }
                }

            }

            if (match) {
                data.add(elem);
            }
        }
    }

    private void sort(String sortField, SortOrder sortOrder, List<T> data) {
        data.sort(new GeneralLazySorter<T>(sortField, sortOrder));
    }

    private void sort(List<SortMeta> multiSortMeta, List<T> data) {

        Comparator<T> aggregatedComparator = null;

        for (SortMeta sortMeta : multiSortMeta) {

            if (aggregatedComparator == null)
                aggregatedComparator = new GeneralLazySorter<T>(sortMeta.getSortField(), sortMeta.getSortOrder());
            else
                aggregatedComparator.thenComparing(new GeneralLazySorter<T>(sortMeta.getSortField(), sortMeta.getSortOrder()));
        }

        data.sort(aggregatedComparator);
    }

    private List<T> paginate(int first, int pageSize, List<T> data) {
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }

}
