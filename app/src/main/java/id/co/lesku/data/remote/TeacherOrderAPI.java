package id.co.lesku.data.remote;

import java.util.List;

import id.co.lesku.data.remote.contracts.CRUDContract;
import id.co.lesku.model.TeacherOrder;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class TeacherOrderAPI extends BaseAPI implements CRUDContract<TeacherOrder, Integer> {
    @Override
    public Maybe<List<TeacherOrder>> getList() {
        return app.mAPIService.getTeachersOrder().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public void create(TeacherOrder obj) {

    }

    @Override
    public Maybe<TeacherOrder> read(Integer id) {
        return null;
    }

    @Override
    public void update(TeacherOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
