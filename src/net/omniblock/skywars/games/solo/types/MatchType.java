/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.games.solo.types;

public enum MatchType {
	/** Modo Normal **/
	NORMAL("Normal"),
	/** Modo Insano **/
	INSANE("Mejorado"),
	/** Modo Z (Especial) **/
	Z("Z"), 
	/** Ninguno, usado para saber que el plugin aún no está asignado a trabajar en una modalidad **/
	NONE("Ninguno");
	
	private String name = "Unknow";
	
	MatchType(){}
	
	MatchType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
