import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();



		setTitle("Select Your Hero!");
		setBackground(Color.RED);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(220, 350);
		setLocation(((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2));


		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x54DDE2));
		setContentPane(panel);
		panel.setLayout(null);

		ImageIcon sleepyButton = new ImageIcon();
		ImageIcon brainyButton = new ImageIcon();


		try {
			Image brainyPNG = ImageIO.read(this.getClass().getResourceAsStream("/Images/brainy.png"))
					.getScaledInstance(90, 90, Image.SCALE_REPLICATE);

			brainyButton = new ImageIcon(brainyPNG);

			Image sleepyPNG = ImageIO.read(this.getClass().getResourceAsStream("/Images/sleepy.png"))
					.getScaledInstance(90, 90, Image.SCALE_REPLICATE);

			sleepyButton = new ImageIcon(sleepyPNG);

		} catch (IOException ex) {
			System.out.print(ex);
		}

		JButton buttonBrainy = new JButton(brainyButton);
		buttonBrainy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GameCube(new Brainy(new PlayerCharacteristic(2,5,15),1, "Brainy", 20, new Location(5, 6)));
				dispose();
			}
		});
		buttonBrainy.setForeground(Color.magenta);
		buttonBrainy.setBackground(Color.magenta);
		buttonBrainy.setBounds(40, 50, 110, 110);

		JButton buttonSleepy = new JButton(sleepyButton);
		buttonSleepy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GameCube(new Sleepy(new PlayerCharacteristic(1,5,15),1, "Sleepy", 20, new Location(5, 6)));
				dispose();
			}
		});
		buttonSleepy.setForeground(new Color(0x59971C));
		buttonSleepy.setBackground(new Color(0x59971C));
		buttonSleepy.setBounds(40, 180, 110, 110);

		panel.add(buttonBrainy);
		panel.add(buttonSleepy);

	}
}
