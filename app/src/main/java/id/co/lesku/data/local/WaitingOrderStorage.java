package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.model.WaitingOrder;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class WaitingOrderStorage implements RAGEContract<WaitingOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<WaitingOrder>> getList() {
        List<WaitingOrder> waitingOrders = isCacheValid() ? Hawk.get(K.WAITING_ORDER_LIST, new ArrayList<WaitingOrder>()) : null;
        return waitingOrders == null ? Maybe.<List<WaitingOrder>>empty() : Maybe.just(waitingOrders).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<WaitingOrder> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<WaitingOrder> objs) {

    }

    @Override
    public void add(WaitingOrder obj) {

    }

    @Override
    public void edit(WaitingOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
