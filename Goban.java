import java.util.ArrayList;

public class Goban {

	public Pierre[] tab; // Pierre stocké dans l'ordre dans lequel elle sont joué avec des X et Y absolu ( ex x=340 et y=430)
	public Pierre[][] coord; // Pierre stocké en fonction de leur coordonné sur le terrain (ex coord[12][10] : x=12 y=10 
	public int nbredePierre; 
	public int nextCase; // Nombre de pixel pour passer à la prochaine intersections
	public int nbreCase; 
	public int nbrePointJoueur1;
	public int nbrePointJoueur2;
	public Terrain main;
	public int convertir; // utilisé pour convertir les X absolu en coordonné x ( ex X=12*convertir ou x=12/convertir)
	public int taille; // taille choisi grace au menu
	public ArrayList<Groupe> listeGroupe=new ArrayList<Groupe>(); // Liste des groupes du Goban

	Goban(int nextCase,int taille,Terrain main){
		this.nbredePierre=0;
		this.nbreCase=(taille+1)*(taille+1);
		this.nextCase=nextCase;
		this.tab=new Pierre[this.nbreCase];
		this.taille=taille+1;
		this.coord=new Pierre[this.taille+2][this.taille+2]; // +2 car les bord font aussi parti des coordonés

		this.main=main;
		this.nbrePointJoueur1=0;
		this.nbrePointJoueur2=0;
		this.convertir=this.main.getCarre();
	}
	public int getSize(){
		return this.nbredePierre;
	}


	public boolean dispoPierre(int x,int y){ //Verifie si les coordonné ne sont pas déja prises
		for (int i=0;i<this.nbredePierre;i++){
			if (x==this.tab[i].pgetX() && y==this.tab[i].pgetY() ){
				return false;
			}
		}
		return true;
	}
	public boolean verifierSuicide(int x,int y,int joueur){ 
		int coo=0;
		for (int i=0;i<this.nbredePierre;i++){
			if(y==this.tab[i].pgetY()){
				if((x==this.tab[i].pgetX()+this.nextCase || x==this.tab[i].pgetX()-this.nextCase)){// && joueur!=this.tab[i].getJoueur()){ //Probleme avec la fusion de groupe
					coo++;
				}
			}
			if(x==this.tab[i].pgetX()){
				if((y==this.tab[i].pgetY()+this.nextCase || y==this.tab[i].pgetY()-this.nextCase)){// && joueur!=this.tab[i].getJoueur()){ // Probleme avec la fusion de groupe
					coo++;
				}
			}
		}
		if (coo>=2){
			if (x==this.main.getmaxLarg() || x==this.main.getminLarg() && (y==this.main.getminLong() || y==this.main.getmaxLong())){
				return false;
			}
			if (x==this.main.getmaxLarg() || x==this.main.getminLarg() || y==this.main.getminLong() || y==this.main.getmaxLong()){
				if (coo==3){
					return false;
				}
			}
			if (coo==4){
				return false;
			}
		}
		return true;
	}

	public boolean ajoutPierre(int x,int y,int joueur){
		if ((dispoPierre(x,y)) && verifierSuicide(x,y,joueur)==true){
			this.tab[this.nbredePierre]=new Pierre(x,y,joueur);
			this.nbredePierre=this.nbredePierre+1;
			x=(x/convertir);
			y=(y/convertir);
			this.coord[x][y]=new Pierre(x,y,joueur);
			this.listeGroupe.add(new Groupe(this.coord[x][y]));
			this.calculerLib();
			detecterGroupe(this.coord[x][y]);
			//afficherListGroup();
			return true;
		}
		else 
			return false;
	}
	public void detecterGroupe(Pierre P){
		if(this.coord[P.pgetX()+1][P.pgetY()]!=null && this.coord[P.pgetX()+1][P.pgetY()].getJoueur()==P.getJoueur()){
			trouverGroupe(P).fusionnerDeuxGroupe(trouverGroupe(this.coord[P.pgetX()+1][P.pgetY()]));
			this.listeGroupe.remove(trouverGroupe(this.coord[P.pgetX()+1][P.pgetY()]));
		}

		if(this.coord[P.pgetX()-1][P.pgetY()]!=null && this.coord[P.pgetX()-1][P.pgetY()].getJoueur()==P.getJoueur()){
			trouverGroupe(P).fusionnerDeuxGroupe(trouverGroupe(this.coord[P.pgetX()-1][P.pgetY()]));
			this.listeGroupe.remove(trouverGroupe(this.coord[P.pgetX()-1][P.pgetY()]));
		}
		if(this.coord[P.pgetX()][P.pgetY()+1]!=null && this.coord[P.pgetX()][P.pgetY()+1].getJoueur()==P.getJoueur()){
			trouverGroupe(P).fusionnerDeuxGroupe(trouverGroupe(this.coord[P.pgetX()][P.pgetY()+1]));
			this.listeGroupe.remove(trouverGroupe(this.coord[P.pgetX()][P.pgetY()+1]));
		}
		if(this.coord[P.pgetX()][P.pgetY()-1]!=null && this.coord[P.pgetX()][P.pgetY()-1].getJoueur()==P.getJoueur()){
			trouverGroupe(P).fusionnerDeuxGroupe(trouverGroupe(this.coord[P.pgetX()][P.pgetY()-1]));
			this.listeGroupe.remove(trouverGroupe(this.coord[P.pgetX()][P.pgetY()-1]));
		}
	}

	public Groupe trouverGroupe(Pierre P){
		for (int i=0;i<this.listeGroupe.size();i++){
			if(this.listeGroupe.get(i).getJoueur()==P.getJoueur()){
				for (int j=0;j<this.listeGroupe.get(i).getList().size();j++){
					if (this.listeGroupe.get(i).getList().get(j)==P){
						return this.listeGroupe.get(i);
					}
				}
			}
		}
		return null;
	}

	public void Capture(){
		this.calculerLib();
		int compteur=0;
		for (int i=0;i<this.listeGroupe.size();i++){
			this.calculerLib();
			for (int j=0;j<this.listeGroupe.get(i).getList().size();j++){

				if(this.listeGroupe.get(i).getList().get(j).getLib()==0){
					compteur++;
				}
			}
			if(compteur==this.listeGroupe.get(i).getList().size()){
				for (int h=0;h<this.listeGroupe.get(i).getList().size();h++){
					if(this.listeGroupe.get(i).getList().get(h).getJoueur()==1){
						this.nbrePointJoueur2=this.nbrePointJoueur2+1;
					}
					else {
						this.nbrePointJoueur1=this.nbrePointJoueur1+1;
					}
					supprimerPierre(this.listeGroupe.get(i).getList().get(h));


				}
				this.listeGroupe.remove(i);
				this.calculerLib();

			}
			compteur=0;

		}
	}

	public void afficherListGroup(){
		for (int i=0;i<this.listeGroupe.size();i++){
			System.out.println("NUMERO "+(i));
			this.listeGroupe.get(i).afficherGroupe();
		}
	}

	public void supprimerPierre(Pierre P){
		int pos=0;

		//x=12
		//y=10
		// la pierre en mode [12][3] 
		this.coord[P.pgetX()][P.pgetY()]=null;

		for (int i=0;i<this.nbredePierre;i++){
			if(this.tab[i].pgetX()==((P.pgetX()*convertir)) && this.tab[i].pgetY()==((P.pgetY()*convertir))) {
				pos=i;
			}
		}
		for (int i=pos+1;i<this.nbredePierre;i++){
			tab[i-1]=tab[i];
		}
		this.tab[this.nbredePierre-1]=null;
		this.nbredePierre=this.nbredePierre-1;
	}
	public void afficherPierre(){
		for (int i=0;i<this.nbredePierre;i++){
			System.out.println("X"+this.coord[(this.tab[i].pgetX()/convertir)][(this.tab[i].pgetY()/convertir)].pgetX());
			System.out.println("Y:"+this.coord[(this.tab[i].pgetX()/convertir)][(this.tab[i].pgetY()/convertir)].pgetY());
			System.out.println("Lib:"+this.coord[(this.tab[i].pgetX()/convertir)][(this.tab[i].pgetY()/convertir)].getLib());
		}



	}
	public boolean retour(){
			if(this.nbredePierre>0){
				supprimerPierre(this.coord[this.tab[this.nbredePierre-1].pgetX()/convertir][this.tab[this.nbredePierre-1].pgetY()/convertir]);
				if (this.main.getJoueur()==1){
					this.main.setJoueur(2);
				}
				else
					this.main.setJoueur(1);
			}
		return true;
	}

	public void calculerLib(){
		if (this.nbredePierre>1){
			for (int i=0;i<this.nbredePierre;i++){
				int liberte=4;
				for (int j=0;j<this.nbredePierre;j++){
					if (this.tab[i].pgetX()==this.tab[j].pgetX()){
						if(this.tab[i].pgetY()==this.tab[j].pgetY()+this.nextCase || this.tab[i].pgetY()==this.tab[j].pgetY()-this.nextCase){
							liberte=liberte-1;
						}
					}
					if(this.tab[i].pgetY()==this.tab[j].pgetY()){
						if (this.tab[i].pgetX()==this.tab[j].pgetX()+this.nextCase || this.tab[i].pgetX()==this.tab[j].pgetX()-this.nextCase){
							liberte=liberte-1;
						}
					}
				}
				if (this.tab[i].pgetX()==this.main.getmaxLarg() || this.tab[i].pgetX()==this.main.getminLarg()){
					liberte=liberte-1;
				}
				if (this.tab[i].pgetY()==this.main.getmaxLong() || this.tab[i].pgetY()==this.main.getminLong()){
					liberte=liberte-1;
				}
				this.tab[i].setLib(liberte);
				this.coord[(this.tab[i].pgetX()/convertir)][(this.tab[i].pgetY()/convertir)].setLib(liberte);
			}
		}
	}
}
