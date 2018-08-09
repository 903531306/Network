package demo.image.com.demonetwork;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.ys.network.base.BaseActivity;

import org.json.JSONObject;

import java.io.Serializable;

import demo.image.com.demonetwork.databinding.ActivityMainBinding;

import demo.image.com.demonetwork.presenter.impl.TestPresenter.TestView;
import demo.image.com.demonetwork.presenter.impl.TestPresenterImpl;

public class MainActivity extends BaseActivity<TestPresenterImpl,ActivityMainBinding> implements TestView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try {
           JSONObject jsonObject=new JSONObject();
           jsonObject.put("aid","深圳市");
           mPresenter.area(jsonObject,mActivity);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void succ(Serializable serializable) {

    }

    @Override
    public void error(String str) {

    }
}
