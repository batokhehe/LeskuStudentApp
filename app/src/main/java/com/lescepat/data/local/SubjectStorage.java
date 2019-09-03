package com.lescepat.data.local;

import com.lescepat.data.local.contracts.CacheContract;
import com.lescepat.data.local.contracts.RAGEContract;
import com.lescepat.model.Subject;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class SubjectStorage implements RAGEContract<Subject, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<Subject>> getList() {
        List<Subject> subjects = isCacheValid() ? Hawk.get(K.SUBJECT_LIST, new ArrayList<Subject>()) : null;
        return subjects == null ? Maybe.<List<Subject>>empty() : Maybe.just(subjects).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Subject> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<Subject> objs) {
        Hawk.put(K.SUBJECT_LIST, objs);
    }

    @Override
    public void add(Subject obj) {

    }

    @Override
    public void edit(Subject obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
