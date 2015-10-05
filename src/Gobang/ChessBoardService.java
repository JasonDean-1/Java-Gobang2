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
	// ����ﵽӮ������������Ŀ
	private final int WIN_COUNT = 5;
	private ChessBoardFrame chessBoardFrame = null;

	public ChessBoardService(ChessBoardFrame chessBoardFrame)
	{
		this.chessBoardFrame = chessBoardFrame;
	}

	/**
	 * �ж���Ӯ
	 * 
	 * @param posX
	 *            ���ӵ�X���ꡣ
	 * @param posY
	 *            ���ӵ�Y����
	 * @param ico
	 *            ��������
	 * @return ��������������������һ��ֱ�ӣ������棬�����෴��
	 */
	public boolean isWon(int posX, int posY, String player)
	{
		// ֱ������X����
		int startX = 0;
		// ֱ�����Y����
		int startY = 0;
		// ֱ�߽���X����
		int endX = ChessBoardFrame.BOARD_SIZE - 1;
		// ֱ�߽���Y����
		int endY = endX;
		// ͬ��ֱ�������������ۻ���
		int sameCount = 0;
		int temp = 0;

		// ����������СX������Y����
		temp = posX - WIN_COUNT + 1;
		startX = temp < 0 ? 0 : temp;
		temp = posY - WIN_COUNT + 1;
		startY = temp < 0 ? 0 : temp;
		// �����յ�����X������Y����
		temp = posX + WIN_COUNT - 1;
		endX = temp > ChessBoardFrame.BOARD_SIZE - 1 ? ChessBoardFrame.BOARD_SIZE - 1 : temp;
		temp = posY + WIN_COUNT - 1;
		endY = temp > ChessBoardFrame.BOARD_SIZE - 1 ? ChessBoardFrame.BOARD_SIZE - 1 : temp;
		// �����ҷ��������ͬ�������ӵ���Ŀ
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
			// ���ϵ��¼�����ͬ�������ӵ���Ŀ
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
			// �����ϵ����¼�����ͬ�������ӵ���Ŀ
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
