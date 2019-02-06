package id.co.lesku.viewmodels;

import id.co.lesku.model.Product;
import id.co.lesku.viewmodels.inputs.ProductViewModelInputs;
import id.co.lesku.viewmodels.outputs.ProductViewModelOutputs;

public class ProductViewModel extends BaseViewModel implements ProductViewModelInputs, ProductViewModelOutputs {

    private Product mProduct;

    public ProductViewModel (Product product)
    {
        mProduct = product;
        notifyChange();
    }

    @Override
    public void setProduct (Product product)
    {
        mProduct = product;
        notifyChange();
    }

    @Override
    public int getId ()
    {
        return mProduct.getId();
    }

    @Override
    public String getName ()
    {
        return String.valueOf(mProduct.getName());
    }

    @Override
    public String getDescription ()
    {
        return String.valueOf(mProduct.getDescription());
    }

    @Override
    public String getImage ()
    {
        return String.valueOf(mProduct.getImage());
    }
}
