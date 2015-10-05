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
	private final String PlAYER = "●";
	private final String AI = "*";
	private boolean isPlayerTurn = true;
	private boolean isOver = false;
	private int selectedX = -1;
	private int selectedY = -1;
	// 定义棋盘的大小
	public static int BOARD_SIZE = 15;
	// 定义棋盘宽、高多少个像素
	private final int BOARD_WIDTH = 535;
	private final int BOARD_HEIGHT = 550;
	// 定义棋盘座标的像素值和棋盘数组之间的偏移距。
	private final int X_OFFSET = 5;
	private final int Y_OFFSET = 6;
	// 定义棋盘座标的像素值和棋盘数组之间的比率。
	private final int RATE = BOARD_WIDTH / BOARD_SIZE;
	private ChessBoardFrame chessBoardFrame = null;
	private String[][] chessBoard = new String[BOARD_SIZE][BOARD_SIZE];
	private ChessBoardPanel chessBoardPanel = new ChessBoardPanel();
	private ChessBoardService service = null;
	private AI ai = new AI();
	private JDialog overDialog = null;

	// 下面三个位图分别代表棋盘、黑子、白子
	BufferedImage boardTable;
	BufferedImage chessWhite;
	BufferedImage chessBlack;
	// 当鼠标移动时候的选择框
	BufferedImage selected;

	/**
	 * 构造体
	 * 
	 * @throws IOException
	 */
	public ChessBoardFrame() throws IOException
	{
		initialize();
	}

	/**
	 * 初始化界面
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

		// 当鼠标移动时，改变选中点的座标
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
		 * 电脑随机生成2个整数，作为电脑下棋的座标，赋给board数组。 并且检测
		 * 
		 * @1.如果下棋的点已经棋子，不能重复下棋。
		 * 
		 * @2.每次下棋后，需要扫描谁赢了
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
					chessBoard[xPos][yPos] = "●";
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

			// 当鼠标退出棋盘区后，复位选中点座标
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
		// 利用ToolKit获取屏幕宽高，并使用setLocation将Frame初始化到屏幕中央
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
		// 重写JPanel的paint方法，实现绘画
		public void paint(Graphics graphics)
		{
			// 将绘制五子棋棋盘
			graphics.drawImage(boardTable, 0, 0, null);
			// 绘制选中点的红框
			if (selectedX >= 0 && selectedY >= 0)
			{
				graphics.drawImage(selected, selectedX * RATE, selectedY * RATE, null);
			}
			// 遍历数组，绘制棋子。
			for (int i = 0; i < BOARD_SIZE; i++)
			{
				for (int j = 0; j < BOARD_SIZE; j++)
				{
					// 绘制黑棋
					if (chessBoard[i][j].equals("●"))
					{
						graphics.drawImage(chessBlack, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
					// 绘制白棋
					if (chessBoard[i][j].equals("*"))
					{
						graphics.drawImage(chessWhite, i * RATE + X_OFFSET, j * RATE + Y_OFFSET, null);
					}
				}
			}
		}
	}

	/**
	 * 返回当前期盼状态，用于设计电脑算法
	 * 
	 * @return
	 */
	public String[][] getBoard()
	{
		return this.chessBoard;
	}
}
