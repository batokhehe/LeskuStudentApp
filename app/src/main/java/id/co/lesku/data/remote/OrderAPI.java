package id.co.lesku.data.remote;

import com.google.gson.JsonObject;

import java.util.List;

import id.co.lesku.data.remote.contracts.OrderClass;
import id.co.lesku.models.DetailsOrder;
import id.co.lesku.models.UnpaidOrder;
import id.co.lesku.utils.constants.K;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class OrderAPI extends BaseAPI implements OrderClass {
    @Override
    public Maybe<ResponseBody> store(RequestBody object) {
        return app.mAPIService.addOrderClass(object).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<UnpaidOrder>> getUnpaidOrderList() {
        return app.mAPIService.getUnpaidOrderList().retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<List<DetailsOrder>> getDetailsOrderList(String studyClassId) {
        return app.mAPIService.getDetailsOrder(studyClassId).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<JsonObject> uploadTrfFile(String id, String trfFile) {
        return app.mAPIService.uploadTrfFile(id, trfFile).retry(K.MAX_RETRIES).subscribeOn(Schedulers.io());
    }
}
