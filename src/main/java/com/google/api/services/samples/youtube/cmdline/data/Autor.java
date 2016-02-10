/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.api.services.samples.youtube.cmdline.data;

/**
 *
 * @author Qih
 */
public class Autor {
    private int idpessoa;
    private String titulos;

    public Autor(int idpessoa, String titulos) {
        this.idpessoa = idpessoa;
        this.titulos = titulos;
    }

    public int getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(int idpessoa) {
        this.idpessoa = idpessoa;
    }

    public String getTitulos() {
        return titulos;
    }

    public void setTitulos(String titulos) {
        this.titulos = titulos;
    }
    
}
