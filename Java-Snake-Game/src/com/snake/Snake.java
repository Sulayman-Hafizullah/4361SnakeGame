package com.snake;

import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.JFrame;


public class Snake extends JFrame {

    public Snake() throws IOException {
        
        initUI();
    }
    
    private void initUI() throws IOException {
        
        add(new Board());
               
        setResizable(false);
        pack();
        
        setTitle("3D Snake Game");
        //setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:/Users/Sulayman/Desktop/CS4361/4361SnakeGame/Java-Snake-Game/src/resources/sand.jpg")))));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex;
			try {
				ex = new Snake();
				ex.setVisible(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           // ex.setVisible(true);
        });
    }
}

