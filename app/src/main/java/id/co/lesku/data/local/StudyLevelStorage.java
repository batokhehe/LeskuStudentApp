package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.model.StudyLevel;
import id.co.lesku.utils.constants.K;
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
