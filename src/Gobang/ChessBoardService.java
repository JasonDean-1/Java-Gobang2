/**
 * 
 */
package Gobang;

import Gobang.ChessBoardFrame;

/**
 * @author jason dean( jason-dean@outlook.com)
 * @version 1.0
 */
public class ChessBoardService
{
	// 定义达到赢条件的棋子数目
	private final int WIN_COUNT = 5;
	private ChessBoardFrame chessBoardFrame = null;

	public ChessBoardService(ChessBoardFrame chessBoardFrame)
	{
		this.chessBoardFrame = chessBoardFrame;
	}

	/**
	 * 判断输赢
	 * 
	 * @param posX
	 *            棋子的X坐标。
	 * @param posY
	 *            棋子的Y坐标
	 * @param ico
	 *            棋子类型
	 * @return 如果有五颗相邻棋子连成一条直接，返回真，否则相反。
	 */
	public boolean isWon(int posX, int posY, String player)
	{
		// 直线起点的X坐标
		int startX = 0;
		// 直线起点Y坐标
		int startY = 0;
		// 直线结束X坐标
		int endX = ChessBoardFrame.BOARD_SIZE - 1;
		// 直线结束Y坐标
		int endY = endX;
		// 同条直线上相邻棋子累积数
		int sameCount = 0;
		int temp = 0;

		// 计算起点的最小X坐标与Y坐标
		temp = posX - WIN_COUNT + 1;
		startX = temp < 0 ? 0 : temp;
		temp = posY - WIN_COUNT + 1;
		startY = temp < 0 ? 0 : temp;
		// 计算终点的最大X坐标与Y坐标
		temp = posX + WIN_COUNT - 1;
		endX = temp > ChessBoardFrame.BOARD_SIZE - 1 ? ChessBoardFrame.BOARD_SIZE - 1 : temp;
		temp = posY + WIN_COUNT - 1;
		endY = temp > ChessBoardFrame.BOARD_SIZE - 1 ? ChessBoardFrame.BOARD_SIZE - 1 : temp;
		// 从左到右方向计算相同相邻棋子的数目
		String[][] board = chessBoardFrame.getBoard();
		for (int i = startY; i < endY; i++)
		{
			if (board[posX][i] == player && board[posX][i + 1] == player)
			{
				sameCount++;
			} else if (sameCount != WIN_COUNT - 1)
			{
				sameCount = 0;
			}
		}
		if (sameCount == 0)
		{
			// 从上到下计算相同相邻棋子的数目
			for (int i = startX; i < endX; i++)
			{
				if (board[i][posY] == player && board[i + 1][posY] == player)
				{
					sameCount++;
				} else if (sameCount != WIN_COUNT - 1)
				{
					sameCount = 0;
				}
			}
		}
		if (sameCount == 0)
		{
			// 从左上到右下计算相同相邻棋子的数目
			int j = startY;
			for (int i = startX; i < endX; i++)
			{
				if (j < endY)
				{
					if (board[i][j] == player && board[i + 1][j + 1] == player)
					{
						sameCount++;
					} else if (sameCount != WIN_COUNT - 1)
					{
						sameCount = 0;
					}
					j++;
				}
			}
		}
		return sameCount == WIN_COUNT - 1 ? true : false;
	}
}
