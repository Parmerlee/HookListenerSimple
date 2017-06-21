package com.jing.hooklistener;

import android.util.Log;
import android.view.View;

public class ViewClickProxy {


    public static class OnLongClickListenerProxy implements View.OnLongClickListener {
        private View.OnLongClickListener object;
        private HookListenerContract.OnLongClickListener mlistener;

        public OnLongClickListenerProxy(View.OnLongClickListener object, HookListenerContract.OnLongClickListener listener) {
            this.object = object;
            this.mlistener = listener;
        }

        @Override
        public boolean onLongClick(View v) {
            Log.e("OnLongClickProxy", "-------------OnLongClickListenerProxy-----------");
            if (mlistener != null) mlistener.doInListener(v);
            if (object != null) return object.onLongClick(v);
            return false;
        }
    }


    public static class OnClickListenerProxy implements View.OnClickListener {
        private View.OnClickListener object;
        private HookListenerContract.OnClickListener mlistener;

        public OnClickListenerProxy(View.OnClickListener object, HookListenerContract.OnClickListener listener) {
            this.object = object;
            this.mlistener = listener;
        }

        @Override
        public void onClick(View v) {
            Log.e("OnClickListenerProxy", "---------------OnClickListenerProxy-------------");
            if (mlistener != null) mlistener.doInListener(v);
            if (object != null) object.onClick(v);
        }
    }


    public static class OnFocusChangeListenerProxy implements View.OnFocusChangeListener {
        private View.OnFocusChangeListener object;
        private HookListenerContract.OnFocusChangeListener mlistener;

        public OnFocusChangeListenerProxy(View.OnFocusChangeListener object, HookListenerContract.OnFocusChangeListener listener) {
            this.object = object;
            this.mlistener = listener;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.e("OnFocusChangeProxy", "---------------OnFocusChangeListenerProxy-------------");
            if (mlistener != null) mlistener.doInListener(v, hasFocus);
            if (object != null) object.onFocusChange(v, hasFocus);
        }
    }
}
