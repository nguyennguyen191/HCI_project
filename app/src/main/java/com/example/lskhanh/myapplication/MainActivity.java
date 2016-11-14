package com.example.lskhanh.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.app.Activity;


public class MainActivity extends AppCompatActivity implements OnTouchListener, OnClickListener {
    Button button1 = null;
    Button button2 = null;
    TextView result = null;
    EditText editText = null;

    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    private boolean shouldClick = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        rootLayout = (ViewGroup) findViewById(R.id.view_root);

        button1 = (Button) rootLayout.findViewById(R.id.button1);
        button2 = (Button) rootLayout.findViewById(R.id.button2);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        result = (TextView) findViewById(R.id.result);
        editText = (EditText) findViewById(R.id.editText);
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);   // handle the event first
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {

                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  // hide the soft keyboard
                    editText.setCursorVisible(true); //This is to display cursor when upon onTouch of Edittext
                }
                return true;
            }
        });

        button1.setOnClickListener(new getValue());
        button2.setOnClickListener(new getValue());

        /*button1.setLayoutParams(layoutParams);*/
        button1.setOnTouchListener(new ChoiceTouchListener());
        /*button2.setLayoutParams(layoutParams);*/
        button2.setOnTouchListener(new ChoiceTouchListener());
    }


    private final class ChoiceTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;

                    shouldClick = true;
                    break;
                case MotionEvent.ACTION_UP:
                    if (shouldClick)
                        view.performClick();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    shouldClick = false;

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }


    // Uniquement pour le bouton "envoyer"
    private final class getValue implements OnClickListener {
        @Override

        public void onClick(View v) {
            Button b = (Button) v;
            String buttonText = (String) b.getText();
            editText.append(buttonText);
        }

    }

    protected void hideSoftKeyboard(EditText input) {
        input.setInputType(0);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
    ;

    /*//*private OnTouchListener changePosition = new OnTouchListener(){

        @Override
        public boolean onTouch(View p_v, MotionEvent p_event) {

            m_dx = p_event.getX() - m_lastTouchX;
            m_dy = p_event.getY() - m_lastTouchY;

            m_posX = m_prevX + m_dx;
            m_posY = m_prevY + m_dy;

            if (m_posX > 0 && m_posY > 0 && (m_posX + p_v.getWidth()) < m_alTop.getWidth() && (m_posY + p_v.getHeight()) < m_alTop.getHeight()) {
                p_v.setLayoutParams(new AbsoluteLayout.LayoutParams(p_v.getMeasuredWidth(), p_v.getMeasuredHeight(), (int) m_posX, (int) m_posY));

                m_prevX = m_posX;
                m_prevY = m_posY;

            }
            return true;
        };
    };*/

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onClick(View view) {

    }
}