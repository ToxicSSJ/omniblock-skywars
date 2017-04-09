package net.omniblock.skywars;

/**
 * En conveniencia para Wirlie, en lugar de usar los booleanos para los estados uso un enumerador. Pero para
 * conservar ambas preferencias he creado una funcion en la clase {@link Skywars} para que se pueda traducir
 * los valores del enum a los booleanos.
 * 
 * @author Wirlie
 * @see {@link Skywars#getGameState()} {@link Skywars#updateGameState(SkywarsGameState)}
 *
 */

public enum SkywarsGameState {

	IN_LOBBY,
	IN_PRE_GAME,
	IN_GAME,
	FINISHING,
	
}
