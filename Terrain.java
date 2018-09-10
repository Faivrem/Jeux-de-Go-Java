
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Terrain extends JPanel implements MouseListener{

	private int taille; // 9x9 , 13x13 , 19x19
	private int etatsouris; //souris cliqué ou non
	private int taille_carre; //60 , 40, 30
	private int x_souris; 
	private int y_souris;
	private int joueur; // Joueur actif
	private Goban g; 
	private int abandon; //si ==2 alors parti terminé
	private boolean jeux; // évaluer a true si la partie en cours

	private ControlPanel control;

	private int maxLarg; // ex: 9*60=540  // 480x480
	private int maxLong;		// 540
	private int minLarg; // 60
	private int minLong; //

	public Terrain(int n){
		setBackground(new Color(204, 102, 51));
		//Interface avec entier

		this.setLocation(100,100);
		if (n==1){
			this.taille=8;
			this.taille_carre=60;
		}

		if (n==2){
			this.taille=12;
			this.taille_carre=40;
		}
		if (n==3){
			this.taille=18;
			this.taille_carre=30;
		}
		this.setSize(600,700);
		this.joueur=1;
		this.etatsouris=0;
		this.jeux=true;
		this.g=new Goban(this.taille_carre,this.taille,this);
		addMouseListener(this);


	}           

	//Accesseurs//Setteur
	public int getTaille(){
		return this.taille;
	}
	public int getCarre(){
		return this.taille_carre;
	}
	public Goban getGoban(){
		return this.g;
	}
	public int getJoueur() {
		return this.joueur;
	}
	public void setJoueur(int n){
		this.joueur=n;
	}
	public boolean onoff(){
		return this.jeux;
	}
	public void off(){
		this.jeux=false;
	}
	public int getmaxLarg(){
		return this.maxLarg;
	}
	public int getminLarg(){
		return this.minLarg;
	}
	public int getmaxLong(){
		return this.maxLong;
	}
	public int getminLong(){
		return this.minLarg;
	}
	public void reniSouris(){
		this.x_souris=0;
		this.y_souris=0;
	}
	public void repeindre(){
		super.repaint();
	}
	//METHODE
	public void addControlPanel(ControlPanel P){
		this.control=P;
	}
	public void paintComponent(Graphics g){// DESSIN GRILLE

		this.maxLarg=(this.taille+1)*taille_carre; // ex: 9*60=540  // 480x480
		this.maxLong=maxLarg; 			// 540
		this.minLarg=this.taille_carre; // 60
		this.minLong=this.taille_carre; // 60

		int hoshi=(this.taille*taille_carre)/2;// 240 pixel entre les 2 // coté du carré
		if (this.taille==18){
			hoshi=(12*taille_carre)/2;
		}
		int bordure_hoshi=this.taille_carre*(this.taille/4);  // 120
		if (this.taille==18){
			bordure_hoshi=this.taille_carre*(12/4);
		}
		int cercle=10;
		int poscercle=cercle/2;
		int pierre=20;
		int pospierre=pierre/2;

		g.setColor(Color.BLACK);
		super.paintComponent(g);
		for(int iX = 1; iX <=this.taille+1; iX++){
			for(int iY = 1; iY <=this.taille+1; iY++){
				//Dessiner ---------
				g.drawLine(this.minLarg, (iY * taille_carre),this.maxLarg, (iY * taille_carre));
				//Dessiner | | | | |
				g.drawLine((iX * taille_carre),this.minLong, (iX * taille_carre), this.maxLong);

			}
		}
		for (int j=this.minLong+bordure_hoshi;j<=this.maxLong-bordure_hoshi;j=j+hoshi){ // Hoshi
			for (int i=this.minLarg+bordure_hoshi;i<=this.maxLarg-bordure_hoshi;i=i+hoshi){
				g.fillOval(i-poscercle,j-poscercle, cercle,cercle);
			}
		}
		g.fillOval(this.minLarg+((this.maxLarg-this.minLarg)/2)-poscercle,this.minLong+((this.maxLong-this.minLong)/2)-poscercle, cercle, cercle);


		if(etatsouris==1){
			if (jeux==true){
				this.control.temps();
				int ecart=this.taille_carre/2;
				centrerPosition();
				if(this.x_souris<=this.maxLarg+ecart && this.x_souris>=this.minLarg-ecart && this.y_souris<=this.maxLong+ecart && this.y_souris>=this.minLong-ecart){

					if (this.g.ajoutPierre(this.x_souris,this.y_souris,this.joueur)==true){
						this.x_souris=0;
						this.y_souris=0;
						this.abandon=0;
						this.control.setFlag(0);
						this.control.majHisto();
						this.g.calculerLib();
						this.g.Capture();
						if(joueur==1){
							joueur=2;
						}
						else{
							joueur=1;
						}
					}
				}
			repaint();
			}
			for (int i=0;i<this.g.getSize();i++){
				if (this.g.tab[i].getJoueur()==1){
					g.setColor(Color.GRAY);
				}
				if (this.g.tab[i].getJoueur()==2){
					g.setColor(Color.YELLOW);
				}

				g.fillOval(this.g.tab[i].pgetX()-pospierre,this.g.tab[i].pgetY()-pospierre,pierre,pierre);
			}
		
		}
		if(this.jeux==false){
			g.setColor(Color.BLACK);
			if (this.g.nbrePointJoueur1>this.g.nbrePointJoueur2){
				g.drawString("Le gagnant est le joueur 1",20,this.maxLong+20);
			}
			if (this.g.nbrePointJoueur1<this.g.nbrePointJoueur2){
				g.drawString("Le gagnant est le joueur 2",20,this.maxLong+20);
			}
			if (this.g.nbrePointJoueur1==this.g.nbrePointJoueur2){
				g.drawString("Les joueurs sont a égalité",20,this.maxLong+20);
			}
		}
	}
	public void centrerPosition(){ // Centre la position du clique de la souris sur les intersections 
		int ecart=this.taille_carre/2;	
		for (int POS=this.minLarg ;POS<=this.maxLarg;POS=POS+this.taille_carre){
			if(this.x_souris>POS-ecart && this.x_souris<=POS+ecart){
				this.x_souris=POS;
			}
			if (this.y_souris>POS-ecart && this.y_souris<=POS+ecart){
				this.y_souris=POS;
			}
		}

	}
	public void passerSonTour(){
		if (this.joueur==1){
			this.joueur=2;
		}
		else 
			this.joueur=1;
		this.control.setFlag(0); // remet le chronomètre a 0
		this.abandon++; 
		if (this.abandon==2){ // si les 2 joueurs ont passé leur tour
			this.off();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		//System.out.printf("(x=%d,y=%d)",e.getX(),e.getY());
		this.x_souris=e.getX();
		this.y_souris=e.getY();
		this.etatsouris=1;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.etatsouris=0;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}





}
