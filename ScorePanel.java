import java.awt.Graphics;
import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Color;

public class ScorePanel extends JPanel {

	private JLabel joueur1 = null;
	private JLabel joueur2 = null;
	JLabel score1 = new JLabel("Score:"+this.ptj2);
	JLabel score2 = new JLabel("Score:"+this.ptj2);
	private Terrain main=null;
	private int ptj1=0;
	private int ptj2=0;
	private int ecart=10;

	public ScorePanel(Terrain main){
		setBackground(Color.WHITE);
		this.setLayout(null);
		this.main=main;
		this.ptj1=this.main.getGoban().nbrePointJoueur1;
		this.ptj2=this.main.getGoban().nbrePointJoueur2;
		this.joueur1=new JLabel("Joueur 1:");
		joueur1.setForeground(Color.GRAY);
		this.joueur2=new JLabel("Joueur 2:");
		joueur2.setForeground(Color.YELLOW);
		joueur1.setFont(new Font("Marlett", Font.PLAIN, 16));
		joueur2.setFont(new Font("Marlett", Font.PLAIN, 16));


		this.joueur1.setBounds(6, 10, 100, 50);
		this.joueur2.setBounds(6,110, 100, 50);

		this.add(joueur1);
		this.add(joueur2);

		score1.setBounds(6, 55, 61, 16);
		score2.setBounds(6, 161, 61, 16);
		this.add(score2);
		this.add(score1);
	}
	public void paintComponent(Graphics g){
		super.repaint();
		if(this.main.getJoueur()==1){
			g.drawLine(6, 50, 80,50);	
		}
		else 
			g.drawLine(6,150,80,150);

		ecart=10;
		this.ptj1=this.main.getGoban().nbrePointJoueur1;
		this.ptj2=this.main.getGoban().nbrePointJoueur2;
		score();
		if(this.ptj1>0){
			g.setColor(Color.YELLOW);
			for (int i=0;i<this.ptj1;i++)
			{
				g.fillOval(90+ecart,30, 10, 10);
				ecart=ecart+10;
			}
			ecart=10;
		}
		if (this.ptj2>0){
			g.setColor(Color.GRAY);
			for (int i=0;i<this.ptj2;i++)
			{
				g.fillOval(90+ecart,130, 10, 10);
				ecart=ecart+10;

			}
		}
	}
	public void score(){
		this.score1.setText("Score:"+this.ptj1);
		this.score2.setText("Score:"+this.ptj2);
	}
}
