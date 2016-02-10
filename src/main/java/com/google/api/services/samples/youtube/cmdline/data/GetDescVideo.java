/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Qih
 */
public class GetDescVideo {
    
    /*
    Neste metodo a descrição de um video sera retornada se o id for valido
     */
    public GetDescVideo() {
    }

    
    
    //Variavel que contem dados de permissão da API
    private static final String PROPERTIES_FILENAME = "youtube.properties"; 
    //Máximo de resultados por query
    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;
    //Define uma variável global para fazer as chamadas
    private static YouTube youtube;
    
    
//    public static void main(String[] args) {
    public Video getDescVideo(String videoId){
        // Faz a leitura da chave de desenvolvedor no arquivo properties
        Properties properties = new Properties();
        try {
            InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("Ocorreu um erro na leitura de " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }
        
        
        //Inicializa a variável que deve fazer as chamadas na API
        try{
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-GetDescVideo").build();
                                 
            // Escolhe os atributos que a API deve retornar
            YouTube.Search.List search = youtube.search().list("id,snippet");
            
            // COLOCAR O ID DO VIDEO
            //String queryTerm = "7y93MYaTx6M";
            String queryTerm = videoId;
            
            // Set a chave do developer
            String apiKey = properties.getProperty("youtube.apikey");
  
            // ------- INICIO
            //Trabalhando com video request
            String videoQuery = queryTerm;
            YouTube.Videos.List searchVideos = youtube.videos().list("id,snippet,statistics").setId(videoQuery);
            
            searchVideos.setKey(apiKey);
            searchVideos.setFields("items(id,snippet/title,snippet/description,snippet/publishedAt,statistics/viewCount,statistics/likeCount,statistics/dislikeCount,statistics/commentCount)");
            // Faz a chamada na API e imprime os resultados
            //Falta o setType
            VideoListResponse videoResponse = searchVideos.execute();
            List<Video> videoResultList = videoResponse.getItems();
            if (videoResultList != null) {
                Video video = videoResultList.get(0);
                return video;
                /* prints de verificação
                VideoSnippet snippet = video.getSnippet();                
                System.out.println("\n================== Video ==================\n");
                System.out.println("  - ID: " +video.getId());
                System.out.println("  - Titulo: " + video.getSnippet().getTitle());
                System.out.println("  - Descrição: " + video.getSnippet().getDescription());
                System.out.println("  - Publicado em: " + video.getSnippet().getPublishedAt());
                System.out.println("  - Quantidade de visualizações:" + video.getStatistics().getViewCount());
                System.out.println("  - Quantidade de comentarios:" + video.getStatistics().getCommentCount());
                System.out.println("  - Quantidade de likes:" + video.getStatistics().getLikeCount());
                System.out.println("  - Quantidade de dislikes:" + video.getStatistics().getDislikeCount());
                String teste = video.getSnippet().getPublishedAt().toString();
                System.out.println("teste: " + teste);
                teste = teste.substring(0, 4);
                System.out.println("teste: " + teste);
                */
            }
            
            // ------- FIM
            
            
            
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
        
    }
    
}
