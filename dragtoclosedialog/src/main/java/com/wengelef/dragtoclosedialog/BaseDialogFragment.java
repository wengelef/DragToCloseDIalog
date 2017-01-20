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

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract class BaseDialogFragment extends DialogFragment {

    private static final int ANIMATION_DURATION = 200;

    @Nullable
    protected DialogAnimationListener mAnimationListener;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        final View decorView = getDialog()
                .getWindow()
                .getDecorView();

        ObjectAnimator fadeIn = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));

        ObjectAnimator scaleUp = ObjectAnimator.ofPropertyValuesHolder(getContentView(),
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f));

        scaleUp.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mAnimationListener != null) {
                    mAnimationListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        scaleUp.setDuration(ANIMATION_DURATION);
        fadeIn.setDuration(ANIMATION_DURATION);
        scaleUp.start();
        fadeIn.start();
    }

    /**
     * Get the View that needs to be scaled down
     * @return Content view of the Dialog
     */
    protected abstract View getContentView();

    protected abstract @LayoutRes int getLayoutResId();

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        setCancelable(true);
    }

    public void close() {
        final View decorView = getDialog()
                .getWindow()
                .getDecorView();

        ObjectAnimator fadeOut = ObjectAnimator.ofPropertyValuesHolder(decorView,
                PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f));

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(getContentView(),
                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f),
                PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f));

        scaleDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        scaleDown.setDuration(ANIMATION_DURATION);
        fadeOut.setDuration(ANIMATION_DURATION);
        scaleDown.start();
        fadeOut.start();
    }

    protected interface DialogAnimationListener {
        void onAnimationEnd();
    }
}
