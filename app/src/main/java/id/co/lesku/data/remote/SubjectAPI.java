package id.co.lesku.data.remote;

import java.util.List;

import id.co.lesku.data.remote.contracts.CRUDContract;
import id.co.lesku.model.Subject;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class SubjectAPI extends BaseAPI implements CRUDContract<Subject, Integer> {
    @Override
    public Maybe<List<Subject>> getList() {
        return null;
    }

    @Override
    public Maybe<List<Subject>> findList(Integer studyLevelId) {
        return app.mAPIService.getSubjects(studyLevelId).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<Subject>> findListSelected(Integer id, Integer io) {
        return null;
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
