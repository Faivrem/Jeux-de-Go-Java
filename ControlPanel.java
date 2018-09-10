import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ControlPanel extends JPanel implements ActionListener{

	private Terrain main = null;
	private	JButton retour = new JButton("Retour arrière");
	private	JButton passer = new JButton("Passez son tour");
	private JButton abandonner = new JButton("Abandonner");
	long CYCLE=(System.currentTimeMillis()/1000)+1; 
	int minute=0;
	int seconde=0;
	private JLabel time=new JLabel(this.minute +"min"+ this.seconde+"s");
	private JTextArea histo =null;
	private final JScrollPane barre= new JScrollPane();
	private int flag=0; // recommencer chrono


	public ControlPanel(Terrain main){
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		this.retour.setBounds(10,50,150,50);	
		this.passer.setBounds(10,200,150,50);	
		this.time.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 30));
		this.time.setBounds(10,284, 153, 76);

		this.add(this.time);
		this.add(this.retour);
		this.add(this.passer);
		this.add(barre);


		this.abandonner.setBounds(30, 532, 117, 29);
		this.add(this.abandonner);

		passer.addActionListener(this);
		retour.addActionListener(this);
		abandonner.addActionListener(this);

		this.main=main;
		barre.setBounds(20, 396, 144, 132);

		this.histo = new JTextArea();
		barre.setViewportView(histo);
		histo.setWrapStyleWord(true);
		histo.setEditable(false);
		histo.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		histo.setLineWrap(true);
		histo.setVisible(true);
		histo.setCaretPosition(histo.getDocument().getLength ()); 

		JLabel lblHistorique = new JLabel("Historique:");
		barre.setColumnHeaderView(lblHistorique);




	}
	public void setFlag(int n){
		this.flag=n;
	}
	public void majHisto(){ // rajoute une ligne a chaque fois qu'un coup est joué
		int i= this.main.getGoban().getSize()-1;
		this.histo.append("Tour "+(i+1)+") Joueur "+main.getGoban().tab[i].getJoueur()+" a joué en X:"+(main.getGoban().tab[i].pgetX())/this.main.getCarre()+", Y:"+(main.getGoban().tab[i].pgetY())/this.main.getCarre()+"\n");
	}
	public void supHisto(){ // supprime une ligne a chaque retour arrière
		String[] lignes = this.histo.getText().split("\n");

		StringBuilder copie = new StringBuilder();
		// Ajoute toute les lignes sauf la dernière
		for(int i=0; i<lignes.length-1; i++){
			copie.append(lignes[i]);
			copie.append("\n");
		} 


		this.histo.setText(copie.toString()); // remplace dans histo - la derniere ligne
	}
	public void temps(){
		if (this.flag==0){
			this.minute=0;
			this.seconde=0;
			this.flag=1;
			this.time.setText(this.minute +"min"+ this.seconde+"s");
		}
		// temps en secondes ex 210
		//System.out.println("min : "+this.minute+"seconde; " + this.seconde + " s");
		if (System.currentTimeMillis()/1000>=this.CYCLE){
			CYCLE=CYCLE+1;
			this.seconde++;
			if (this.seconde>59){
				this.seconde=0;
				this.minute++;
			}
			if (this.minute>3){
				this.main.passerSonTour();
				//this.seconde=0;
				//this.minute=0;
			}
			//System.out.println("min : "+this.minute+"seconde; " + this.seconde + " s");
			this.time.setText(this.minute +"min"+ this.seconde+"s");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==retour){
			if(this.main.onoff()==true){
				if (this.main.getGoban().retour()==true){
					this.supHisto();
					this.main.reniSouris(); 
				}
			}
		}
		if (e.getSource()==passer){
			if (this.main.onoff()==true){
				this.main.passerSonTour();
				this.flag=0; // remet le chrono a zero
				this.main.reniSouris();
			}
		}
		if (e.getSource()==abandonner){
			this.main.off();
		}


	}
}
