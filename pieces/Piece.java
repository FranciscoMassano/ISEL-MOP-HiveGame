package tps.tp4.pieces;

import java.awt.Color;
import java.awt.Point;

import tps.tp4.Board;
import tps.tp4.BoardPlace;
import tps.tp4.Game;
import tps.tp4.Game.Direction;

public abstract class Piece {

	final public static int DIMPIECE = 25;

	private boolean isFromPlayerA;
	private int x = -1, y = -1;
	protected Game game;

	private String name;
	public Color color;
	
	public boolean isVerified;
	public boolean isOnBoard;
	public boolean wasMoved;
	/**
	 * constructor
	 */
	public Piece(String name, Color color, Game game, boolean isFromPlayerA) {
		this.name = name;
		this.game = game;
		this.color = color;
		this.isFromPlayerA = isFromPlayerA;
		this.isVerified = false;
		this.isOnBoard = false;
		this.wasMoved = false;
	}

	/**
	 * toString
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * get color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * get if piece if from player A or not
	 */
	public boolean isFromPlayerA() {
		//condicao ? true : false
		return isFromPlayerA ? true : false;
	}

	/**
	 * set xy
	 */
	public void setXY(int x, int y) {
		this.y =y;
		this.x =x;
	}

	/**
	 * get x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * set y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * move this piece to x,y if doesn't violate the rules
	 */
	public abstract boolean moveTo(int x, int y);

	/**
	 * checks if the x, y received position have one neighbor that is not me
	 */
	protected boolean haveValidNeighbour(int x, int y) {
		//posicao da peca a ver se tem vizinhos
		BoardPlace pos = game.getBoard().getBoardPlace(x, y);
		//precorrer todas as direcoes 
		for (int i = 0; i < Game.Direction.values().length; i++) {
			//ponto na direcao indice i
			Point p = game.getBoard().getNeighbourPoint(x, y, Game.Direction.values()[i]);
			//se a peca no ponto p for do mesmo player que que a peca em x,y
			if (game.getBoard().getBoardPlace(p.x, p.y).getPiece().isFromPlayerA() == pos.getPiece().isFromPlayerA()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * move one step if it is verify the rules
	 */
	protected boolean moveOneCheckedStep(int x, int y) {
		// TODO
		return false;
	}

	/**
	 * move to the destination if the move from the current position to the
	 * destiny doesn't violate the one hive rule. It can move several steps.
	 */
	protected boolean moveWithOnehiveRuleChecked(int x, int y) {
		//x y ponto destino
		//obter D, deste ponto para o ponto destino
		Direction D = getDirection(this.x, this.y, x, y);
		if (game.getBoard().justOneHive(x, y)) {
			return true;
		}
		return false;
	}

	/**
	 * get the direction from start coordinates to destiny coordinates
	 */
	protected static Direction getDirection(int fromX, int fromY, int toX,
			int toY) {

		for (Direction d : Direction.values()) {
			Point p = Board.getNeighbourPoint(fromX, fromY, d);
			if (p == null)
				continue;
			if (p.x == toX && p.y == toY) {
				return d;
			}
		}
		return null;
	}

}
