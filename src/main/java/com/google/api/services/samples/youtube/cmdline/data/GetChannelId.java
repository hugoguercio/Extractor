/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Deve retornar o ID de um canal
 *
 * @author Qih
 */
public class GetChannelId {

    public GetChannelId() {
    }

    //Define as variaves, inicializa o objeto de busca
    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;
    private static YouTube youtube;
    //public static void main(String[] args) {
    public void getChannelID(){
        // Faz a leitura da chave de desenvolvedor do arquivo properties
        Properties properties = new Properties();
        try {
            InputStream in = Search.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }
        //Inicializa o objeto para fazer as requisições a API
        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-GetChannelId").build();

            // Termo de busca
            String queryTerm = "Khan Academy";

            // Define os atributos que a chamada deve retornar
            YouTube.Search.List search = youtube.search().list("id");

            //Carrega a chave de desenvolvedor
            String apiKey = properties.getProperty("youtube.apikey");
            
            search.setKey(apiKey);
            search.setQ(queryTerm);

            //Define que somente canais devem ser retornados na busca
            search.setType("channel");

            //Para otimizar, retornar somente os atributos que são utilizados
            search.setFields("items(id)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            
            
            //Faz a chamada na API
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            
            //Imprime os resultados
            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator(), queryTerm);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    
    
     private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query){

        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            
            SearchResult myChannel = iteratorSearchResults.next();
             
           

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (true){//(myChannel.getKind().equals("youtube#channel")) {

                System.out.println(" Channel Id" + myChannel.getId());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
     }
}
