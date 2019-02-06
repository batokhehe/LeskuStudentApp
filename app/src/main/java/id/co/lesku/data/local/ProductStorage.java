package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.model.Product;
import id.co.lesku.utils.constants.K;
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
