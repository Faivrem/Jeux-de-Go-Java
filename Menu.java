import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{

	private int choix;
	private int etat;
	private JRadioButton radio1=new JRadioButton("9x9");
	private JRadioButton radio2=new JRadioButton("13x13");
	private JRadioButton radio3=new JRadioButton("19x19");


	public Menu() {
		
		this.choix=1;
		JPanel panneau=new JPanel();
		panneau.setBackground(Color.LIGHT_GRAY);
		panneau.setBounds(0, 0, 300, 278);
		this.setSize(300,300);
		this.setTitle("Menu");
		this.setLocation(100,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		// Boutons
		
		radio1.setFont(new Font("Malayalam MN", Font.PLAIN, 16));
		radio1.setForeground(new Color(0, 102, 51));
		radio1.setBounds(10, 76, 77, 84);
		
		radio2.setFont(new Font("Malayalam MN", Font.PLAIN, 16));
		radio2.setForeground(new Color(0, 102, 204));
		radio2.setBounds(99, 76, 91, 84);
		radio3.setFont(new Font("Malayalam MN", Font.PLAIN, 16));
		radio3.setForeground(new Color(204, 0, 51));
		radio3.setBounds(189, 76, 111, 84);

		ButtonGroup bg=new ButtonGroup();
		bg.add(radio1);
		bg.add(radio2);
		bg.add(radio3);
		panneau.setLayout(null);

		panneau.add(radio1);
		panneau.add(radio2);
		panneau.add(radio3);
		this.getContentPane().setLayout(null);
		this.getContentPane().add(panneau);

		JLabel question=new JLabel("Quel terrain choississez-vous?");
		question.setFont(new Font("Malayalam MN", Font.PLAIN, 15));
		question.setBounds(0, 6, 300, 84);
		panneau.add(question);
		question.setHorizontalAlignment(JLabel.CENTER);

		JButton valider=new JButton("Valider");
		valider.setFont(new Font("Malayalam MN", Font.PLAIN, 15));
		valider.setBounds(55, 172, 191, 61);
		panneau.add(valider);
		
		radio1.addActionListener(new TraitementBut1());
		radio2.addActionListener(new TraitementBut2());
		radio3.addActionListener(new TraitementBut3());
		valider.addActionListener(new Traitementvalider());
		
	
		
		
		}
	public void MenuOn(){
		this.setVisible(true);
		this.etat=1;
	}
	public void MenuOf(){
		this.setVisible(false);
		this.etat=0;
	}
		
	public int getEtat(){
		return this.etat;
		
	}

	public int getChoix(){
		return this.choix;


	}

    public  class   TraitementBut1 implements   ActionListener{
       public  void    actionPerformed(ActionEvent e)
       {
           choix=1;
       }
   }
    public  class   TraitementBut2 implements   ActionListener{
        public  void    actionPerformed(ActionEvent e)
        {
            choix=2;
        }
    }
    public  class   TraitementBut3 implements   ActionListener{
        public  void    actionPerformed(ActionEvent e)
        {
            choix=3; 
        }
    }
    public  class   Traitementvalider implements   ActionListener{
    	
        public  void    actionPerformed(ActionEvent e)
        {
            setVisible(false); 
            etat=0; 
        }
    }
    
}
