package com.example.santander.pocporquinho;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Santander on 11/01/17.
 */

public class PorquinhoComponent {

    private PorquinhoView porquinhoView;
    private Activity mActivity;

    private RelativeLayout container;
    private Button button;
    private LinearLayout containerMensagem;
    private LinearLayout linearInvista;
    private int actionBarButtonHeight;
    private int tamanhoTela70Perc;
    private int tamanhoTela30Perc;
    private boolean containerMensagemVisivel = true;
    private boolean containerAberto = false;
    private int touchHeight;
    private int countMove;
    private int buttonLinguaTopHeight;

    public PorquinhoComponent(PorquinhoView porquinhoView, Activity activity) {
        this.porquinhoView = porquinhoView;
        this.mActivity = activity;
    }

    public void initComponent(){

        container = (RelativeLayout) mActivity.findViewById(R.id.container);
        containerMensagem = (LinearLayout) mActivity.findViewById(R.id.container_mensagem);
        linearInvista = (LinearLayout) mActivity.findViewById(R.id.linear_invista);
        linearInvista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                porquinhoView.actionInvista();
            }
        });

        button = (Button) mActivity.findViewById(R.id.button);
        button.setOnTouchListener(onTouchListener());

        calcularMetricas();

        changeColorSegmento();
    }

    private void changeColorSegmento() {

    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                actionBarButtonHeight = porquinhoView.getAjusteTelaHeight() + button.getHeight();

                buttonLinguaTopHeight = 0;
                if (containerMensagemVisivel)
                    buttonLinguaTopHeight = containerMensagem.getHeight();

                touchHeight = (int) motionEvent.getRawY() - actionBarButtonHeight;

                ViewGroup.LayoutParams params = container.getLayoutParams();

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        if (touchHeight < tamanhoTela70Perc) {
                            params.height = touchHeight;
                            container.setLayoutParams(params);
                        }

                        if (touchHeight > tamanhoTela30Perc) {
                            containerMensagemVisivel = false;
                            containerMensagem.setVisibility(View.INVISIBLE);
                        }
                        countMove = countMove + 1;

                        Log.v("COUNT_MOVE", String.valueOf(countMove));
                        Log.v("ACTION_MOVE", String.valueOf(touchHeight));
                        break;

                    case MotionEvent.ACTION_UP:
                        if (countMove > 20) { // movimento
                            if (touchHeight < tamanhoTela30Perc) {
                                animacaoFecharLayout();
                            } else {
                                animacaoAbrirLayout();
                            }
                        } else { // click
                            if (containerAberto) {
                                animacaoFecharLayout();
                            } else {
                                containerMensagem.setVisibility(View.INVISIBLE);
                                containerMensagemVisivel = false;
                                animacaoAbrirLayout();
                            }
                        }
                        countMove = 0;
                        Log.v("ACTION_UP", String.valueOf(touchHeight));
                        break;

                    case MotionEvent.ACTION_DOWN:
                        Log.v("ACTION_DOWN", String.valueOf(touchHeight));
                        break;
                }
                return true;
            }
        };
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animacaoAbrirLayout() {
        animateLayoutParams(touchHeight, tamanhoTela70Perc);
        button.setBackground(mActivity.getDrawable(R.drawable.aba_push_porquinho_fechar_select));
        containerAberto = true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animacaoFecharLayout() {
        animateLayoutParams(touchHeight, buttonLinguaTopHeight);
        button.setBackground(mActivity.getDrawable(R.drawable.aba_push_porquinho_abrir_select));
        containerAberto = false;
    }

    private void calcularMetricas() {
        int tamanhoTela = getScreenHeight();

        tamanhoTela70Perc = (int) (tamanhoTela * 0.7);

        tamanhoTela30Perc = (int) (tamanhoTela * 0.3);
    }

    private int getScreenHeight() {
        DisplayMetrics metrics = mActivity.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    private void animateLayoutParams(int fromHeight, final int toHeight) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromHeight, toHeight);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = container.getLayoutParams();
                params.height = (int) animation.getAnimatedValue();
                container.setLayoutParams(params);

                Log.v("onAnimationUpdate", String.valueOf(animation.getAnimatedValue()));
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.v("onAnimationStart", "onAnimationStart");

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.v("onAnimationEnd", "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.v("onAnimationCancel", "onAnimationCancel");

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.v("onAnimationRepeat", "onAnimationRepeat");

            }
        });
        valueAnimator.start();
    }


}
