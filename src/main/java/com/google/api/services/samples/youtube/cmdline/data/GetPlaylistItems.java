/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import com.alchemyapi.api.AlchemyAPI;
import com.alchemyapi.api.AlchemyAPI_KeywordParams;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.VideoStatistics;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Qih
 */
public class GetPlaylistItems {
    public void recomendacao() throws Exception {
		final String key = "f95303ef6e7ee718326799092b02fe78585782e4";
    	
                AlchemyAPI a = AlchemyAPI.GetInstanceFromString(key);
		AlchemyAPI_KeywordParams params = new AlchemyAPI_KeywordParams();
		//Document doc = a.TextGetRankedKeywords("A Social Network is a social structure consisting of individuals or organizations, usually represented by nodes tied by one or more types of interdependency or relationship. This work focuses on using data mining techniques to identify intra and inter orga (...)");
		Document doc_conc = a.TextGetRankedConcepts("An abstract is a brief summary of a research article, thesis, review, conference proceeding or any in-depth analysis of a particular subject or discipline, and is often used to help the reader quickly ascertain the paper's purpose.[1] When used, an abstract always appears at the beginning of a manuscript or typescript, acting as the point-of-entry for any given academic paper or patent application. Abstracting and indexing services for various academic disciplines are aimed at compiling a body of literature for that particular subject. A Social Network is a social structure consisting of individuals or organizations, usually represented by nodes tied by one or more types of interdependency or relationship. This work focuses on using data mining techniques to identify intra and inter orga (...)");
		
		System.out.println(getStringFromDocument(doc_conc));
	}
	
	private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            
            System.out.println(writer.toString());
            
            NodeList nodes = doc.getDocumentElement().getElementsByTagName("concept");
            for(int i=0; i<nodes.getLength(); i++){
              Node node = nodes.item(i);

              if(node instanceof Element){
                //a child element to process
                Element concepts = (Element) node;
                NodeList nodesConcepts = concepts.getChildNodes();
                
                for(int j=0; j<nodesConcepts.getLength(); j++){
                	Node nodeConcepts = nodesConcepts.item(j);
                	if(nodeConcepts instanceof Element){
                		Element nodeConcept = (Element) nodeConcepts;
	                	System.out.println(nodeConcept.getNodeName() + "; " + nodeConcept.getFirstChild().getNodeValue());
                	}
                }
              }
            }
            
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /*
    Método para gerar uma lista com os id's de todos os uploads de um canal
     */
    public GetPlaylistItems() {
    }

    
    
    //Variavel que contem dados de permissão da API
    private static final String PROPERTIES_FILENAME = "youtube.properties"; 
    //Máximo de resultados por query
    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;
    //Define uma variável global para fazer as chamadas
    private static YouTube youtube;
    
    
    //public static void main(String[] args) {
    public List<PlaylistItem> getPlaylistItems(){
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
            }).setApplicationName("youtube-cmdline-GetPlaylistItems").build();

            // Set a chave do developer
            String apiKey = properties.getProperty("youtube.apikey");
            String uploadPlaylistId = "UU4a-Gbdw7vOaccHmFo40b9g";
           
            //Cria uma lista para armazenar os items da playlist
            List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
            
            // Recupera a playlist com os videos do canal
            YouTube.PlaylistItems.List playlistItemRequest =
                youtube.playlistItems().list("id,contentDetails,snippet");
            playlistItemRequest.setPlaylistId(uploadPlaylistId);
            
            //Aplica a key de desenvolvedor
            playlistItemRequest.setKey(apiKey);
            
            //Recupera somente os campos solicitados para otimizar a consulta 
            playlistItemRequest.setFields(
                        "items(contentDetails/videoId,snippet/title,snippet/description,snippet/publishedAt),nextPageToken,pageInfo");
            String nextToken = "";
            
            //Faz chamadas na API até que todas as páginas tenham seus dados coletados
            int aux = 0;
            do {
                    nextToken = "CDcQAA";
                    System.out.println("Pagina n: "+aux);
                    System.out.println(""+nextToken);
                    playlistItemRequest.setPageToken(nextToken);
                    PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                    playlistItemList.addAll(playlistItemResult.getItems());

                    nextToken = playlistItemResult.getNextPageToken();
                    aux++;
           }while (nextToken != null);
            
            //Imprime na tela para conferir
         //  prettyPrint(playlistItemList.size(), playlistItemList.iterator());
       //     insertDB(playlistItemList.size(), playlistItemList.iterator());
            return playlistItemList;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
    
    private static void prettyPrint(int size, Iterator<PlaylistItem> playlistEntries) {
        System.out.println("=============================================================");
        System.out.println("\t\tTotal Videos Uploaded: " + size);
        System.out.println("=============================================================\n");

        while (playlistEntries.hasNext()) {
            PlaylistItem playlistItem = playlistEntries.next();
            System.out.println(" video name  = " + playlistItem.getSnippet().getTitle());
            System.out.println(" video id    = " + playlistItem.getContentDetails().getVideoId());
            System.out.println(" video description = " + playlistItem.getSnippet().getDescription());
            System.out.println(" publishedAt = " + playlistItem.getSnippet().getPublishedAt());
            
            System.out.println("\n-------------------------------------------------------------\n");
        }
    }
    
    private static void insertDB(int size, Iterator<PlaylistItem> playlistEntries) throws ClassNotFoundException{
        DAO dao = new DAO();
        while (playlistEntries.hasNext()){
            PlaylistItem playlistItem = playlistEntries.next();
            dao.addVideoId(playlistItem);
        }
    }
   
}
