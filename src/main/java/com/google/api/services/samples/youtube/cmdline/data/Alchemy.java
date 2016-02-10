/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import com.alchemyapi.api.AlchemyAPI;
import com.alchemyapi.api.AlchemyAPI_KeywordParams;
import com.google.api.services.youtube.model.Video;
import java.io.StringWriter;
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
public class Alchemy {
    String key;
    public Alchemy(String key) {
        this.key = key;
    }
    //victor final String key = "f95303ef6e7ee718326799092b02fe78585782e4";
    
    //final String key = "e86ddcea84450720240df70e0f0890051a19e4aa";
    
    public void videoKeywordExtraction(Video video) throws Exception {
        
        try{ 
                DAO dao = new DAO();
    
            AlchemyAPI a = AlchemyAPI.GetInstanceFromString(key);
            AlchemyAPI_KeywordParams params = new AlchemyAPI_KeywordParams();
            String whoAmI = video.getSnippet().getTitle() + video.getSnippet().getDescription();            
            Document doc_conc = a.TextGetRankedKeywords(whoAmI);
            doc_conc.getDocumentElement().normalize();            
            NodeList nList = doc_conc.getElementsByTagName("keyword");
        
            for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
                
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode;
                        String keyword = eElement.getElementsByTagName("text").item(0).getTextContent();
                        
                        dao.addAssocociacaoKeywordVideo(keyword, video.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));
                        //dao.addVideoInfo(video);
                        //Strings de teste
                        //System.out.println("Text: " + eElement.getElementsByTagName("text").item(0).getTextContent());
			//System.out.println("Relevance: " + eElement.getElementsByTagName("relevance").item(0).getTextContent());
                        //System.out.println("dbpedia link: " + eElement.getElementsByTagName("dbpedia").item(0).getTextContent());
 
		}
            }
            
    }catch(Exception e){
        System.out.println("Deu erro:" +e);
    }
           // System.out.println(getStringFromDocument(doc_conc));
    }
    
    
    
    
    public void videoConceptExtraction(Video video) throws Exception {
           try{ 
                DAO dao = new DAO();
    
            AlchemyAPI a = AlchemyAPI.GetInstanceFromString(key);
            AlchemyAPI_KeywordParams params = new AlchemyAPI_KeywordParams();
            String whoAmI = video.getSnippet().getTitle() + video.getSnippet().getDescription();
            Document doc_conc = a.TextGetRankedConcepts(whoAmI);
            
            doc_conc.getDocumentElement().normalize();
            NodeList nList = doc_conc.getElementsByTagName("concept");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
			Element eElement = (Element) nNode;
                        //dao.addConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""),eElement.getElementsByTagName("dbpedia").item(0).getTextContent().replace("'", ""));
                        //String idConceito = dao.getIdConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""));                       
                        //dao.addAssocociacaoConceitoVideo(Integer.parseInt(idConceito), video.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));
                        //dao.addVideoInfo(video);
                        //Strings de teste
                        //System.out.println("Text: " + eElement.getElementsByTagName("text").item(0).getTextContent());
			//System.out.println("Relevance: " + eElement.getElementsByTagName("relevance").item(0).getTextContent());
                        //System.out.println("dbpedia link: " + eElement.getElementsByTagName("dbpedia").item(0).getTextContent());
 
		}
            }
            
    }catch(Exception e){
        System.out.println("Deu erro:" +e);
    }
           // System.out.println(getStringFromDocument(doc_conc));
    }
    
    public void documentConceptExtraction(Documento documento) throws Exception {
            DAO dao = new DAO();
            //final String key = "f95303ef6e7ee718326799092b02fe78585782e4";
            AlchemyAPI a = AlchemyAPI.GetInstanceFromString(key);
            AlchemyAPI_KeywordParams params = new AlchemyAPI_KeywordParams();
            // INICIACAO que deve ser feita.
            String whoAmI = documento.getTitulo();
            if (whoAmI.length() < 15){
                return;
            }
            //Documento com foco em identificar o idioma
            Document docLang = a.TextGetLanguage(whoAmI);
            docLang.getDocumentElement().normalize();
            NodeList nListLang = docLang.getElementsByTagName("language");
            //System.out.println("temos:"+nListLang.getLength());
            for (int tempLang = 0; tempLang < nListLang.getLength(); tempLang++) {
                Node nNodeLang = nListLang.item(tempLang);
                if (nNodeLang.getNodeType() == Node.ELEMENT_NODE) {    

                    Element eElement2 = (Element) nNodeLang;
                    String lang = "english";
                    //Se for em ingles
                    if(lang.equals(nNodeLang.getTextContent())){
                        Document doc = a.TextGetRankedConcepts(whoAmI);
                        NodeList nList = doc.getElementsByTagName("concept");
                        for (int temp = 0; temp < nList.getLength(); temp++) {
                            Node nNode = nList.item(temp);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNode;
                                    System.out.println("XML forneceu conceitos !");
                                    //Lê o xml e add os conceitos extraidos na tabela.
                                    dao.addConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""),eElement.getElementsByTagName("dbpedia").item(0).getTextContent().replace("'", ""));
                                    
                                    //Lê o xml e add as associacoes com conceito e peso
                                    String idConceito = dao.getIdConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""));                       
                                    dao.addAssocociacaoConceitoDocumento(Integer.parseInt(idConceito), documento.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));


                                    //dao.addAssocociacaoConceitoVideo(Integer.parseInt(idConceito), video.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));
                                    //dao.addVideoInfo(video);
                                    //Strings de teste
                                    System.out.println("Text: " + eElement.getElementsByTagName("text").item(0).getTextContent());
                                    System.out.println("Relevance: " + eElement.getElementsByTagName("relevance").item(0).getTextContent());
                                    //System.out.println("dbpedia link: " + eElement.getElementsByTagName("dbpedia").item(0).getTextContent());

                            }
  
                    }
            }//Se não for em ingles 
            
            
//            Document doc = a.TextGetRankedConcepts(whoAmI);
//            doc.getDocumentElement().normalize();

            
            /////////////// ok
//            NodeList nList = doc.getElementsByTagName("concept");
//            for (int temp = 0; temp < nList.getLength(); temp++) {
//
//                Node nNode = nList.item(temp);
//                
//
//                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//
//                        Element eElement = (Element) nNode;
//                        dao.addConceito(eElement.getElementsByTagName("text").item(0).getTextContent(),eElement.getElementsByTagName("dbpedia").item(0).getTextContent());
//                        String idConceito = dao.getIdConceito(eElement.getElementsByTagName("text").item(0).getTextContent());                       
//                        dao.addAssocociacaoConceitoDocumento(Integer.parseInt(idConceito), documento.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));
//
//
//                        //dao.addAssocociacaoConceitoVideo(Integer.parseInt(idConceito), video.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));
//                        //dao.addVideoInfo(video);
//                        //Strings de teste
//                        //System.out.println("Text: " + eElement.getElementsByTagName("text").item(0).getTextContent());
//                        //System.out.println("Relevance: " + eElement.getElementsByTagName("relevance").item(0).getTextContent());
//                        //System.out.println("dbpedia link: " + eElement.getElementsByTagName("dbpedia").item(0).getTextContent());
//
//                }
//            }  
                        //ok

            
            
            
           
            
            
           // System.out.println(getStringFromDocument(doc_conc));
            
            
            
            
            
            //            //Check if doc is in english
//            NodeList checkLang = doc.getElementsByTagName("language");
//            for (int aux = 0; aux < checkLang.getLength(); aux++) {
// 
//		Node nNode2 = checkLang.item(aux);
// 
// 
//		if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
//                    Element eElement2 = (Element) nNode2;
//                    String lang = eElement2.getElementsByTagName("language").item(0).getTextContent();
//                    if(lang.equals("english")){ //in english
                        //ok
//            
//            Node nLang = doc.getElementById("language");
//            if(nLang.getNodeType() == Node.ELEMENT_NODE){
//                Element eLang = (Element) nLang;
//                String sAux = "english";
//                if(sAux.equals(eLang.getElementsByTagName("language").item(0))){
//                    System.out.println("english");
//                }else{
//                    System.out.println("Wtf");
//                }
//            }
        }
    }
    }
    
    public void documentConceptExtractionATU(Documento documento){
        try{
        DAO dao = new DAO();
        //final String key = "f95303ef6e7ee718326799092b02fe78585782e4";
        AlchemyAPI a = AlchemyAPI.GetInstanceFromString(key);
        AlchemyAPI_KeywordParams params = new AlchemyAPI_KeywordParams();
        // INICIACAO que deve ser feita.
        String whoAmI = documento.getTitulo();

        if (whoAmI.length() < 15){
            return;
        }
        Document doc = a.TextGetRankedConcepts(whoAmI);
        NodeList nList = doc.getElementsByTagName("concept");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                //System.out.println("XML forneceu conceitos !");
                //Lê o xml e add os conceitos extraidos na tabela.
                dao.addConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""),eElement.getElementsByTagName("dbpedia").item(0).getTextContent().replace("'", ""));

                //Lê o xml e add as associacoes com conceito e peso
                String idConceito = dao.getIdConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""));                       
                dao.addAssocociacaoConceitoDocumento(Integer.parseInt(idConceito), documento.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));


                //dao.addAssocociacaoConceitoVideo(Integer.parseInt(idConceito), video.getId(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));
                //dao.addVideoInfo(video);
                //Strings de teste
                //System.out.println("Text: " + eElement.getElementsByTagName("text").item(0).getTextContent());
                //System.out.println("Relevance: " + eElement.getElementsByTagName("relevance").item(0).getTextContent());
                //System.out.println("dbpedia link: " + eElement.getElementsByTagName("dbpedia").item(0).getTextContent());

                }
  
            }
        }catch(Exception e){
            System.out.println("2");
            System.out.println(e);
            
        }
     
    }   
    
    public void autorConceptExtraction(Autor autor){
        try{
            DAO dao = new DAO();
            AlchemyAPI a = AlchemyAPI.GetInstanceFromString(key);
            AlchemyAPI_KeywordParams params = new AlchemyAPI_KeywordParams();
            // INICIACAO que deve ser feita.
            String whoAmI = autor.getTitulos();
            //Strings menores que 15 nao sao avaliadas
            if (whoAmI.length() < 15){
                return;
            }
            Document doc = a.TextGetRankedConcepts(whoAmI);
            NodeList nList = doc.getElementsByTagName("concept");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //System.out.println("XML forneceu conceitos !");
                    //Lê o xml e add os conceitos extraidos na tabela.
                    dao.addConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""),eElement.getElementsByTagName("dbpedia").item(0).getTextContent().replace("'", ""));

                    //Lê o xml e add as associacoes com conceito e peso
                    String idConceito = dao.getIdConceito(eElement.getElementsByTagName("text").item(0).getTextContent().replace("'", ""));                   
                    ////TO DO
                    dao.addAssocociacaoAutorConceito(Integer.parseInt(idConceito), autor.getIdpessoa(), Double.parseDouble(eElement.getElementsByTagName("relevance").item(0).getTextContent()));
                    //Strings de teste
                    //System.out.println("Text: " + eElement.getElementsByTagName("text").item(0).getTextContent());
                    
//System.out.println("Relevance: " + eElement.getElementsByTagName("relevance").item(0).getTextContent());
                    //System.out.println("dbpedia link: " + eElement.getElementsByTagName("dbpedia").item(0).getTextContent());

                    }

                }
            dao.closeConnection();
        }catch(Exception e){
            System.out.println("2");
            System.out.println(e);
            
        }
    }
    
    
    
    
    //Metodo Victor
    public void recomendacao() throws Exception {
            final String key = "f95303ef6e7ee718326799092b02fe78585782e4";

            AlchemyAPI a = AlchemyAPI.GetInstanceFromString(key);
            AlchemyAPI_KeywordParams params = new AlchemyAPI_KeywordParams();
            //Document doc = a.TextGetRankedKeywords("A Social Network is a social structure consisting of individuals or organizations, usually represented by nodes tied by one or more types of interdependency or relationship. This work focuses on using data mining techniques to identify intra and inter orga (...)");
            Document doc_conc = a.TextGetRankedConcepts("A Social Network is a social structure consisting of individuals or organizations, usually represented by nodes tied by one or more types of interdependency or relationship. This work focuses on using data mining techniques to identify intra and inter orga (...)");

            System.out.println(getStringFromDocument(doc_conc));
    }

    
    //Metodo para teste
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
}
