package com.lescepat.data.local;

import com.lescepat.data.local.contracts.CacheContract;
import com.lescepat.data.local.contracts.RAGEContract;
import com.lescepat.model.TeacherOrder;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class TeacherOrderStorage implements RAGEContract<TeacherOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<TeacherOrder>> getList() {
        List<TeacherOrder> teacherOrder = isCacheValid() ? Hawk.get(K.TEACHER_BLANK_SCHEDULE_LIST, new ArrayList<TeacherOrder>()) : null;
        return teacherOrder == null ? Maybe.<List<TeacherOrder>>empty() : Maybe.just(teacherOrder).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<TeacherOrder> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<TeacherOrder> objs) {

    }

    @Override
    public void add(TeacherOrder obj) {

    }

    @Override
    public void edit(TeacherOrder obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
