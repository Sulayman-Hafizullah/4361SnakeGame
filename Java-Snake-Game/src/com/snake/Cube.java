package com.snake;


import java.awt.Graphics;
import java.awt.Point;




public class Cube {
	int x, y, size, shift;
	Point[] cubeOnePoints;
	Point[] cubeTwoPoints;
	int[] xVals = new int[4];
	int[] yVals = new int[4];

	public Cube(int x, int y, int size, int shift) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.shift = shift;
		cubeOnePoints = getCubeOnePoints();
		cubeTwoPoints = getCubeTwoPoints();
	}

	private Point[] getCubeOnePoints() {
		Point[] points = new Point[4];
		points[0] = new Point(x, y);
		points[1] = new Point(x + size, y);
		points[2] = new Point(x + size, y + size);
		points[3] = new Point(x, y + size);
		return points;
	}

	private Point[] getCubeTwoPoints() {
		int newX = x + shift;
		int newY = y + shift;
		Point[] points = new Point[4];
		points[0] = new Point(newX, newY);
		points[1] = new Point(newX + size, newY);
		points[2] = new Point(newX + size, newY + size);
		points[3] = new Point(newX, newY + size);
		return points;
	}

	public void drawCube(Graphics g) {
		g.drawRect(x, y, size, size);
		// g.setColor(Color.GRAY);
		// left side of cube
		xVals[0] = cubeOnePoints[0].x;
		xVals[1] = cubeOnePoints[3].x;
		yVals[0] = cubeOnePoints[0].y;
		yVals[1] = cubeOnePoints[3].y;
		xVals[2] = cubeTwoPoints[3].x;
		xVals[3] = cubeTwoPoints[0].x;
		yVals[2] = cubeTwoPoints[3].y;
		yVals[3] = cubeTwoPoints[0].y;
		g.fillPolygon(xVals, yVals, 4);

		// top of cube
		// g.setColor(Color.MAGENTA);
		xVals[0] = cubeOnePoints[0].x;
		xVals[1] = cubeOnePoints[1].x;
		xVals[2] = cubeTwoPoints[1].x;
		xVals[3] = cubeTwoPoints[0].x;
		yVals[0] = cubeOnePoints[0].y;
		yVals[1] = cubeOnePoints[1].y;
		yVals[2] = cubeTwoPoints[1].y;
		yVals[3] = cubeTwoPoints[0].y;
		g.fillPolygon(xVals, yVals, 4);
		// g.drawRect(x + shift, y + shift, size, size);
		g.fillRect(x + shift, y + shift, size, size);
		// draw connecting lines
		g.setColor(java.awt.Color.BLACK);
		for (int i = 0; i < 4; i++) {
			if(i !=2) {
			g.drawLine(cubeOnePoints[i].x, cubeOnePoints[i].y, cubeTwoPoints[i].x, cubeTwoPoints[i].y);
			}
			if(i == 3) { //back lines
				//g.drawLine(cubeOnePoints[i].x, cubeOnePoints[i].y, cubeTwoPoints[i].x + 5, cubeOnePoints[i].y);
				g.drawLine(cubeOnePoints[i].x, cubeOnePoints[i].y, cubeOnePoints[i].x, cubeOnePoints[i].y-10);
				g.drawLine(cubeOnePoints[i].x, cubeOnePoints[i].y-10, cubeTwoPoints[i].x + 5, cubeOnePoints[i].y-10);
				//g.drawLine(cubeOnePoints[i].x+10, cubeOnePoints[i].y, cubeOnePoints[i].x+10, cubeOnePoints[i].y-10);
			}
			else if(i == 1) { //top line
				g.drawLine(cubeOnePoints[i].x-5, cubeTwoPoints[i].y-1, cubeTwoPoints[i].x, cubeTwoPoints[i].y-1);
				//front right
				g.drawLine(cubeTwoPoints[i].x, cubeTwoPoints[i].y-1, cubeTwoPoints[i].x, cubeTwoPoints[i].y+10);
			}
			else if(i == 2) { //front bottom
				g.drawLine(cubeTwoPoints[i].x, cubeTwoPoints[i].y, cubeOnePoints[i].x-5, cubeTwoPoints[i].y);
				//front left line
				g.drawLine(cubeOnePoints[i].x-5, cubeTwoPoints[i].y, cubeOnePoints[i].x-5, cubeTwoPoints[i].y-11);
			}
		}

	}

}
