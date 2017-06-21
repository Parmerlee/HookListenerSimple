package com.jing.hooklistener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ItemClickProxy {
    /****
     * ItemClick代理类
     * 2017年6月19日11:30:58
     */
    public static class OnItemClickProxyListener implements AdapterView.OnItemClickListener {
        private AdapterView.OnItemClickListener onItemClickListener;
        private HookListenerContract.OnItemClickListenerProxy onItemClickProxyListener;

        public OnItemClickProxyListener(AdapterView.OnItemClickListener onItemClickListener, HookListenerContract.OnItemClickListenerProxy onItemClickProxyListener) {

            this.onItemClickListener = onItemClickListener;
            this.onItemClickProxyListener = onItemClickProxyListener;
        }


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(parent, view, position, id);
            }
            if (null != onItemClickProxyListener) {
                onItemClickProxyListener.onItemClick(parent, view, position, id);
            }
        }
    }

    /****
     * ItemLongClick代理类
     * 2017年6月19日11:30:58
     */
    public static class OnItemLongClickListenerProxy implements AdapterView.OnItemLongClickListener {

        HookListenerContract.OnItemLongClickListenerProxy onItemLongClickListenerProxy;
        AdapterView.OnItemLongClickListener onItemLongClickListener;

        public OnItemLongClickListenerProxy(AdapterView.OnItemLongClickListener onItemLongClickListener,
                                            HookListenerContract.OnItemLongClickListenerProxy onItemLongClickProxyListener) {
            this.onItemLongClickListener = onItemLongClickListener;
            this.onItemLongClickListenerProxy = onItemLongClickProxyListener;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (null != onItemLongClickListenerProxy) {
                onItemLongClickListenerProxy.onItemLongClick(parent, view, position, id);
            }
            if (null != onItemLongClickListener) {
                return onItemLongClickListener.onItemLongClick(parent, view, position, id);
            }
            return false;
        }
    }

    public static class OnItemSelectedListenerProxy implements AdapterView.OnItemSelectedListener {
        HookListenerContract.OnItemSelectedClickListenerProxy onItemLongClickListenerProxy;
        AdapterView.OnItemSelectedListener listener;

        public OnItemSelectedListenerProxy(AdapterView.OnItemSelectedListener listener,
                                           HookListenerContract.OnItemSelectedClickListenerProxy onItemLongClickProxyListener) {
            this.listener = listener;
            this.onItemLongClickListenerProxy = onItemLongClickProxyListener;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (listener != null) {
                listener.onItemSelected(parent, view, position, id);
            }
            if (onItemLongClickListenerProxy != null) {
                onItemLongClickListenerProxy.onItemSelectedClick(parent, view, position, id);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            if (listener != null) {
                listener.onNothingSelected(parent);
            }
            if (onItemLongClickListenerProxy != null) {
                onItemLongClickListenerProxy.onNothingSelected(parent);
            }
        }
    }
}
