package TheChunk;

import java.io.Serializable;

import TheUser.Usuario;

public class DataChunk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4414381718957155549L;
	private byte[] cipheredString;
	private Integer option;
	private Usuario sender;
	private Usuario destination;
	
	public byte[] getCipheredString(){
		return cipheredString;
	}
	
	public Usuario getSender(){
		return sender;
	}

	public Usuario getDestination(){
		return destination;
	}
	
	public Integer getOption(){
		return option;
	}
	
	public void setCipheredString(byte[] cipheredString){
		this.cipheredString = cipheredString;
	}
	
	public void setSender(Usuario sender){
		this.sender = sender;
	}
	
	public void setDestination(Usuario destination){
		this.destination = destination;
	}
	
	public void setOption(Integer option){
		this.option = option;
	}
}
