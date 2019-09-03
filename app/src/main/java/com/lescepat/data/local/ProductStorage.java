package com.lescepat.data.local;

import com.lescepat.data.local.contracts.CacheContract;
import com.lescepat.data.local.contracts.RAGEContract;
import com.lescepat.model.Product;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class ProductStorage implements RAGEContract<Product, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<Product>> getList() {
        List<Product> products = isCacheValid() ? Hawk.get(K.PRODUCT_LIST, new ArrayList<Product>()) : null;
        return products == null ? Maybe.<List<Product>>empty() : Maybe.just(products).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Product> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<Product> objs) {

    }

    @Override
    public void add(Product obj) {

    }

    @Override
    public void edit(Product obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
