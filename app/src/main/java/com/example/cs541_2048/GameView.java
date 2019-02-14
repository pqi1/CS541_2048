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
        //initGameView();
        startGame();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initGameView();
        startGame();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initGameView();
        startGame();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w,h) - 10) / 4;

        addCards(cardWidth, cardWidth);
    }

    private void printOutMatrix(){
        System.out.println("-------------------------------");

        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                System.out.println("这是xy： "+ x + y + this.cardsMap[x][y].getNum());
            }
        }
    }
    //add and initialize the cardsMap
    private void addCards(int cardWidth, int cardHeight){
        Card card;
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
    //set the card number to 0, and add two random number
    private void startGame(){
        addCards(getCardWidth(), getCardWidth());
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                printOutMatrix();
                return false;
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
//
//    private void initGameView() {
//        startGame();
//
//        //printOutMatrix();
//
////        setOnTouchListener(new OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                printOutMatrix();
////
////                return false;
////            }
////        });
//        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//        printOutMatrix();
//        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//
//
//    }

//
//        setOnTouchListener(new OnTouchListener() {
//            //float numbers to record the finger position
//            private float startX, startY, offsetX, offsetY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                printOutMatrix();
//
//                return true;
//            }true


//            public boolean onTouch(View v, MotionEvent event) {
//                printOutMatrix();
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getX();
//                        startY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        offsetX = event.getX() - startX;
//                        offsetY = event.getY() - startY;
//
//                        if(Math.abs(offsetX) > Math.abs(offsetY)){
//                            if(offsetX < -5){
//                                System.out.println("Left");
//                                swipeLeft();
//                            } else if(offsetX > 5){
//                                System.out.println("Right");
//                                swipeRight();
//                            }
//                        }else{
//                            if(offsetY < -5){
//                                System.out.println("Up");
//                                swipeUp();
//                            }else if(offsetY > 5){
//                                System.out.println("Down");
//                                swipeDown();
//                                }
//
//                        }
//                        break;
//                }

//                return true;
//            }
//        });
//    }





    private void swipeRight(){
//        for(int y = 0; y < 4; y++){
//            for(int x = 3; x >= 0; x--){
//                for(int x1 = x-1; x1>=0; x1--){
//                    if(cardsMap[x][y].getNum()<=0){
//                        cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
//                        cardsMap[x1][y].setNum(0);
//                        x++;
//                        break;
//                    }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
//                        cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
//                        cardsMap[x1][y].setNum(0);
//                        break;
//                    }
//                }
//            }
//
//        }
    }

    private void swipeLeft(){
        printOutMatrix();
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                for(int x_right = x+1; x_right<4; x_right++){
                    System.out.println(x_right);
                    System.out.println("this is the number" + cardsMap[x][y].getNum());
//                    if(cardsMap[x_right][y].getNum() > 0){
//                        System.out.println("进来了");
//                        if(cardsMap[x][y].getNum()<=0){
//                            cardsMap[x][y].setNum(cardsMap[x_right][y].getNum());
//                            cardsMap[x_right][y].setNum(0);
//                            System.out.println("hello");
//                            x--;
//                            break;
//                        }else if(cardsMap[x][y].equals(cardsMap[x_right][y])){
//                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
//                            cardsMap[x_right][y].setNum(0);
//                            break;
//                        }
//                    }
                }
            }

        }
    }

    private void swipeUp(){
//        for(int x = 0; x < 4; x++){
//            for(int y = 0; y < 4; y++){
//                for(int y1 = y+1; y1<4; y1++){
//                    if(cardsMap[x][y].getNum()<=0){
//                        cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
//                        cardsMap[x][y1].setNum(0);
//                        x--;
//                        break;
//                    }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
//                        cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
//                        cardsMap[x][y1].setNum(0);
//                        break;
//                    }
//                }
//            }
//
//        }
    }

    private void swipeDown(){
//        for(int x = 0; x < 4; x++){
//            for(int y = 3; y >= 0; y--){
//                for(int y1 = y-1; y1>=0; y1--){
//                    if(cardsMap[x][y].getNum()<=0){
//                        cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
//                        cardsMap[x][y1].setNum(0);
//                        x--;
//                        break;
//                    }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
//                        cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
//                        cardsMap[x][y1].setNum(0);
//                        break;
//                    }
//                }
//            }
//
//        }
    }

}

