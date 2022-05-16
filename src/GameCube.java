import javax.imageio.ImageIO;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static javax.swing.JOptionPane.showMessageDialog;

public class GameCube extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel score;
	private int[][] map;
	private Player player;
	private ArrayList<Enemy> smurfHunters;
	private JButton[][] buttons;
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Objects> goldCoins = new ArrayList<Objects>();

	private ArrayList<Location> arrPaths = new ArrayList<Location>();

	private Objects mushroom;
	private int gameColumn = 11;
	private int gameRow = 13;

	private int goldAmount = 5;
	private Random randomNum = new Random();

	public GameCube(Player player) {

		readFile("/Images/harita.txt");

		setTitle("Let's Smurf");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(0xC6C6FC));

		setSize(640, 480);
		setLocationRelativeTo(null);
		setResizable(true);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0xAAC49C));

		ImageIcon smurfetteIcon = getIconImage("smurfette", 60, 60);
		JLabel smurfette = new JLabel(smurfetteIcon);
		smurfette.setBounds(540, 260, 40, 40);

		score = new JLabel("SmurfCoin : " + player.showScore());
		score.setForeground(new Color(0x610710));
		score.setBounds(280, 5, 220, 10);

		panel.add(score);
		panel.add(smurfette);
		add(panel);

		this.player = player;



		createMap(panel);
		dropCharsToMap();

		Thread gold = new Thread(() -> {
			goldHQ();
		});

		gold.start();
		Thread mushroom = new Thread(() -> {
			mushroomHQ();
		});
		mushroom.start();

		setVisible(true);

		enemyPath(null,false);

	}


	private synchronized void enemyPath(Enemy tempEnemy, boolean isMoving) {
		ArrayList<Location> path;
		if (arrPaths != null) {
			for (Location coordinate : arrPaths) {
				buttons[coordinate.getX()][coordinate.getY()].setBackground(new Color(0xC6C6FC));
			}
			arrPaths.clear();
		}

		for (Enemy enemy : smurfHunters) {
			if(!enemy.equals(tempEnemy)) {
				path = enemy.closestDijkstra(map, player.getCoordinate());
				arrPaths.addAll(path);
				for (Location coordinate : path) {
					buttons[coordinate.getX()][coordinate.getY()].setBackground(enemy.getColor());
				}
				if (isMoving)
					enemyMove(enemy, path);
			}
		}
	}


	private synchronized void enemyMove(Enemy enemy, ArrayList<Location> path) {
		Location newCoordinate;
		if (path.size() > enemy.getEnemyCharacteristic().getStep()) {
			newCoordinate = path.get(path.size() - enemy.getEnemyCharacteristic().getStep() - 1);
			for (int i = 0; i < enemy.getEnemyCharacteristic().getStep(); i++) {
				if(buttons[path.get(path.size() - i - 1).getX()][path.get(path.size() - i - 1).getY()].getBackground() == enemy.getColor() )
					buttons[path.get(path.size() - i - 1).getX()][path.get(path.size() - i - 1).getY()].setBackground(new Color(0xC6C6FC));
			}

			if (tileHQ(newCoordinate.getX(), newCoordinate.getY(), false)) {
				buttons[newCoordinate.getX()][newCoordinate.getY()]
						.setIcon(buttons[enemy.getCoordinate().getX()][enemy.getCoordinate().getY()].getIcon());
				buttons[enemy.getCoordinate().getX()][enemy.getCoordinate().getY()].setIcon(null);
				enemy.setCoordinate(newCoordinate);

			}
			else if (newCoordinate.getX() == player.getCoordinate().getX()
					&& newCoordinate.getY() == player.getCoordinate().getY()) {
				duelTime(enemy);
			}

			enemyPath(null,false);
		}
	}

 	private synchronized void duelTime(Enemy enemy) {
		Player playerScore =  new Score(this.player, enemy);

		int score = playerScore.showScore();

		Thread scoreThread = new Thread(() -> {
			updateSmurfCoin(score);
		});

		scoreThread.start();

		if (score <= 0) {
			showMessageDialog(null, "You lost your coins! Smurf Village is destroyed!");
			dispose();
			System.exit(0);
		}


		else {
			buttons[enemy.getStartingPoint().getX()][enemy.getStartingPoint().getY()]
					.setIcon(buttons[enemy.getCoordinate().getX()][enemy.getCoordinate().getY()].getIcon());

			if(enemy.getCoordinate().getX() != enemy.getStartingPoint().getX() && enemy.getCoordinate().getY() != enemy.getStartingPoint().getY()) {
				buttons[enemy.getCoordinate().getX()][enemy.getCoordinate().getY()].setIcon(null);
			}

			enemy.setCoordinate(enemy.getStartingPoint());
		}

	}

	private void goldHQ() {

		int randomX, randomY;
		try {
			while (true) {
				Thread.sleep((randomNum.nextInt(10) + 1) * 1000);

				for (int i = 0; i < goldAmount; i++) {

					randomX = 0; randomY = 0;
					while (map[randomX][randomY] == 0 || !tileHQ(randomX, randomY, true)
							|| doorCoordinates(randomX, randomY) != "")
					{
						randomX = randomNum.nextInt(gameColumn);
						randomY = randomNum.nextInt(gameRow);
					}

					goldCoins.add(new Gold("Gold", 5, 10, 5, true, new Location(randomX, randomY)));
				}


				for (Objects gold : goldCoins) {

					if (tileHQ(gold.getCoordinate().getX(), gold.getCoordinate().getY(), false)) {
						buttons[gold.getCoordinate().getX()][gold.getCoordinate().getY()]
								.setIcon(getIconImage("coin", 50, 40));

					} else if (gold.getCoordinate().getX() == player.getCoordinate().getX()
							&& gold.getCoordinate().getY() == player.getCoordinate().getY()) {
						player.setSmurfCoin(player.getSmurfCoin() + gold.getObjectScore());
						updateSmurfCoin(player.showScore());
					}

				}

				Thread.sleep(5 * 1000);
				for (Objects gold : goldCoins) {
					if (gold.isOnOff() && tileHQ(gold.getCoordinate().getX(), gold.getCoordinate().getY(), false)) {
						buttons[gold.getCoordinate().getX()][gold.getCoordinate().getY()].setIcon(null);
					}
				}
				goldCoins.clear();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void mushroomHQ() {

		int randomX, randomY;

		try {
			while (true) {
				Thread.sleep((randomNum.nextInt(20) + 1) * 1000);

				randomX = 0; randomY = 0;

				while (map[randomX][randomY] == 0 || !tileHQ(randomX, randomY, true) || doorCoordinates(randomX, randomY) != "") {
					randomX = randomNum.nextInt(gameColumn);
					randomY = randomNum.nextInt(gameRow);
				}

				mushroom = new Mushroom("Mushroom", 50, 20, 7, true, new Location(randomX, randomY));

				if (tileHQ(mushroom.getCoordinate().getX(), mushroom.getCoordinate().getY(), false)) {

					buttons[mushroom.getCoordinate().getX()][mushroom.getCoordinate().getY()]
							.setIcon(getIconImage("mushroom", 40,40));

				} else if (mushroom.getCoordinate().getX() == player.getCoordinate().getX()
						&& mushroom.getCoordinate().getY() == player.getCoordinate().getY()) {
					player.setSmurfCoin(player.getSmurfCoin() + mushroom.getObjectScore());
					updateSmurfCoin(player.showScore());
				}

				Thread.sleep(mushroom.getTimeToAppear() * 1000);
				if (mushroom.isOnOff() && tileHQ(mushroom.getCoordinate().getX(), mushroom.getCoordinate().getY(), false)) {
					buttons[mushroom.getCoordinate().getX()][mushroom.getCoordinate().getY()].setIcon(null);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean tileHQ(int x, int y, boolean check) {

		charReset();

		for (Character character : characters) {
			if (character.getCoordinate().getX() == x && character.getCoordinate().getY() == y)
				return false;
		}
		if (check) {
			for (Objects objects : goldCoins) {
				if (objects.getCoordinate().getX() == x && objects.getCoordinate().getY() == y)
					return false;
			}
			if (mushroom != null) {
				if (mushroom.getCoordinate().getX() == x && mushroom.getCoordinate().getY() == y)
					return false;
			}
		}

		return true;
	}

	private void charReset() {
		characters.clear();
		characters.add(player);
		characters.addAll(smurfHunters);
	}

	private synchronized void moveChar(int keyCode) throws InterruptedException {

		int x, y, step;


		x = player.getCoordinate().getX();
		y = player.getCoordinate().getY();

		step = player.getPlayerCharacteristic().getStep();

		Enemy enemy; Enemy temp;

		switch (keyCode) {

		case KeyEvent.VK_UP:
			if (x - step > 0) {

 				if (map[x - step][y] != 0) {

					enemy = null;

					for (int i = 1; i <= step; i++) {

						if (map[x - i][y] != 0) {
							objectsHQ(x - i, y);

							temp = enemyHQ(x - i, y);
							if(temp != null) {
								enemy = temp;
							}

							player.getCoordinate().setX(x - i);

							buttons[x - i][y].setIcon(buttons[x - i + 1][y].getIcon());
							buttons[x - i + 1][y].setIcon(null);

							Thread.sleep(250);
							if(i == step) {
								enemyPath(enemy,true);
							}
						}
						else {
							break;
						}

					}

				}
			}
			break;
		case KeyEvent.VK_DOWN:
			if (x + step < gameColumn) {
				if (map[x + step][y] != 0) {
					enemy = null;
					for (int i = 1; i <= step; i++) {
						if (map[x + i][y] != 0) {
							objectsHQ(x + i, y);
							temp = enemyHQ(x + i, y);
							if(temp != null) {
								enemy = temp;
							}
							player.getCoordinate().setX(x + i);
							buttons[x + i][y].setIcon(buttons[x + i - 1][y].getIcon());
							buttons[x + i - 1][y].setIcon(null);
							Thread.sleep(250);
							if(i == step) {
								enemyPath(enemy,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (y + step < gameRow) {
				if (map[x][y + step] != 0) {
					enemy = null;
					for (int i = 1; i <= step; i++) {
						if (map[x][y + i] != 0) {
							objectsHQ(x, y + i);
							temp = enemyHQ(x, y + i);
							if(temp != null) {
								enemy = temp;
							}
							player.getCoordinate().setY(y + i);
							buttons[x][y + i].setIcon(buttons[x][y + i - 1].getIcon());
							buttons[x][y + i - 1].setIcon(null);
							Thread.sleep(250);
							if (x == 7 && (y + i) == 12) {
								showMessageDialog(null, "You Saved The Village! Good Job!");
								dispose();
								System.exit(0);
							}
							if(i == step) {
								enemyPath(enemy,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		case KeyEvent.VK_LEFT:
			if (y - step > 0) {
				if (map[x][y - step] != 0) {

					enemy = null;

					for (int i = 1; i <= step; i++) {

						if (map[x][y - i] != 0) {

							objectsHQ(x, y - i);
							temp = enemyHQ(x, y - i);
							if(temp != null) {
								enemy = temp;
							}

							player.getCoordinate().setY(y - i);
							buttons[x][y - i].setIcon(buttons[x][y - i + 1].getIcon());
							buttons[x][y - i + 1].setIcon(null);
							Thread.sleep(250);

							if(i == step) {
								enemyPath(enemy,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		}
	}

	private void updateSmurfCoin(int smurfCoin) {
		score.setText("SmurfCoin : "+ smurfCoin);
	}

	private void objectsHQ(int x, int y) {

		for (Objects objects : goldCoins) {
			if (objects.getCoordinate().getX() == x && objects.getCoordinate().getY() == y && objects.isOnOff()) {
				player.setSmurfCoin(player.getSmurfCoin() + objects.getObjectScore());
				objects.setOnOff(false);

				Thread thread3 = new Thread(() -> {
					updateSmurfCoin(player.showScore());
				});
				thread3.start();
			}
		}
		if (mushroom != null) {
			if (mushroom.getCoordinate().getX() == x && mushroom.getCoordinate().getY() == y && mushroom.isOnOff()) {
				player.setSmurfCoin(player.getSmurfCoin() + mushroom.getObjectScore());
				mushroom.setOnOff(false);
				Thread thread4 = new Thread(() -> {
					updateSmurfCoin(player.showScore());
				});
				thread4.start();

			}
		}
	}

	private Enemy enemyHQ(int x, int y) {
		Enemy enemy = null;
		for (Enemy tempEnemy : smurfHunters) {
			if (tempEnemy.getCoordinate().getX() == x && tempEnemy.getCoordinate().getY() == y) {
				enemy = tempEnemy;
				break;
			}
		}
		if (enemy != null) {
			duelTime(enemy);
			return enemy;
		}
		return null;

	}

	private void createMap(JPanel panel) {

		int buttonWidth = 40;
		int buttonHeight = 35;

		String openDoors;
		charReset();

		buttons = new JButton[gameColumn][gameRow];

		for (int i = 0; i < gameColumn; i++) {
			for (int j = 0; j < gameRow; j++) {
				openDoors = doorCoordinates(i, j);
				buttons[i][j] = new JButton();
				buttons[i][j].setForeground(Color.YELLOW);

				buttons[i][j].setBorder(new LineBorder(new Color(2)));

				if (map[i][j] == 0) {
					buttons[i][j].setBackground(new Color(101, 44, 44));
				} else if (openDoors != "") {
					buttons[i][j].setBackground(new Color(156, 127, 71));
					for (Character character : characters) {
						if (character.getCoordinate().getX() == i && character.getCoordinate().getY() == j) {
							buttons[i][j].setBackground(new Color(0xC6C6FC));
						} else {
							buttons[i][j].setText(openDoors);
						}
					}
					if(buttons[i][j].getBackground() != new Color(0xC6C6FC)) {
						map[i][j] = 0;

						buttons[i][j].setBackground(new Color(102, 0, 255));
					}
				} else {
					buttons[i][j].setBackground(new Color(0xC6C6FC));
				}


				buttons[i][j].setBounds(20 + j * buttonWidth, 20 + i * buttonHeight, buttonWidth, buttonHeight);
				buttons[i][j].addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {

					}

					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
								|| e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
							Thread thread = new Thread(() -> {
								try {
									moveChar(e.getKeyCode());
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							});
							thread.start();
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {

					}
				});

				panel.add(buttons[i][j]);
			}
		}
	}

	private void dropCharsToMap() {

		String imageName = ""; int x, y;
		charReset();

		for (Character character : characters) {

			x = character.getCoordinate().getX();
			y = character.getCoordinate().getY();

			switch (character.getName()) {
			case "Gargamel":
				imageName = "gargamel";
				break;
			case "Azrael":
				imageName = "azrael";
				break;
			case "Brainy":
				imageName = "brainy";
				break;
			case "Sleepy":
				imageName = "sleepy";
				break;
			}

			buttons[x][y].setText("");
			buttons[x][y].setIcon(getIconImage(imageName, 42, 38));

		}
	}

	private ImageIcon getIconImage(String imageName, int witdh, int height) {
		Image image;
		ImageIcon imageIcon = null;
		try {
			image = ImageIO.read(this.getClass().getResourceAsStream("/Images/" + imageName + ".png"))
					.getScaledInstance(witdh, height, java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
		} catch (IOException ex) {
			System.out.print(ex);
		}
		return imageIcon;
	}

	private String doorCoordinates(int a, int b) {
		if (a == 0 && b == 3) {
			return "A";
		} else if (a == 0 && b == 10) {
			return "B";
		} else if (a == 5 && b == 0) {
			return "C";
		} else if (a == 10 && b == 3) {
			return "D";
		} else if (a == 5 && b == 6) {
			return "S";
		} else {
			return "";
		}
	}

	private void readFile(String filePath) {

		Enemy enemy;
		Location coordinate = new Location(0, 0);
		map = new int[gameColumn][gameRow];
		smurfHunters = new ArrayList<Enemy>();
		int enemyNumber = 0, rowNumber = 0, enemyID;
		String enemyName = "";
		try {
			Scanner scn = new Scanner(this.getClass().getResourceAsStream(filePath));
			String row;
			while (scn.hasNextLine()) {
				row = scn.nextLine();
				if (row.startsWith("Karakter:")) {
					enemyID = enemyNumber++;
					for (var item : row.split(",")) {
						item = item.substring(item.indexOf(":") + 1);
						if (item.length() > 1) {
							enemyName = item;
						} else {
							switch (item) {
							case "A":
								coordinate = new Location(0, 3);
								break;
							case "B":
								coordinate = new Location(0, 10);
								break;
							case "C":
								coordinate = new Location(5, 0);
								break;
							case "D":
								coordinate = new Location(10, 3);
								break;
							} 
						}
					}

					if (enemyName.startsWith("Gargamel")) {
						enemy = new Gargamel(new EnemyCharacteristic(2, true), enemyID, enemyName,
								new Color(randomNum.nextFloat(), randomNum.nextFloat(), randomNum.nextFloat()), coordinate);
						enemy.setStartingPoint(coordinate);
						smurfHunters.add(enemy);
					} else if (enemyName.startsWith("Azrael")) {
						enemy = new Azrael(new EnemyCharacteristic(1, false), enemyID, enemyName,
								new Color(randomNum.nextFloat(), randomNum.nextFloat(), randomNum.nextFloat()), coordinate);
						enemy.setStartingPoint(coordinate);
						smurfHunters.add(enemy);
					}

				} else {
					int i = 0;
					for (var item : row.split("	")) {
						map[rowNumber][i] = Integer.parseInt(item);
						i++;
					}
					rowNumber++;
				}
			}
		} catch (Exception e) {
			System.err.println("Error:" + e);
		}
	}

}
