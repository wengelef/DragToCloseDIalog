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

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class DragDialogFragment extends BaseDialogFragment {

    public enum CloseDirection { DEFAULT, TOP, BOTTOM }

    private static final int DRAG_TO_DISMISS_DISTANCE = 400;

    private float mInitialDragContainerPosition;

    private CloseDirection mCloseDirection;

    @Override
    public void onStart() {
        mAnimationListener = getDialogAnimationListener();
        getContentView().setOnTouchListener(getTouchListener());
        mCloseDirection = getCloseDirection();
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

    protected CloseDirection getCloseDirection() {
        return CloseDirection.DEFAULT;
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
                if (getCloseBottom()) {
                    if (mDistanceCovered > DRAG_TO_DISMISS_DISTANCE) {
                        close();
                    } else {
                        getContentView().animate().y(mInitialDragContainerPosition);
                    }
                }
                if (getCloseTop()) {
                    if (mDistanceCovered < (DRAG_TO_DISMISS_DISTANCE * -1)) {
                        close();
                    } else {
                        getContentView().animate().y(mInitialDragContainerPosition);
                    }
                }
            }
        };
    }

    private boolean getCloseBottom() {
        return mCloseDirection.equals(CloseDirection.DEFAULT) || mCloseDirection.equals(CloseDirection.BOTTOM);
    }

    private boolean getCloseTop() {
        return mCloseDirection.equals(CloseDirection.DEFAULT) || mCloseDirection.equals(CloseDirection.TOP);
    }
}
