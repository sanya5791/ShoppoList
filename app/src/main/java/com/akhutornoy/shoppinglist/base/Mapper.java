package com.akhutornoy.shoppinglist.base;

import java.util.ArrayList;
import java.util.List;

public abstract class Mapper<S, T> {
    public List<T> map(List<S> source){
        if (source == null) throw new NullPointerException();

        List<T> convertedList = new ArrayList<T>();
        for (S sourceItem : source) {
            convertedList.add(map(sourceItem));
        }

        return convertedList;
    }

    public List<S> mapInverse(List<T> source){
        if (source == null) throw new NullPointerException();

        List<S> convertedList = new ArrayList<S>();
        for (T sourceItem : source) {
            convertedList.add(mapInverse(sourceItem));
        }

        return convertedList;
    }

    public abstract T map(S source);

    public abstract S mapInverse(T source);
}
