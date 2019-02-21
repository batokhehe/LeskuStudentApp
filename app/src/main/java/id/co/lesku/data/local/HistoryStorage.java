package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.model.History;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class HistoryStorage implements RAGEContract<History, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<History>> getList() {
        List<History> Historys = isCacheValid() ? Hawk.get(K.HISTORY_LIST, new ArrayList<History>()) : null;
        return Historys == null ? Maybe.<List<History>>empty() : Maybe.just(Historys).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<History> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<History> objs) {

    }

    @Override
    public void add(History obj) {

    }

    @Override
    public void edit(History obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
