package com.akhutornoy.shoppinglist.ui.createproduct_onescreen;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.activity.BaseToolbarActivity;
import com.akhutornoy.shoppinglist.ui.editproduct.fragment.EditProductFragment;
import com.akhutornoy.shoppinglist.ui.editproduct.fragment.EditProductFragmentArgs;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import timber.log.Timber;

public class CreateProductActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.title_create_product);
        initViews();
        //if need to navigate NOT to default navigation destination
        if (needEditProduct()) {
            showEditProductFragment();
        }
    }

    private void showEditProductFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host);

        String editProductName = getEditProductNameArg();

        if (editProductName == null) {
            throw new IllegalArgumentException(String.format("Need 'Product Name' to start '%s' fragment", EditProductFragment.class.getSimpleName()));
        }
        EditProductFragmentArgs args = new EditProductFragmentArgs.Builder()
                .setEditProductName(editProductName)
                .build();
        navController.navigate(R.id.editProductFragment, args.toBundle());
    }

    private boolean needEditProduct() {
        return getEditProductNameArg() != null;
    }

    @Nullable
    private String getEditProductNameArg() {
        try {
            String editProductName;
            Bundle extras = getIntent().getExtras();
            editProductName = CreateProductActivityArgs.fromBundle(extras).getEditProductName();
            return editProductName;
        } catch (Exception e) {
            Timber.d("getEditProductNameArg: EditProductName Argument wasn't passed");
            return null;
        }
    }

    @Override
    @LayoutRes
    protected int getContentViewId() {
        return R.layout.activity_create_product_one_screen;
    }

    @Override
    @IdRes
    protected int getToolbarResId() {
        return R.id.toolbar;
    }

    private void initViews() {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        menu.findItem(R.id.menu_add).setVisible(false);
        menu.findItem(R.id.menu_edit).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}
