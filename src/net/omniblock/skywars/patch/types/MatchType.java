package net.omniblock.skywars.patch.types;

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
