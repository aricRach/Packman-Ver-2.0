package GUI;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import Algo.equation;
import Coords.MyCoords;
import GIS.box;
import GIS.fruit;
import GIS.game;
import GIS.ghost;
import GIS.metaDataFruit;
import GIS.metaDataPack;
import GIS.packman;
import Geom.Point3D;
import Map.converts;
import Map.pix;
import Robot.Play;

/**
 * this class represent Gui Window with menuBar , options such as : open Csv , save Csv , save KML , run,
 * showing the progress of the game(packmans in their way to eat the fruits)
 * @author Tal and Aric
 */

public class guiGame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	// package friendly elements
	static JMenuBar wholeMenuBar;
	static JMenu fileMenu,input,start;
	static JMenuItem openItem, saveItem,clearItem;
	static JMenuItem packmanItem,fruitItem,ghostItem,playerItem;
	static JMenuItem runItem,stepItem;

	static converts c;

	static ArrayList<fruit> fruits;
	static ArrayList<packman> packmans;
	static ArrayList<ghost> ghosts;
	static ArrayList<box> boxes;
	private static Play play1;
	static game g;
	static packman player;

	static int x = -1; // for initialize
	static int y = -1; // for initialize

	static boolean addPack;
	static boolean addFruit;
	static boolean addGhost;
	static boolean addPlayer;
	static boolean step;
	static boolean run;

	static ImageIcon imageIcon;
	static MyJLabel jLabel ;

	// Constructor //
	public guiGame ()
	{
		super("Packman Game");
		fruits=new ArrayList<fruit>();
		packmans=new ArrayList<packman>();
		ghosts=new ArrayList<ghost>();
		boxes=new ArrayList<box>();

		addPack=false;
		addFruit=false;
		addGhost=false;
		addPlayer=false;
		step=false;
		run=false;
		player=null;

		try { // because there is map element in the converts
			c=new converts();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void init() {

		wholeMenuBar = new JMenuBar();
		setJMenuBar(wholeMenuBar);
		wholeMenuBar.setVisible(true);

		//set file menu	with open, save
		fileMenu = new JMenu("File");
		input = new JMenu("Input");
		start = new JMenu("start");

		packmanItem =new JMenuItem("packman");
		input.add(packmanItem);

		fruitItem =new JMenuItem("fruit");
		input.add(fruitItem);

		ghostItem =new JMenuItem("ghost");
		input.add(ghostItem);

		playerItem =new JMenuItem("player");
		input.add(playerItem);

		runItem=new JMenuItem("run");
		start.add(runItem);

		stepItem=new JMenuItem("step");
		start.add(stepItem);
		stepItem.setEnabled(false);// aric

		//Open csv
		openItem = new JMenuItem("Open");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);

		//Save to csv
		saveItem = new JMenuItem("save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		fileMenu.add(saveItem);

		//clear
		clearItem=new JMenuItem("clear");
		fileMenu.add(clearItem);
		clearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

		wholeMenuBar.add(fileMenu);
		wholeMenuBar.add(input);
		wholeMenuBar.add(start);

		imageIcon = new ImageIcon("Ariel1.png");
		jLabel = new MyJLabel(imageIcon);
		getContentPane().add(jLabel);
	}

	public void createAndShowGUI()
	{
		init();

		// Actions Listeners for all JMenuItems //
		//local classes

		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fruits.clear();
					packmans.clear();
					ghosts.clear();
					boxes.clear();
					player=null;
					playerItem.setEnabled(true);
					readFileDialog();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call stam function
				String data=Stam.exploitData();
				//then send it into writeResults
				
				writeResults(data);
			}
		});

		clearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("clean pressed");
				fruits.clear();
				packmans.clear();
				ghosts.clear();
				boxes.clear();
				player=null;
				playerItem.setEnabled(true);
				repaint();
			}
		});

		packmanItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPack=true;
			}
		});

		fruitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFruit=true;
			}
		});

		ghostItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addGhost=true;
			}
		});

		playerItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPlayer=true;
			}
		});

		stepItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				step=true;
			}
		});

		runItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				run=true;
				try {
					jLabel.startAutoPlay();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		addWindowListener( new WindowAdapter()
		{
			public void windowResized(WindowEvent evt)
			{
				jLabel.repaint();
			}
		});

		setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight()); // default bounds of map picture
		//System.out.println(JLabel.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		jLabel.repaint();
	}

	public void readFileDialog() throws IOException {

		this.setExtendedState(Frame.MAXIMIZED_BOTH); // Full screen when click on Open file

		// try read from the file
		FileDialog fd = new FileDialog(this, "Open text file", FileDialog.LOAD);
		fd.setFile("*.csv");
		fd.setDirectory("C:\\");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		});

		fd.setVisible(true); // select path to open csv File
		String folder = fd.getDirectory();
		String fileName = fd.getFile();       
		this.setTitle(fileName);// give the game the name of the file

		// create the game 
		g=new game(fruits,packmans,ghosts,boxes);

		// create the play according to the file we open
		setPlay1(new Play(folder+"\\"+fileName));

		ArrayList<String>gameString=getPlay1().getBoard();// put in gameString all the data about the game

		game.createNewGame(gameString);// put the data into our collections
		game.paintGame(getHeight(),getWidth()); // use create and show gui

		//ask for an id in order to write the records
		String num=JOptionPane.showInputDialog("how many users?");
		int numberOfUsers=Integer.parseInt(num);
		while(numberOfUsers>3 || numberOfUsers<1) {

			JOptionPane.showMessageDialog(null,"please enter number between 1-3 ");
			num=JOptionPane.showInputDialog("how many users?");
			numberOfUsers=Integer.parseInt(num);
		}
		int [] users=new int[numberOfUsers];
		for(int i=0;i<numberOfUsers;i++) {

			String idString=JOptionPane.showInputDialog("enter an id");
			int idNum=Integer.parseInt(idString);
			users[i]=idNum;
		}		
		System.out.println("the users: "+Arrays.toString(users));

		switch(numberOfUsers) {

		case 1:	play1.setIDs(users[0]);
		break;
		case 2:	play1.setIDs(users[0],users[1]);
		break;
		case 3: play1.setIDs(users[0],users[1],users[2]);
		break;
		}
	}

	public  void writeResults(String results) {
		 //try write to the file
				FileDialog fd = new FileDialog(this, "Save the text file", FileDialog.SAVE);
				fd.setFile("newFile.csv");
				fd.setFilenameFilter(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".txt");
					}
				});
		//		fd.setVisible(true);
				String folder = fd.getDirectory();
				String fileName = fd.getFile();
				try {
				FileWriter fw = new FileWriter(folder + fileName);
					PrintWriter outs = new PrintWriter(fw);

			    //	String csvString=writeCsv.Write(fruits, packmans,getHeight(),getWidth());
				outs.println(results);
					outs.close();
					fw.close();
				} catch (IOException ex) {
					System.out.print("Error writing file  " + ex);
				}

	}

	//get arrayList of fruits and packmans from game into the gui and repaint with the new arraysList
	public void openFileGUI(ArrayList<fruit>ff,ArrayList<packman>pp,
			ArrayList<ghost>gg,ArrayList<box>bb,packman ourPlayer)
	{	
		//push the ff and pp into fruits and packmans
		//push the gg and bb into ghosts and boxes arrays
		int i=0;
		while(i<pp.size()) {

			packmans.add(pp.get(i));
			i++;
		}
		i=0;
		while(i<ff.size()) {

			fruits.add(ff.get(i));
			i++;
		}

		i=0;
		while(i<gg.size()) {

			ghosts.add(gg.get(i));
			i++;
		}


		i=0;
		while(i<bb.size()) {

			boxes.add(bb.get(i));
			i++;
		}

		player = ourPlayer;

		addWindowListener( new WindowAdapter()
		{
			public void windowResized(WindowEvent evt)
			{
				jLabel.repaint();
			}
		});

		jLabel.repaint();

	}

	// Getters and Setters //

	public static int fruitSize() {

		return fruits.size();
	}

	public static int packSize() {
		return packmans.size();
	}

	public static int ghostSize() {

		return ghosts.size();
	}

	public static int boxSize() {
		return boxes.size();
	}

	public static ArrayList<fruit> getFruitArr() {

		return fruits;
	}
	public static ArrayList<packman> getPackArr() {

		return packmans;
	}

	public static ArrayList<ghost> getGhostArr() {

		return ghosts;
	}
	public static ArrayList<box> getBoxArr() {

		return boxes;
	}

	public static void main(String st[])
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
				guiGame demo = new guiGame();
				demo.createAndShowGUI();

			}
		});
	}

	public static Play getPlay1() {
		return play1;
	}

	public static void setPlay1(Play play1) {
		guiGame.play1 = play1;
	}
}


class MyJLabel extends JLabel implements MouseListener,MouseMotionListener
{

	private static final long serialVersionUID = 1L;
	ImageIcon imageIcon;
	private int oldWidth; // for stretch the screen
	private int oldHeight; // for stretch the screen  
	private boolean start;
	double angle;// the agle between the current position of mouse and player

	public MyJLabel(ImageIcon icon)
	{
		super();
		this.imageIcon = icon;
		addMouseListener(this);
		addMouseMotionListener(this);
		oldHeight=getHeight();
		oldWidth=getWidth();
		start=false;

	}
	/**
	 * this function use annonymy thread make the player move according to the angle
	 * then update the collection of the elements and show in gui
	 * 
	 */
	private void startGame() {

		new Thread() // annonymy thread
		{
			public void run()
			{
				while(guiGame.getPlay1().isRuning())
				{
					guiGame.getPlay1().rotate(angle);
					//System.out.println(guiGame.getPlay1().getStatistics());

					//game!
					ArrayList<String> gameAfterStep=guiGame.getPlay1().getBoard();
					game.clear();
					game.createNewGame(gameAfterStep);
					try {
						game.paintGame(getHeight(), getWidth());

						try {
							Thread.sleep(100);// get time to draw before make more iteration
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				JOptionPane.showMessageDialog(null,"The game is Over the result:"
						+ " "+guiGame.getPlay1().getStatistics());
			}
		}.start();
	}


//	public  void startAutoPlay() throws IOException {
//
//	//	Point3D firstFruitPos=(guiGame.fruits.get(0).getPosition());
//		Point3D playerCoords=converts.pixel2Coords(200, 420,getHeight(), getWidth());
//		guiGame.getPlay1().setInitLocation(playerCoords.x(),playerCoords.y());
//		
//		metaDataPack data=new metaDataPack("M",20,1);
//	//	Point3D position =new Point3D(50,400,0); // add packman location in pixels
//		guiGame.player=new packman(data,new Point3D(200,420));
//		repaint();				
//		//set init location for the player
//	//	 playerCoords=converts.pixel2Coords(50, 400,getHeight(), getWidth());
//	//	guiGame.getPlay1().setInitLocation(playerCoords.x(),playerCoords.y());
//		System.out.println("Player created the game start");
//		guiGame.getPlay1().start();
//		//System.out.println(guiGame.getPlay1().getBoard());
//			
//		double radius=guiGame.player.getRadius();
//		new Thread() // annonymy thread
//		{
//			public void run()
//			{
//
//				while(guiGame.getPlay1().isRuning()) {
//
//					int index = 0;
//					try {
//						index = equation.minToEat(guiGame.boxes,guiGame.fruits, guiGame.player.getPosition());
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//
//					ArrayList<Point3D> path = null;
//					try {
//						path = equation.getPath(guiGame.boxes, guiGame.fruits, guiGame.player.getPosition(), index);
//
//						for(int i=0;i<path.size();i++) {
//							
//							pix playerPix=new pix(guiGame.player.getX(),guiGame.player.getY());
//							pix fruitPix=new pix(path.get(i).x(),path.get(i).y());
//							double angle=converts.angleBet2Pixels(playerPix, fruitPix, getHeight(), getWidth());				
//							System.out.println(angle);
//
//							while(!guiGame.player.getPosition().isEquals(path.get(i)) && guiGame.getPlay1().isRuning()) {
//
//								angle=converts.angleBet2Pixels(playerPix, fruitPix, getHeight(), getWidth());				
//
//								guiGame.getPlay1().rotate(angle);
//								System.out.println(guiGame.getPlay1().isRuning());
//								ArrayList<String>gameAfterStep=guiGame.getPlay1().getBoard();
//								game.clear();
//								game.createNewGame(gameAfterStep);
//								try {
//									game.paintGame(getHeight(), getWidth());
//
//									try {
//										Thread.sleep(100);// get time to draw before make more iteration
//									} catch (InterruptedException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//								} catch (IOException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						}
//
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}							
//				}
//
//			}
//		}.start();
//	}

	
	
	public  void startAutoPlay() throws IOException {

		//for example 8
//		Point3D playerCoords=converts.pixel2Coords(70, 288,getHeight(), getWidth());
//		guiGame.getPlay1().setInitLocation(playerCoords.x(),playerCoords.y());
//		metaDataPack data=new metaDataPack("M",20,1);
//		guiGame.player=new packman(data,new Point3D(70,288));
		
		//for example 5
		double x=guiGame.fruits.get(0).getX();
		double y=guiGame.fruits.get(0).getY();
		//400 //500
		Point3D playerCoords=converts.pixel2Coords(x, y,getHeight(), getWidth());
		guiGame.getPlay1().setInitLocation(playerCoords.x(),playerCoords.y());
		metaDataPack data=new metaDataPack("M",20,1);
		guiGame.player=new packman(data,new Point3D(x,y));
		
		repaint();
		System.out.println("Player created the game start");
		guiGame.getPlay1().start();
			
		new Thread() // annonymy thread
		{
			public void run()
			{

				while(guiGame.getPlay1().isRuning()) {
					double h=getHeight();
					double w=getWidth();
					int index = 0;
					try {
						
						index = equation.minToEat(guiGame.boxes,guiGame.fruits, guiGame.player.getPosition(),h,w);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ArrayList<Point3D> path = null;
					try {
						path = equation.getPath(guiGame.boxes, guiGame.fruits, guiGame.player.getPosition(), index,h,w);
						System.out.println("////////////////////////////////////////////////////////////////////////////");
						for(int i=0;i<path.size();i++) {
						//	path = equation.getPath(guiGame.boxes, guiGame.fruits, guiGame.player.getPosition(), index,h,w);

							pix playerPix=new pix(guiGame.player.getX(),guiGame.player.getY());
							pix fruitPix=new pix(path.get(i).x(),path.get(i).y());
							double angle=converts.angleBet2Pixels(playerPix, fruitPix, getHeight(), getWidth());				
							
								boolean find=true;
							while(find && guiGame.getPlay1().isRuning()) {
								path = equation.getPath(guiGame.boxes, guiGame.fruits, guiGame.player.getPosition(), index,h,w);
								index = equation.minToEat(guiGame.boxes,guiGame.fruits, guiGame.player.getPosition(),h,w);
								playerPix=new pix(guiGame.player.getX(),guiGame.player.getY());
								fruitPix=new pix(path.get(0).x(),path.get(0).y());
								angle=converts.angleBet2Pixels(playerPix, fruitPix, getHeight(), getWidth());
								System.out.println("angle :"+angle +"kabasooso");
								guiGame.getPlay1().rotate(angle);
								ArrayList<String>gameAfterStep=guiGame.getPlay1().getBoard();
								game.clear();
								game.createNewGame(gameAfterStep);
								try {
									game.paintGame(getHeight(), getWidth());

									try {
										Thread.sleep(300);// get time to draw before make more iteration
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//fruitPix doesnt exist in fruits
								//path = equation.getPath(guiGame.boxes, guiGame.fruits, guiGame.player.getPosition(), index,h,w);//not good here
								index = equation.minToEat(guiGame.boxes,guiGame.fruits, guiGame.player.getPosition(),h,w);
								if (index>=0 && index<guiGame.fruits.size()) {
								
									pix kabasoPix=new pix(guiGame.fruits.get(index).getX(),guiGame.fruits.get(index).getY());
									find=exist(kabasoPix);
								}										
							}
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}							
				}

			}
		}.start();
	}
		
	public static boolean exist(pix current) {
		
		int size=guiGame.fruitSize();
			for(int i=0;i<size;i++) {

				if (guiGame.fruits.get(i).getX()==current.x() && guiGame.fruits.get(i).getY()==current.y() ) {
					
					return true;
				}
			}
			return false;
		
	}

	//

	/**
	 * this function change the position of fruits accordingly to resize window
	 */
	public void reSizeFruit(){

		int size=guiGame.fruits.size();
		int i=0;
		while(i<size) {

			double xOld=guiGame.fruits.get(i).getX();
			double yOld=guiGame.fruits.get(i).getY();

			double newWidth = xOld*getWidth()/oldWidth;
			double newHeight = yOld*getHeight()/oldHeight;

			guiGame.fruits.get(i).setX(newWidth);
			guiGame.fruits.get(i).setY(newHeight);

			i++;
		}
	}

	/**
	 * this function change the position of packmans accordingly to resize window
	 */
	public void reSizePackman(){

		int size=guiGame.packmans.size();
		int i=0;
		while(i<size) {

			double xOld=guiGame.packmans.get(i).getX();
			double yOld=guiGame.packmans.get(i).getY();

			double newWidth = xOld*getWidth()/oldWidth;
			double newHeight = yOld*getHeight()/oldHeight;

			guiGame.packmans.get(i).setX(newWidth);
			guiGame.packmans.get(i).setY(newHeight);

			i++;
		}
	}

	/**
	 * this function change the position of ghosts according to resize window
	 */
	public void reSizeGhost(){

		int size=guiGame.ghosts.size();
		int i=0;
		while(i<size) {

			double xOld=guiGame.ghosts.get(i).getX();
			double yOld=guiGame.ghosts.get(i).getY();

			double newWidth = xOld*getWidth()/oldWidth;
			double newHeight = yOld*getHeight()/oldHeight;

			guiGame.ghosts.get(i).setX(newWidth);
			guiGame.ghosts.get(i).setY(newHeight);

			i++;
		}
	}

	/**
	 * this function change the position of player according to resize window
	 */
	public void reSizePlayer(){

		double xOld=guiGame.player.getX();
		double yOld=guiGame.player.getY();

		double newWidth = xOld*getWidth()/oldWidth;
		double newHeight = yOld*getHeight()/oldHeight;

		guiGame.player.setX(newWidth);
		guiGame.player.setY(newHeight);

	}

	/**
	 * this function change the position of boxes according to resize window
	 */
	public void reSizeBox(){

		int size=guiGame.boxes.size();
		int i=0;
		while(i<size) {

			//Start point
			double xOld=guiGame.boxes.get(i).getStart().x();
			double yOld=guiGame.boxes.get(i).getStart().y();

			double newWidth = xOld*getWidth()/oldWidth;
			double newHeight = yOld*getHeight()/oldHeight;

			guiGame.boxes.get(i).getStart().set_x(newWidth);
			guiGame.boxes.get(i).getStart().set_y(newHeight);

			//End point
			xOld=guiGame.boxes.get(i).getEnd().x();
			yOld=guiGame.boxes.get(i).getEnd().y();

			newWidth = xOld*getWidth()/oldWidth;
			newHeight = yOld*getHeight()/oldHeight;

			guiGame.boxes.get(i).getEnd().set_x(newWidth);
			guiGame.boxes.get(i).getEnd().set_y(newHeight);

			i++;
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);

		// update the pixels after stretch
		reSizeFruit();
		reSizePackman();
		reSizeGhost();
		reSizeBox();
		if (guiGame.player!=null) {// the first time we load the screen player==null
			reSizePlayer();
		}

		oldHeight=getHeight();
		oldWidth=getWidth();

		paintFruits(g);
		paintPackmans(g);
		paintGhosts(g);
		paintBoxes(g);
		paintPlayer(g);
	}

	/**
	 * this function scan all the fruit elements and paint them on the screen as red point
	 */
	public void paintFruits(Graphics g) {

		int sizeFruit=guiGame.fruitSize(); // get the number of fruits we draw
		int i=0; // index of the arrayList
		while(i<sizeFruit) { // there are still fruits to show

			guiGame.x = (int)guiGame.getFruitArr().get(i).getX(); // get the X-Axis pixel
			guiGame.y = (int)guiGame.getFruitArr().get(i).getY(); // get the Y-Axis pixel
			Image packman;
			try {
				packman = ImageIO.read(new File("rsz_15fruit.png"));
				g.drawImage(packman,guiGame.x,guiGame.y,this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}

	public void paintGhosts(Graphics g) {

		int sizeGhost=guiGame.ghostSize(); // get the number of fruits we draw
		int i=0; // index of the arrayList
		while(i<sizeGhost) { // there are still fruits to show

			g.setColor(Color.red);
			guiGame.x = (int)guiGame.getGhostArr().get(i).getX(); // get the X-Axis pixel
			guiGame.y = (int)guiGame.getGhostArr().get(i).getY(); // get the Y-Axis pixel

			g.fillOval(guiGame.x, guiGame.y, 7, 7);
			i++;
		}
	}

	public void paintPlayer(Graphics g) {

		g.setColor(Color.PINK);

		if(guiGame.player!=null) {
			int pX=(int) guiGame.player.getPosition().get_x();
			int pY=(int) guiGame.player.getPosition().get_y();

			g.fillOval(pX, pY, 20, 20);

		}
	}

	public void paintBoxes(Graphics g) {

		g.setColor(Color.black);

		int sizeBoxes=guiGame.boxSize(); // get the number of fruits we draw
		int i=0; // index of the arrayList
		while(i<sizeBoxes) { // there are still fruits to show

			int p1X = (int)guiGame.getBoxArr().get(i).getStart().x(); // get the X-Axis pixel
			int p1Y = (int)guiGame.getBoxArr().get(i).getStart().y();  // get the Y-Axis pixel

			int p2X = (int)guiGame.getBoxArr().get(i).getEnd().x(); // get the X-Axis pixel
			int p2Y = (int)guiGame.getBoxArr().get(i).getEnd().y();  // get the Y-Axis pixel

			g.fillRect(p1X, p2Y, p2X-p1X, p1Y-p2Y);

			i++;
		}
	}


	/**
	 * this function scan all the Packmans elements and paint them on the screen as blue point
	 */
	public void paintPackmans(Graphics g) {

		Color c=new Color(0,0,204); //blue Sea
		g.setColor(c);
		int sizePackmans=guiGame.packSize(); // get the number of fruits we draw
		int j=0; // index of the arrayList
		while(j<sizePackmans) { // there are still packmans to show

			guiGame.x = (int)guiGame.getPackArr().get(j).getX(); // get the X-Axis pixel
			guiGame.y = (int)guiGame.getPackArr().get(j).getY(); // get the Y-Axis pixel
			Image packman;
			try {
				packman = ImageIO.read(new File("rsz_15packman.png"));
				g.drawImage(packman, guiGame.x,guiGame.y,this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			j++;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

		// aric mybe delete make the functions outside this claa

		guiGame.x=e.getX();
		guiGame.y=e.getY();

		if (guiGame.addPack) {
			String i="draw pack";
			i+=1;
			metaDataPack data=new metaDataPack(i,1,1);
			Point3D position =new Point3D(guiGame.x,guiGame.y,0); // add packman location in pixels
			packman pack=new packman(data,position);
			guiGame.packmans.add(pack);
			repaint();
			System.out.println("number of packmans: "+guiGame.packmans.size());	
			guiGame.addPack=false;
		}

		if (guiGame.addFruit) {

			String j="draw fruit";
			j+=1;
			metaDataFruit data=new metaDataFruit(j,1);
			Point3D position =new Point3D(guiGame.x,guiGame.y,0); // add fruit location in pixels
			fruit f=new fruit(data,position);
			guiGame.fruits.add(f);
			repaint();
			System.out.println("number of fruits: "+guiGame.fruits.size());
			guiGame.addFruit=false;
		}

		if (guiGame.addGhost) {

			String h="draw ghost";
			h+=1;
			metaDataPack data=new metaDataPack(h,1,1);
			Point3D position =new Point3D(guiGame.x,guiGame.y,0); // add packman location in pixels
			ghost g=new ghost(data,position);
			guiGame.ghosts.add(g);
			repaint();
			System.out.println("number of ghosts: "+guiGame.ghosts.size());	
			guiGame.addGhost=false;
		}
		if (guiGame.addPlayer) {

			int x=guiGame.x;
			int y=guiGame.y;
			String p="M";
			metaDataPack data=new metaDataPack(p,20,1);
			Point3D position =new Point3D(x,y,0); // add packman location in pixels
			guiGame.player=new packman(data,position);
			repaint();
			guiGame.addPlayer=false;
			guiGame.playerItem.setEnabled(false);
			guiGame.stepItem.setEnabled(true);//aric 

			//set init location for the player
			Point3D playerCoords=converts.pixel2Coords(x, y,getHeight(), getWidth());
			guiGame.getPlay1().setInitLocation(playerCoords.x(),playerCoords.y());
			guiGame.getPlay1().start();//aric
			System.out.println("Player created the game start");
			startGame();
		}

		if (guiGame.step) {

			start=true;

		}

		if (guiGame.run) {

		
		}

		System.out.println("("+ guiGame.x + "," + guiGame.y +")");
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mouseMoved(MouseEvent e)
	{
		// change the value of x and y according to the 
		//mouse position then the thread will see the new position if the mouse moved 
		//and the old position if the mouse stays
		guiGame.x=e.getX();
		guiGame.y=e.getY();

		if (guiGame.player!=null) {

			pix player=new pix(guiGame.player.getX(),guiGame.player.getY());
			pix pressed=new pix(guiGame.x,guiGame.y);
			angle=converts.angleBet2Pixels(player,pressed,getHeight(),getWidth());
		}	
	}
}