package tps.tp4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


import tps.tp4.Game.Direction;
import tps.tp4.pieces.Piece;
import tps.tp4.pieces.QueenBee;

public class Board extends JPanel {
	private static final long serialVersionUID = 1L;

	// background color for the board
	//static Color BOARDBACKGROUNDCOLOR = new Color(255,211,0);//cyber
	static Color BOARDBACKGROUNDCOLOR = new Color(0xC9F76F);

	// number of cells in the board
	public static final int DIMX = 31;
	public static final int DIMY = 15;

	// the board data - array 2D of BoardPlaces
	private BoardPlace[][] board = new BoardPlace[DIMX][DIMY];

	// game reference
	private Game game;

	// references for the two PlayerData
	//private PlayerData playerAData, playerBData;
	public PlayerData playerAData, playerBData;


	// Font for the pieces
	public Font piecesFont;
	
	//more
	MouseListener boardListener;

	// methods ===============================================

	/**
	 * constructor
	 * 
	 * @param fontPieces
	 */
	public Board(Game game, Font piecesFont) {
		this.game = game;
		this.piecesFont = piecesFont;
		
		playerAData = game.getPlayerData(true);
		playerBData = game.getPlayerData(false);
		
		setPreferredSize(new Dimension(600, 400));
		
		//click on board chamado aqui
		boardListener = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				clickOnBoard(e.getX(), e.getY());
			}
		};
		addMouseListener(boardListener);
		
		initBoard();
		//QueenBee bee = new QueenBee(game, true);
		//addPiece(bee, 10, 10);
	}

	/**
	 * Create the board places for pieces
	 */
	private void initBoard() {
		
		//TODO
		setBackground(BOARDBACKGROUNDCOLOR);

		for (int y = 0; y < DIMY; y++) {
			for (int x = 0; x < DIMX; x++) {
				board[x][y] = new BoardPlace(this, x, y);
				
			}
		}
		//repaint();

	}

	/**
	 * method called by the mouseListener of the board. Should check if the x, y
	 * received is inside any of the polygons. In affirmative case should call
	 * game.clickOnBoard with the (x, y) of the BoardPlace clicked
	 */
	private void clickOnBoard(int xPix, int yPix) {
		//verificar se isInside
		//System.out.println(xPix + " "+ yPix);
		for (int i = 0; i < DIMX; i++) {
			for (int j = 0; j < DIMY; j++) {
				if (board[i][j].isInsideBoardPlace(xPix,yPix)) {
					//System.out.println("Is Inside");
					//System.out.println(board[i][j]);
					game.clickOnBoard(i, j);
					//getPiecesFromThisPoint(i, j, new ArrayList<Piece>() );
					repaint();
					
				}
			}
		}
	}

	/**
	 * clears the board data - clear all the pieces on board
	 */
	public void resetBoard() {
		// TODO
		initBoard();
		for (int i = 0; i < DIMX; i++) {
			for (int j = 0; j < DIMY; j++) {
				getBoardPlace(i, j).clear();
			}
		}
		repaint();
	}

	/**
	 * sets one boardPlace selected state
	 */
	public void setSelXY(int x, int y, boolean selectedState) {
		board[x][y].setSelected(selectedState);
		
	}

	/**
	 * draw the board - call the paintComponent for the super and for each one of
	 * the BoardPlaces
	 */
	public void paintComponent(Graphics g) {
		// TODO
		
		super.paintComponent(g);
		for (int y = 0; y < DIMY; y++) {
			for (int x = 0; x < DIMX; x++) {
				board[x][y].paintComponent(g);
			}
		}
	}

	/**
	 * get the neighbor point starting from x,y and going in the d direction. If the
	 * point doesn't exist the method returns null.
	 */
	public static Point getNeighbourPoint(int x, int y, Direction d) {
		Point p = new Point(x, y);

		// IMPORTANT NOTE: as the move depends on X, we must work on Y first
		switch (d) {
		case N:
			if (y == 0)
				return null;
			p.y--;
			break;
		case NE:
			// y first
			if (p.x % 2 == 0) {
				if (y == 0)
					return null;
				p.y--;
			}
			// then x
			if (x == Board.DIMX - 1)
				return null;
			p.x++;
			break;
		case SE:
			if (p.x % 2 == 1) {
				if (y == Board.DIMY - 1)
					return null;
				p.y++;
			}
			if (x == Board.DIMX - 1)
				return null;
			p.x++;
			break;
		case S:
			if (y == Board.DIMY - 1)
				return null;
			p.y++;
			break;
		case SO:
			if (p.x % 2 == 1) {
				if (y == Board.DIMY - 1)
					return null;
				p.y++;
			}
			if (x == 0)
				return null;
			p.x--;
			break;
		case NO:
			if (p.x % 2 == 0) {
				if (y == 0)
					return null;
				p.y--;
			}
			if (x == 0)
				return null;
			p.x--;
			break;
		}
		return p;
	}

	/**
	 * returns the (tail) piece on board[x][y]
	 * usar getBoardPlace
	 */
	public Piece getPiece(int x, int y) {
		return getBoardPlace(x, y).getPiece();
	}

	/**
	 * returns the BoardPlace at board[x][y]
	 * clickOnBoard
	 */
	public BoardPlace getBoardPlace(int x, int y) {
		return board[x][y];
	}

	/**
	 * add a piece (on tail) on the BoardPlace x,y. Should increase the
	 * numberOfPiecesOnBoard of the player that own the piece. Any change to the
	 * board should call the repaint() method. Every piece on board should keep its
	 * BoardPlace coordinates on board.
	 */
	public void addPiece(Piece p, int x, int y) {
		// TODO
		//erro null pointer
		if (p.isFromPlayerA()) {
			playerAData.incNumberOfPiecesOnBoard();
			//System.out.println(p.isFromPlayerA());
		}else {
			playerBData.incNumberOfPiecesOnBoard();
			//System.out.println(p.isFromPlayerA());

		}
		getBoardPlace(x, y).addPiece(p);
		p.setXY(x, y);	
	}

	/**
	 * Removes the piece if this piece is on tail on its BoardPlace. Should adjust
	 * numberOfPiecesOnBoard from its owner
	 */
	public boolean remPiece(Piece p) {
		for (int i = 0; i < DIMX; i++) {
			for (int j = 0; j < DIMY; j++) {
				if (p.getX() == i && p.getY() == j) {
					
					if (board[i][j].getPiece().isFromPlayerA()) {
						playerAData.decNumberOfPiecesOnBoard();
					}else {
						playerBData.decNumberOfPiecesOnBoard();
					}
					getBoardPlace(i, j).remPiece(p);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * check if staring from x,y is just one hive. The number of adjacent pieces
	 * should be all the pieces on board. Can be used an ArrayList to collect the
	 * pieces.
	 * 
	 * Vou a peca x,y e verifico de tenho vizinho (getNeighbourPoints).
	 * 1.
	 * 1. getBoardPlace (x,y) 
	 * 1 ver vizinhos desta boardPlace
	 * 1 se esta peca estiver a tocar num
	 * 
	 */
	public boolean justOneHive(int x, int y) {
		//x,y destino vazio boardplace
		List<Piece> piecesRecieved = new ArrayList<Piece>();
		List<Piece> piecesVerified = new ArrayList<Piece>();
		int numbofpieces = 0;
		//ver quantas epcas existem
		//colocar isVerified a false
		for (int i = 0; i < DIMX; i++) {
			for (int j = 0; j < DIMY; j++) {
				if (getBoardPlace(i, j).getPiece() != null) {
					getBoardPlace(i, j).getPiece().isVerified = false;
					numbofpieces++;
				}
			}
		}
		//correr a board ate encontrar a peca selecionada, para evitar ser colocada na lista de pecas ja verificadas
		//colocar isVerified a true
		for (int i = 0; i < DIMX; i++) {
			for (int j = 0; j < DIMY; j++) {
				if (getBoardPlace(i, j).getPiece() != null && getBoardPlace(i, j).isSelected() ) {
					getBoardPlace(i, j).getPiece().isVerified = true;
				}
			}
		}		
		//se nao houver peca em xy
		if (getBoardPlace(x, y).getPiece() == null) {
			//poe na lista os bp vizinhos preenchidos
			getPiecesFromThisPoint(x, y, piecesRecieved);
			//precorre recebidas
			for (int i = 0; i < piecesRecieved.size(); i++) {
				//se nao estiver verificada
				if (!piecesRecieved.get(i).isVerified) {
					piecesRecieved.get(i).isVerified = true;
					//se estiverem nao verificadas poe na lista de verificadas, altera isVerified
					piecesVerified.add(piecesRecieved.get(i));
					//para ver as pecas juntas a uma peca ja verificada, adicionar ao array
					getPiecesFromThisPoint(piecesRecieved.get(i).getX(), piecesRecieved.get(i).getY(), piecesRecieved);
				}
			}
		}
		return numbofpieces -1 == piecesVerified.size() ? true: false;
	}

	/**
	 * Get all the pieces that are connected with the x, y received, and put them on
	 * the List received.
	 * 
	 * poe numa lista com todas as pecas ligadas em x e y
	 */
	private void getPiecesFromThisPoint(int x, int y, List<Piece> pieces) {
		//precorrer todas as direcoes
		for (int i = 0; i < Direction.values().length; i++) {
			//ober ponto numa determinada direcao i
			Point aux = getNeighbourPoint(x, y, Direction.values()[i]);
			if ( (x >= 1 && y >= 1) || (x<= 31 && y <=15) ) {
				//se a peca em aux existir e nao for a peca original
				if (getBoardPlace(aux.x, aux.y).getPiece() != null && getBoardPlace(x, y).getPiece() != getBoardPlace(aux.x, aux.y).getPiece() ) {
					//evitar repeticoes
					if (!pieces.contains(getPiece(aux.x, aux.y))) {
						pieces.add(getPiece(aux.x, aux.y));
					}
				}
			}		
		}
	}

}
