package guopuran.bwie.com.guopuran;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guopuran.bwie.com.guopuran.bean.Bean;
import guopuran.bwie.com.guopuran.presenter.IpresenterImpl;
import guopuran.bwie.com.guopuran.view.IView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {
    private String url="http://www.zhaoapi.cn/product/searchProducts";
    private IpresenterImpl mIpresenterImpl;
    private EditText edit_sou;
    private TextView text_change;
    private TextView text_true;
    private XRecyclerView xRecyclerView;
    private int page;
    private boolean flag=true;
    private final int ITEM_COUNT=2;
    private XrecyAdapter xrecyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page=1;
        initPresenter();
        initView();
    }

    private void initView() {
        //获取资源ID
        edit_sou = findViewById(R.id.main_edit_sou);
        text_change = findViewById(R.id.main_text_change);
        text_true = findViewById(R.id.main_text_true);
        xRecyclerView = findViewById(R.id.main_recy);
        text_change.setOnClickListener(this);
        text_true.setOnClickListener(this);
        changeView();
        //支持刷新和加载
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                initUrl();
            }

            @Override
            public void onLoadMore() {
                initUrl();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.main_text_true:
                page=1;
                initUrl();
                break;
            case R.id.main_text_change:
                List<Bean.DataBean> list = xrecyAdapter.getList();
                changeView();
                xrecyAdapter.setList(list);
                break;
            default:break;
        }
    }

    private void initUrl() {
        String edit = edit_sou.getText().toString();
        Map<String,String> params=new HashMap<>();
        params.put("keywords",edit);
        params.put("page",page+"");
        mIpresenterImpl.requestter(url,params,Bean.class);
    }

    public void changeView(){
        if (flag){
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            xRecyclerView.setLayoutManager(linearLayoutManager);
        }else{
            GridLayoutManager gridLayoutManager=new GridLayoutManager(this,ITEM_COUNT);
            gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            xRecyclerView.setLayoutManager(gridLayoutManager);
        }
        xrecyAdapter = new XrecyAdapter(this,flag);
        xRecyclerView.setAdapter(xrecyAdapter);
        xrecyAdapter.setonclick(new XrecyAdapter.onClick() {
            @Override
            public void itemonclick(int position) {
                int id = xrecyAdapter.getitem(position).getPid();
                String pid = String.valueOf(id);
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("pid",pid);
                startActivity(intent);
            }
        });
        xrecyAdapter.setonlongclick(new XrecyAdapter.onlongClick() {
            @Override
            public void itemonlongclick(View view, int position) {
                xrecyAdapter.del(view,position);
                /*ObjectAnimator animator=ObjectAnimator.ofFloat(view,"translationX",0,500);
                animator.setDuration(1000);
                animator.start();*/
            }
        });
        flag=!flag;
    }

    //互绑
    private void initPresenter() {
        mIpresenterImpl=new IpresenterImpl(this);
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIpresenterImpl.Deatch();
    }

    @Override
    public void getdata(Object object) {
        if (object instanceof Bean){
            Bean bean= (Bean) object;
            if (page==1){
                xrecyAdapter.setList(bean.getData());
            }else{
                xrecyAdapter.addList(bean.getData());
            }
            page++;
            //停止刷新和加载
            xRecyclerView.refreshComplete();
            xRecyclerView.loadMoreComplete();
        }
    }
}
