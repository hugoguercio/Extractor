/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
public class TxtRead{

    public TxtRead() {
    }
    
    
    
    public ArrayList<String> initializeArrayKeys(){
        BufferedReader br = null;
        ArrayList<String> keys = new ArrayList<String>();
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\Users\\Qih\\Desktop\\APITXT.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
                            keys.add(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
            return keys;
    }
    
    public ArrayList<String> initializeArrayID(){
        BufferedReader br = null;
        ArrayList<String> ArrayID = new ArrayList<String>();
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\Users\\Qih\\Desktop\\ID.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
                            ArrayID.add(sCurrentLine);
                            System.out.println(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
            return ArrayID;
    }
    
    public List<Integer> initializeArrayDocumentos(){
        BufferedReader br = null;
        List<Integer> ArrayDOC = new ArrayList<Integer>();
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\Users\\Qih\\Desktop\\DOC.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
                            ArrayDOC.add(Integer.parseInt(sCurrentLine));
//                            System.out.println(sCurrentLine);
			}
                        System.out.println("acabou de criar array doc");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
            return ArrayDOC;
    }
}