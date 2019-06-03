package id.co.lesku.data.remote;

import java.util.List;

import id.co.lesku.data.remote.contracts.CRUDContract;
import id.co.lesku.model.StudyLevel;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class StudyLevelAPI extends BaseAPI implements CRUDContract<StudyLevel, Integer> {
    @Override
    public Maybe<List<StudyLevel>> getList() {
        return app.mAPIService.getStudyLevels().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<StudyLevel>> findList(Integer id) {
        return null;
    }

    @Override
    public Maybe<List<StudyLevel>> findListSelected(Integer id, Integer io) {
        return null;
    }

    @Override
    public void create(StudyLevel obj) {

    }

    @Override
    public Maybe<StudyLevel> read(Integer id) {
        return null;
    }

    @Override
    public void update(StudyLevel obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
