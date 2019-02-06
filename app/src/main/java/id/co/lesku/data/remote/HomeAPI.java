package id.co.lesku.data.remote;

import java.util.List;

import id.co.lesku.data.remote.contracts.CRUDContract;
import id.co.lesku.model.Product;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class HomeAPI extends BaseAPI implements CRUDContract<Product, Integer> {
    @Override
    public Maybe<List<Product>> getList ()
    {
        return app.mAPIService.getProducts().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
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
