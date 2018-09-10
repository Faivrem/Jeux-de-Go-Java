import java.util.ArrayList;

public class Groupe {
	public ArrayList<Pierre> listePierre = new ArrayList<Pierre>(); //Liste de pierre du groupe
	public int joueur; // Joueur du groupe

	/*public static void main(String[] args){
		Pierre P=new Pierre(1,2,2);
		Pierre P1=new Pierre(2,3,2);
		Pierre P2=new Pierre(12,3,2);
		Groupe groupe=new Groupe(P);
		Groupe gro=new Groupe(P1);
		groupe.fusionnerDeuxGroupe(gro);
		groupe.ajouterPierre(P2);
		groupe.afficherGroupe();
		Pierre P3=new Pierre(200,21,2);
		Pierre P4=new Pierre(200,32,2);
		Pierre P5=new Pierre(200,320,1);
		Groupe gros=new Groupe(P3);
		gros.ajouterPierre(P4);
		gros.ajouterPierre(P5);
		gros.afficherGroupe();
		groupe.fusionnerDeuxGroupe(gros);
		groupe.afficherGroupe();

	}*/
	public int getJoueur(){
		return this.joueur;
	}

	public Groupe(Pierre P){
		this.listePierre.add(P);
		this.joueur=P.getJoueur();
	}

	public ArrayList<Pierre> getList(){
		return this.listePierre;
	}

	boolean estPresent(Pierre P){ //Si la pierre P est présent dans le groupe
		for (int i=0;i<listePierre.size();i++){
			if (listePierre.get(i)==P){
				return true;
			}
		} 
		return false;
	}
	
	void supprimerGroupe(){
		this.listePierre=null;
	}
	void fusionnerDeuxGroupe(Groupe group){ // Fusionne 2 groupes 
		if (this.getJoueur()==group.getJoueur()){
			this.listePierre.addAll(group.getList());
		}
	}

	void afficherGroupe(){
		System.out.println("Groupe de taille "+this.listePierre.size()+"   Du joueur:"+this.joueur+ "avec les pierres:");
		for (int i=0;i<this.listePierre.size();i++){
			System.out.println("Pierre num:" +i+ "avec X="+this.listePierre.get(i).pgetX()+"et Y="+this.listePierre.get(i).pgetY() +"  disposant des liberté:"+this.listePierre.get(i).getLib());
		}
	}

}
