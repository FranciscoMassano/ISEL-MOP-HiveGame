package tps.tp4.pieces;

import java.awt.Color;
import java.awt.Point;

import tps.tp4.Board;
import tps.tp4.Game;
import tps.tp4.Game.Direction;

/**
 * Spider class
 */
public class Spider extends Piece {
	public static Color color = new Color(0xA62D00);

	/**
	 * constructor
	 */
	public Spider(Game game, boolean isFromPlayerA) {
		super("Spider", color, game, isFromPlayerA);
		
	}

	/**
	 * Move this piece to x,y if doesn't violate the rules.
	 * 
	 * The Spider must move exactly 3 different steps. Should not violate the one
	 * hive rule and the physical possible move rule in each step.
	 */
	public boolean moveTo(int x, int y) {

		Direction d = null;
		// Ponto onde esta a primeira peça nessa direção
		Point p = null;
		
		//nao esta a correr as varias direcoes
		//verificar a regra justOneHive e que o espaco esta vazio
		if (game.getBoard().justOneHive(x, y) && game.getBoard().getBoardPlace( x, y).getPiece() == null) {
			//percorre todas as direcoes
			for (int i = 0; i < Game.Direction.values().length; i++) {
				//ponto vizinho encontado na direção atual
				p = Board.getNeighbourPoint(this.getX(), this.getY(), Game.Direction.values()[i]);
				
				//verificar se existe peca nessa direcao
				if (game.getBoard().getBoardPlace(p.x, p.y).getPiece() != null ) {
					//guardar a direcao para onde vamos
					d = Game.Direction.values()[i];
					
					//correr a board toda
					label: for (int j = 0; j < Board.DIMX; j++) {
						for (int k = 0; k < Board.DIMY; k++) {
							//posicao da peca seguinte nessa direcao, vizinho
							if (p.x == j && p.y == k ) {
								////prixmo vizinho
								p = Board.getNeighbourPoint(j, k, d);
								if (p.x == x && p.y == y) {
									return true;
								}
								else {
									//j = 0;
									//k = 0;
									//i++;
									break label;
								}
								
							}
						}
					}
				}
			}
		}
		
		
		//if (game.canPhysicallyMoveTo(this.getX(), this.getY(), d) && game.getBoard().justOneHive(x, y) ) 

		return false;
		
		/**
		 * if (game.getBoard().getPiece(x, y) != null) {
			game.setStatusInfo("Invalid move - the destiny must be empty");
			return false;
		}

		// execute search for all the coordinates, with limit of 3 steps
		boolean found = false;
		for (Direction d : Direction.values()) {
			Point p = Board.getNeighbourPoint(getX(), getY(), d);
			if (p == null)
				continue;

			// TODO

			if (findPlace(p.x, p.y, x, y, 3, d)) {
				found = true;
				break;
			}
		}

		if (!found) {
			game.setStatusInfo("Invalid move - the destiny can't be reached in 3 valid steps");
			return false;
		}

		// move if one hive rule checked
		boolean moved = moveWithOnehiveRuleChecked(x, y);

		if (moved) {
			System.out.println("Piece " + this + " with (x,y) of (" + getX() + ", " + getY() + ") moved to (" + x + ", "
					+ y + ")");
		}
		return moved;
		 * 
		 * 
		 * 
		 * */
		
	}

	/**
	 * Find if current Spider can move in 3 steps to the final position. For each
	 * step it decreases the value toMove. If it is zero that means and is not the
	 * destiny, that means that the Spider doesn't arrived at the destination by
	 * this path, We must try all the paths.
	 */
	private boolean findPlace(int thisX, int thisY, int xFinal, int yFinal, int toMove, Direction lastDirection) {

		if (toMove == 0)
			return false;

		// TODO
		return false;
	}
	/**
	 * 
	 * 
	 * Direction d = null;
		// Ponto onde esta a primeira peça nessa direção
		Point p = null;
		
		//nao esta a correr as varias direcoes
		//verificar a regra justOneHive e que o espaco esta vazio
		if (game.getBoard().justOneHive(x, y) && game.getBoard().getBoardPlace( x, y).getPiece() == null) {
			//percorre todas as direcoes
			for (int i = 0; i < Game.Direction.values().length; i++) {
				//ponto vizinho encontado na direção atual
				p = Board.getNeighbourPoint(this.getX(), this.getY(), Game.Direction.values()[i]);
				
				//verificar se existe peca nessa direcao
				if (game.getBoard().getBoardPlace(p.x, p.y).getPiece() != null ) {
					//guardar a direcao para onde vamos
					d = Game.Direction.values()[i];
					
					//correr a board toda
					label: for (int j = 0; j < Board.DIMX; j++) {
						for (int k = 0; k < Board.DIMY; k++) {
							//posicao da peca seguinte nessa direcao, vizinho
							if (p.x == j && p.y == k ) {
								////prixmo vizinho
								p = Board.getNeighbourPoint(j, k, d);
								if (p.x == x && p.y == y) {
									return true;
								}
								else {
									//j = 0;
									//k = 0;
									//i++;
									break label;
								}
								
							}
						}
					}
				}
			}
		}
		
		
		//if (game.canPhysicallyMoveTo(this.getX(), this.getY(), d) && game.getBoard().justOneHive(x, y) ) 

		return false;
	 * 
	 */

}