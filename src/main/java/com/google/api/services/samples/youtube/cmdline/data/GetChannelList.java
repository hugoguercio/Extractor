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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Qih
 */
public class GetChannelList {

    /**
     * Aqui é definida a variável que contem os dados da chave
     * de desenvolvedor da API
     */
    public GetChannelList() {
    }

    
    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;

    //Define uma variável global para fazer as chamadas
    private static YouTube youtube;
    
    //public static void main(String[] args) {
    public void GetChannelList(){
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
            }).setApplicationName("youtube-cmdline-GetChannelList").build();
            
            
            // Set a chave do developer
            String apiKey = properties.getProperty("youtube.apikey");
           
            // Escolhe os atributos que a API deve retornar
            YouTube.Channels.List channelRequest = youtube.channels().list("id,snippet");
            channelRequest.setKey(apiKey);
            //channelRequest.setOauthToken("ZhQU2P93yyDr1OYcNbpfkbNZ");
            channelRequest.setId("UC4a-Gbdw7vOaccHmFo40b9g");
            channelRequest.setFields("items(id)");
            ChannelListResponse channelResult = channelRequest.execute();
 
            
            
            
            
            
            List<Channel> channelsList = channelResult.getItems();
            if (channelsList != null) {
                // The user's default channel is the first item in the list.
                // Extract the playlist ID for the channel's videos from the
                // API response.
                String uploadPlaylistId =
                        channelsList.get(0).getId();//getContentDetails().getRelatedPlaylists().getUploads();
//                    channelsList.get(0).getId();



                //Esta parte deve colocar os itens da playlist numa lista para serem utilizados 

                // Define a list to store items in the list of uploaded videos.
                List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();

                // Retrieve the playlist of the channel's uploaded videos.
                YouTube.PlaylistItems.List playlistItemRequest =
                        youtube.playlistItems().list("id,contentDetails,snippet");
               playlistItemRequest.setPlaylistId(uploadPlaylistId);

                // Only retrieve data used in this application, thereby making
                // the application more efficient. See:
                // https://developers.google.com/youtube/v3/getting-started#partial
                playlistItemRequest.setFields(
                        "items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");

                String nextToken = "";

                // Call the API one or more times to retrieve all items in the
               // list. As long as the API response returns a nextPageToken,
                // there are still more items to retrieve.
                do {
                    playlistItemRequest.setPageToken(nextToken);
                    PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                    playlistItemList.addAll(playlistItemResult.getItems());

                    nextToken = playlistItemResult.getNextPageToken();
                } while (nextToken != null);

                // Prints information about the results.
              
                prettyPrint(playlistItemList.size(), playlistItemList.iterator());

                
          }
          
                
            
  
            
            
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
//        
    }
     private static void prettyPrint(int size, Iterator<PlaylistItem> playlistEntries) {
        System.out.println("=============================================================");
        System.out.println("\t\tTotal Videos Uploaded: " + size);
        System.out.println("=============================================================\n");

        while (playlistEntries.hasNext()) {
            PlaylistItem playlistItem = playlistEntries.next();
            System.out.println(" video name  = " + playlistItem.getSnippet().getTitle());
            System.out.println(" video id    = " + playlistItem.getContentDetails().getVideoId());
            System.out.println(" upload date = " + playlistItem.getSnippet().getPublishedAt());
            System.out.println("\n-------------------------------------------------------------\n");
        }
    }
    
}
