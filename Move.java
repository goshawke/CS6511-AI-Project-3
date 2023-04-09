package com.CS6511_AI.maven.tictactoe;

public class Move 
{
	int moveId;
	int gameId;
	int teamId;
	
	// the {x,y} location of the move
	int moveX, moveY;
	
	String move;

	// "X" or "O"
	String symbol;

	public Move(int moveId, int gameId, int teamId, int moveX, int moveY, String move, String symbol) {
		super();
		this.moveId = moveId;
		this.gameId = gameId;
		this.teamId = teamId;
		this.moveX = moveX;
		this.moveY = moveY;
		this.symbol = symbol;
	}

	public String getMove() {
		return move;
	}
	
	public int getMoveId() {
		return moveId;
	}

	public int getGameId() {
		return gameId;
	}

	public int getTeamId() {
		return teamId;
	}

	public int getMoveX() {
		return moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return "Move [moveId=" + moveId + ", gameId=" + gameId + ", teamId=" + teamId + ", moveX=" + moveX + ", moveY="
				+ moveY + ", symbol=" + symbol + "]";
	}
	
	

}
