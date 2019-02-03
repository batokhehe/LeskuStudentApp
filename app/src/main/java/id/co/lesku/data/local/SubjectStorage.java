package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.models.Subject;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class SubjectStorage implements RAGEContract<Subject, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<Subject>> getList() {
        List<Subject> teacherOrder = isCacheValid() ? Hawk.get(K.SUBJECT_LIST, new ArrayList<Subject>()) : null;
        return teacherOrder == null ? Maybe.<List<Subject>>empty() : Maybe.just(teacherOrder).subscribeOn(Schedulers.io());
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
