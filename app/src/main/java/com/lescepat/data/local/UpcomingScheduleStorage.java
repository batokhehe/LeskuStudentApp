package com.lescepat.data.local;

import com.lescepat.data.local.contracts.CacheContract;
import com.lescepat.data.local.contracts.RAGEContract;
import com.lescepat.model.UpcomingSchedule;
import com.lescepat.utils.constants.K;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

public class UpcomingScheduleStorage implements RAGEContract<UpcomingSchedule, Integer>, CacheContract {
    @Override
    public boolean isCacheValid() {
        return false;
    }

    @Override
    public Maybe<List<UpcomingSchedule>> getList() {
        List<UpcomingSchedule> upcomingSchedules = isCacheValid() ? Hawk.get(K.UPCOMING_SCHEDULE_LIST, new ArrayList<UpcomingSchedule>()) : null;
        return upcomingSchedules == null ? Maybe.<List<UpcomingSchedule>>empty() : Maybe.just(upcomingSchedules).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<UpcomingSchedule> get(Integer id) {
        return null;
    }

    @Override
    public void addAll(List<UpcomingSchedule> objs) {

    }

    @Override
    public void add(UpcomingSchedule obj) {

    }

    @Override
    public void edit(UpcomingSchedule obj, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }
}
