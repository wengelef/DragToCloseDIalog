package com.wengelef.dragtoclosedialogsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wengelef.dragtoclosedialog.DragDialogFragment;


public class TopCloseDialog extends DragDialogFragment {

    private View mContentView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentView = view.findViewById(R.id.content_view);
    }

    @Override
    protected View getContentView() {
        return mContentView;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fr_top_close_dialog;
    }

    @Override
    protected CloseDirection getCloseDirection() {
        return CloseDirection.TOP;
    }
}
