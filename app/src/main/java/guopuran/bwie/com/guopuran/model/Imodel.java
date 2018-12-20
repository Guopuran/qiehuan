package guopuran.bwie.com.guopuran.model;

import java.util.Map;

import guopuran.bwie.com.guopuran.util.MyCallBack;

public interface Imodel {
    void requestmodel(String url, Map<String,String> parmas, Class clazz, MyCallBack callBack);
}
