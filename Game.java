package tps.tp4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.Destroyable;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.TextAction;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.w3c.dom.Text;


import javazoom.jl.player.Player;




import classcode.p15Swing.p02buildedLayouts.ProportionalLayout;
import jaco.mp3.player.MP3Player;
import tps.layouts.CenterLayout;
import tps.tp4.Game.Direction;
import tps.tp4.pieces.Piece;
import tps.tp4.pieces.QueenBee;
import tps.tp4.pieces.Spider;

/**
 * HIVE GAME: one Queen, two Beetles, two Grasshoppers, three Spiders and three
 * Ants
 * 
 * http://en.wikipedia.org/wiki/Hive_(game)
 * http://www.gen42.com/downloads/rules/Hive_Rules.pdf
 */

/**
 * the main class - that supports the game
 */
public class Game extends JFrame {

	// enumerate that supports directions
	public enum Direction {
		N, NE, SE, S, SO, NO
	}

	private static final long serialVersionUID = 1L;
	private static final Color COLORPLAYER_A = Color.black;
	private static final Color COLORPLAYER_B = Color.lightGray;
	private static final int MAX_NUMBER_OF_MOVES_TO_PLACE_QUEENBEE = 4;

	private JLabel mainLabel;
	private HiveLabel currentHiveLabel = null;
	//private Piece currentPiece;
	public Piece currentPiece;
	private JPanel controlPanelOut;
	private JLabel lb_message;
	private JPanel controlPanel;
	private Board board;

	private Font fontCurrentPlayer;
	private Font fontPieces;

	// buttons to move the Hive, if possible
	private JButton bn_moveUp;
	private JButton bn_moveDown;
	private JButton bn_moveNO;
	private JButton bn_moveNE;
	private JButton bn_moveSE;
	private JButton bn_moveSO;
	private JButton bn_changePlayer;

	private boolean placingQueenBee = false;
	private boolean isPlayerAToPlay;
	private PlayerData currentPlayerData;
	private boolean endOfGame = false;

	private PlayerData playerAData;
	private PlayerData playerBData;
	
	private MouseListener mlMoveUp;
	private MouseListener mlChangePlayer;
	private MouseListener mlMoveDown;
	private MouseListener mlMoveSE;
	private MouseListener mlMoveSO;
	private MouseListener mlMoveNE;
	private MouseListener mlMoveNO;
	
	private BoardPlace lastPos = null;
	private String musicPath;
	private String imagePath;
	private String myImagePath;

	/**
	 * methods =============================================
	 */

	/**
	 * main
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// create object Game
				Game g = new Game();

				// launch the frame, but will be activated with some delay
				g.init();
			}
		});
		System.out.println("End of main...");

	}

	/**
	 * load resources: fonts, images, sounds
	 */
	private void loadResources() {
		fontPieces = new Font("Aharoni", Font.BOLD, 15);
		musicPath = System.getProperty("user.dir") + "/src/tps/tp4/Media/music.mp3";
		imagePath = System.getProperty("user.dir") + "/src/tps/tp4/Media/isel(1).png";
		myImagePath = System.getProperty("user.dir") + "/src/tps/tp4/Media/eu.jpg";
		
		//playMusic(musicPath);

	}

	/**
	 * the JFrame initialization method
	 * 
	 * inicializar ambos os players
	 */
	private void init() {
		loadResources();
		
		/*Listeners
		 * 
		 * 
		 * 
		 */
		mlChangePlayer = new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {bn_changePlayerAction();}
			public void mouseReleased(MouseEvent e) {}
		};
		mlMoveUp = new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {moveUp();}
			public void mouseReleased(MouseEvent e) {}
		};
		mlMoveDown = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {moveDown();}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};
		mlMoveSE = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {moveSE();}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};
		mlMoveSO = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {moveSO();}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};
		mlMoveNE = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {moveNE();}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};
		mlMoveNO = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {moveNO();}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		};

		// set title
		setTitle("Hive Game");
		//fechar
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLayout(new CenterLayout());
		//getContentPane().setBackground(new Color(255,255,255));
		setLocationRelativeTo(null);
		setResizable(false);
		//criar panel com a Label do jogador actual
		controlPanelOut = new JPanel();
		//colocar panel no topo
		add(controlPanelOut, CenterLayout.NORTH);
		//panel c a board - opcional
		JPanel midPanel = new JPanel();
		//midPanel.setBackground(new Color(0, 200, 0));
		add(midPanel, CenterLayout.CENTER);
		
		
		//criar panel c controlos em baixo
		controlPanel = new JPanel();
		//controlPanel.setBackground(Color.blue);
		add(controlPanel, CenterLayout.SOUTH);
		
		/*
		 * Inicializar os players e paineis
		 */
		
		playerAData = new PlayerData(this, true);	
		playerBData = new PlayerData(this, false);
		isPlayerAToPlay = true;
		currentPlayerData = getPlayerData(isPlayerAToPlay);
		
		playerAData.init(true);
		playerBData.init(false);
		
		//criar e adicionar a board
		board = new Board(this, fontPieces);
		midPanel.add(board);
		
		
		/*
		 * Label de topo apresenta o jogador actual
		 */
		//currentPlayerData = playerAData;
		mainLabel = new JLabel("Current Player -> " + currentPlayerData.toString(isPlayerAToPlay));
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		mainLabel.setFont(new Font("Arial", Font.BOLD, 30));	
		controlPanelOut.add(mainLabel);
		
		/*
		 * Inicio dos botoes 
		 * 1. Instanciar o botao
		 * 2. Adicionar ML
		 * 3. Adicionar o botao ao panel
		 * 
		 */
		bn_changePlayer = new JButton("Change Player");
		controlPanel.add(bn_changePlayer);
		bn_changePlayer.addMouseListener(mlChangePlayer);
		
		bn_moveUp = new JButton("Move Up");
		bn_moveUp.addMouseListener(mlMoveUp);
		controlPanel.add(bn_moveUp);
		
		bn_moveDown = new JButton("Move Down");
		bn_moveDown.addMouseListener(mlMoveDown);
		controlPanel.add(bn_moveDown);
		
		bn_moveNO = new JButton("Move North West");
		bn_moveNO.addMouseListener(mlMoveNO);
		controlPanel.add(bn_moveNO);
		
		bn_moveNE = new JButton("Move North East");
		bn_moveNE.addMouseListener(mlMoveNE);
		controlPanel.add(bn_moveNE);
		
		bn_moveSO = new JButton("Move South East");
		bn_moveSO.addMouseListener(mlMoveSO);
		controlPanel.add(bn_moveSO);
		
		bn_moveSE = new JButton("Move South West");
		bn_moveSE.addMouseListener(mlMoveSE);
		controlPanel.add(bn_moveSE);
		
		lb_message = new JLabel();
		lb_message.setAlignmentX(SwingConstants.CENTER);
		lb_message.setAlignmentY(SwingConstants.CENTER);
		lb_message.setBorder(new LineBorder(Color.black, 1));
		controlPanel.add(lb_message);
		
		buildMenu();
		setVisible(true);
		
	}

	/**
	 * build menu
	 * 1. Listeners
	 * 2. Novo JMenuBar
	 * 3. Novo JMenu, add to JMenubar
	 * 4. Novos JMenuItems, add Listeners, add to JMenu
	 * 5. 
	 */
	private void buildMenu() {
		
		/*
		 * Listeners - funcoes em mousePressed
		 */
		
		MouseListener mlAbout = new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {about();}
			public void mouseReleased(MouseEvent e) {}
		};
		MouseListener mlscore = new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {viewScores();}
			public void mouseReleased(MouseEvent e) {}
		};
		MouseListener mlRestart = new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {startAgain();}
			public void mouseReleased(MouseEvent e) {}
		};
		MouseListener mlRules = new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {rules();}
			public void mouseReleased(MouseEvent e) {}
		};
		MouseListener mlMusic = new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		};

		/*
		 * Menu dropdown
		 * must have: Restart game, ViewScores, About
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//menu drop down
		JMenu opcoesMenu = new JMenu("Menu"); 
		menuBar.add(opcoesMenu);
		//items do menu
		JMenuItem restartAction = new JMenuItem("Recomecar");
		JMenuItem scoreAction = new JMenuItem("View Scores");
		JMenuItem aboutAction = new JMenuItem("About");
		JMenuItem rulesAction = new JMenuItem("Rules");
		JMenuItem musicAction = new JMenuItem("Music On/Off");
		//actions de cada opcao
		aboutAction.addMouseListener(mlAbout);
		scoreAction.addMouseListener(mlscore);
		restartAction.addMouseListener(mlRestart);
		rulesAction.addMouseListener(mlRules);
		musicAction.addMouseListener(mlMusic);
		//atribuicao dos itens 
		opcoesMenu.add(restartAction);
		opcoesMenu.add(scoreAction);
		opcoesMenu.add(aboutAction);
		opcoesMenu.add(rulesAction);
		//opcoesMenu.add(musicAction);
		

		
	}
	/**
	 * activate About window
	 * new JFrame pop up
	 * 
	 */
	private void about() {
		/*
		 * Contruir a nova frame
		 */
		JFrame f = new JFrame();
		f.setTitle("About");
		f.setSize(400, 650);
		f.setLocationRelativeTo(null); 
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setVisible(true);
		f.setLayout(new CenterLayout());
		f.setResizable(false);

		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

		/*
		 * Escrever
		 */
		
		JPanel tituloPanel = new JPanel();
		tituloPanel.setLayout(new GridLayout(0, 1, 0, 0));
		tituloPanel.setAlignmentX(SwingConstants.CENTER);
	
		Icon icon = new ImageIcon(imagePath);
		JLabel pic = new JLabel();
		pic.setLayout(new GridLayout(0,1,0,0));
		pic.setHorizontalAlignment(SwingConstants.CENTER);
		pic.setVerticalAlignment(SwingConstants.TOP);
		pic.setIcon(icon);

		
		JPanel segundo = new JPanel();
		segundo.setLayout(new GridLayout(0,1,0,0));
		
		JPanel terceiro = new JPanel();
		terceiro.setBorder(padding);
		terceiro.setLayout(new GridLayout(0,1,0,0));
		
		//titulo e icon
		JLabel tituloLabel = new JLabel("About", SwingConstants.CENTER);
		tituloLabel.setVerticalAlignment(SwingConstants.TOP);
		tituloLabel.setFont(new Font("Calibri", Font.BOLD, 30));
		tituloLabel.add(pic);		
		
		//corpo
		JLabel text = new JLabel("The Hive Game", SwingConstants.CENTER);
		text.setFont(new Font("Calibri", Font.BOLD, 100));
		
		JLabel text1 = new JLabel("Modelacao e Programacao", SwingConstants.CENTER);
		text.setFont(new Font("Calibri", Font.PLAIN, 30));
		
		JLabel text2 = new JLabel("Desenvolvido por: Francisco Gomes A40708", SwingConstants.CENTER);
		text.setFont(new Font("Calibri", Font.PLAIN, 30));
		
		JLabel myPic = new JLabel();
		Icon myIcon = new ImageIcon(myImagePath);
		myPic.setLayout(new GridLayout(0,1,0,0));
		myPic.setHorizontalAlignment(SwingConstants.CENTER);
		myPic.setVerticalAlignment(SwingConstants.TOP);
		myPic.setIcon(myIcon);
		
		//adicionar label ao panel
		tituloPanel.add(pic);
		
		//adicionar o corpo ao painel respectivo
		tituloPanel.add(text);
		segundo.add(text1);
		segundo.add(text2);
		terceiro.add(myPic);	
		
		//adicionar panels a frame
		f.add(tituloPanel, BorderLayout.NORTH);
		///f.add(primeiro);
		f.add(segundo, BorderLayout.CENTER);
		f.add(terceiro,BorderLayout.SOUTH);
		
		System.out.println("About window...");
	}

	/**
	 * activate View scores window
	 * new JPanel pop up
	 * display scores
	 */
	private void viewScores() {
		/*
		 * Contruir a nova frame
		 */
		JFrame f = new JFrame();
		f.setTitle("Scores");
		f.setSize(600, 400);
		f.setLocationRelativeTo(null); 
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setVisible(true);
		f.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		
		/*
		 * Escrever
		 */
		
		
		
		System.out.println("View Scores window...");
	}
	
	private void rules() {
		/*
		 * Contruir a nova frame
		 */
		JFrame f = new JFrame();
		f.setTitle("Regras");
		f.setSize(870, 430);
		f.setLocationRelativeTo(null); 
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setVisible(true);
		//f.setResizable(false);
		f.setLayout(new GridLayout(0, 1, 0, 0));
		//f.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		
		/*
		 * Escrever
		 */
		
		JPanel tituloPanel = new JPanel();
		tituloPanel.setBorder(padding);
		
		JPanel movimentosTitulo = new JPanel();
		movimentosTitulo.setBorder(padding);
		
		JPanel introPanel = new JPanel();
		introPanel.setBorder(padding);
		introPanel.setLayout(new GridLayout(0,1,0,1));
		
		JPanel movimentos = new JPanel();
		movimentos.setBorder(padding);
		movimentos.setLayout(new GridLayout(0,1,0,1));
		
		//titulo
		JLabel tituloLabel = new JLabel("Regras");
		tituloLabel.setFont(new Font("Calibri", Font.BOLD, 30));
		//Introducao 
		setFont(new Font("Calibri", Font.PLAIN, 15));
		JLabel intro = new JLabel("Para comecar o jogo, cada jogador a vez coloca uma das suas pecas em jogo criando a Hive.");
		JLabel intro1 = new JLabel("A cada turno cada jogador coloca uma peca em jogo, ou move uma das suas pecas alterando a configuracao da Hive.");
		JLabel intro2 = new JLabel("Ao colocar uma peca, essa peca apenas pode tocar em pecas da mesma cor, apos estar na board pode mover-se e tocar pecas do outro jogador.");
		JLabel intro3 = new JLabel("As pecas nao podem ser movidas ate a QueenBee estar em jogo, a mesma tem que ser colocada ate ao quarto turno.");
		JLabel intro4 = new JLabel("Um jogador ganha quando a Queen Bee do oponente estiver rodeada de pecas, seja de que jogador for.", SwingConstants.LEFT);
		JLabel intro5 = new JLabel("A Hive nunca pode ser separada.");
		//tituloMov
		JLabel tituloMov = new JLabel("Movimentos");
		tituloMov.setFont(new Font("Calibri", Font.BOLD, 30));
		//Movimentos
		setFont(new Font("Calibri", Font.PLAIN, 15));
		JLabel mov = new JLabel("QueenBee: Move-se apenas uma posicao de cada vez.", SwingConstants.LEFT);
		JLabel mov1 = new JLabel("Ant: Move-se a posicao que desejar.");
		JLabel mov2 = new JLabel("Grasshopper: Salta da posicao actual ate a proxima posicao livre numa linha recta.");
		JLabel mov3 = new JLabel("Spider: Pode mover-se na direcao que desejar desde que salte por cima de apenas uma peca.");
		JLabel mov4 = new JLabel("Beetle: Move-se uma posicao de cada vez, como a QueenBee");
		//JLabel mov5 = new JLabel("              podem haver um qualquer numero de Beetles em cima de uma unica posicao.");
		
		//add ao painel superior
		tituloPanel.add(tituloLabel);
		
		//painel das regras
		introPanel.add(intro);
		introPanel.add(intro1);
		introPanel.add(intro2);
		introPanel.add(intro3);
		introPanel.add(intro4);
		introPanel.add(intro5);
		//movimentos titulo
		movimentosTitulo.add(tituloMov);
		//movimentos
		movimentos.add(mov);
		movimentos.add(mov1);
		movimentos.add(mov2);
		movimentos.add(mov3);
		movimentos.add(mov4);
		//movimentos.add(mov5);
		
		//add a frame
		f.add(tituloPanel);
		f.add(introPanel);
		f.add(movimentosTitulo);
		f.add(movimentos);
		
		System.out.println("Rules window...");
	}

	/**
	 * get color from player
	 */
	static public Color getColorFromPlayer(boolean isPlayerA) {
		return isPlayerA ? COLORPLAYER_A : COLORPLAYER_B;
	}

	/**
	 * get board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * get player data
	 */
	public PlayerData getPlayerData(boolean fromPlayerA) {
		return fromPlayerA ? playerAData : playerBData;
	}

	/**
	 * change player actions - to be called from the menu or from the button
	 * changePlayer
	 */
	private void bn_changePlayerAction() {
		currentPlayerData.incNumberOfMoves();
		changePlayer();
		setStatusInfo("Changing Player");
	}

	/**
	 * change player actions
	 */
	private void changePlayer() {
		if (checkFinishGame()) {
			doFinishGameActions();
		}else {
			
			//para evitar nullPointer
			//tirar a selecao da peca atual 
			//current piece null para as proximas interacoes de selecao
			if (currentHiveLabel != null) {
				currentHiveLabel.setToNormal();		
				currentPiece = null;
			}
			//alterar os valores de currentPlayerData
			// init(true) para o player que nao o actual
			// init(false) para o player actual
			// currentPlayerData = player actual
			if (currentPlayerData == playerAData) {
				currentPlayerData = playerBData;
				isPlayerAToPlay = false;
				playerAData.init(isPlayerAToPlay);
				playerAData.displayNumberOfMoves();
				playerBData.init(!isPlayerAToPlay);
				playerBData.displayNumberOfMoves();
				mainLabel.setText("Current Player -> " + currentPlayerData.toString(isPlayerAToPlay));
				repaint();				
			}else{
				currentPlayerData = playerAData;
				isPlayerAToPlay = true;
				playerBData.init(!isPlayerAToPlay);
				playerBData.displayNumberOfMoves();
				playerAData.init(isPlayerAToPlay);
				playerAData.displayNumberOfMoves();		
				mainLabel.setText("Current Player -> " + currentPlayerData.toString(isPlayerAToPlay));
				repaint();
			}
		}
	}

	/**
	 * start again actions
	 */
	private void startAgain() {
		System.out.println("Recomecar");
		board.resetBoard();
		this.init();
		playerAData = null;
		playerBData = null;
		
		 
		
		isPlayerAToPlay = true;
		
		playerAData = new PlayerData(this, true);	
		playerBData = new PlayerData(this, false);
		
//		playerAData.init(true);
//		playerBData.init(false);
		
		playerAData.setNumberOfMoves(0);
		playerBData.setNumberOfMoves(0);
		
		
		
		
		currentPlayerData = getPlayerData(isPlayerAToPlay);
		
		
		
		//currentPiece = null;
		//currentHiveLabel = null;
		
		//enableControlButtons(true);
		setStatusInfo("Novo Jogo");
		repaint();
		
	}

	/**
	 * check if coordinate x,y only have friendly neighbor of current player
	 */
	private boolean onlyHaveFriendlyNeighbors(int x, int y) {
		BoardPlace bp = board.getBoardPlace(x, y);
		
		List<Piece> pieces = new ArrayList<Piece>();
		int friends = 0;
		
		for (int i = 0; i < Direction.values().length; i++) {
			Point p = Board.getNeighbourPoint(x, y, Direction.values()[i]);
			
			if (getBoard().getBoardPlace(p.x, p.y).getPiece() != null)
				friends++;
		}
		for (int j = 0; j < Direction.values().length; j++) {
			Point p = Board.getNeighbourPoint(x, y, Direction.values()[j]);
			if (board.getBoardPlace(p.x, p.y).getPiece() != null && currentPiece.isFromPlayerA() == board.getBoardPlace(p.x, p.y).getPiece().isFromPlayerA()) {
				pieces.add(board.getBoardPlace(x, y).getPiece());
			}
		}
		if (friends == 0) {
			friends = 10;
		}
		
		return pieces.size() ==  friends ? true : false;
	}

	/**
	 * a click on the board
	 */
	public void clickOnBoard(int x, int y) {		

		//board place vazio - mete uma peca (caso a peca actual seja o beetle vai para esse sitio)
		if (board.getBoardPlace(x, y).getPiece() == null  /* || board.getBoardPlace(x, y).getPiece() != null && board.getBoardPlace(x, y).getPiece().toString() == "Beetle" */) {
			//para colocar uma peca selecionada noutro sitio
			if (lastPos != null && lastPos.getPiece() != null && lastPos.getPiece().isOnBoard) {
				//verificar just one hive
				if (board.justOneHive(x, y)) {
					//verificar as regras da peca
					if (currentPiece.moveTo(x, y)) {
						lastPos.setSelected(false);
						board.getBoardPlace(x, y).addPiece(currentPiece);
						lastPos.remPiece(currentPiece);
						currentPiece.setXY(x, y);
						currentPiece = null;
						lastPos = null;
						currentPlayerData.incNumberOfMoves();
						currentPlayerData.displayNumberOfMoves();
						mainLabel.setText("Current Player -> " + currentPlayerData.toString(isPlayerAToPlay));
						changePlayer();
						repaint();
					}
				}
			//colocar uma peca escolhida pela label
			//nao e necessario justOneHive, onlyHaveFriendlyNei resolve o problema
			}else {
				try {
					//da 4a jogada para a frente, a queen bee tem que estar na board
					if (currentPlayerData.getNumberOfPiecesOnBoard() >= MAX_NUMBER_OF_MOVES_TO_PLACE_QUEENBEE - 1 && currentPlayerData.isQueenBeeAlreadyOnBoard() || currentPiece.toString() == "QueenBee") {
						//verificar se nao tem inimigos no sitio
						if (onlyHaveFriendlyNeighbors(x, y) ) { 
							getBoard().addPiece(currentPiece, x, y);
							board.getBoardPlace(x, y).getPiece().isOnBoard = true;
							setStatusInfo(currentPlayerData.toString(isPlayerAToPlay) + " is playing a " + board.getBoardPlace(x, y).getPiece().toString() + " On (" + x + ", " + y + ")");
							currentHiveLabel.deactivate();
							currentPiece = null;
							lastPos = null;
							currentPlayerData.incNumberOfMoves();
							//System.out.println(currentPlayerData.getNumberOfPiecesOnBoard());
							currentPlayerData.displayNumberOfMoves();
							mainLabel.setText("Current Player -> " + currentPlayerData.toString(isPlayerAToPlay));
							changePlayer();
							repaint();
						}
					}
					//entre a segunda e quarta jogada
					if (currentPlayerData.getNumberOfPiecesOnBoard() >= 1 && currentPlayerData.getNumberOfPiecesOnBoard() < MAX_NUMBER_OF_MOVES_TO_PLACE_QUEENBEE - 1 ) {
						if (onlyHaveFriendlyNeighbors(x, y) ) { 
							getBoard().addPiece(currentPiece, x, y);
							board.getBoardPlace(x, y).getPiece().isOnBoard = true;
							setStatusInfo(currentPlayerData.toString(isPlayerAToPlay) + " is playing a " + board.getBoardPlace(x, y).getPiece().toString() + " On (" + x + ", " + y + ")");
							currentHiveLabel.deactivate();
							currentPiece = null;
							lastPos = null;
							currentPlayerData.incNumberOfMoves();
							//System.out.println(currentPlayerData.getNumberOfPiecesOnBoard());
							currentPlayerData.displayNumberOfMoves();
							mainLabel.setText("Current Player -> " + currentPlayerData.toString(isPlayerAToPlay));
							changePlayer();
							repaint();
						}
					}
					//primeira jogada ignora onlHaveFriendly
					if (currentPlayerData.getNumberOfPiecesOnBoard() < 1) { 
						getBoard().addPiece(currentPiece, x, y);
						board.getBoardPlace(x, y).getPiece().isOnBoard = true;
						setStatusInfo(currentPlayerData.toString(isPlayerAToPlay) + " is playing a " + board.getBoardPlace(x, y).getPiece().toString() + " On (" + x + ", " + y + ")");
						currentHiveLabel.deactivate();
						currentPiece = null;
						lastPos = null;
						currentPlayerData.incNumberOfMoves();
						//System.out.println(currentPlayerData.getNumberOfPiecesOnBoard());
						currentPlayerData.displayNumberOfMoves();
						mainLabel.setText("Current Player -> " + currentPlayerData.toString(isPlayerAToPlay));
						changePlayer();
						repaint();
					}
				//caso a peca seja null, nao apresentar erro	
				} catch (Exception e) {
					if (currentPiece == null) {
						setStatusInfo("Nao tem peca selecionada");
					}
				}	
			}				
		}
		//selecao da peca
		else {
			//se a queenbee nao estiver na board nao e possivel selecionar, logo nao e possivel mover
			if (currentPlayerData.isQueenBeeAlreadyOnBoard()) {
				//selecting a piece
				if(board.getBoardPlace(x, y).getPiece() != null && !board.getBoardPlace(x, y).isSelected()) {
					//correr a board toda para des-selecionar os anteriores			
					for (int i = 0; i < Board.DIMX; i++) {
						for (int j = 0; j < Board.DIMY; j++) {
							if (board.getBoardPlace(x, y).equals(board.getBoardPlace(i, j))) {
								board.setSelXY(x, y, true);
								currentPiece = board.getPiece(i, j);
								//guardo a posicao, ao fazer o move, remover nesta posicao 
								lastPos = board.getBoardPlace(i, j);
							}else {
								board.setSelXY(i, j, false);
							}
						}
					}
					if (board.getBoardPlace(x, y).getPiece().isFromPlayerA() && !isPlayerAToPlay) {
						board.setSelXY(x, y, false);
						
					}
					if (!board.getBoardPlace(x, y).getPiece().isFromPlayerA() && isPlayerAToPlay) {
						board.setSelXY(x, y, false);
					}
					setStatusInfo(currentPlayerData.toString(isPlayerAToPlay) + " is selecting " + board.getBoardPlace(x, y).getPiece().toString() + " On (" + x + ", " + y + ")" );
					repaint();
				//se nao houver queen bee, todas as pecas sao deselecionadas	
				}else{
					for (int i = 0; i < Board.DIMX; i++) {
						for (int j = 0; j < Board.DIMY; j++) {
							if (board.getBoardPlace(x, y).equals(board.getBoardPlace(i, j))) {
								board.setSelXY(i, j, false);
							}else {
								board.setSelXY(i, j, false);
							}
						}
					}
					//impedir a selecao de pecas do outro jogador
					if (board.getBoardPlace(x, y).getPiece().isFromPlayerA() && !isPlayerAToPlay) {
						board.setSelXY(x, y, false);
					}
					if (!board.getBoardPlace(x, y).getPiece().isFromPlayerA() && isPlayerAToPlay) {
						board.setSelXY(x, y, false);
					}
					repaint();
				}			
			}	
		}	
	}

	/**
	 * a click on a label on side panel
	 */
	public void clickOnPieceLabelOnSidePanel(HiveLabel hl) {
		//se houver label previamente selecionada, por normal
		if (currentHiveLabel != null) {
			currentHiveLabel.setToNormal();
		}
		lastPos = null;
		//player A
		if (hl.getPiece().isFromPlayerA() && currentPlayerData == playerAData) {
			currentPiece = hl.getPiece();
			currentHiveLabel = hl;
			setStatusInfo( currentPlayerData.toString(isPlayerAToPlay) + " selected a " + hl.toString());
			hl.activate();
		}
		//player B
		if (!hl.getPiece().isFromPlayerA() && currentPlayerData == playerBData) {
			currentPiece = hl.getPiece();
			currentHiveLabel = hl;
			setStatusInfo(currentPlayerData.toString(isPlayerAToPlay) + " selected a " + hl.toString());
			hl.activate();
		}
		
		//desactivar a label
		//repor variaveis a null para impedir colocar a mesma peca 2 vezes
		//deactivate e feito no clickOnBoard para desactivar a label actual
		if (hl.isDeactivated() == true) {
			hl.deactivate();
			currentPiece = null;
			currentHiveLabel = null;
		}
		
		
	}

	/**
	 * Can move to border, used to check if piece can be placed on hive physically
	 * sliding from the border. We can use a ArrayList to keep the boardPlaces and
	 * try to find a way to the border. The ArrayList is used to avoid loops. If a
	 * new boardPlace is already in the ArrayList so it will start a loop, so
	 * abandon that boardPlace as not valid move. This method only call the
	 * auxiliary method.
	 */
	private boolean canMoveToBorder(int x, int y) {
		return canMoveToBorder(x, y, new ArrayList<BoardPlace>());
	}

	/**
	 * can move to border - auxiliary method
	 */
	private boolean canMoveToBorder(int x, int y, ArrayList<BoardPlace> path) {
		
		return false;
	}

	/**
	 * check if can move physically from x,y in to the direction received
	 * 
	 * By physical we mean, that the piece has physical space to move. A piece,
	 * with the NE and NO places occupied, cannot move to N.
	 */
	public boolean canPhysicallyMoveTo(int x, int y, Direction d) {
		
		//switch para escolher a direcao e obter os pontos
		//obter dois pontos, verificar se existe peca
		switch (d) {
			case N: {
				//NO && NE ocupados
				Point no = Board.getNeighbourPoint(x, y, Direction.NO);
				Point ne = Board.getNeighbourPoint(x, y, Direction.NE);
				//ambos desocupados ou um deles desocupado
				if (board.getBoardPlace(no.x, no.y).getPiece() == null && board.getBoardPlace(ne.x, ne.y).getPiece() == null || board.getBoardPlace(no.x, no.y).getPiece() == null && board.getBoardPlace(ne.x, ne.y).getPiece() != null || board.getBoardPlace(no.x, no.y).getPiece() != null && board.getBoardPlace(ne.x, ne.y).getPiece() == null) {
					//System.out.println("Can Move North");
					return true;
				}
			}
			case NO: {
				//N && SO ocupados
				Point n = Board.getNeighbourPoint(x, y, Direction.N);
				Point so = Board.getNeighbourPoint(x, y, Direction.SO);
				if (board.getBoardPlace(n.x, n.y).getPiece() == null && board.getBoardPlace(so.x, so.y).getPiece() == null || board.getBoardPlace(n.x, n.y).getPiece() == null && board.getBoardPlace(so.x, so.y).getPiece() != null || board.getBoardPlace(n.x, n.y).getPiece() != null && board.getBoardPlace(so.x, so.y).getPiece() == null) {
					//System.out.println("Can Move North West");
					return true;
				}
			}
			case NE: {
				//N && SE ocupados
				Point n = Board.getNeighbourPoint(x, y, Direction.N);
				Point se = Board.getNeighbourPoint(x, y, Direction.SE);	
				if (board.getBoardPlace(n.x, n.y).getPiece() == null && board.getBoardPlace(se.x, se.y).getPiece() == null || board.getBoardPlace(n.x, n.y).getPiece() == null && board.getBoardPlace(se.x, se.y).getPiece() != null || board.getBoardPlace(n.x, n.y).getPiece() != null && board.getBoardPlace(se.x, se.y).getPiece() == null ) {
					//System.out.println("Can Move North East");
					return true;
				}
			}
			case S: {
				//SO && SE ocupados
				Point so = Board.getNeighbourPoint(x, y, Direction.SO);
				Point se = Board.getNeighbourPoint(x, y, Direction.SE);
				if (board.getBoardPlace(so.x, so.y).getPiece() == null && board.getBoardPlace(se.x, se.y).getPiece() == null || board.getBoardPlace(so.x, so.y).getPiece() == null && board.getBoardPlace(se.x, se.y).getPiece() != null || board.getBoardPlace(so.x, so.y).getPiece() != null && board.getBoardPlace(se.x, se.y).getPiece() == null) {
					//System.out.println("Can Move South");
					return true;
				}
			}
			case SO: {
				//S && NO ocupados
				Point s = Board.getNeighbourPoint(x, y, Direction.S);
				Point no = Board.getNeighbourPoint(x, y, Direction.NO);
				if (board.getBoardPlace(s.x, s.y).getPiece() == null && board.getBoardPlace(no.x, no.y).getPiece() == null || board.getBoardPlace(s.x, s.y).getPiece() != null && board.getBoardPlace(no.x, no.y).getPiece() == null || board.getBoardPlace(s.x, s.y).getPiece() == null && board.getBoardPlace(no.x, no.y).getPiece() != null) {
					//System.out.println("Can Move South West");
					return true;
				}
			}
			case SE: {
				//S && NE ocupados
				Point s = Board.getNeighbourPoint(x, y, Direction.S);
				Point ne = Board.getNeighbourPoint(x, y, Direction.NE);	
				if (board.getBoardPlace(s.x, s.y).getPiece() == null && board.getBoardPlace(ne.x, ne.y).getPiece() == null || board.getBoardPlace(s.x, s.y).getPiece() != null && board.getBoardPlace(ne.x, ne.y).getPiece() == null || board.getBoardPlace(s.x, s.y).getPiece() == null && board.getBoardPlace(ne.x, ne.y).getPiece() != null) {
					//System.out.println("Can Move South East");
					return true;
				}
			}
		
		}
		setStatusInfo("Posicao Invalida");
		return false;
		
	}

	/**
	 * set status info in label status
	 */
	public void setStatusInfo(String str) {
		lb_message.setFont(new Font("Arial", Font.BOLD, 15));
		lb_message.setText("  " + str + "  ");
	}

	/**
	 * Move the received piece unconditionally from its position to target position
	 * with: remPiece and addPiece.
	 */
	public void moveUnconditional(Piece p, int x, int y) {
		
	}

	/**
	 * check if end of game - update playerAWon and/or playerBWon states
	 */
	private boolean checkFinishGame() {
		
		//check finish game ambos players
		boolean playerASurrrounded = checkFinishGame(true);//playerA = true
		boolean playerBSurrounded = checkFinishGame(false);//playerB = false
		//se checkFinish == true, o jogador esta rodeado e perdeu (set false)
		if (playerASurrrounded && playerBSurrounded) {
			playerAData.setPlayerWon(false);
			playerBData.setPlayerWon(false);
			return true;
		}
		if (playerASurrrounded) {
			playerAData.setPlayerWon(false);
			playerBData.setPlayerWon(true);
			return true;
		}
		if (playerBSurrounded) {
			playerBData.setPlayerWon(false);
			playerAData.setPlayerWon(true);
			return true;
		}
		return false;
	}

	/**
	 * check if queen of received player is surrounded
	 * 
	 * 1. correr todas a board e encontrar a queen
	 * 2. ver em todas as 6 direcoes se existem pecas 
	 * 
	 */
	private boolean checkFinishGame(boolean playerA) {
		//obter uma lista de pecas a volta da queen de um certo player
		//correndo um duplo for na board ate encontrar a queen
		//correr um for para cada direcao, se houver peca, colocar na lista
		List<Piece> piecesAroundQueen = new ArrayList<Piece>();		
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				Piece temp = board.getBoardPlace(i, j).getPiece();
				if (temp != null && temp.toString()== "QueenBee" && temp.isFromPlayerA() == playerA) {
					for (int k= 0; k < Direction.values().length; k++) {
						Point aux = Board.getNeighbourPoint(i, j, Direction.values()[k]);
						if (board.getBoardPlace(aux.x, aux.y).getPiece() != null) {
							piecesAroundQueen.add(board.getBoardPlace(aux.x, aux.y).getPiece());
						}
						
					}
				}
			}
		}
		//se a lista for de tamanho 6 a queen esta rodeada
		if (piecesAroundQueen.size() == 6) {
			return true;
		}
		return false;
		
		
	}

	/**
	 * do end of game actions
	 */
	private void doFinishGameActions() {
		

		enableControlButtons(false);
		if (playerAData.playerWon() && !playerBData.playerWon()) {
			mainLabel.setText("Player A Ganhou");
			mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
			repaint();
		}
		if (!playerAData.playerWon() && playerBData.playerWon()) {
			mainLabel.setText("Player B Ganhou");
			mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
			repaint();
		}
		if (playerAData.playerWon() && playerBData.playerWon() ) {
			mainLabel.setFont(new Font("Arial", Font.BOLD, 30));
			mainLabel.setText("Empate");
			repaint();
		}
		setStatusInfo("The Game is Over");
		board.removeMouseListener(board.boardListener);
	}

	/**
	 * change state of general buttons
	 */
	private void enableControlButtons(boolean enable) {
		bn_changePlayer.setEnabled(enable);
		bn_moveDown.setEnabled(enable);
		bn_moveUp.setEnabled(enable);
		bn_moveNE.setEnabled(enable);
		bn_moveNO.setEnabled(enable);
		bn_moveSE.setEnabled(enable);
		bn_moveSO.setEnabled(enable);
		
		if (enable == false) {
			removeListeners();
		}else {
			addListeners();
		}
	}
	
	private void removeListeners() {
		bn_changePlayer.removeMouseListener(mlChangePlayer);
		bn_moveDown.removeMouseListener(mlMoveDown);
		bn_moveUp.removeMouseListener(mlMoveUp);
		bn_moveNE.removeMouseListener(mlMoveNE);
		bn_moveNO.removeMouseListener(mlMoveNO);
		bn_moveSE.removeMouseListener(mlMoveSE);
		bn_moveSO.removeMouseListener(mlMoveSO);
	}
	
	private void addListeners() {
		bn_changePlayer.addMouseListener(mlChangePlayer);
		bn_moveDown.addMouseListener(mlMoveDown);
		bn_moveUp.addMouseListener(mlMoveUp);
		bn_moveNE.addMouseListener(mlMoveNE);
		bn_moveNO.addMouseListener(mlMoveNO);
		bn_moveSE.addMouseListener(mlMoveSE);
		bn_moveSO.addMouseListener(mlMoveSO);
	}

	/**
	 * move hive UP, if it can be moved
	 * 
	 * no fim, incrementar numberOfMoves
	 */
	private void moveUp() {
		//colocar wasMoved de cada peca a false
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null) {
					board.getBoardPlace(i, j).getPiece().wasMoved = false;
				}
			}
		}
		//se a peca nao tiver sido movida, mudar
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null && !board.getBoardPlace(i, j).getPiece().wasMoved) {
					board.getBoardPlace(i, j).migrateTo(Direction.N);
				}
			}
		}
		setStatusInfo("Hive moved North");
		repaint();
	}

	/**
	 * move hive DOWN, if it can
	 */
	private void moveDown() {
		//currentPlayerData.incNumberOfMoves();
		//currentPlayerData.displayNumberOfMoves();
		//for de tras para a frente para resolver pecas saltar para cima das outras
		for (int i = Board.DIMX - 1; i >= 0; i--) {
			for (int  j = Board.DIMY - 1; j >= 0; j--) {
				if (board.getBoardPlace(i, j).getPiece() != null) {
					board.getBoardPlace(i, j).getPiece().wasMoved = false;
				}
			}
		}
		
		for (int i = Board.DIMX - 1; i >= 0; i--) {
			for (int  j = Board.DIMY - 1; j >= 0; j--) {
				if (board.getBoardPlace(i, j).getPiece() != null && !board.getBoardPlace(i, j).getPiece().wasMoved) {
					board.getBoardPlace(i, j).migrateTo(Direction.S);
				}
			}
		}
		setStatusInfo("Hive moved South");
		repaint();
		
		
	}

	/**
	 * move hive NO, if it can
	 */
	private void moveNO() {
		//currentPlayerData.incNumberOfMoves();
		//currentPlayerData.displayNumberOfMoves();
	
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null) {
					board.getBoardPlace(i, j).getPiece().wasMoved = false;
				}
			}
		}
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null && !board.getBoardPlace(i, j).getPiece().wasMoved) {
					board.getBoardPlace(i, j).migrateTo(Direction.NO);
				}
			}
		}
		setStatusInfo("Hive moved North West");
		repaint();
		
	}

	/**
	 * move hive NE, if it can
	 */
	private void moveNE() {
		//TODO
		//currentPlayerData.incNumberOfMoves();
		//currentPlayerData.displayNumberOfMoves();
		
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null) {
					board.getBoardPlace(i, j).getPiece().wasMoved = false;
				}
			}
		}
	
		
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = board.DIMY -1; j >=0; j--) {
				if (board.getBoardPlace(i, j).getPiece() != null && !board.getBoardPlace(i, j).getPiece().wasMoved) {
					board.getBoardPlace(i, j).migrateTo(Direction.NE);
				}
			}
		}
		setStatusInfo("Hive moved North East");
		repaint();
		
	}

	/**
	 * move hive SO, if it can
	 */
	private void moveSO() {
		
		//TODO
		//currentPlayerData.incNumberOfMoves();
		//currentPlayerData.displayNumberOfMoves();
		
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null) {
					board.getBoardPlace(i, j).getPiece().wasMoved = false;
				}
			}
		}
		
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null && !board.getBoardPlace(i, j).getPiece().wasMoved) {
					board.getBoardPlace(i, j).migrateTo(Direction.SO);
				}
			}
		}
		setStatusInfo("Hive moved South West");
		repaint();
		
	}

	/**
	 * move hive SE, if it can
	 */
	private void moveSE() {
		//currentPlayerData.incNumberOfMoves();
		//currentPlayerData.displayNumberOfMoves();
		
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null) {
					board.getBoardPlace(i, j).getPiece().wasMoved = false;
				}
			}
		}
		
		for (int i = 0; i < board.DIMX; i++) {
			for (int j = 0; j < board.DIMY; j++) {
				if (board.getBoardPlace(i, j).getPiece() != null && !board.getBoardPlace(i, j).getPiece().wasMoved) {
					board.getBoardPlace(i, j).migrateTo(Direction.SE);
				}
			}
		}
		setStatusInfo("Hive moved South East");
		repaint();
		
	}

    /**
     * Made for playing the music without stoping the game*/
	private void playMusic(String path) {
		new Thread() {
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try {
			            FileInputStream fis = new FileInputStream(path);
			            Player playMP3 = new Player(fis);
			            playMP3.play();
			        } catch(Exception e) {
			            System.out.println(e);
			        }
					
				}
				
			}
		}.start();	
			
//		MP3Player player = new MP3Player();
//	    player.addToPlayList(new File(path));
//	    player.setRepeat(true);
//	    player.play();
//		
	}	
}
