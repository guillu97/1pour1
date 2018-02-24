package a1pour1.hebergratuit.net.a1pour1;

/**
 * Created by SPORE on 24/02/2018.
 */



public class utilisateur {
    final private int id;
    final private String nom;
    final private String prenom;
    final private String adresseMail;
    final private String mdp;
    final private int numTel;
    final private String adresse;
    final private String ville;

    public utilisateur(int id, String nom, String prenom, String adresseMail, String mdp, int numTel, String adresse, String ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.mdp = mdp;
        this.numTel = numTel;
        this.adresse = adresse;
        this.ville = ville;
    }
}
