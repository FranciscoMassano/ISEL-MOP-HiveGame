package tps.tp4.pieces;

import java.awt.Color;

import tps.tp4.Game;
import tps.tp4.Game.Direction;

/**
 * Beetle class
 */
public class Beetle extends Piece {
	final static private Color color = Color.magenta;

	/**
	 * constructor
	 */
	public Beetle(Game game, boolean isFromPlayerA) {
		super("Beetle", color, game, isFromPlayerA);
		// TODO
	}

	/**
	 * Move this piece to x,y if doesn't violate the rules.
	 * 
	 * The Beetle can move only one step and be placed on top on another
	 * piece(s). Should not violate the one hive rule.
	 */
	public boolean moveTo(int x, int y) {
		//obter uma direcao
		Direction d = getDirection(this.getX(), this.getY(), x, y);
		if (game.canPhysicallyMoveTo(x, y, d) && game.getBoard().justOneHive(x, y) ) {
			if (d != null) {
				//Como explicado no enunciado, certas posicoes mudam conforme a paridade do x
				if (this.getX()%2==0) {
					//N +0 -1
					if ( this.getX() == x && this.getY()-1 == y ) 
						return true;
					//NO -1 -1
					if (this.getX()-1 == x && this.getY() -1 == y) {
						return true;
					}
					//SO -1 +0
					if (this.getX()-1 == x && this.getY() +0 == y) {
						return true;
					}
					//S +0 +1
					if (this.getX() == x && this.getY() +1 == y) {
						return true;
					}
					//SE +1 +0
					if (this.getX()+1 == x && this.getY() == y) {
						return true;
					}
					//NE +1 -1
					if (this.getX() +1 == x && this.getY() -1 == y) {
						return true;
					}	
				}
				if (this.getX()%2!=0) {
					//N +0 -1
					if ( this.getX() == x && this.getY()-1 == y ) 
						return true;
					//NO -1 +0
					if (this.getX()-1 == x && this.getY() == y) {
						return true;
					}
					//SO -1 +1
					if (this.getX()-1 == x && this.getY() +1 == y) {
						return true;
					}
					//S +0 +1
					if (this.getX() == x && this.getY() +1 == y) {
						return true;
					}
					//SE +1 +1
					if (this.getX()+1 == x && this.getY()+1 == y) {
						return true;
					}
					//NE +1 +0
					if (this.getX()+1 == x && this.getY() == y) {
						return true;
					}
				}
			}
		}
		return false;
	}
}