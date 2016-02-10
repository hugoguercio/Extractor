/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;


import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Qih
 */
public class PopularDocumentos {

    public static void main(String args[]) throws Exception{
//popular os documentos
                 
        try{
        
        ArrayList<String> keys = new TxtRead().initializeArrayKeys();
        List<Integer> documentos = new TxtRead().initializeArrayDocumentos();
        int max = 5000;//documentos.size();
        int activeKey=0;
        int maxReqPorKey = 999;
        int activeDoc=0;
        while(activeKey<keys.size()){//enquanto tem chaves no array   
            System.out.println("usando key numero: "+activeKey);
            System.out.println("key: "+keys.get(activeKey));
            int chamadasAPI = 0;     
            DAO dao = new DAO();
            while(chamadasAPI<maxReqPorKey){//enquanto tem requisicoes
                if(activeDoc==max){//ja populou tudo
                    break;
                }
                Alchemy alchemy = new Alchemy(keys.get(activeKey));
                dao.getDocumentoPorId(documentos.get(activeDoc), alchemy);

                //activeKey++;
                chamadasAPI++;
                activeDoc++;
                
            }
            activeKey++;
            System.out.println("Usando a key: "+keys.get(activeKey));
        }

        }catch(Exception e){
            System.out.println("Exception no main: "+e);
        }
//        Alchemy alchemy = new Alchemy();
//        DAO dao = new DAO();
//        dao.getAllDocumento(start,start+1000,alchemy);
//        
        
//        alchemy.recomendacao();
        /*
        //    Populando BD com videos e conceitos extraidos dos videos
        GetPlaylistItems GPI = new GetPlaylistItems();
        GetDescVideo GDC = new GetDescVideo();
        Alchemy Alchemy = new Alchemy();
        List<PlaylistItem> playlistItemList = GPI.getPlaylistItems();
        Iterator<PlaylistItem> iterator = playlistItemList.iterator();
        while (iterator.hasNext()){
            
            PlaylistItem playlistItem = iterator.next();
            String id = playlistItem.getContentDetails().getVideoId();
            Video video = GDC.getDescVideo(id);
            String whoAmI = video.getSnippet().getTitle() + video.getSnippet().getDescription();
            Alchemy.videoConceptExtraction(video);
            
        }
        */
    }
     
    
    
    
    
    
    
    ////////////////////////////
    //     Testes aqui \/
    ////////////////////////////
    /*
    
     public static void main (String[] args) throws Exception{

        Alchemy al = new Alchemy();
        String haha = ("Academic literature uses the abstract to succinctly communicate complex research. An abstract may act as a stand-alone entity instead of a full paper. As such, an abstract is used by many organizations as the basis for selecting research that is proposed for presentation in the form of a poster, platform/oral presentation or workshop presentation at an academic conference. Most literature database search engines index only abstracts rather than providing the entire text of the paper. Full texts of scientific papers must often be purchased because of copyright and/or publisher fees and therefore the abstract is a significant selling point for the reprint or electronic form of the full text.[2]\n" +
"\n" +
"Abstracts are protected under copyright law just as any other form of written speech is protected. However, publishers of scientific articles invariably make abstracts freely available, even when the article itself is not. For example, articles in the biomedical literature are available publicly from MEDLINE which is accessible through PubMed. The abstract can convey the main results and conclusions of a scientific article but the full text article must be consulted for details of the methodology, the full experimental results, and a critical discussion of the interpretations and conclusions. Consulting the abstract alone is inadequate for scholarship and may lead to inappropriate medical decisions.[3]\n" +
"\n" +
"An abstract allows one to sift through copious amounts of papers for ones in which the researcher can have more confidence that they will be relevant to his or her research. Once papers are chosen based on the abstract, they must be read carefully to be evaluated for relevance. It is generally agreed that one must not base reference citations on the abstract alone, but the content of an entire paper.\n" +
"\n" +
"According to the results of a study published in PLOS Medicine, the \"exaggerated and inappropriate coverage of research findings in the news media\" is ultimately related to inaccurately reporting or over-interpreting research results in many abstract conclusions.[4] A study published in JAMA concluded that \"inconsistencies in data between abstract and body and reporting of data and other information solely in the abstract are relatively common and that a simple educational intervention directed to the author is ineffective in reducing that frequency.\"[5] Other \"studies comparing the accuracy of information reported in a journal abstract with that reported in the text of the full publication have found claims that are inconsistent with, or missing from, the body of the full article.\"[6][7]");
        al.conceptExtraction(haha);
               
     }
*/
}
