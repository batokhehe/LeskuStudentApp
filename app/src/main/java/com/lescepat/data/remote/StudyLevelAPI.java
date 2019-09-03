package com.lescepat.data.remote;

import com.lescepat.model.StudyLevel;
import com.lescepat.utils.constants.K;

import java.util.List;

import com.lescepat.data.remote.contracts.CRUDContract;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class StudyLevelAPI extends BaseAPI implements CRUDContract<StudyLevel, Integer, String> {
    @Override
    public Maybe<List<StudyLevel>> getList() {
        return app.mAPIService.getStudyLevels().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<StudyLevel>> findList(Integer id) {
        return null;
    }

    @Override
    public Maybe<List<StudyLevel>> findListSelected(Integer id, Integer io, String s) {
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
