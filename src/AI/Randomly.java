
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
	 �����������2����������Ϊ������������꣬����board���顣
	 */
	public int[] getRandomPosition()
	{
		int posX = (int) (Math.random() * (ChessBoardFrame.BOARD_SIZE - 1));
		int posY = (int) (Math.random() * (ChessBoardFrame.BOARD_SIZE - 1));
		int[] result = { posX, posY };
		return result;
	}
}
