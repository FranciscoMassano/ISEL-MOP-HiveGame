package tps.tp4.pieces;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import tps.tp4.Board;
import tps.tp4.BoardPlace;
import tps.tp4.Game;
import tps.tp4.Game.Direction;

/**
 * Ant class
 */
public class Ant extends Piece {
	final static private Color color = Color.blue;

	/**
	 * constructor
	 */
	public Ant(Game game, boolean isFromPlayerA) {
		super("Ant", color, game, isFromPlayerA);
		
	}

	/**
	 * move this piece to x,y if doesn't violate the rules
	 * 
	 * The Ant must move any numbers of steps. Should not violate the one hive
	 * rule and the physical possible move rule in each step.
	 */
	public boolean moveTo(int x, int y) {
		//x e y direcao pra onde vai
		//correr todas as direcoes ate encontrar uma direcao com peca
		//guardar essa direcao
		Direction d = null;
		Point p = null;
		for (int i = 0; i < Game.Direction.values().length; i++) {
			p = game.getBoard().getNeighbourPoint(this.getX(), this.getY(), Game.Direction.values()[i] );
			if (game.getBoard().getBoardPlace(p.x, p.y).getPiece() != null ) {
				d = Game.Direction.values()[i];
			}
		}
//		Direction dd = getDirection(this.getX(), this.getY(), x, y);
//		if (dd != null) {
//			p = game.getBoard().getNeighbourPoint(this.getX(), this.getY(), dd );
//
//		}
		
		//verificar a regra justOneHive e regras da peca para mover
		if (game.getBoard().justOneHive(x, y) && game.getBoard().getBoardPlace( x, y).getPiece() == null) {
			System.out.println(d);
			System.out.println("Valido ");
			return true;
		}
		else {
			System.out.println("Nao valido");
		}
		
		//if (game.canPhysicallyMoveTo(this.getX(), this.getY(), d) && game.getBoard().justOneHive(x, y) ) 

		return false;
		
	}

	/**
	 * Find if current Ant can move in any number of steps to the final
	 * position. The Spider should try all the paths. But must prevent loops,
	 * For that, it receives a ArrayList with the BoardPlaces that we already
	 * moved. If the new one is already there, that means that is a loop, so it
	 * must abandon that path.
	 */
	private boolean findPlace(int nextX, int nextY, int xFinal, int yFinal,
			Direction lastDirection, ArrayList<BoardPlace> pathList) {
		// TODO
		return false;
	}
}