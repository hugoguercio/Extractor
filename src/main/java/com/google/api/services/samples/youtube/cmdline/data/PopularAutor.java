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
public class PopularAutor {
    public static void main(String args[]) throws Exception{
        try{
            //Inicializa variáveis
            
            ArrayList<String> keys = new TxtRead().initializeArrayKeys();
            List<Integer> autores = new TxtRead().initializeArrayDocumentos();
            int max = autores.size();
            int activeKey=0;
            int maxReqPorKey = 999;
            int activeAutor=0;
            
            //enquanto tem chaves no array   
            while(activeKey<keys.size()){
                System.out.println("usando key numero: "+activeKey);
                System.out.println("key: "+keys.get(activeKey));
                int chamadasAPI = 0;     
                DAO dao = new DAO();
                while(chamadasAPI<maxReqPorKey){//enquanto tem requisicoes
                    if(activeAutor==max){//ja populou tudo
                        break;
                    }
                    Alchemy alchemy = new Alchemy(keys.get(activeKey));
                    dao.getAutorPorId(autores.get(activeAutor), alchemy);
                    chamadasAPI++;
                    activeAutor++;

                }
                activeKey++;
                System.out.println("Usando a key: "+keys.get(activeKey));
                //TESTE
                dao.closeConnection();
            }

            
        }catch(Exception e ){
            System.out.println("Exception no main:" +e);
        }
    }
}
