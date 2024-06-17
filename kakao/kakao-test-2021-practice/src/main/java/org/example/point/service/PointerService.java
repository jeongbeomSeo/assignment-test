package org.example.point.service;

import org.example.point.model.Point;

public class PointerService {

    public static int getDist(Point point1, Point point2) {
        return Math.abs(point1.row - point2.row) + Math.abs(point1.col - point2.col);
    }
    public static boolean isValidPoint(int row, int col, int upperRow, int lowerRow, int leftCol, int rightCol) {
        return row >= lowerRow && col >= leftCol && row <= upperRow && col <= rightCol;
    }
    public static Point convertIdToPoint(Integer problem, int id) {
        int row;
        int col;
        if (problem == 1) {
            row = id % 5;
            col = id / 5;
        } else {
            row = id % 60;
            col = id / 60;
        }
        return new Point(row, col);
    }

    public static Integer convertPointToId(Integer problem, Point point) {
        if (problem == 1) {
            return point.col * 5 + point.row;
        } else {
            return point.col * 60 + point.row;
        }
    }
    public static Integer convertPointToId(Integer problem, int row, int col) {
        if (problem == 1) {
            return col * 5 + row;
        } else {
            return col * 60 + row;
        }
    }
}
