package com.lescepat.data.remote;

import com.lescepat.model.TeacherOrder;
import com.lescepat.utils.constants.K;

import java.util.List;

import com.lescepat.data.remote.contracts.CRUDContract;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class TeacherOrderAPI extends BaseAPI implements CRUDContract<TeacherOrder, Integer, String> {
    @Override
    public Maybe<List<TeacherOrder>> getList() {
        return null;
    }

    @Override
    public Maybe<List<TeacherOrder>> findList(Integer id) {
        return null;
    }

    @Override
    public Maybe<List<TeacherOrder>> findListSelected(Integer subjectId, Integer studyLevel, String schedule) {
        return app.mAPIService.getTeachersOrder(subjectId, studyLevel, schedule).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
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
