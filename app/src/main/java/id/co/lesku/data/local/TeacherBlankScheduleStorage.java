package id.co.lesku.data.local;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.data.local.contracts.CacheContract;
import id.co.lesku.data.local.contracts.RAGEContract;
import id.co.lesku.models.TeacherSchedule;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class TeacherBlankScheduleStorage implements RAGEContract<TeacherSchedule, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<TeacherSchedule>> getList() {
        List<TeacherSchedule> teacherSchedules = isCacheValid() ? Hawk.get(K.TEACHER_BLANK_SCHEDULE_LIST, new ArrayList<TeacherSchedule>()) : null;
        return teacherSchedules == null ? Maybe.<List<TeacherSchedule>>empty() : Maybe.just(teacherSchedules).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<TeacherSchedule> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<TeacherSchedule> objs) {

    }

    @Override
    public void add(TeacherSchedule obj) {

    }

    @Override
    public void edit(TeacherSchedule obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
