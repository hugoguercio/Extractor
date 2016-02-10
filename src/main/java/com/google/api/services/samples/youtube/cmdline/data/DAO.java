/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.Video;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Qih
 */
public class DAO {
    private Connection connection;
    
    public DAO() throws ClassNotFoundException{
        this.connection = new ConnectionFactory().getConnection();
    }
   Connection con = new ConnectionFactory().getConnection();
     
    public void closeConnection() throws SQLException{
        this.connection.close();
    } 
    
   
    public void addVideoId(PlaylistItem item){
        String sq1= "insert into khan"
                + ("(id)")
                + "values(?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sq1);
            
            stmt.setString(1, item.getContentDetails().getVideoId());
            

            
            stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void addConceito(String conceito, String dbpedia){
        String sql = "SELECT * FROM conceito WHERE nm_conceito = '" + conceito + "'";

        try{
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {    //não existe o conceito
                sql = "INSERT INTO conceito (nm_conceito,dbpedia_link) values ('"+conceito+"','"+dbpedia+"')";                

                PreparedStatement stmt2 = connection.prepareStatement(sql);
                stmt2.execute();
                stmt2.close();
            }else{
                stmt.close();
            }
           stmt.close();
        }catch(SQLException e){
            System.out.println("3");
            throw new RuntimeException(e);
            
        }
    }
    
    public void addVideoInfo(Video v){
        
         String sql = "SELECT * FROM video WHERE id = '" + v.getId() + "'";
        try{
            PreparedStatement stmts = connection.prepareStatement(sql);
            ResultSet rs = stmts.executeQuery();
            if (!rs.next()) {
                String sq1= "INSERT INTO video "
                +"(id,title,description,view_count,comment_count,like_count,dislike_count,published_at,ano)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        
                PreparedStatement stmt = connection.prepareStatement(sq1);

                stmt.setString(1, v.getId());
                stmt.setString(2, v.getSnippet().getTitle());
                stmt.setString(3, v.getSnippet().getDescription());
                stmt.setInt(4, Integer.parseInt(v.getStatistics().getViewCount().toString()));
                stmt.setInt(5, Integer.parseInt(v.getStatistics().getCommentCount().toString()));
                stmt.setInt(6, Integer.parseInt(v.getStatistics().getLikeCount().toString()));
                stmt.setInt(7, Integer.parseInt(v.getStatistics().getDislikeCount().toString()));
                stmt.setString(8, (v.getSnippet().getPublishedAt().toString()));
                String aux = v.getSnippet().getPublishedAt().toString();
                aux = aux.substring(0,4);
                stmt.setInt(9, Integer.parseInt(aux));
                stmt.execute();
                stmt.close();
            }else{
                
            }
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        
        
        
        
        
        
        /*
        
        
        String sq1= "INSERT INTO video "
                +"(id,title,description,view_count,comment_count,like_count,dislike_count,published_at,ano)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sq1);
            
            stmt.setString(1, v.getId());
            stmt.setString(2, v.getSnippet().getTitle());
            stmt.setString(3, v.getSnippet().getDescription());
            stmt.setInt(4, Integer.parseInt(v.getStatistics().getViewCount().toString()));
            stmt.setInt(5, Integer.parseInt(v.getStatistics().getCommentCount().toString()));
            stmt.setInt(6, Integer.parseInt(v.getStatistics().getLikeCount().toString()));
            stmt.setInt(7, Integer.parseInt(v.getStatistics().getDislikeCount().toString()));
            stmt.setString(8, (v.getSnippet().getPublishedAt().toString()));
            String aux = v.getSnippet().getPublishedAt().toString();
            aux = aux.substring(0,4);
            stmt.setInt(9, Integer.parseInt(aux));
            stmt.execute();
            stmt.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }*/
    }
    
    public void addAssocociacaoConceitoVideo(Integer idConceito, String idVideo, double relevancia){
        String sq1= "insert into assoc_video_conceito"
                + "(id_conceito,id_video,relevancia)"
                + "values(?,?,?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sq1);
            
            stmt.setInt(1, idConceito);
            stmt.setString(2, idVideo);
            stmt.setDouble(3, relevancia);
            stmt.execute();
            stmt.close();
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public void addAssocociacaoKeywordVideo(String keyword, String idVideo, double relevancia){
        String sq1= "insert into assoc_video_keyword"
                + "(keyword,id_video,relevancia)"
                + "values(?,?,?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sq1);
            
            stmt.setString(1, keyword);
            stmt.setString(2, idVideo);
            stmt.setDouble(3, relevancia);
            stmt.execute();
            stmt.close();
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    
    public void addAssocociacaoConceitoDocumento(Integer idConceito, int idDocumento, double relevancia){
        String sq1= "insert into assoc_documento_conceito"
                + "(id_conceito,id_documento,relevancia)"
                + "values(?,?,?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sq1);
            
            stmt.setInt(1, idConceito);
            stmt.setInt(2, idDocumento);
            stmt.setDouble(3, relevancia);
            stmt.execute();
            stmt.close();
            con.close();
        }catch(SQLException e){
            System.out.println("5");
            throw new RuntimeException(e);
        }
    }
    
    public void addAssocociacaoAutorConceito(Integer idConceito, int idPessoa, double relevancia){
        String sq1= "insert into assoc_autor_conceito"
                + "(id_conceito,id_autor,relevancia)"
                + "values(?,?,?)";
        
        try{
            PreparedStatement stmt = connection.prepareStatement(sq1);
            
            stmt.setInt(1, idConceito);
            stmt.setInt(2, idPessoa);
            stmt.setDouble(3, relevancia);
            stmt.execute();
            stmt.close();
            con.close();
        }catch(SQLException e){
            System.out.println("6");
            throw new RuntimeException(e);
        }
    }    
    
    public String getIdConceito(String nm_conceito){
        String sql = "SELECT * FROM conceito WHERE nm_conceito = '" + nm_conceito + "'";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("id_conceito");
                return id;
           }
            con.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("4");
            throw new RuntimeException(e);
        }
        return null;
    }
    
    public String getDocumentoTitulo(String idDoc){
        String sql = "SELECT titulo FROM documento where iddocumento = '"+ idDoc +"'";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String titulo = rs.getString("iddocumento");
                return titulo;
                
           }
            con.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }
    
    public void getAllDocumento(Alchemy alchemy) throws Exception{
        /*
        Max id= 2248016
        Min id= 1
        */
        int i=0;
        int maxRow = 2248016;

        while(i<maxRow){
            int max = i+10;
            String sql ="SELECT iddocumento,titulo,ano FROM documento where iddocumento between " + i + " and " + max +" order by iddocumento asc";
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("iddocumento");
                    String titulo =rs.getString("titulo");
                    int ano = rs.getInt("ano");
                    System.out.println("mandei o documento de id: "+id);
                    Documento docAux = new Documento(id, titulo, ano);                                
                    alchemy.documentConceptExtraction(docAux);
                    
               }
                con.close();
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
            i=max;
            max = max+10;
        }

        
        /*
        
        FUNCIONANDO \/
        
        
        Alchemy alchemy = new Alchemy();
        String sql ="SELECT iddocumento,titulo,ano FROM documento where iddocumento between 1 and 11 order by iddocumento asc";
        //String sql = "SELECT iddocumento,titulo,ano FROM documento where id ";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("iddocumento");
                String titulo =rs.getString("titulo");
                int ano = rs.getInt("ano");
                System.out.println("mandei o doc: "+id);
                Documento docAux = new Documento(id, titulo, ano);                                
                alchemy.documentConceptExtraction(docAux);
           }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        */
    }
    
    public void getAllDocumento(int start, int maxRow, Alchemy alchemy) throws Exception{
        /*
        Max id= 2248016
        Min id= 1
        */
        
        
        while(start<maxRow){
            int max = start+10;
            String sql ="SELECT iddocumento,titulo,ano FROM documento where iddocumento between " + start + " and " + max +" order by iddocumento asc";
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("iddocumento");
                    String titulo =rs.getString("titulo");
                    int ano = rs.getInt("ano");
                    System.out.println("mandei o doc: "+id);
                    Documento docAux = new Documento(id, titulo, ano);                                
                    alchemy.documentConceptExtractionATU(docAux);
                    
               }
                con.close();
            }catch(SQLException e){
                throw new RuntimeException(e);
            }
            start=max;
        }
    }
    
    
    public void getDocumentoPorId(int idDoc, Alchemy alchemy) throws Exception{
        /*
        Max id= 2248016
        Min id= 1
        */
        
        

            String sql ="SELECT iddocumento,titulo,ano FROM documento where iddocumento = "+idDoc+"";
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("iddocumento");
                    String titulo =rs.getString("titulo");
                    int ano = rs.getInt("ano");
                    //System.out.println("mandei o doc: "+id);
                    Documento docAux = new Documento(id, titulo, ano);                                
                    alchemy.documentConceptExtractionATU(docAux);
               }
               stmt.close();
            }catch(SQLException e){
                System.out.println("1");
                throw new RuntimeException(e);
                
            }
            
    }
    
    
    public void getAutorPorId(int idAutor, Alchemy alchemy) throws Exception{
        String sql ="SELECT idpessoa,titulos FROM autor_concatenado where idpessoa = "+idAutor+"";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("idpessoa");
                    String titulos =rs.getString("titulos");
                    Autor autorAux = new Autor(id,titulos);
                    alchemy.autorConceptExtraction(autorAux);
                    
                }
                stmt.close();
        }catch(SQLException e){
                System.out.println("1");
                throw new RuntimeException(e);
                
        }
    }
}


