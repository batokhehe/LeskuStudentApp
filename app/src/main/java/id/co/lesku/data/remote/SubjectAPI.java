package id.co.lesku.data.remote;

import java.util.List;

import id.co.lesku.data.remote.contracts.CRUDContract;
import id.co.lesku.models.Subject;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class SubjectAPI extends BaseAPI implements CRUDContract<Subject, Integer> {
    @Override
    public Maybe<List<Subject>> getList() {
        return app.mAPIService.getSubjects().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public void create(Subject obj) {

    }

    @Override
    public Maybe<Subject> read(Integer id) {
        return null;
    }

    @Override
    public void update(Subject obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
