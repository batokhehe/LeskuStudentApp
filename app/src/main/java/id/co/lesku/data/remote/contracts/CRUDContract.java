package id.co.lesku.data.remote.contracts;

import java.util.List;

import io.reactivex.Maybe;

public interface CRUDContract<T, U>
{
    Maybe<List<T>> getList();

    void create(T obj);

    Maybe<T> read(U id);

    void update(T obj, U id);

    void delete(U id);
}
