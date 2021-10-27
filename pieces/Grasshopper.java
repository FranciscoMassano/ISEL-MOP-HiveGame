package tps.tp4.pieces;

import java.awt.Color;
import java.awt.Point;

import tps.tp4.Board;
import tps.tp4.Game;
import tps.tp4.Game.Direction;

/**
 * Grasshopper class
 */
public class Grasshopper extends Piece {
	final static private Color color = new Color(70, 90, 40);

	/**
	 * constructor
	 */
	public Grasshopper(Game game, boolean isFromPlayerA) {
		super("GrassHopper", color, game, isFromPlayerA);
		// TODO
	}

	/**
	 * Move this piece to x,y if doesn't violate the rules.
	 * 
	 * The Grasshopper must move in strait jumps over at least one piece (but
	 * not empty places). Should not violate the one hive rule.
	 */
	public boolean moveTo(int x, int y) {
		// Direção para onde vamos
		Direction d = null;
		// Ponto onde esta a primeira peça nessa direção
		Point p = null;
		//nao esta a correr as varias direcoes
		//verificar a regra justOneHive e que o espaco esta vazio
		if (game.getBoard().justOneHive(x, y) && game.getBoard().getBoardPlace( x, y).getPiece() == null) {
			//percorre todas as direcoes
			label: for (int i = 0; i < Game.Direction.values().length; i++) {
				//ponto vizinho encontado na direção atual
				p = Board.getNeighbourPoint(this.getX(), this.getY(), Game.Direction.values()[i]);
				
				//verificar se existe peca nessa direcao
				if (game.getBoard().getBoardPlace(p.x, p.y).getPiece() != null /*&& d == null*/ ) {
					//guardar a direcao para onde vamos
					d = Game.Direction.values()[i];
					
					//correr a board toda
					for (int j = 0; j < Board.DIMX; j++) {
						for (int k = 0; k < Board.DIMY; k++) {
							//posicao da peca seguinte nessa direcao, vizinho
							if (p.x == j && p.y == k ) {
								//prixmo vizinho
								p = Board.getNeighbourPoint(j, k, d);
								//se nao houver peca no ponto p e o ponto nao coincidir com a posicao para onde queremos mover ve a proxima direcao
								if(game.getBoard().getBoardPlace(p.x, p.y).getPiece() == null && (p.x != x && p.y != y))
									continue label;
								else {
									if (p.x == x && p.y == y) {
										return true;
									}
									else {
										j = 0;
										k = 0;
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		//if (game.canPhysicallyMoveTo(this.getX(), this.getY(), d) && game.getBoard().justOneHive(x, y) ) 

		return false;
	}
}