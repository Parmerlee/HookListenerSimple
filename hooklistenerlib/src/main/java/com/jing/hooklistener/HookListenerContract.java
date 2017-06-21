package com.jing.hooklistener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by wulei
 * Data: 2016/10/18.
 */

public class HookListenerContract {
    public interface OnFocusChangeListener {
        void doInListener(View v, boolean hasFocus);
    }

    public interface OnClickListener {
        void doInListener(View v);
    }

    public interface OnLongClickListener {
        void doInListener(View v);
    }

    public interface OnItemClickListenerProxy {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public interface OnItemLongClickListenerProxy {
        void onItemLongClick(AdapterView<?> parent, View view, int position, long id);
    }

    public interface OnItemSelectedClickListenerProxy {
        void onItemSelectedClick(AdapterView<?> parent, View view, int position, long id);

        void onNothingSelected(AdapterView<?> parent);
    }

}
