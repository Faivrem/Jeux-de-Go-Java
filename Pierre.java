
public class Pierre {

	public int x;
	public int y;
	public int joueur;
	public int lib;
	
	public Pierre(int x,int y,int joueur){
		this.x=x;
		this.y=y;
		this.lib=4;
		this.joueur=joueur;
	} 
	public int pgetX(){
		return this.x;
	}
	public int pgetY(){
		return this.y;
	}
	public int getJoueur(){
		return this.joueur;
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	public void setLib(int lib){
		this.lib=lib;
	}
	public int getLib(){
		return this.lib;
	}
}
