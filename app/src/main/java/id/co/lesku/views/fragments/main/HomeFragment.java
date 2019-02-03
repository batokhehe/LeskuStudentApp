package id.co.lesku.views.fragments.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.lesku.R;
import id.co.lesku.data.DataManager;
import id.co.lesku.databinding.FragmentHomeBinding;
import id.co.lesku.models.Product;
import id.co.lesku.utils.RetrofitErrorAdapter;
import id.co.lesku.viewmodels.ProductListViewModel;
import id.co.lesku.views.adapters.product.ProductAdapter;
import id.co.lesku.views.fragments.BaseFragment;
import id.co.lesku.views.fragments.transaction.OrderClassFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment {
    FragmentHomeBinding mBinding;
    List<Product>       mProduct;
    private OnFragmentInteractionListener mListener;
    ProductAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
        setArguments(new Bundle());
        mProduct = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initUI() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        Toast.makeText(getContext(), "Subject : " + Hawk.get(K.SUBJECT_LIST), Toast.LENGTH_SHORT).show();

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mBinding.setProducts(new ProductListViewModel());

        adapter = new ProductAdapter(mProduct, getContext());

        mBinding.homeRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.homeRecycleView.setAdapter(adapter);

        mBinding.llHomeList.showCustomLoading(true, "Loading User List...");

        DataManager.can().getProductList().observeOn(AndroidSchedulers.mainThread())
                .defaultIfEmpty(new ArrayList<Product>())
                .subscribe(new Consumer<List<Product>>()
                {
                    @Override
                    public void accept (List<Product> products) throws Exception
                    {
                        if (mProduct != null) { mProduct.clear(); }
                        mProduct.addAll(products);
                        mBinding.homeRecycleView.getAdapter().notifyDataSetChanged();
                        mBinding.llHomeList.showCustomLoading(false);
                        if (mProduct.size() == 0)
                        {
                            mBinding.llHomeList.showEmptyView(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept (Throwable throwable) throws Exception
                    {
                        RetrofitErrorAdapter error = new RetrofitErrorAdapter(throwable);
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mBinding.llHomeList.showCustomLoading(false);
                    }
                });

        adapter.setOnClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
//                Toast.makeText(getContext(),
//                        "id : " + product.getId() +
//                        "name : " + product.getName() +
//                        "min_order : " + product.getMinOrder() +
//                        "max_order : " + product.getMaxOrder(),
//                        Toast.LENGTH_SHORT).show();
                OrderClassFragment fragment = new OrderClassFragment();
                Bundle args = new Bundle();
                args.putInt("id", product.getId());
                args.putString("name", product.getName());
                args.putInt("min_order", product.getMinOrder());
                args.putInt("max_order", product.getMaxOrder());
                args.putInt("multiple", product.getMultiple());
                fragment.setArguments(args);
                getFragmentManager().beginTransaction().
                        replace(R.id.frame, fragment).
                        addToBackStack(null).
                        commit();
            }
        });

        return mBinding.getRoot();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
