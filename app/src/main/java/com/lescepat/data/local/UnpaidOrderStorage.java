package com.lescepat.data.local;

import com.lescepat.data.local.contracts.CacheContract;
import com.lescepat.data.local.contracts.RAGEContract;
import com.lescepat.model.UnpaidOrder;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class UnpaidOrderStorage implements RAGEContract<UnpaidOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<UnpaidOrder>> getList() {
        List<UnpaidOrder> unpaidOrders = isCacheValid() ? Hawk.get(K.UNPAID_ORDER_LIST, new ArrayList<UnpaidOrder>()) : null;
        return unpaidOrders == null ? Maybe.<List<UnpaidOrder>>empty() : Maybe.just(unpaidOrders).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<UnpaidOrder> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<UnpaidOrder> objs) {

    }

    @Override
    public void add(UnpaidOrder obj) {

    }

    @Override
    public void edit(UnpaidOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
