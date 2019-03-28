package com.saadmahmud.laddershuffle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saadmahmud.laddershuffle.customview.CircleView;
import com.saadmahmud.laddershuffle.customview.LineView;
import com.saadmahmud.laddershuffle.customview.UserView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.saadmahmud.laddershuffle.Util.showLog;

public class ScndActivity extends AppCompatActivity {

    int CIRCLE_COLOR = Color.RED;
    int LINE_THICKNESS;
    int USER_IMAGE_RADIUS;
    int USER_LINE_LENGTH = 1000;
    int HORIZONTAL_LINE_LENGTH = 300;
    int CIRCLE_RADIUS = 30;
    int CIRCLE_LAYOUT_THICKNESS = 80;
    int LADDER_STEP_MIN_GAP = 100;
    int ANIM_DURATION = 10000;
    int LADDER_LAYOUT_MARGIN = 40;

    LinearLayout.LayoutParams ladderLayoutParams;
    LinearLayout.LayoutParams circleParams;

    CircleView circleView;
    LinearLayout layoutLadderView;
    LinearLayout layoutItemView;
    LinearLayout layoutUserView;
    EditText etUserId;
    List<Rect> mUserLineList;
    List<TextView> mItemTVList;

    LineMatrix mLineMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scnd);

        initDimens();

        initializeView();

    }

    private void initDimens() {
        /* on pixel 2 xl device, 1 dp= 3.5 px */
        LINE_THICKNESS = (int) getResources().getDimension(R.dimen.user_line_thickness);
        USER_LINE_LENGTH = (int) getResources().getDimension(R.dimen.user_line_length);
        HORIZONTAL_LINE_LENGTH = (int) getResources().getDimension(R.dimen.horizontal_line_length);
        CIRCLE_RADIUS = (int) getResources().getDimension(R.dimen.circle_radius);
        USER_IMAGE_RADIUS = (int) getResources().getDimension(R.dimen.user_image_radius);
        CIRCLE_LAYOUT_THICKNESS = (int) getResources().getDimension(R.dimen.circle_layout_thickness);
        LADDER_STEP_MIN_GAP = (int) getResources().getDimension(R.dimen.ladder_step_min_gap);
        LADDER_LAYOUT_MARGIN = (int) getResources().getDimension(R.dimen.ladder_layout_margin);

        showLog("DIMEN: LINE_THICKNESS " + getResources().getDimension(R.dimen.user_line_thickness));
        showLog("DIMEN: USER_LINE_LENGTH " + getResources().getDimension(R.dimen.user_line_length));
        showLog("DIMEN: HORIZONTAL_LINE_LENGTH " + getResources().getDimension(R.dimen.horizontal_line_length));
        showLog("DIMEN: CIRCLE_RADIUS " + getResources().getDimension(R.dimen.circle_radius));
        showLog("DIMEN: CIRCLE_LAYOUT_THICKNESS " + getResources().getDimension(R.dimen.circle_layout_thickness));
        showLog("DIMEN: LADDER_STEP_MIN_GAP " + getResources().getDimension(R.dimen.ladder_step_min_gap));
        showLog("DIMEN: LADDER_LAYOUT_MARGIN " + getResources().getDimension(R.dimen.ladder_layout_margin));
    }

    private void initializeView() {
        layoutLadderView = findViewById(R.id.layoutLadderView);
        layoutItemView = findViewById(R.id.layoutItemView);
        layoutUserView = findViewById(R.id.layoutUserView);

        etUserId = findViewById(R.id.etUserId);

        layoutLadderView.removeAllViews();
        layoutItemView.removeAllViews();
        layoutUserView.removeAllViews();

        mUserLineList = new ArrayList<>();
        mItemTVList = new ArrayList<>();

        mLineMatrix = new LineMatrix();

        Rect vLRect = new Rect(0, 0, LINE_THICKNESS, USER_LINE_LENGTH);
        Rect hLRect = new Rect(0, 0, HORIZONTAL_LINE_LENGTH, LINE_THICKNESS);

        ladderLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, USER_LINE_LENGTH);

        mUserLineList.add(vLRect);
        mUserLineList.add(vLRect);
        mUserLineList.add(vLRect);
        mUserLineList.add(vLRect);
        mUserLineList.add(vLRect);
        mUserLineList.add(vLRect);
        mUserLineList.add(vLRect);

        LinearLayout ladderItemLayout = new LinearLayout(this);
        ladderItemLayout.setOrientation(LinearLayout.HORIZONTAL);
        ladderItemLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout userImageLayout = new LinearLayout(this);
        userImageLayout.setOrientation(LinearLayout.HORIZONTAL);
        userImageLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout hL = new LinearLayout(this);
        hL.setOrientation(LinearLayout.HORIZONTAL);
        ladderLayoutParams.setMargins(LADDER_LAYOUT_MARGIN, LADDER_LAYOUT_MARGIN, LADDER_LAYOUT_MARGIN, 0);
        hL.setLayoutParams(ladderLayoutParams);

        showLog("User line size " + mUserLineList.size());

        /*If USER line count is 3 HORIZONTAL lines will be generated for 2 times (between 3 lines)*/

        Integer[] hLMargins = null;

        int vLID = 0;
        for (int userLineNumber = 0; userLineNumber < mUserLineList.size(); userLineNumber++) {

            Rect lineRect = mUserLineList.get(userLineNumber);

            addUserLine(hL, lineRect, vLID);

            addUserImage(userImageLayout, vLID);

            addLadderItem(ladderItemLayout, vLID);

            int horLineCount = new Random().nextInt(4) + 2;
            /* Horizontal lines will be at least 2 to 5*/

            showLog("horLineCount " + horLineCount);

            List<Integer> leftHLineMargins = new ArrayList<>();

            if (hLMargins != null) {
                leftHLineMargins.addAll(Arrays.asList(hLMargins));
            }

            hLMargins = Util.getMargins(0, USER_LINE_LENGTH, horLineCount, LADDER_STEP_MIN_GAP);

            showLog("level: userLineNumber " + userLineNumber);
            /* check for same level line in left ladder */

            int rightMarginSum = 0;

            for (int i = 0; i < hLMargins.length; i++) {

                Integer rightMargin = hLMargins[i];

                int leftMarginSum = 0;

                rightMarginSum += (rightMargin + LINE_THICKNESS);

                showLog("level: rightMargin " + rightMargin + ", rightMarginSum " + rightMarginSum);

                for (Integer leftMargin : leftHLineMargins) {
                    leftMarginSum += (leftMargin + LINE_THICKNESS);
                    showLog("level: leftMargin " + leftMargin + ", leftMarginSum= " + leftMarginSum);

                    if (leftMarginSum > rightMarginSum) {
                        showLog("level: two margins are NOT at same level ");
                        break;
                    }

                    if (leftMarginSum == rightMarginSum) {
                        /* two margins are at same level */
                        showLog("level: two margins are at SAME LEVEL ");
                        hLMargins[i] += (LADDER_STEP_MIN_GAP / 2);
                        rightMarginSum += (LADDER_STEP_MIN_GAP / 2);

                        break;
                    }
                }
            }
            showLog("horLineMargins count " + hLMargins.length);

            if (vLID < mUserLineList.size() - 1) {
                addHorizontalLine(userLineNumber, hL, hLRect, hLMargins);
            }

            vLID++;
        }

        circleView = new CircleView(this, CIRCLE_RADIUS, CIRCLE_COLOR);

        circleParams = new LinearLayout.LayoutParams(CIRCLE_LAYOUT_THICKNESS, CIRCLE_LAYOUT_THICKNESS);
        circleView.setLayoutParams(circleParams);

        layoutLadderView.addView(hL);
        layoutLadderView.addView(circleView);
        layoutItemView.addView(ladderItemLayout);
        layoutUserView.addView(userImageLayout);
    }

    private void addUserLine(LinearLayout a, Rect r, int id) {
        LineView lineView = new LineView(this, r);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(r.width(), ViewGroup.LayoutParams.WRAP_CONTENT));
        lineView.setId(id);
        a.addView(lineView);

        mLineMatrix.addUserLines(lineView);
    }

    int[] colorArray = new int[]{Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.RED
            , Color.MAGENTA, Color.YELLOW, Color.LTGRAY, Color.RED, Color.BLACK};

    private void addUserImage(LinearLayout userViewLayout, int id) {
        UserView userView = new UserView(this, USER_IMAGE_RADIUS, colorArray[id]);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(HORIZONTAL_LINE_LENGTH, HORIZONTAL_LINE_LENGTH);
        userView.setLayoutParams(lp);
        userView.setTag(id);

        userView.setOnClickListener(userViewClickListener);

        userViewLayout.addView(userView);
    }

    private View.OnClickListener userViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int userIdAsTag = (int) v.getTag();

            showLog("userIdAsTag " + userIdAsTag);

            startGame(userIdAsTag);
        }
    };

    private void addLadderItem(LinearLayout itemLayout, int id) {
        TextView itemTv = new TextView(this);
        itemTv.setText("Item: " + (id + 1));
        itemTv.setTextColor(Color.BLACK);
        itemTv.setBackgroundColor(Color.WHITE);
        itemTv.setVisibility(View.INVISIBLE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(HORIZONTAL_LINE_LENGTH, HORIZONTAL_LINE_LENGTH);
        itemTv.setLayoutParams(lp);

        itemLayout.addView(itemTv);

        mItemTVList.add(itemTv);

        showLog("addLadderItem " + id + ": itemTv= " + itemTv.getText() + ", itemLayout child= " + itemLayout.getChildCount());

    }

    private void addHorizontalLine(int userLineID, LinearLayout hL, Rect rect, Integer[] horLineMargins) {
        LinearLayout vL = new LinearLayout(this);
        vL.setOrientation(LinearLayout.VERTICAL);

        int lineCount = horLineMargins.length;
        showLog("lineCount= " + lineCount);

        for (int i = 0; i < lineCount; i++) {
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(rect.width(), rect.height());
            lParams.topMargin = horLineMargins[i];

            LineView lV = new LineView(this, new Rect(rect.left, rect.top, rect.right, rect.bottom));
            lV.setLayoutParams(lParams);
            lV.setId(100 + i);
            vL.addView(lV);

            showLog("LineMatrix 11 HorizontalLine id= " + lV.getId()
                    + ", x= " + lV.getX() + ", y= " + lV.getY() + ", Width= " + lV.getWidth());

            showLog("LineMatrix 11 addHorizontalLine userLineID= " + userLineID);
            mLineMatrix.addHorizontalLine(userLineID, lV);

        }

        hL.addView(vL);
    }

    private void moveView(View view, final int selectedUserLine) {
        int size = mLineMatrix.getFinalPathMap().get(selectedUserLine).size();
        float[] x = new float[size];
        float[] y = new float[size];

        Path path = new Path();
        Paint paint = new Paint();
        paint.setColor(colorArray[selectedUserLine]);
        paint.setStyle(Paint.Style.FILL);

        int i = 0;
        for (Point point : mLineMatrix.getFinalPathMap().get(selectedUserLine)) {
            x[i] = point.x;
            y[i] = point.y;

            path.lineTo(x[i], y[i]);

            showLog("Circle path point: x,y= " + x[i] + "," + y[i]);

            i++;
        }

        final int selectedItemIndex = (int) Math.floor(x[size - 1] / HORIZONTAL_LINE_LENGTH);

        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "x", x);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "y", y);
//        ObjectAnimator paths = ObjectAnimator.ofFloat(path, "y", y);
//        ValueAnimator animCol = ObjectAnimator.ofArgb(new int[]{Color.YELLOW});
        new Canvas().drawPath(path, new Paint());

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(ANIM_DURATION);
        animSet.playTogether(animX, animY);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showLog("ANIMATION END");

                onLadderItemSelected(selectedUserLine, selectedItemIndex);
            }

        });
        animSet.start();


    }

    private void onLadderItemSelected(int selectedUserIndex, int selectedItemIndex) {
        showLog("ANIMATION selectedItemIndex " + selectedItemIndex);
        TextView tvItem = mItemTVList.get(selectedItemIndex);
        if (tvItem == null) return;
        tvItem.setText(tvItem.getText() + "\nUser #" + (selectedUserIndex + 1));
        tvItem.setTextColor(colorArray[selectedUserIndex]);
        tvItem.setVisibility(View.VISIBLE);
    }

    public void startAnimate(View view) {

        int selectedUserLine = 0;
        try {
            selectedUserLine = Integer.parseInt(etUserId.getText().toString()) - 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (selectedUserLine < 0 || selectedUserLine > mUserLineList.size()) {
            Toast.makeText(this, "Please select valid user. Maximum " + (mUserLineList.size() - 1), Toast.LENGTH_SHORT).show();
            return;
        }

        startGame(selectedUserLine);
    }

    private void startGame(int selectedUserLine) {
        calculatePath(selectedUserLine);

        moveView(circleView, selectedUserLine);
    }

    List<LineProperties> mLineProperties;

    private void calculatePath(int selectedUserLine) {
        LineView userLine = mLineMatrix.getUserLines().get(selectedUserLine);

        mLineProperties = new ArrayList<>();
        mLineProperties.add(new LineProperties(userLine));

        int vLineHeight = userLine.getHeight();

        showLog("mLineMatrix UserLine id= " + userLine.getId() + ", x= " + userLine.getX() + ", y= " + userLine.getY() + ", vLineHeight= " + vLineHeight);

        Point startPoint = new Point(Math.round(userLine.getX()), Math.round(userLine.getY()));
        Point currentSelectedPoint = new Point(startPoint.x, startPoint.y);

        if (mLineMatrix.getFinalPathMap().get(selectedUserLine) == null) {
            mLineMatrix.getFinalPathMap().put(selectedUserLine, new ArrayList<Point>());
        }

        /*add first point*/
        List<Point> pointList = new ArrayList<>();
        pointList.add(currentSelectedPoint);
        showLog("mLineMatrix currentSelectedPoint x,y= " + currentSelectedPoint);

        int currentUserLine = selectedUserLine;
        int searchedHeight = 0;
        showLog("pre-while vLineHeight " + vLineHeight);

        //start of while
        while (searchedHeight < USER_LINE_LENGTH) {

            showLog("while currentUserLine " + currentUserLine);
            showLog("while searchedHeight / 3 " + searchedHeight / 3 + ", USER_LINE_LENGTH= " + USER_LINE_LENGTH);

            showLog("currentSelectedPoint 11 x,y= " + currentSelectedPoint);


            List<LineView> rightHorizontalLineList = new ArrayList<>();
            List<LineView> leftHorizontalLineList = new ArrayList<>();

            if (mLineMatrix.mHLinesMap.get(currentUserLine) != null) {
                rightHorizontalLineList.addAll(mLineMatrix.mHLinesMap.get(currentUserLine));
            }
            if (currentUserLine > 0 && mLineMatrix.mHLinesMap.get(currentUserLine - 1) != null) {
                leftHorizontalLineList.addAll(mLineMatrix.mHLinesMap.get(currentUserLine - 1));
            }
            List<LineView> hLineList = new ArrayList<>();

            hLineList.addAll(rightHorizontalLineList);
            hLineList.addAll(leftHorizontalLineList);

            showLog("while hLineList size: " + hLineList.size());

            for (int q = 0; q < hLineList.size(); q++) {
                for (int i = 0; i < hLineList.size() - 1; i++) {

                    if (hLineList.get(i + 1).getY() < hLineList.get(i).getY()) {
                        Collections.swap(hLineList, i, i + 1);
                    }
                }
            }

            for (int i = 0; i < hLineList.size() - 1; i++) {
                showLog("hLine " + i + " x,y= " + hLineList.get(i).getX() + "," + hLineList.get(i).getY());
            }

            boolean hasNextIntersection = false;

            for (LineView hLine : hLineList) {
                /* if hLine is below previous added point
                 * then take this point as intersection point
                 *
                 * */
                if (hLine != null) {
                    /*
                     * Check if this hLine is next intersection line for this User line
                     * */

                    if (hLine.getY() > currentSelectedPoint.y) {
                        /* selected hLine as intersection line */

                        currentSelectedPoint = new Point(currentSelectedPoint.x, Math.round(hLine.getY()));

                        pointList.add(currentSelectedPoint);

                        showLog("currentSelectedPoint 22 x,y= " + currentSelectedPoint);

                        /* find end of this intersecting line
                         * x is current x + width of the line
                         * y is same for horizontal line
                         * */
//                            currentSelectedPoint = new Point(Math.round(currentSelectedPoint.x + hLine.getWidth()), Math.round(hLine.getY()));

                        if (rightHorizontalLineList.contains(hLine)) {
                            /*
                             * Selected User line is at the right side of hLine (previous selected vLine)
                             *
                             * */
                            showLog("currentSelectedPoint Right of User Line. currnt x= "
                                    + currentSelectedPoint.x + "; hLine.Width= " + hLine.getWidth());

                            currentSelectedPoint = new Point(Math.round(currentSelectedPoint.x
                                    + hLine.getWidth() + LINE_THICKNESS), Math.round(hLine.getY()));
                            currentUserLine++; //+ (LINE_THICKNESS * currentUserLine)
                            showLog("currentSelectedPoint Right of User Line.  " + currentSelectedPoint);

                        } else if (leftHorizontalLineList.contains(hLine)) {

                            showLog("currentSelectedPoint Left of User Line. currnt x= "
                                    + currentSelectedPoint.x + "; hLine.Width= " + hLine.getWidth());

                            currentSelectedPoint = new Point(Math.round(currentSelectedPoint.x
                                    - hLine.getWidth() - LINE_THICKNESS), Math.round(hLine.getY()));
                            currentUserLine--;
                            showLog("currentSelectedPoint Left of User Line: " + currentSelectedPoint);
                        } else {
                            showLog("currentSelectedPoint NOT any of User Line: " + currentSelectedPoint);
                        }
                        // mLineMatrix.addPointMap(currentUserLine, currentSelectedPoint);

                        pointList.add(currentSelectedPoint);

                        hasNextIntersection = true;
                        break;
                    }
                }
            }

            showLog("hasNextIntersection " + hasNextIntersection);
            /* Check if END has come */
            if (!hasNextIntersection) {
                currentSelectedPoint = new Point(currentSelectedPoint.x, USER_LINE_LENGTH);
                pointList.add(currentSelectedPoint);
            }

            searchedHeight = currentSelectedPoint.y;

            showLog(" searchedHeight " + searchedHeight);
        }

        // end of while
        showLog("pointList size: " + pointList.size());

        mLineMatrix.addPointListToFinalPathMap(selectedUserLine, pointList);

        for (int i = 0; i < mLineMatrix.getFinalPathMap().get(selectedUserLine).size(); i++) {
            showLog("i= " + i + ". size: " + mLineMatrix.getFinalPathMap().get(selectedUserLine).size());
            showLog("mLineMatrix 11 mPointList x,y= "
                    + mLineMatrix.getFinalPathMap().get(selectedUserLine).get(i));
            showLog("mLineMatrix 1-- pointList x,y= " + pointList.get(i));
        }

    }

    public void relaunchClick(View view) {
        initializeView();
    }

    public void showLogClick(View view) {


        showLog("mLineMatrix");
        if (mLineMatrix.getUserLines().size() != 0) {
            showLog("mLineMatrix ==");
            int vLineNumber = 0;
            for (LineView lv : mLineMatrix.getUserLines()) {
                showLog("mLineMatrix UserLine id= " + lv.getId() + ", x= " + lv.getX() + ", y= " + lv.getY());

                Point startPoint = new Point(Math.round(lv.getX()), Math.round(lv.getY()));
                showLog("mLineMatrix LinePath vLineNumber= " + vLineNumber + ", x= " + startPoint.x + ", y= " + startPoint.y);


                if (mLineMatrix.mHLinesMap.get(vLineNumber) != null) {

                    for (LineView hLine : mLineMatrix.mHLinesMap.get(vLineNumber)) {
                        if (hLine != null) {
                            showLog("mLineMatrix H Line x1,y1 = " + startPoint.x + "," + Math.round(hLine.getY())
                                    + ". x2,y2 = " + (startPoint.x + Math.round(hLine.getWidth())) + "," + Math.round(hLine.getY()));
                        }
                    }
                }
                vLineNumber++;
            }
        }
        if (mLineMatrix.mHLinesMap.size() > 0) {
            showLog("mLineMatrix mHLinesMap ==");
            for (Integer i : mLineMatrix.mHLinesMap.keySet()) {
                showLog("mLineMatrix mHLinesMap size= " + mLineMatrix.mHLinesMap.get(i).size());
                for (int j = 0; j < mLineMatrix.mHLinesMap.get(i).size(); j++) {
                    LineView lv = mLineMatrix.mHLinesMap.get(i).get(j);
                    showLog("mLineMatrix mHLinesMap LineView id= " + lv.getId() + ", x= " + lv.getX() + ", y= " + lv.getY());
                }
            }
        }
        showLog("mLineMatrix circleView id= " + circleView.getId()
                + ", x= " + circleView.getX() + ", y= " + circleView.getY());
    }
}
