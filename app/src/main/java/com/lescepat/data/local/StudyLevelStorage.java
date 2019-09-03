package com.lescepat.data.local;

import com.lescepat.data.local.contracts.CacheContract;
import com.lescepat.data.local.contracts.RAGEContract;
import com.lescepat.model.StudyLevel;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class StudyLevelStorage implements RAGEContract<StudyLevel, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<StudyLevel>> getList() {
        List<StudyLevel> studyLevels = isCacheValid() ? Hawk.get(K.STUDY_LEVEL_LIST, new ArrayList<StudyLevel>()) : null;
        return studyLevels == null ? Maybe.<List<StudyLevel>>empty() : Maybe.just(studyLevels).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<StudyLevel> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<StudyLevel> objs) {
        Hawk.put(K.STUDY_LEVEL_LIST, objs);
    }

    @Override
    public void add(StudyLevel obj) {

    }

    @Override
    public void edit(StudyLevel obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
