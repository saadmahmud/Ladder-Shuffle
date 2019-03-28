package com.saadmahmud.laddershuffle;

import android.graphics.Point;

import com.saadmahmud.laddershuffle.customview.LineView;

import java.util.Map;

public class LineProperties {
    LineView lineView;

    /* vertical line intersecting this lineView.
     * Integer - to determine whether intersection lines are in left or right side of Line
     *
     * 0 = to left side
     * 1 = to right side of the Line
     *
     * */

    Map<Integer, Point> intersectingLines;

    public LineProperties(LineView lineView) {
        this.lineView = lineView;
    }

    public LineView getLineView() {
        return lineView;
    }

    public void setLineView(LineView lineView) {
        this.lineView = lineView;
    }

    public Map<Integer, Point> getIntersectingLines() {
        return intersectingLines;
    }

    public void setIntersectingLines(Map<Integer, Point> intersectingLines) {
        this.intersectingLines = intersectingLines;
    }
}
