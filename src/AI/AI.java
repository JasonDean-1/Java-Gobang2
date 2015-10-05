package AI;

import AI.AI.*;
import Gobang.*;

public class AI
{
	public final String AI = "*";

	public int[] getPosition()
	{
		return new Randomly().getRandomPosition();
	}
}
