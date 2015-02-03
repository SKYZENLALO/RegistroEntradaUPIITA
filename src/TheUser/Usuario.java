package TheUser;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * 
 * @author Cesar Arturo
 */
public class Usuario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3012323533700800590L;
	private String nombre;
	private String host;
	private String psswd;

	public Usuario(String nombre, String host, String psswd) {
		this.nombre = nombre;
		this.host = host;
		this.psswd = psswd;
	}

	public String getNombre() {
		return nombre;
	}

	public String getHost() {
		return host;
	}
	
	public String getPsswd(){
		return psswd;
	}
	
	public void setHostAddress(String host){
		this.host = host;
	}
}
