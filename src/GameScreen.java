package fr.constantechard.ludo;

import javax.swing.JFrame;

public class GameScreen{
	public static void main(String[] args){
		JRframe jframe = new JFrame();
		jframe.setBounds(10, 10, 1000, 600);
		jframe.setTitle("LUDO by Julien CONSTANT & Noé ECHARD");
		jframe.setResizable(false);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);

		//TODO le paramétrage avec le gamemove
	}
}