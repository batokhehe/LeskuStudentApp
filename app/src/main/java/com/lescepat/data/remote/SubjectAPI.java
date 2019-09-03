package com.lescepat.data.remote;

import com.lescepat.model.Subject;
import com.lescepat.utils.constants.K;

import java.util.List;

import com.lescepat.data.remote.contracts.CRUDContract;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class SubjectAPI extends BaseAPI implements CRUDContract<Subject, Integer, String> {
    @Override
    public Maybe<List<Subject>> getList() {
        return null;
    }

    @Override
    public Maybe<List<Subject>> findList(Integer studyLevelId) {
        return app.mAPIService.getSubjects(studyLevelId).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<Subject>> findListSelected(Integer id, Integer io, String s) {
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
