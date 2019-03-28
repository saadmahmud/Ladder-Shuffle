package com.saadmahmud.laddershuffle;

import android.graphics.Point;

import com.saadmahmud.laddershuffle.customview.LineView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineMatrix {

    Point startPoint;
    List<Point> mPathPoints = new ArrayList<>();
    List<LineView> mUserLines = new ArrayList<>();
    Map<Integer, List<LineView>> mHLinesMap = new HashMap<>();
    private Map<Integer, List<Point>> mFinalPathMap = new HashMap<>();

    Point endPoint;

    public LineMatrix() {
    }

    public List<Point> getPathPoints() {
        return mPathPoints;
    }

    public void addPathPoints(Point mPathPoints) {
        this.mPathPoints.add(mPathPoints);
    }

    public List<LineView> getUserLines() {
        return mUserLines;
    }

    public void addUserLines(LineView mUserLines) {
        this.mUserLines.add(mUserLines);
    }

    public void addHorizontalLine(Integer pos, LineView mHorizontalLine) {
        if (this.mHLinesMap.get(pos) == null) {
            this.mHLinesMap.put(pos, new ArrayList<LineView>());
        }
        this.mHLinesMap.get(pos).add(mHorizontalLine);
    }

//    public void addPointMap(Integer pos, Point nextPoint) {
//        if (this.mFinalPathMap.get(pos) == null) {
//            this.mFinalPathMap.put(pos, new ArrayList<Point>());
//        }
//        this.mFinalPathMap.get(pos).add(nextPoint);
//        showLog("mLineMatrix 00 addPointMap nextPoint x,y= "
//                + mFinalPathMap.get(pos).get(mFinalPathMap.get(pos).size() - 1)
//                + ". size: " + mFinalPathMap.get(pos).size());
//
//        for (int i = 0; i < mFinalPathMap.get(pos).size(); i++) {
//            showLog("mLineMatrix 01 addPointMap nextPoint i= " + i
//                    + ". x,y= " + mFinalPathMap.get(pos).get(i));
//        }
//    }

    public void addPointListToFinalPathMap(Integer pos, List<Point> pointList) {

        this.mFinalPathMap.put(pos, pointList);
    }

    public Map<Integer, List<Point>> getFinalPathMap() {
        return mFinalPathMap;
    }
}
