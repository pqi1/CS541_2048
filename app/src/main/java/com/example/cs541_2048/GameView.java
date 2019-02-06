package com.example.cs541_2048;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameView extends GridLayout {
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();

    public GameView(Context context) {
        super(context);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w,h) - 10) / 4;

        addCards(cardWidth, cardWidth);
    }

    private void addCards(int cardWidth, int cardHeight){
        Card card = null;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                card = new Card(getContext());
                card.setNum(0);
                addView(card, cardWidth, cardHeight);

                cardsMap[i][j] = card;
            }
        }

    }

    private int getCardWidth(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int cardWidth = displayMetrics.widthPixels;

        return (cardWidth - 10)/4;
    }

    private void startGame(){
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }

    private void initGameView(){
        setColumnCount(4);

        setBackgroundColor(0xffbbada0);
        addCards(getCardWidth(), getCardWidth());

        startGame();

        setOnTouchListener(new OnTouchListener() {
            //float numbers to record the finger position
            private float startX, startY, offsetX, offsetY;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if(Math.abs(offsetX) > Math.abs(offsetY)){
                            if(offsetX < -5){
                                System.out.println("Left");
                                swipeLeft();
                            } else if(offsetX > 5){
                                System.out.println("Right");
                                swipeRight();
                            }
                        }else{
                            if(offsetY < -5){
                                System.out.println("Up");
                                swipeUp();
                            }else if(offsetY > 5){
                                System.out.println("Down");
                                swipeDown();
                                }

                        }
                        break;
                }

                return true;
            }
        });
    }

    private void addRandomNum(){
        emptyPoints.clear();

        for (int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                if(cardsMap[x][y].getNum() <= 0){
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        Point point = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[point.x][point.y].setNum(Math.random()>0.1?2:4);
    }



    private void swipeRight(){

    }

    private void swipeLeft(){

    }

    private void swipeUp(){

    }

    private void swipeDown(){

    }

}
