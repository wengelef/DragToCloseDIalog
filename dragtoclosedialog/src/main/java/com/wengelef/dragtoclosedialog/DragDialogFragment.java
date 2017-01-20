/*
 * Copyright (c) wengelef 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wengelef.dragtoclosedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public abstract class DragDialogFragment extends BaseDialogFragment {

    private static final int DRAG_TO_DISMISS_DISTANCE = 400;

    private float mInitialDragContainerPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResId(), container, false);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        mAnimationListener = getDialogAnimationListener();
        getContentView().setOnTouchListener(getTouchListener());
        super.onStart();
    }

    private DialogAnimationListener getDialogAnimationListener() {
        return new DialogAnimationListener() {
            @Override
            public void onAnimationEnd() {
                mInitialDragContainerPosition = getContentView().getY();
            }
        };
    }

    private View.OnTouchListener getTouchListener() {
        return new View.OnTouchListener() {

            private float mLastTouchY;
            private float mDistanceCovered;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        mLastTouchY = event.getRawY();
                        mDistanceCovered = 0.0f;
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        final float y = event.getRawY();
                        final float dy = y - mLastTouchY;
                        mDistanceCovered += dy;
                        getContentView().setY(getContentView().getY() + dy);
                        mLastTouchY = y;
                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        onDragEnd();
                        break;
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        onDragEnd();
                        break;
                    }

                    default: {
                        return true;
                    }
                }
                return true;
            }

            private void onDragEnd() {
                if (mDistanceCovered > DRAG_TO_DISMISS_DISTANCE) {
                    close();
                } else {
                    getContentView().animate().y(mInitialDragContainerPosition);
                }
            }
        };
    }
}
