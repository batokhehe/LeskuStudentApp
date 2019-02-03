package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.models.TeacherOrder;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class TeacherOrderStorage implements RAGEContract<TeacherOrder, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<TeacherOrder>> getList() {
        List<TeacherOrder> teacherOrder = isCacheValid() ? Hawk.get(K.TEACHER_ORDER_LIST, new ArrayList<TeacherOrder>()) : null;
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
