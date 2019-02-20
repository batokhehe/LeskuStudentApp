package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.model.PaidOrder;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class PaidOrderStorage implements RAGEContract<PaidOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<PaidOrder>> getList() {
        List<PaidOrder> paidOrders = isCacheValid() ? Hawk.get(K.PAID_ORDER_LIST, new ArrayList<PaidOrder>()) : null;
        return paidOrders == null ? Maybe.<List<PaidOrder>>empty() : Maybe.just(paidOrders).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<PaidOrder> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<PaidOrder> objs) {

    }

    @Override
    public void add(PaidOrder obj) {

    }

    @Override
    public void edit(PaidOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
