package tps.tp4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorChooserUI;


import tps.layouts.CenterLayout;
import tps.tp4.pieces.Ant;
import tps.tp4.pieces.Beetle;
import tps.tp4.pieces.Grasshopper;
import tps.tp4.pieces.Piece;
import tps.tp4.pieces.QueenBee;
import tps.tp4.pieces.Spider;

/**
 * class that keep and control the data from one player
 * 
 */
public class PlayerData {

	private static Color ACTIVEPLAYERCOLOR = Color.orange;
	private static Color INACTIVEPLAYERCOLOR = Color.gray;

	/**
	 * one Queen, two Beetles, two Grasshoppers, three Spiders and three Ants
	 * 
	 * Don't change this
	 */
	private final PiecesAndItsNumber[] ListaDePecas = new PiecesAndItsNumber[] {
			new PiecesAndItsNumber(PType.QUEENBEE, 1),
			new PiecesAndItsNumber(PType.BEETLE, 2),
			new PiecesAndItsNumber(PType.GRASHOPPER, 2),
			new PiecesAndItsNumber(PType.SPIDER, 3),
			new PiecesAndItsNumber(PType.ANT, 3) };

	private JPanel sidePanel = new JPanel();
	private JLabel movesLabel;
	private JLabel playerLabel;
	private HiveLabel queenBeeLabel;
	public QueenBee queenBee;

	private int numberOfPiecesOnBoard = 0;
	private int numberOfMoves = 0;

	private boolean playerWon = false;
	private Game game;
	
	

	/**
	 * auxiliary class
	 */
	public Color playerColor;
	private boolean colorChange = false;
	public boolean playerA = true;
	
	
	private class PiecesAndItsNumber {
		PType tipo;
		int nPecas;

		public PiecesAndItsNumber(PType tipo, int nPecas) {
			this.tipo = tipo;
			this.nPecas = nPecas;
		}

	}

	/**
	 * Constructor - should build the side panel for the player
	 */
	public PlayerData(Game game, boolean isPlayerA) {
		this.game = game;
		playerA = isPlayerA;
		if (isPlayerA) {
			playerLabel = new JLabel("Player A");
			playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
			playerLabel.setBorder(new LineBorder(Color.black, 1));
			
			
			playerColor = Color.white;
			JLabel playerColorLabel = new JLabel("Player A");
			playerColorLabel.setBackground(playerColor);
			playerColorLabel.setOpaque(true);
			playerColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			playerColorLabel.setBorder(new LineBorder(Color.black, 1));
			
			MouseListener mlColor = new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}		
				@Override
				public void mousePressed(MouseEvent e) {}			
				@Override
				public void mouseExited(MouseEvent e) {}		
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {
					if (colorChange == false) {
						Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);	
						playerColor = newColor;
						colorChange = true;
					}
					//System.out.println("mouse clicked");
					playerColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
					playerColorLabel.setBackground(playerColor);
					
					//playerColorLabel.setOpaque(true);
				}
			};
			
			playerColorLabel.addMouseListener(mlColor);
			
			
			//setting the layout of the sidePanel
			sidePanel.setLayout(new GridLayout(17, 1, 10, 2));
			//setting padding
			Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			sidePanel.setBorder(padding);

			
			sidePanel.add(playerLabel);	
			sidePanel.add(new JLabel());
			sidePanel.add(playerColorLabel);
			sidePanel.add(new JLabel());
			
			//queen bee	
			queenBeeLabel = new HiveLabel(ListaDePecas[0].tipo.createNew(game, true), game);
			queenBeeLabel.init();
			queenBeeLabel.setBackground(queenBeeLabel.getPiece().getColor());
			queenBeeLabel.setBorder(new LineBorder(Color.black, 1));
			queenBeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			queenBeeLabel.setOpaque(true);
			MouseListener queenBeeML = new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
				game.clickOnPieceLabelOnSidePanel(queenBeeLabel);	
				}
			};
			queenBeeLabel.addMouseListener(queenBeeML);
			sidePanel.add(queenBeeLabel);
			//outras labels
			for (int i = 1; i < ListaDePecas.length; i++) {
				int occ = ListaDePecas[i].nPecas;
				for (int j = 0; j < occ; j++) {
					HiveLabel aux = new HiveLabel(ListaDePecas[i].tipo.createNew(game, isPlayerA) , game);
					aux.init();
					Piece p = aux.getPiece();
					aux.setBackground(p.getColor());
					aux.setForeground(Color.BLACK);
					aux.setBorder(new LineBorder(Color.black, 1));
					aux.setHorizontalAlignment(SwingConstants.CENTER);
					aux.setOpaque(true);
					sidePanel.add(aux);					
					//listener to interact with the labels
					MouseListener ml = new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent e) {}		
						@Override
						public void mousePressed(MouseEvent e) {}			
						@Override
						public void mouseExited(MouseEvent e) {}		
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseClicked(MouseEvent e) {
							//System.out.println("mouse clicked");
							game.clickOnPieceLabelOnSidePanel(aux);	
						}
					};
					aux.addMouseListener(ml);
					
				}			
			}
			//parte inferior
			movesLabel = new JLabel( String.valueOf(numberOfMoves) );
			movesLabel.setHorizontalAlignment(SwingConstants.CENTER);
			movesLabel.setBackground(Color.gray);
			movesLabel.setOpaque(true);
			movesLabel.setBorder(new LineBorder(Color.black, 1));
			sidePanel.add(new JPanel());
			sidePanel.add(movesLabel);
			game.getContentPane().add(sidePanel, CenterLayout.WEST);
			
			

		}else {
			
			playerLabel = new JLabel("Player B");
			playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
			playerLabel.setBorder(new LineBorder(Color.black, 1));
			playerLabel.setForeground(Color.white);
			
			playerColor = Color.black;
			JLabel playerColorLabel = new JLabel("Player B");
			playerColorLabel.setForeground(Color.white);
			playerColorLabel.setBackground(playerColor);
			playerColorLabel.setOpaque(true);
			playerColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			playerColorLabel.setBorder(new LineBorder(Color.black, 1));
			//mudar a cor, se colorChange = false
			MouseListener mlColor = new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}		
				@Override
				public void mousePressed(MouseEvent e) {}			
				@Override
				public void mouseExited(MouseEvent e) {}		
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseClicked(MouseEvent e) {
					if (colorChange == false) {
						Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);	
						playerColor = newColor;
						colorChange = true;
					}
					playerColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
					playerColorLabel.setBackground(playerColor);
				}
			};
			playerColorLabel.addMouseListener(mlColor);
			
			sidePanel.setLayout(new GridLayout(17, 1, 10, 2));
			
			Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
			sidePanel.setBorder(padding);
			
			
			sidePanel.add(playerLabel);	
			sidePanel.add(new JLabel());
			sidePanel.add(playerColorLabel);
			sidePanel.add(new JLabel());	
			
			

			//queen bee
			queenBeeLabel = new HiveLabel(ListaDePecas[0].tipo.createNew(game, false), game);
			queenBeeLabel.init();
			queenBeeLabel.setBackground(queenBeeLabel.getPiece().getColor());
			queenBeeLabel.setForeground(Color.white);
			queenBeeLabel.setBorder(new LineBorder(Color.black, 1));
			queenBeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			queenBeeLabel.setOpaque(true);
			MouseListener queenBeeML = new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {
				game.clickOnPieceLabelOnSidePanel(queenBeeLabel);	
				}
			};
			queenBeeLabel.addMouseListener(queenBeeML);
			sidePanel.add(queenBeeLabel);
			
			for (int i = 1; i < ListaDePecas.length; i++) {
				int occ = ListaDePecas[i].nPecas;
				for (int j = 0; j < occ; j++) {
					HiveLabel aux = new HiveLabel(ListaDePecas[i].tipo.createNew(game, isPlayerA) , game);
					aux.init();
					Piece p = aux.getPiece();
					aux.setBackground(p.getColor());
					aux.setForeground(Color.white);
					aux.setHorizontalAlignment(SwingConstants.CENTER);
					aux.setOpaque(true);
					aux.setBorder(new LineBorder(Color.black, 1));
					sidePanel.add(aux);
					MouseListener ml = new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent e) {}		
						@Override
						public void mousePressed(MouseEvent e) {}			
						@Override
						public void mouseExited(MouseEvent e) {}		
						@Override
						public void mouseEntered(MouseEvent e) {}
						@Override
						public void mouseClicked(MouseEvent e) {
							//System.out.println("mouse clicked");
							game.clickOnPieceLabelOnSidePanel(aux);	
						}
					};
					aux.addMouseListener(ml);
//					if (i == 0) {
//						queenBeeLabel = aux;
//					}
				}
			}
			movesLabel = new JLabel(String.valueOf(getNumberOfMoves()) );
			movesLabel.setHorizontalAlignment(SwingConstants.CENTER);
			movesLabel.setBackground(Color.gray);
			movesLabel.setOpaque(true);
			movesLabel.setBorder(new LineBorder(Color.black, 1));
			sidePanel.add(new JLabel());
			sidePanel.add(movesLabel);
			game.getContentPane().add(sidePanel, CenterLayout.EAST);
			this.init(true);

		}
		
	}

	/**
	 * Initializes the counters and the labels
	 */
	public void init(boolean playerIsActive) {
		//setNumberOfMoves(0);
		//colocar cores de activo, nao actovp
		if (playerIsActive == true) {
			playerLabel.setBackground(ACTIVEPLAYERCOLOR);
			playerLabel.setOpaque(true);
			/*
			 * Reseting the playerData
			 */
			
			
		}
		else {
			playerLabel.setBackground(INACTIVEPLAYERCOLOR);
			playerLabel.setOpaque(true);
			displayNumberOfMoves();
		}
		
		//reset
		
	}

	/**
	 * get side panel
	 */
	JPanel getSidePanel() {
		return sidePanel;
	}
	

	/**
	 * get number of moves of this player
	 */
	int getNumberOfMoves() {
		return numberOfMoves;
	}

	/**
	 * increment number of moves of this player
	 */
	void incNumberOfMoves() {
		this.numberOfMoves++;
	}

	/**
	 * get Queen Bee reference of this player
	 */
	QueenBee getQueenBee() {
		return queenBee;
	}

	/**
	 * sets the number of moves ...
	 */
	void setNumberOfMoves(int n) {
		this.numberOfMoves = n;
	}

	public Color getPLayerColor() {
		return playerColor;
	}
	/**
	 * get the number of pieces on board ...
	 */
	int getNumberOfPiecesOnBoard() {
		return numberOfPiecesOnBoard;
	}

	/**
	 * set the number of pieces on board ...
	 */
	void setNumberOfPiecesOnBoard(int np) {
		this.numberOfPiecesOnBoard = np;
	}
	

	/**
	 * increases the number of pieces on board ...
	 */
	void incNumberOfPiecesOnBoard() {
		this.numberOfPiecesOnBoard++;
	}

	/**
	 * decreases the number of pieces on board ..
	 */
	void decNumberOfPiecesOnBoard() {
		this.numberOfPiecesOnBoard--;
	}

	/**
	 * set this player background as current player or not
	 */
	public void setPlayerPanelActive(boolean active) {
		getSidePanel().setEnabled(active);
	}

	/**
	 * check if queen bee of this player is already on board
	 */
	public boolean isQueenBeeAlreadyOnBoard() {
		for (int i = 0; i < game.getBoard().DIMX ; i++) {
			for (int j = 0; j < game.getBoard().DIMY; j++) {
				//caso for queenBee e a peca for do jogador em questao
				if (game.getBoard().getBoardPlace(i, j).getPiece() != null && game.getBoard().getBoardPlace(i, j).getPiece().toString() == "QueenBee" && game.getBoard().getBoardPlace(i, j).getPiece().isFromPlayerA() == this.playerA ) {
					return true;
				}
				
			}
		}
		return false;
	}

	/**
	 * display the current number of moves in the last label
	 */
	public void displayNumberOfMoves() {
		movesLabel.setText("Moves " + numberOfMoves);
	}

	/**
	 * get the reference for the queen bee of this player
	 */
	public HiveLabel getQueenBeeLabel() {
		return queenBeeLabel;
	}

	/**
	 * sets if player won
	 */
	void setPlayerWon(boolean won) {
		playerWon = won;
	}

	/**
	 * return true if player won
	 */
	boolean playerWon() {
		return playerWon;
	}
	
	public String toString(boolean isPlayerA) {		
		return isPlayerA ? "Player A" : "Player B";	
	}

}

/**
 * classe que suporta as labels das peças iniciais de cada jogador
 */
class HiveLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	final static Border unselBorder = BorderFactory
			.createLineBorder(Color.darkGray);
	final static Border selBorder = BorderFactory.createLineBorder(Color.white,
			3);
	final static Border usedBorder = BorderFactory.createLineBorder(Color.red,
			3);

	private Piece p;
	private Game game;
	private boolean isDeactivated = false;

	/**
	 * 
	 */
	public HiveLabel(Piece p, Game game) {
		this.p = p;
		this.game = game;
		
	}

	/**
	 * 
	 */
	public Piece getPiece() {
		return p;
	}

	/**
	 * 
	 */
	public String toString() {
		return p.toString();
	}

	/**
	 * 
	 */
	public void init() {
		//System.out.println(toString());
		setText(p.toString());
		isDeactivated = false;
		setToNormal();
		
	}

	/**
	 * 
	 */
	public void activate() {
		setBorder(selBorder);
	}

	/**
	 * 
	 */
	public void setToNormal() {
		setBorder(unselBorder);
	}

	/**
	 * 
	 */
	public void deactivate() {
	
		isDeactivated = true;
		setBorder(usedBorder);
		setBackground(Color.DARK_GRAY);
		
		
		
	}

	/**
	 * 
	 */
	public boolean isDeactivated() {
		return isDeactivated;
	}

}

/**
 * enum with the several pieces and create methods
 */
enum PType {
	QUEENBEE {
		Piece createNew(Game game, boolean isFromPlayerA) {
			return new QueenBee(game, isFromPlayerA);
		};
	},
	BEETLE {
		Piece createNew(Game game, boolean isFromPlayerA) {
			return new Beetle(game, isFromPlayerA);
		};
	},
	GRASHOPPER {
		Piece createNew(Game game, boolean isFromPlayerA) {
			return new Grasshopper(game, isFromPlayerA);
		};
	},
	SPIDER {
		Piece createNew(Game game, boolean isFromPlayerA) {
			return new Spider(game, isFromPlayerA);
		};
	},
	ANT {
		Piece createNew(Game game, boolean isFromPlayerA) {
			return new Ant(game, isFromPlayerA);
		};
	};

	abstract Piece createNew(Game game, boolean isFromPlayerA);
};


