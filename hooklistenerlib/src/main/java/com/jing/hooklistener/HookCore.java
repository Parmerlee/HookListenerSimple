package com.jing.hooklistener;

import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.jing.hooklistenerlib.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wulei
 * Data: 2016/10/18.
 */

public class HookCore {
    private ListenerManager mListenerManager;

    private HookCore() {
    }

    private static class SingleHolder {
        public static final HookCore INSTANCE = new HookCore();
    }

    public static HookCore getInstance() {
        return SingleHolder.INSTANCE;
    }


    /**
     * 入口函数
     *
     * @param activity
     */
    public void startHook(Activity activity, ListenerManager listenerManager) {
        mListenerManager = listenerManager;

        List<View> views = getAllChildViews(activity);
        for (View v : views) {
            hookSingleView(v);
        }
    }

    /**
     * hook 单个view
     *
     * @param view
     */
    private void hookSingleView(View view) {
        if (view.getTag() != null) {
            return;
        }
        Class mClassView = null;
        try {
            mClassView = Class.forName("android.view.View");
            Method method = mClassView.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfoObject = method.invoke(view);

            Class mClassListenerInfo = Class.forName("android.view.View$ListenerInfo");

            Field feildOnClickListener = mClassListenerInfo.getDeclaredField("mOnClickListener");
            feildOnClickListener.setAccessible(true);
            View.OnClickListener mOnClickListenerObject = (View.OnClickListener) feildOnClickListener.get(listenerInfoObject);

            Field feildOnLongClickListener = mClassListenerInfo.getDeclaredField("mOnLongClickListener");
            feildOnLongClickListener.setAccessible(true);
            View.OnLongClickListener mOnLongClickListenerObject = (View.OnLongClickListener) feildOnLongClickListener.get(listenerInfoObject);

            Field feildOnFocusChangeListener = mClassListenerInfo.getDeclaredField("mOnFocusChangeListener");
            feildOnFocusChangeListener.setAccessible(true);
            View.OnFocusChangeListener mOnFocusChangeListenerObject = (View.OnFocusChangeListener) feildOnFocusChangeListener.get(listenerInfoObject);

            View.OnClickListener onClickListenerProxy = new ViewClickProxy.OnClickListenerProxy(mOnClickListenerObject, mListenerManager.mOnClickListener);
            View.OnLongClickListener onLongClickListenerProxy = new ViewClickProxy.OnLongClickListenerProxy(mOnLongClickListenerObject, mListenerManager.mOnLongClickListener);
            View.OnFocusChangeListener onFocusChangeListenerProxy = new ViewClickProxy.OnFocusChangeListenerProxy(mOnFocusChangeListenerObject, mListenerManager.mOnFocusChangeListener);
            feildOnClickListener.set(listenerInfoObject, onClickListenerProxy);
            feildOnLongClickListener.set(listenerInfoObject, onLongClickListenerProxy);
            feildOnFocusChangeListener.set(listenerInfoObject, onFocusChangeListenerProxy);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历需要监听Listenerd的Activity
     *
     * @param activity
     * @return
     */
    public List<View> getAllChildViews(Activity activity) {
        View view = activity.getWindow().getDecorView();
        return getAllChildViews(view);
    }

    private List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            hookListViewListener((ViewGroup) view);
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    /**
     * hook到Listview的listener
     *
     * @param viewGroup
     */
    private void hookListViewListener(ViewGroup viewGroup) {//已经设置过的不会重新设置
        boolean b = viewGroup instanceof ListView || viewGroup instanceof GridView;

        if (b) {
            ListView listView = null;
            GridView gridView = null;
            if (viewGroup instanceof ListView) {
                listView = (ListView) viewGroup;
            }
            if (viewGroup instanceof GridView) {
                gridView = (GridView) viewGroup;
            }

            AdapterView.OnItemClickListener itemClickListener = (listView == null ? gridView : listView).getOnItemClickListener();

            AdapterView.OnItemLongClickListener itemLongClickListener = (listView == null ? gridView : listView).getOnItemLongClickListener();

            AdapterView.OnItemSelectedListener itemSelectedListener = (listView == null ? gridView : listView).getOnItemSelectedListener();


            if (null != itemClickListener) {

                if (null == (listView == null ? gridView : listView).getTag(R.id.tag_onItemClick)) {//还没hook过

                    (listView == null ? gridView : listView).setOnItemClickListener(new ItemClickProxy.OnItemClickProxyListener(itemClickListener, mListenerManager.getOnItemClickProxyListener()));
                }
            }
            if (itemLongClickListener != null) {
                (listView == null ? gridView : listView).setOnItemLongClickListener(new ItemClickProxy.OnItemLongClickListenerProxy(itemLongClickListener, mListenerManager.getOnItemLongClickProxyListener()));
            }
            if (itemSelectedListener != null) {
                (listView == null ? gridView : listView).setOnItemSelectedListener(new ItemClickProxy.OnItemSelectedListenerProxy(itemSelectedListener, mListenerManager.getOnItemSelectedProxyListener()));
            }
        }
    }

    //hook context.startActivity()系列，不需要与View系列一起注册，只需在Application里注册即可。并且回调事件需要在自定义的Instrumentation里额外再写。
    public static void attachContext() {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 拿到原始的 mInstrumentation字段
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);


            // 创建代理对象
            Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);


            // 偷梁换柱
            mInstrumentationField.set(currentActivityThread, evilInstrumentation);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
