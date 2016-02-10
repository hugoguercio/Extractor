/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import com.google.api.services.youtube.model.Video;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Qih
 */
public class Teste {
    public static void main(String args[]){

            int[] ints = {1, 2, 3};
            List<Integer> intList = new ArrayList<Integer>();
            for (int index = 0; index < ints.length; index++)
            {
                intList.add(ints[index]);
            }
        System.out.println(intList.size());

//        GetDescVideo GDC = new GetDescVideo();
//        Video v = GDC.getDescVideo("7y93MYaTx6M");
//        System.out.println(""+v.getSnippet().getDescription());
    }
}
