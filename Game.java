import java.awt.Color;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {

		int choix=0;
		Menu M1=new Menu(); //ETAT=0
		M1.MenuOn(); //ETAT=1
		while (M1.getEtat()==1){
			System.out.print("");
		}
		choix=M1.getChoix();
		
		JFrame Fenetre=new JFrame();	
		Fenetre.setSize(800,800);
		Fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Fenetre.getContentPane().setLayout(null);
		Fenetre.setResizable(false);
		Terrain main=new Terrain(choix);
		main.setBounds(0,0,600,600);
		
		ControlPanel control=new ControlPanel(main);
		control.setBounds(600,0,200,600);
		
		main.addControlPanel(control);
		
		ScorePanel score=new ScorePanel(main);
		score.setBounds(0,600,800,200);	
		
	

		Fenetre.getContentPane().add(main);
		Fenetre.getContentPane().add(control);
		Fenetre.getContentPane().add(score);
	
		Fenetre.setVisible(true);

	}
}