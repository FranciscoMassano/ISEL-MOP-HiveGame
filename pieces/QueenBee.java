package tps.tp4.pieces;

import java.awt.Color;

import tps.tp4.Game;
import tps.tp4.Game.Direction;

/**
 * QueenBee class
 */
public class QueenBee extends Piece {
	final static private Color color = Color.yellow;

	/**
	 * constructor
	 */
	public QueenBee(Game game, boolean isFromPlayerA) {
		super("QueenBee", color, game, isFromPlayerA);
		// TODO
	}

	/**
	 * Move this piece to x,y if doesn't violate the rules.
	 * 
	 * The QueenBee can move only one step. Should not violate the one hive rule
	 * and the physical possible move rule.
	 * 
	 * usar canMove?, oneHive?
	 */
	public boolean moveTo(int x, int y) {
			Direction d = getDirection(this.getX(), this.getY(), x, y);
		if (game.canPhysicallyMoveTo(x, y, d) && game.getBoard().justOneHive(x, y) ) {
			if (d != null) {
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