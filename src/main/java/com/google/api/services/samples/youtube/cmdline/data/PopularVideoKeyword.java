/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import java.util.ArrayList;

/**
 *
 * @author Qih
 */
public class PopularVideoKeyword {
    public static void main(String args[]) throws Exception{
        int start =0; 
        int max = 4458;
        ArrayList<String> keys = new TxtRead().initializeArrayKeys();
        ArrayList<String> arrayId = new TxtRead().initializeArrayID();
        int activeKey=0;
        int activeID=0;
        int qtdRequisicoesPorChave = 900;
        while(activeKey<keys.size()){//enquanto tem chaves no array
            int chamadasAPI = 0;            
            while(chamadasAPI<qtdRequisicoesPorChave){//Tem requisições possíveis.
                if(start==max){//ja populou tudo
                    break;
                }
                Alchemy alchemy = new Alchemy(keys.get(activeKey));
                GetDescVideo GDC = new GetDescVideo();
                alchemy.videoKeywordExtraction(GDC.getDescVideo(arrayId.get(activeID)));
                start++;
                chamadasAPI++;
                activeID++;
            }
            if(start==max){//ja populou tudo
                    break;
            }
            activeKey++;
            chamadasAPI = 0;                      
        }
    }    
}
