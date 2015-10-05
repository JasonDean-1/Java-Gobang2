
package AI;

import Gobang.*;

/**
 * @author jason dean
 *
 */
public class Randomly
{
	/**
	 * @return String[][]
	 */

	public Randomly()
	{
	}

	/*
	 电脑随机生成2个整数，作为电脑下棋的座标，赋给board数组。
	 */
	public int[] getRandomPosition()
	{
		int posX = (int) (Math.random() * (ChessBoardFrame.BOARD_SIZE - 1));
		int posY = (int) (Math.random() * (ChessBoardFrame.BOARD_SIZE - 1));
		int[] result = { posX, posY };
		return result;
	}
}
