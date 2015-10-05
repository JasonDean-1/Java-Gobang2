package Gobang;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import AI.AI;

/**
 * @author jason dean( jason-dean@outlook.com)
 * @version 1.0
 */
public class ChessBoardFrame extends JFrame
{
	private final String PlAYER = "��";
	private final String AI = "*";
	private boolean isPlayerTurn = true;
	private boolean isOver = false;
	private int selectedX = -1;
	private int selectedY = -1;
	// �������̵Ĵ�С
	public static int BOARD_SIZE = 15;
	// �������̿��߶��ٸ�����
	private final int BOARD_WIDTH = 535;
	private final int BOARD_HEIGHT = 550;
	// �����������������ֵ����������֮���ƫ�ƾࡣ
	private final int X_OFFSET = 5;
	private final int Y_OFFSET = 6;
	// �����������������ֵ����������֮��ı��ʡ�
	private final int RATE = BOARD_WIDTH / BOARD_SIZE;
	private ChessBoardFrame chessBoardFrame = null;
	private String[][] chessBoard = new String[BOARD_SIZE][BOARD_SIZE];
	private ChessBoardPanel chessBoardPanel = new ChessBoardPanel();
	private ChessBoardService service = null;
	private AI ai = new AI();
	private JDialog overDialog = null;

	// ��������λͼ�ֱ�������̡����ӡ�����
	BufferedImage boardTable;
	BufferedImage chessWhite;
	BufferedImage chessBlack;
	// ������ƶ�ʱ���ѡ���
	BufferedImage selected;

	/**
	 * ������
	 * 
	 * @throws IOException
	 */
	public ChessBoardFrame() throws IOException
	{
		initialize();
	}

	/**
	 * ��ʼ������
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException
	{
		chessBoardFrame = this;
		service = new ChessBoardService(chessBoardFrame);
		boardTable = ImageIO.read(new File("Image/board.jpg"));
		chessBlack = ImageIO.read(new File("Image/black.gif"));
		chessWhite = ImageIO.read(new File("Image/white.gif"));
		selected = ImageIO.read(new File("Image/selected.gif"));
		for (int i = 0; i < BOARD_SIZE; i++)
		{
			for (int j = 0; j < BOARD_SIZE; j++)
			{
				chessBoard[i][j] = "+";
			}
		}

		// ������ƶ�ʱ���ı�ѡ�е������
		chessBoardPanel.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				selectedX = (e.getX() - X_OFFSET) / RATE;
				selectedY = (e.getY() - Y_OFFSET) / RATE;
				chessBoardPanel.repaint();
			}
		});
		/*
		 * �����������2����������Ϊ������������꣬����board���顣 ���Ҽ��
		 * 
		 * @1.�������ĵ��Ѿ����ӣ������ظ����塣
		 * 
		 * @2.ÿ���������Ҫɨ��˭Ӯ��
		 */
		chessBoardPanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int xPos = -1;
				int yPos = -1;
				do
				{
					if (isPlayerTurn)
					{
						xPos = (int) ((e.getX() - X_OFFSET) / RATE);
						yPos = (int) ((e.getY() - Y_OFFSET) / RATE);
						if (chessBoard[xPos][yPos] != "+")
							return;
					} else
					{
						int[] aiPos = ai.getPosition();
						xPos = aiPos[0];
						yPos = aiPos[1];
					}
				} while (chessBoard[xPos][yPos] != "+");
				if (isPlayerTurn)
				{
					chessBoard[xPos][yPos] = "��";
					isOver = service.isWon(xPos, yPos, PlAYER);
					isPlayerTurn = false;
				} else
				{
					chessBoard[xPos][yPos] = "*";
					isOver = service.isWon(xPos, yPos, AI);
					isPlayerTurn = true;
				}

				chessBoardPanel.repaint();
				if (isOver)
				{
					JOptionPane.showMessageDialog(null, "GameOver", "GameOver", JOptionPane.ERROR_MESSAGE);
				}
			}

			// ������˳��������󣬸�λѡ�е�����
			@Override
			public void mouseExited(MouseEvent e)
			{
				selectedX = -1;
				selectedY = -1;
				chessBoardPanel.repaint();
			}
		});
		this.add(chessBoardPanel);

		this.setTitle("GoBang Game");
		this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		// ����ToolKit��ȡ��Ļ��ߣ���ʹ��setLocation��Frame��ʼ������Ļ����
		Toolkit toolKit = Toolkit.getDefaultToolkit();
		this.setLocation((int) ((toolKit.getScreenSize().getWidth() - BOARD_WIDTH) / 2),
				(int) ((toolKit.getScreenSize().getHeight() - BOARD_HEIGHT) / 2));
		// this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);

	}

	class ChessBoardPanel extends JPanel
	{
		// ��дJPanel��paint������ʵ�ֻ滭
		public void paint(Graphics graphics)
		{
			// ����������������
			graphics.drawImage(boardTable, 0, 0, null);
			// ����ѡ�е�ĺ��
			if (selectedX >= 0 && selectedY >= 0)
			{
				graphics.drawImage(selected, selectedX * RATE, selectedY * RATE, null);
			}
			// �������飬�������ӡ�
			for (int i = 0; i < BOARD_SIZE; i++)
			{
				for (int j = 0; j < BOARD_SIZE; j++)
				{
					// ���ƺ���
					if (chessBoard[i][j].equals("��"))
					{
						graphics.drawImage(chessBlack, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
					// ���ư���
					if (chessBoard[i][j].equals("*"))
					{
						graphics.drawImage(chessWhite, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
				}
			}
		}
	}

	/**
	 * ���ص�ǰ����״̬��������Ƶ����㷨
	 * 
	 * @return
	 */
	public String[][] getBoard()
	{
		return this.chessBoard;
	}
}
