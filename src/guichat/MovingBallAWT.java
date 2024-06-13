package guichat;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

//配列で5つのボールを動かしてみよう

public class MovingBallAWT {
	public static void main(String[] args) {
		FFrame f = new FFrame();
		f.setSize(500, 500);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.show();
	}

	static class FFrame extends Frame implements Runnable {

		Random random = new Random();
		Thread th;
		Ball myBall1;
		Ball myBall2;

		private boolean enable = true;
		private int counter = 0;
		private int changeCounter = 100;

		FFrame() {
			th = new Thread(this);
			th.start();
		}

		public void run() {

			myBall1 = new Ball();
			myBall1.setPosition(200, 150);
			myBall1.setR(random.nextInt(30, 70));
			myBall1.setColor(Color.RED);

			myBall2 = new Ball();
			myBall2.setPosition(50, 250);
			myBall2.setR(random.nextInt(30, 70));
			myBall2.setColor(Color.GREEN);

			while (enable) {

				try {
					th.sleep(100);
					counter++;
					if (counter % changeCounter == 0) {
						myBall1.setR(random.nextInt(30, 70));
						myBall2.setR(random.nextInt(30, 70));
					}
					if (counter >= 600)
						enable = false;
				} catch (InterruptedException e) {
				}

				myBall1.move(myBall1.getR());
				myBall2.move(myBall2.getR());

				repaint(); // paint()メソッドが呼び出される
			}
		}

		public void paint(Graphics g) {
			myBall1.draw(g);
			myBall2.draw(g);
		}

		// Ball というインナークラスを作る
		class Ball {
			float x;
			float y;
			int r; // 半径
			Color c = Color.RED;

			int xDir = 1; // 1:+方向 -1: -方向
			int yDir = 1;

			void setColor(Color c) {
				this.c = c;
			}

			void move(int r) {

				if ((xDir == 1) && (x >= 500-r*2)) {
					xDir = -1;
				}
				if ((xDir == -1) && (x <= 0)) {
					xDir = 1;
				}

				if (xDir == 1) {
					x = x + 10;
				} else {
					x = x - 10;
				}

				if ((yDir == 1) && (y >= 500-r*2)) {
					yDir = -1;
				}
				if ((yDir == -1) && (y <= r)) {
					yDir = 1;
				}

				if (yDir == 1) {
					y = y + 10;
				} else {
					y = y - 10;
				}

			}

			void setPosition(float x, float y) {
				this.x = x;
				this.y = y;
			}

			void setR(int r) {
				this.r = r;
			}

			int getR(){
				return r;
			}

			void draw(Graphics g) {
				g.setColor(c);
				g.fillOval((int)x, (int)y, 2 * r, 2 * r); // rは半径なので2倍にする
			}

		}// innner class Ball end

	}

}
