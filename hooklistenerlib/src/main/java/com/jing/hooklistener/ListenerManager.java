package com.jing.hooklistener;

/**
 * Created by wulei
 * Data: 2016/10/18.
 */

public class ListenerManager {
    public HookListenerContract.OnFocusChangeListener mOnFocusChangeListener;
    public HookListenerContract.OnClickListener mOnClickListener;
    public HookListenerContract.OnLongClickListener mOnLongClickListener;

    public HookListenerContract.OnItemClickListenerProxy onItemClickProxyListener;
    public HookListenerContract.OnItemLongClickListenerProxy OnItemLongClickProxyListener;
    public HookListenerContract.OnItemSelectedClickListenerProxy OnItemSelectedProxyListener;

    private ListenerManager() {
    }

    public static ListenerManager create(Builer builer) {
        if (builer == null) {
            return null;
        }
        return builer.build();
    }

    public static class Builer {
        private ListenerManager listenerManager = new ListenerManager();

        public Builer buildOnFocusChangeListener(HookListenerContract.OnFocusChangeListener onFocusChangeListener) {
            listenerManager.mOnFocusChangeListener = onFocusChangeListener;
            return this;
        }

        public Builer buildOnClickListener(HookListenerContract.OnClickListener onClickListener) {
            listenerManager.mOnClickListener = onClickListener;
            return this;
        }

        public Builer buildOnLongClickListener(HookListenerContract.OnLongClickListener onLongClickListener) {
            listenerManager.mOnLongClickListener = onLongClickListener;
            return this;
        }

        /***
         * listview 的点击代理监听器回调
         *
         * @param onItemClickProxyListener
         * @return
         */
        public Builer buildOnItemClickProxyListener(HookListenerContract.OnItemClickListenerProxy onItemClickProxyListener) {
            listenerManager.onItemClickProxyListener = onItemClickProxyListener;
            return this;
        }

        /***
         * listview 的长按代理监听器回调
         *
         * @param onItemLongClickProxyListener
         * @return
         */
        public Builer buildOnItemLongClickProxyListener(HookListenerContract.OnItemLongClickListenerProxy onItemLongClickProxyListener) {
            listenerManager.OnItemLongClickProxyListener = onItemLongClickProxyListener;
            return this;
        }

        /***
         * listview 的onItemSelected代理监听器
         *
         * @param onItemSelectedProxyListener
         * @return
         */
        public Builer buildOnItemSelectedProxyListener(HookListenerContract.OnItemSelectedClickListenerProxy onItemSelectedProxyListener) {
            listenerManager.OnItemSelectedProxyListener = onItemSelectedProxyListener;
            return this;
        }

        public ListenerManager build() {
            return listenerManager;
        }
    }

    public HookListenerContract.OnItemClickListenerProxy getOnItemClickProxyListener() {
        return onItemClickProxyListener;
    }

    public HookListenerContract.OnItemLongClickListenerProxy getOnItemLongClickProxyListener() {
        return OnItemLongClickProxyListener;
    }

    public HookListenerContract.OnItemSelectedClickListenerProxy getOnItemSelectedProxyListener() {
        return OnItemSelectedProxyListener;
    }

}
