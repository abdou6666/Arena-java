/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.arena.services;

import edu.arena.entities.Equipe;
import edu.arena.entities.Tournois;
import edu.arena.utils.DataBase;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author tarek
 */
public class TournoisCrud {
     private Connection con;
    private Statement ste;

    public TournoisCrud() {
        con = DataBase.getInstance().getConnection();

    }

   
    
     public void ajouter(Tournois t) throws SQLException {
        PreparedStatement pre = con.prepareStatement("INSERT INTO tournois (Titre,Date_debut,Date_fin,DescriptionTournois,Type,NbrParticipants,IdJeux)VALUES (?,?,?,?,?,?,?);");
        pre.setString(1, t.getTitre());
        pre.setString(2, t.getDate_debut());
        pre.setString(3, t.getDate_fin());
        pre.setString(4, t.getDescriptionTournois());
        pre.setString(5, t.getType());
        pre.setInt(6, t.getNbrParticipants());
        pre.setInt(7, t.getIdJeux());
        pre.executeUpdate();
    }

     
     

     
     
    public boolean Update(int IdTournois ,String Titre , String Date_debut,String Date_fin,String DescriptionTournois, String Type , int NbrParticipants , int IdJeux) {
            try {
            PreparedStatement pre = con.prepareStatement("UPDATE tournois SET Titre= ? ,Date_debut = ? , Date_fin= ? , DescriptionTournois= ? , Type= ? , NbrParticipants= ? , IdJeux= ? where IdTournois= ? ;");
            pre.setString(1, Titre);
            pre.setString(2, Date_debut);
            pre.setString(3, Date_fin);   
            pre.setString(4,DescriptionTournois);
            pre.setString(5,Type);
            pre.setInt(6, NbrParticipants);
            pre.setInt(7, IdJeux);
            pre.setInt(8, IdTournois);

            if (pre.executeUpdate() != 0) {
                System.out.println(" tournois updated");
                 } else {
                System.out.println("non");
            }
                return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean delete(Integer idcode) throws SQLException {

        PreparedStatement pre = con.prepareStatement("Delete from tournois where IdTournois=? ;");
        pre.setInt(1, idcode);
        if (pre.executeUpdate() != 0) {
            System.out.println("Tournament Deleted");
            return true;
        }
        System.out.println("id Tournament not found!!!");
        return false;

    }
    
        public List<Tournois> readAll() throws SQLException {

        List<Tournois> lu = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select IdTournois , Titre,Date_debut,Date_fin,DescriptionTournois ,Type,NbrParticipants , IdJeux from tournois");
        while (rs.next()) {
            
            
            Integer IdTournois = rs.getInt("IdTournois");
            String Titre = rs.getString("Titre");
            String Date_debut = rs.getString("Date_debut");
            String Date_fin = rs.getString("Date_fin");
            String DescriptionTournois = rs.getString("DescriptionTournois");
            String Type = rs.getString("Type");
            Integer NbrParticipants = rs.getInt("NbrParticipants");
            Integer IdJeux = rs.getInt("IdJeux");

            
            
            Tournois t = new Tournois(IdTournois,Titre, Date_debut, Date_fin,DescriptionTournois,Type,NbrParticipants,IdJeux);
            lu.add(t);
        }
        return lu;
    }
        
        
        public List<String> ListTournois() {

        List<String> arr = new ArrayList<>();

        try {

            PreparedStatement pre = con.prepareStatement("SELECT Titre from tournois ");
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {

                String Titre = rs.getString("Titre");

                arr.add(Titre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JeuxCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arr;

    }
        
        
        
            public int getidTournoisbynom(String i) throws SQLException {
        ste = con.createStatement();
        int idtournois = 0;

        try {

            PreparedStatement pre = con.prepareStatement("SELECT IdTournois  from tournois where Titre=?");
            pre.setString(1, i);
   
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {


                
                 idtournois = rs.getInt("IdTournois");

            }
        } catch (SQLException ex) {
            Logger.getLogger(JeuxCrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idtournois;

    }
        
    
}
