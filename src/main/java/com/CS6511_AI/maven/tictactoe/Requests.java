package com.CS6511_AI.maven.tictactoe;

import okhttp3.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// for all method calls, userId has default value of "1134"
/*
 * POST Methods

Static int create_team(String name)
	Return int teamId if “OK”
	Return -1 if “Fail” and Print error message to console
Static Int add_team_member(int teamId)
	Returns 0 and prints message if “OK”
	Returns -1 if “Fail” and Print error message to console
Static Int create_game(int teamId1, int teamId2, int boardSize, int target)
	Returns int gameId if “OK”
	Return -1 if “Fail” and Print error message to console
Static int make_move(int gameID, int teamId, int x, int y)
	Returns int moveID if “OK”
	Returns -1 if “Fail” and Print error message to console

GET Methods

Static List <Move> get_moves(int gameId, int count=1)
	If “OK”, Return list of Move objects
	Return null if no moves and print to console
Static List <Map<String,String>> get_teams()
	Returns a list of String key-value pairs if “OK”
	Return null if “Fail”
Static List <String> get_team_members(int teamId)
	Return list of StringuserIds of team members if “OK”
	Return -1, if team is empty
Static String get_board_string(int gameId)
	Return String board_string if “OK”
	Return -1 if “Fail” and print to console
Static String get_board_map(int gameId)
	Return board map in format ("{\"4,4\":\"O\",\"4,3\":\"X\",\"3,3\":\"O\",\"3,4\":\"X\",\"5,4\":\"O\"}") if “OK”
	Return -1 if “FAIL”

 * 
 */

public class Requests {

	final static String baseUrl = "https://www.notexponential.com/aip2pgaming/api/index.php?";



	public static void main(String [] args) throws IOException
	{
		
		////////////////////////
		// GET method testing//
		////////////////////////

		
		// testing
		// System.out.println(get_board_map(3901));

		// testing
		char[][] board =  new char[12][12];
		String string_board = get_board_string(3794);
		String[] lines = string_board.split("\n");
		// System.out.println(string_board);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				board[i][j] = lines[i].charAt(j);
			}
		}
		
		// Print the resulting board
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}

		int[] move = evaluate(board, 4, 'X');
		System.out.print("Next Move: "+ move[0] + "," + move[1]);
		// testing
		// System.out.println(get_team_members(1343));

		// testing
		// System.out.println(get_teams());



		// testing get_moves(int gameId, int count)
		// System.out.println(get_moves(3839, 1));

		// testing get_moves(int gameId, int count)
		//System.out.println(get_moves(3839, 2));

		// testing get_moves(int gameId)
		// System.out.println(get_moves(3839));

		
		////////////////////////
		// POST method testing//
		////////////////////////
		
		// testing add_team_member(int teamId)
		 // add_team_member(1360);

		// testing create_game(int teamId1, int teamId2, int boardSize, int target)
		// create_game(1355, 1354, 5, 3);
		
		// testing create_team(String name)
		// create_team("TeamConnor");
		
		// testing make_move(int gameId, int teamId, int x, int y)
		// make_move(3794, 1356, 3, 9);

	} // main

	public static int[] evaluate(char[][] board, int target, char player) {
		// Initialize best score and best move
		int bestScore = Integer.MIN_VALUE;
		int[] bestMove = {-1, -1};
		// Evaluate all possible moves
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == '-') {
					// Make the move
					board[i][j] = player;
					
					// Evaluate the move
					int score = score(board, target, player);
					
					// Update the best score and best move
					if (score > bestScore) {
						bestScore = score;
						bestMove[0] = i;
						bestMove[1] = j;
					}
					
					// Undo the move
					board[i][j] = ' ';
				}
			}
		}
		
		return bestMove;
	}

	public static int score(char[][] board, int target, char player) {
		int numRows = board.length;
		int numCols = board[0].length;
		int playerCount = 0;
		int opponentCount = 0;
		int emptyCount = 0;

		// Check rows
		for (int row = 0; row < numRows; row++) {
			int count = 0;
			for (int col = 0; col < numCols; col++) {
				if (board[row][col] == player) {
					count++;
				} else if (board[row][col] != '-') {
					count = 0;
					break;
				}
			}
			if (count == target) {
				playerCount++;
			} else if (count > 0 && count < target) {
				emptyCount++;
			} else if (count == 0) {
				count = 0;
				for (int col = 0; col < numCols; col++) {
					if (board[row][col] != player && board[row][col] != '-') {
						count++;
					} else if (board[row][col] == player) {
						count = 0;
						break;
					}
				}
				if (count == target) {
					opponentCount++;
				} else if (count > 0 && count < target) {
					emptyCount++;
				}
			}
		}

		// Check columns
		for (int col = 0; col < numCols; col++) {
			int count = 0;
			for (int row = 0; row < numRows; row++) {
				if (board[row][col] == player) {
					count++;
				} else if (board[row][col] != '-') {
					count = 0;
					break;
				}
			}
			if (count == target) {
				playerCount++;
			} else if (count > 0 && count < target) {
				emptyCount++;
			} else if (count == 0) {
				count = 0;
				for (int row = 0; row < numRows; row++) {
					if (board[row][col] != player && board[row][col] != '-') {
						count++;
					} else if (board[row][col] == player) {
						count = 0;
						break;
					}
				}
				if (count == target) {
					opponentCount++;
				} else if (count > 0 && count < target) {
					emptyCount++;
				}
			}
		}

		// Check diagonals
    	// Top-left to bottom-right diagonal
		for (int i = 0; i <= numRows - target; i++) {
			for (int j = 0; j <= numCols - target; j++) {
				int count = 0;
				for (int k = 0; k < target; k++) {
					if (board[i+k][j+k] == player) {
						count++;
					} else if (board[i+k][j+k] != ' ') {
						count = 0;
						break;
					}
				}
				if (count == target) {
					playerCount++;
				} else if (count > 0 && count < target) {
					emptyCount++;
				} else if (count == 0) {
					count = 0;
					for (int k = 0; k < target; k++) {
						if (board[i+k][j+k] != player && board[i+k][j+k] != ' ') {
							count++;
						} else if (board[i+k][j+k] == player) {
							count = 0;
							break;
						}
					}
					if (count == target) {
						opponentCount++;
					} else if (count > 0 && count < target) {
						emptyCount++;
					}
				}
			}
		}

		// Bottom-left to top-right diagonal
		for (int i = target - 1; i < numRows; i++) {
			for (int j = 0; j <= numCols - target; j++) {
				int count = 0;
				for (int k = 0; k < target; k++) {
					if (board[i-k][j+k] == player) {
						count++;
					} else if (board[i-k][j+k] != ' ') {
						count = 0;
						break;
					}
				}
				if (count == target) {
					playerCount++;
				} else if (count > 0 && count < target) {
					emptyCount++;
				} else if (count == 0) {
					count = 0;
					for (int k = 0; k < target; k++) {
						if (board[i-k][j+k] != player && board[i-k][j+k] != ' ') {
							count++;
						} else if (board[i-k][j+k] == player) {
							count = 0;
							break;
						}
					}
					if (count == target) {
						opponentCount++;
					} else if (count > 0 && count < target) {
						emptyCount++;
					}
				}
			}
		}
		int score = playerCount - opponentCount;
		if (score > 0 && emptyCount ==  0) {
			score += 100;
		} else if (score < 0 && emptyCount == 0) {
			score -= 100;
		} else if (emptyCount > 0) {
			score += emptyCount;
		}
		return score;
	}
	////////////////////
	// POST API CALLS //
	////////////////////

	static int add_team_member(int teamId) throws IOException{
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("type","member")
				.addFormDataPart("teamId", Integer.toString(teamId))
				.addFormDataPart("userId","1134")
				.build();
		Request request = new Request.Builder()
				.url("https://www.notexponential.com/aip2pgaming/api/index.php")
				.method("POST", body)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();


		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();


		if (code.equals("FAIL"))
		{
			System.out.println("FAIL: add_team_member(teamID=" + teamId +")");
			return -1;
		}
		else if (code.equals("OK"))
		{
			System.out.println("UserId 1134 successfully added to teamId " + teamId);
			return 0;
		}
		else
		{
			System.out.println("Unknown Error: add_team_member(teamID=" + teamId +")");
			return -1;
		}

	} // add_team_member()


	static int create_game(int teamId1, int teamId2, int boardSize, int target) throws IOException{
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("type","game")
				.addFormDataPart("teamId1",Integer.toString(teamId1))
				.addFormDataPart("teamId2",Integer.toString(teamId2))
				.addFormDataPart("gameType","TTT")
				.addFormDataPart("boardSize",Integer.toString(boardSize)) 
				.addFormDataPart("target",Integer.toString(target))
				.build();
		Request request = new Request.Builder()
				.url("https://www.notexponential.com/aip2pgaming/api/index.php")
				.method("POST", body)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();


		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();


		if (code.equals("FAIL"))
		{
			System.out.println("FAIL: create_game");
			return -1;
		}
		else if (code.equals("OK"))
		{
			String gameId = jsonNode.get("gameId").asText();

			System.out.println("Game " + gameId + " successfully created between teams: " + teamId1 + " and " + teamId2);
			return Integer.parseInt(gameId);
		}
		else
		{
			System.out.println("Unknown Error: create_game");
			return -1;
		}
	} // create_game()



	static int create_team(String name) throws IOException{
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("type","team")
				.addFormDataPart("name", name)
				.build();
		Request request = new Request.Builder()
				.url("https://www.notexponential.com/aip2pgaming/api/index.php")
				.method("POST", body)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();

		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();


		if (code.equals("FAIL"))
		{
			String message = jsonNode.get("message").asText();

			System.out.println("FAIL: " + message);
			return -1;
		}
		else if (code.equals("OK"))
		{
			String teamId = jsonNode.get("teamId").asText();

			System.out.println("Team " + name + " successfully created. teamId: " + teamId);
			return 0;
		}
		else
		{
			System.out.println("Unknown Error: create_team");
			return -1;
		}
	} // create_game()


	static int make_move(int gameId, int teamId, int x, int y) throws IOException{
	    OkHttpClient client = new OkHttpClient().newBuilder()
	    	      .build();
	    	    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
	    	    RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
	    	      .addFormDataPart("type","move")
	    	      .addFormDataPart("teamId",Integer.toString(teamId))
	    	      .addFormDataPart("move",Integer.toString(x)+","+Integer.toString(y))
	    	      .addFormDataPart("gameId",Integer.toString(gameId))
	    	      .build();
	    	    Request request = new Request.Builder()
	    	      .url("https://www.notexponential.com/aip2pgaming/api/index.php")
	    	      .method("POST", body)
	    	      .addHeader("x-api-key", "962b43699ecfed02ee31")
	    	      .addHeader("userid", "1134")
	    	      .addHeader("Content-Type", "application/x-www-form-urlencoded")
	    	      .build();
	    	    Response response = client.newCall(request).execute();
	
	
	    		// holds the response from the API call
	    		String jsonResponse = response.body().string();


	    		// Accessing the JSON values within the response string using the jsonNOde object
	    		ObjectMapper objectMapper = new ObjectMapper();
	    		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

	    		String code = jsonNode.get("code").asText();


	    		if (code.equals("FAIL"))
	    		{
	    			
	    			if(jsonNode.get("message")!=null)
	    			{
	    				String message = jsonNode.get("message").asText();
	    				System.out.println("FAIL: " + message + " --> make_move(gameId = " + gameId + ", teamId = " +teamId+ ", x = " + x + ", y = " + y + ")");
		    			return -1;
	    			}
	    			else
	    			{
	    				System.out.println("FAIL: make_move(gameId = " + gameId + ", teamId = " +teamId+ ", x = " + x + ", y = " + y + ")");
		    			return -1;
	    			}

	    			
	    		}
	    		else if (code.equals("OK"))
	    		{
	    			String moveId = jsonNode.get("moveId").asText();

	    			System.out.println("Team " + teamId + "'s Move of {" + x + ", " + y + "} was successful in game " + gameId);
	    			return 0;
	    		}
	    		else
	    		{
	    			System.out.println("Unknown Error: make_move(gameId = " + gameId + ", teamId = " +teamId+ ", x = " + x + ", y = " + y + ")");
	    			return -1;
	    		}	
	} // make_move()


	///////////////////
	// GET API CALLS //
	///////////////////

	static String get_board_map(int gameId) throws IOException
	{
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("text/plain");
		Request request = new Request.Builder()
				.url(baseUrl+"type=boardMap&gameId=" + gameId)
				.method("GET", null)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.build();
		Response response = client.newCall(request).execute();

		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();

		if (code.equals("FAIL"))
		{
			String errorMessage = jsonNode.get("message").asText();
			System.out.println("FAIL: " + errorMessage);
			return "-1";
		}
		else if (code.equals("OK"))
		{
			String output = jsonNode.get("output").asText();
			int target = jsonNode.get("target").asInt();

			if(output.contains("{"))
			{
				// only returning the board map
				// Format: "{"4,4":"O","4,3":"X","3,3":"O","3,4":"X","5,4":"O"}"
				System.out.println("Game is active, board is not empty");
				return output;
			}
			else
			{

				System.out.println("Game is active, but board is empty");
				return output;
			}
			
		}
		else
		{
			System.out.println("Unknown Error" );
			return "-1";
		}
	} // get_board_map()

	static String get_board_string(int gameId) throws IOException{
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("text/plain");
		Request request = new Request.Builder()
				.url(baseUrl+"type=boardString&gameId=" + gameId)
				.method("GET", null)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.build();
		Response response = client.newCall(request).execute();

		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();

		if (code.equals("FAIL"))
		{
			String errorMessage = jsonNode.get("message").asText();
			System.out.println("FAIL: " + errorMessage);
			return "-1";
		}
		else if (code.equals("OK"))
		{
			String output = jsonNode.get("output").asText();
			int target = jsonNode.get("target").asInt();

			/*only returning the board string
		            Format: 
									------------
									------------
									------------
									---OX-------
									---XO-------
									----O-------
									------------
									------------
									------------
									------------
									------------
									------------

			 */
			return output;
		}
		else
		{
			System.out.println("Unknown Error" );
			return "-1";
		}

	} // get_board_string()

	// returns the n (int count) last moves of game with gameId
	static List<Move> get_moves(int gameId, int count) throws IOException
	{
		// Create a list to store the array elements
		List<String> myArray = new ArrayList<>();

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		Request request = new Request.Builder()
				.url(baseUrl+"type=moves&gameId=" + gameId + "&count=" + count)
				.method("GET", null)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();

		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();

		if (code.equals("FAIL"))
		{
			String errorMessage = jsonNode.get("message").asText();
			System.out.println("FAIL: " + errorMessage);
			return null;
		}
		else if (code.equals("OK"))
		{			
			JsonNode movesNode = jsonNode.path("moves");

			// Format: [Move [moveId=92385, gameId=3839, teamId=1347, moveX=2, moveY=0, symbol=O], Move [moveId=92384, gameId=3839, teamId=1348, moveX=1, moveY=2, symbol=X]]
			List<Move> move_list = new ArrayList<> ();

			for (JsonNode moveNode : movesNode) {
				int moveId = moveNode.path("moveId").asInt();
				int teamId = moveNode.path("teamId").asInt();
				String move = moveNode.path("move").asText();
				String symbol = moveNode.path("symbol").asText();
				String[] moveCoordinates = move.split(",");
				int moveX = Integer.parseInt(moveCoordinates[0]);
				int moveY = Integer.parseInt(moveCoordinates[1]);
				Move moveObj = new Move(moveId, gameId, teamId, moveX, moveY, move, symbol);
				move_list.add(moveObj);
			}
			return move_list;
		}
		else
		{
			System.out.println("Unknown Error" );
			myArray.add("-1");
			return null;
		}
	}// get_moves()

	// returns last move of game with gameId using default count value of 1
	static List<Move> get_moves(int gameId) throws IOException
	{
		// Create a list to store the array elements
		List<String> myArray = new ArrayList<>();

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		Request request = new Request.Builder()
				.url(baseUrl+"type=moves&gameId=" + gameId + "&count=1")
				.method("GET", null)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();

		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();

		if (code.equals("FAIL"))
		{
			String errorMessage = jsonNode.get("message").asText();
			System.out.println("FAIL: " + errorMessage);
			return null;
		}
		else if (code.equals("OK"))
		{			
			JsonNode movesNode = jsonNode.path("moves");

			// Format: [Move [moveId=92385, gameId=3839, teamId=1347, moveX=2, moveY=0, symbol=O], Move [moveId=92384, gameId=3839, teamId=1348, moveX=1, moveY=2, symbol=X]]
			List<Move> move_list = new ArrayList<> ();

			for (JsonNode moveNode : movesNode) {
				int moveId = moveNode.path("moveId").asInt();
				int teamId = moveNode.path("teamId").asInt();
				String move = moveNode.path("move").asText();
				String symbol = moveNode.path("symbol").asText();
				String[] moveCoordinates = move.split(",");
				int moveX = Integer.parseInt(moveCoordinates[0]);
				int moveY = Integer.parseInt(moveCoordinates[1]);
				Move moveObj = new Move(moveId, gameId, teamId, moveX, moveY, move, symbol);
				move_list.add(moveObj);
			}
			return move_list;
		}
		else
		{
			System.out.println("Unknown Error" );
			myArray.add("-1");
			return null;
		}
	}// get_moves() default count = 1


	static List <Map<String,String>> get_teams() throws IOException
	{
		// Create a list to store the array elements
		List<String> myArray = new ArrayList<>();

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("text/plain");
		Request request = new Request.Builder()
				.url(baseUrl + "type=myTeams")
				.method("GET", null)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.build();
		Response response = client.newCall(request).execute();


		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();

		if (code.equals("FAIL"))
		{
			String errorMessage = jsonNode.get("message").asText();
			System.out.println("FAIL: " + errorMessage);
			myArray.add("-1");
			return null;
		}
		else if (code.equals("OK"))
		{
			//////

			JsonNode rootNode = objectMapper.readTree(jsonResponse);

			List<Map<String, String>> myTeamsList = new ArrayList<>();

			// Get the "myTeams" array from the JSON object
			JsonNode myTeamsNode = rootNode.get("myTeams");
			Iterator<JsonNode> myTeamsIterator = myTeamsNode.elements();

			// Iterate over the "myTeams" array and extract the team IDs and names
			while (myTeamsIterator.hasNext()) {
				JsonNode teamNode = myTeamsIterator.next();
				Iterator<Map.Entry<String, JsonNode>> teamIterator = teamNode.fields();
				while (teamIterator.hasNext()) {
					Map.Entry<String, JsonNode> entry = teamIterator.next();
					String teamId = entry.getKey();
					String teamName = entry.getValue().asText();

					// Do something with the team ID and name

					Map<String, String> teamMap = new HashMap<>();
					teamMap.put(teamId, teamName);
					myTeamsList.add(teamMap);

					// System.out.println("Team ID: " + teamId + ", Team Name: " + teamName);
				}
			}

			// Format: [{1355=TeamMax}, {1356=TeamConnor}]
			return myTeamsList;
		}
		else
		{
			System.out.println("Unknown Error" );
			myArray.add("-1");
			return null;
		}
	}// get_teams()

	static List <String> get_team_members(int teamId) throws IOException
	{
		// Create a list to store the array elements
		List<String> myArray = new ArrayList<>();

		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		Request request = new Request.Builder()
				.url(baseUrl+"type=team&teamId=" + teamId)
				.method("GET", null)
				.addHeader("x-api-key", "962b43699ecfed02ee31")
				.addHeader("userid", "1134")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		Response response = client.newCall(request).execute();

		// holds the response from the API call
		String jsonResponse = response.body().string();


		// Accessing the JSON values within the response string using the jsonNOde object
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		String code = jsonNode.get("code").asText();

		if (code.equals("FAIL"))
		{
			String errorMessage = jsonNode.get("message").asText();
			System.out.println("FAIL: " + errorMessage);
			myArray.add("-1");
			return myArray;
		}
		else if (code.equals("OK"))
		{
			JsonNode userIdsNode = jsonNode.get("userIds");

			// only returning one UserID because we only have access to a single userID as a team
			// Format: [1174, 1168, 1166, 1171]


			// Iterate over the array elements and add them to the list
			for (JsonNode arrayElement : userIdsNode) {
				String element = arrayElement.asText();
				myArray.add(element);
			}


			return myArray;
		}
		else
		{
			System.out.println("Unknown Error" );
			myArray.add("-1");
			return myArray;
		}
	} // get_team_members()

}

