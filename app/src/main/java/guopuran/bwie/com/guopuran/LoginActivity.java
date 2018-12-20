package guopuran.bwie.com.guopuran;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import guopuran.bwie.com.guopuran.bean.XiangBean;
import guopuran.bwie.com.guopuran.presenter.IpresenterImpl;
import guopuran.bwie.com.guopuran.view.IView;

import static guopuran.bwie.com.guopuran.R.id.banner;
import static guopuran.bwie.com.guopuran.R.id.image;

public class LoginActivity extends AppCompatActivity implements IView {
    private String url="http://www.zhaoapi.cn/product/getProductDetail";
    private IpresenterImpl mIpresenterImpl;
    private Banner banner;
    private TextView title;
    private TextView price;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPresenter();
        initView();
    }

    private void initView() {
        //获取资源ID
        banner = findViewById(R.id.banner);
        title = findViewById(R.id.login_title);
        price = findViewById(R.id.login_price);
        banner.isAutoPlay(false);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //展示
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        initUrl();
    }

    private void initUrl() {
        Intent intent=getIntent();
        String pid = intent.getStringExtra("pid");
        Map<String,String> params=new HashMap<>();
        params.put("pid",pid);
        mIpresenterImpl.requestter(url,params,XiangBean.class);
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
        if (object instanceof XiangBean){
            XiangBean bean= (XiangBean) object;
            XiangBean.DataBean data = bean.getData();
            list = new ArrayList<>();
            subString(data.getImages());
            banner.setImages(list);
            banner.start();
            title.setText(data.getTitle());
            price.setText("￥"+data.getPrice());
        }
    }

    private void subString(String images) {
        int index = images.indexOf("|");
        if (index>=0){
            String substring = images.substring(0, index);
            list.add(substring);
            subString(images.substring(index+1,images.length()));
        }else{
            list.add(images);
        }
    }
}
