package com.lescepat.data.remote;

import com.lescepat.model.Product;
import com.lescepat.utils.constants.K;

import java.util.List;

import com.lescepat.data.remote.contracts.CRUDContract;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class HomeAPI extends BaseAPI implements CRUDContract<Product, Integer, String> {
    @Override
    public Maybe<List<Product>> getList ()
    {
        return app.mAPIService.getProducts().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<Product>> findList(Integer id) {
        return null;
    }

    @Override
    public Maybe<List<Product>> findListSelected(Integer id, Integer io, String s) {
        return null;
    }

    @Override
    public void create(Product obj) {

    }

    @Override
    public Maybe<Product> read(Integer id) {
        return null;
    }

    @Override
    public void update(Product obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
