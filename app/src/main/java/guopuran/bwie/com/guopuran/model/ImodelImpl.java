package guopuran.bwie.com.guopuran.model;

import java.util.Map;

import guopuran.bwie.com.guopuran.util.ICallBack;
import guopuran.bwie.com.guopuran.util.MyCallBack;
import guopuran.bwie.com.guopuran.util.OkUtil;

public class ImodelImpl implements Imodel{
    @Override
    public void requestmodel(String url, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkUtil.getInstance().postenqueue(url, params, clazz, new ICallBack() {
            @Override
            public void success(Object object) {
                callBack.getdata(object);
            }

            @Override
            public void falied(Exception e) {

            }
        });
    }
}
