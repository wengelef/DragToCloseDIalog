package com.wengelef.dragtoclosedialogsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.wengelef.dragtoclosedialog.DragDialogFragment;


public class BottomCloseDialog extends DragDialogFragment {

    private View mContentView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentView = view.findViewById(R.id.content_view);
    }

    @NonNull
    @Override
    protected View getContentView() {
        return mContentView;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fr_bottom_close_dialog;
    }

    @NonNull
    @Override
    protected CloseDirection getCloseDirection() {
        return CloseDirection.BOTTOM;
    }
}
