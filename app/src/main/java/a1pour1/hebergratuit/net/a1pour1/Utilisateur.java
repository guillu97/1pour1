package a1pour1.hebergratuit.net.a1pour1;

/**
 * Created by SPORE on 24/02/2018.
 */



public class Utilisateur {
    static private int id;
    static private String nom;
    static private String prenom;
    static private String adresseMail;
    static  private String mdp;
    static private int numTel;
    static private String adresse;
    static private String ville;


    public Utilisateur(int newId, String newNom, String newPrenom, String newAdresseMail, String newMdp, int newNumTel, String newAdresse, String newVille) {
        this.id = newId;
        this.nom = newNom;
        this.prenom = newPrenom;
        this.adresseMail = newAdresseMail;
        this.mdp = newMdp;
        this.numTel = newNumTel;
        this.adresse = newAdresse;
        this.ville = newVille;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Utilisateur.id = id;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        Utilisateur.nom = nom;
    }

    public static String getPrenom() {
        return prenom;
    }

    public static void setPrenom(String prenom) {
        Utilisateur.prenom = prenom;
    }

    public static String getAdresseMail() {
        return adresseMail;
    }

    public static void setAdresseMail(String adresseMail) {
        Utilisateur.adresseMail = adresseMail;
    }

    public static String getMdp() {
        return mdp;
    }

    public static void setMdp(String mdp) {
        Utilisateur.mdp = mdp;
    }

    public static int getNumTel() {
        return numTel;
    }

    public static void setNumTel(int numTel) {
        Utilisateur.numTel = numTel;
    }

    public static String getAdresse() {
        return adresse;
    }

    public static void setAdresse(String adresse) {
        Utilisateur.adresse = adresse;
    }

    public static String getVille() {
        return ville;
    }

    public static void setVille(String ville) {
        Utilisateur.ville = ville;
    }
}
