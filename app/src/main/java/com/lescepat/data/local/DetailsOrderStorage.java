package com.lescepat.data.local;

import com.lescepat.data.local.contracts.CacheContract;
import com.lescepat.data.local.contracts.RAGEContract;
import com.lescepat.model.DetailsOrder;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class DetailsOrderStorage implements RAGEContract<DetailsOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<DetailsOrder>> getList() {
        List<DetailsOrder> detailsOrder = isCacheValid() ? Hawk.get(K.DETAILS_ORDER_LIST, new ArrayList<DetailsOrder>()) : null;
        return detailsOrder == null ? Maybe.<List<DetailsOrder>>empty() : Maybe.just(detailsOrder).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<DetailsOrder> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<DetailsOrder> objs) {

    }

    @Override
    public void add(DetailsOrder obj) {

    }

    @Override
    public void edit(DetailsOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
