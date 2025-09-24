package edu.ccrm.domain;

public enum Grade {
    S(10), A(9), B(8), C(7), D(6), F(0);

    private int points;
    Grade(int points) { this.points = points; }

    
    public int getPoints() { 
        return points; 
    }
}
