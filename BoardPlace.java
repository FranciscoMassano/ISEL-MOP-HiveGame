package tps.tp4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.print.attribute.SetOfIntegerSyntax;


import tps.tp4.Game.Direction;
import tps.tp4.pieces.Piece;

public class BoardPlace {

	public static Color PIECEBACKGROUNDCOLOR = new Color(0x9CCF3A);
	//public static Color PIECEBACKGROUNDCOLOR = new Color(235,150,5);//honey
	private static Color PIECESELECTIONCOLOR = Color.RED;

	public static int STARTXY = 5;

	// the place to have the pieces on this board place
	// pieces must be added at the tail, and the only accessible piece must be
	// the tail piece
	ArrayList<Piece> pieces = new ArrayList<Piece>();

	// is selected or not
	private boolean selected = false;

	// board reference
	private Board board;

	// the board place coordinates
	int x, y;

	// the polygon for this board place
	Polygon polygon = new Polygon();

	// the selection polygon for this board place
	Polygon selPolygon = new Polygon();

	// the base xy from the board for this place
	private int baseX;
	private int baseY;
	
	//private static Color PIECEBACKGROUNDCOLOR = ;

	// Methods ============================================

	/**
	 * constructor
	 */
	public BoardPlace(Board board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;

		// base data for polygons
		baseX = STARTXY + (int) (x * Piece.DIMPIECE * 0.75);
		baseY = STARTXY + (int) (y * Piece.DIMPIECE);

		if (x % 2 == 1) {
			baseY += Piece.DIMPIECE / 2;
		}

		// build polygon for this board place
		polygon.addPoint(baseX + Piece.DIMPIECE / 4 + 1, baseY + 1);
		polygon.addPoint(baseX + (Piece.DIMPIECE * 3) / 4 - 1, baseY + 1);

		polygon.addPoint(baseX + Piece.DIMPIECE - 1, baseY + Piece.DIMPIECE / 2);
		polygon.addPoint(baseX + (Piece.DIMPIECE * 3) / 4 - 1, baseY
				+ Piece.DIMPIECE - 1);
		polygon.addPoint(baseX + Piece.DIMPIECE / 4 + 1, baseY + Piece.DIMPIECE
				- 1);
		polygon.addPoint(baseX + 1, baseY + Piece.DIMPIECE / 2);

		// build selected polygon
		selPolygon.addPoint(baseX + Piece.DIMPIECE / 4, baseY - 1);
		selPolygon.addPoint(baseX + (Piece.DIMPIECE * 3) / 4, baseY - 1);

		selPolygon.addPoint(baseX + Piece.DIMPIECE, baseY + Piece.DIMPIECE / 2);
		selPolygon.addPoint(baseX + (Piece.DIMPIECE * 3) / 4, baseY
				+ Piece.DIMPIECE);
		selPolygon.addPoint(baseX + Piece.DIMPIECE / 4, baseY + Piece.DIMPIECE);
		selPolygon.addPoint(baseX, baseY + Piece.DIMPIECE / 2);

	}

	/**
	 * get the tail piece - the others are not accessible
	 * ve a ultima peca adicionada ao array pieces
	 */
	public Piece getPiece() {
		// TODO
		if (pieces.size() == 0)
			return null;

		return pieces.get(pieces.size() - 1);
	}

	/**
	 * Add piece to tail
	 */
	public void addPiece(Piece p) {
		// TODO
		//System.out.println("BoardPlace.addPiece");
		
		pieces.add(p);
		
		//System.out.println("No Pieces: " + pieces.size() + " On" + pieces.toString());
		
	}

	/**
	 * remove piece P if it is on tail
	 */
	public boolean remPiece(Piece p) {
		int index = pieces.size()-1;
		if (pieces.get(index) == p) {
			pieces.remove(p);
			return true;
		}
		
		return false;
	}

	/**
	 * clear all the pieces on this boardPlace
	 */
	public void clear() {
		pieces.clear();
	}

	/**
	 * set selected state
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * get selected state
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * equals, two BoardPlaces are equal if they have the same x and y
	 */
	public boolean equals(Object o) {
		BoardPlace temp = (BoardPlace) o;
		if (this.x == temp.x && this.y == temp.y) {
			return true;
		}
		return false;
	}

	/**
	 * to be viewed in debug watch
	 */
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Migrate the state of this board place 1 position to the neighbor in the
	 * received direction. To be used is move HIVE up, down, NO, ....
	 * 
	 * 1. is buscar o ponto destino da peca, getNeighbourPoint
	 * 2. ir buscar o BoardPlace do destino
	 * 3. ir buscar a peca a this.x e this.y
	 */
	public void migrateTo(Direction d) {
		//obter destino
		Point destino = Board.getNeighbourPoint(x, y, d);
		//obter peca
		Piece toBeMoved = board.getBoardPlace(x, y).getPiece();
		//colocar no destino e remover, se ambos nao forem nulos
		if (toBeMoved != null && destino != null) {
			board.addPiece(toBeMoved, destino.x, destino.y);
			this.remPiece(toBeMoved);
			toBeMoved.wasMoved = true;
		}
	}

	/**
	 * Paint this boardPiece - if it doesn't have any piece we should the draw
	 * polygon in background color
	 */
	public void paintComponent(Graphics g) {
		//se nao houver peca
		if (getPiece() == null) {
			// draw empty board place
			g.setColor(PIECEBACKGROUNDCOLOR);
			g.fillPolygon(polygon);
		} else {
			Color current;
			if (getPiece().isFromPlayerA()) {	
				current = board.playerAData.playerColor;
			}else {
				current = board.playerBData.playerColor;
			}
			
			g.setColor(current);
			g.fillPolygon(polygon);
			//Letra na peca
			g.setColor(getPiece().getColor());
			g.setFont(board.piecesFont);
			g.getFontMetrics().getStringBounds(getPiece().toString(), 0 ,1, g);
			g.drawString(String.valueOf(getPiece().toString().charAt(0)), baseX+6 , baseY+17);
		
			//imagens
//			String imagePath = "../MoP/src/tps/tp4/spider.png";
//			BufferedImage image = null;
//			try {
//				image = ImageIO.read(new File(imagePath));
//			} catch (IOException e) {
//				System.out.println("Cant find pic");
//				e.printStackTrace();
//			}
//			g = image.getGraphics();
//			g.setColor(Color.BLUE);
//			g.drawRect(10, 10, image.getWidth() - 20, image.getHeight() - 20);

		}

		// if selected, draw selection
		if (isSelected()) {
			g.setColor(PIECESELECTIONCOLOR);
			g.fillPolygon(polygon);
		}
		
	}

	/**
	 * check if x,y received is inside the polygon of this boardPlace - uses the
	 * contains method from polygon
	 */
	public boolean isInsideBoardPlace(int x, int y) {
		return polygon.contains(x, y);
	}

}
