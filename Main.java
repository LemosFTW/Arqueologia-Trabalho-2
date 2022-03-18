import java.util.Scanner;
import java.io.*;

/**
 * @author Tiago Costa Sousa
 * @author Rodrigo Lemos Fernandes
 * Responsible for reading values and controlling the program.
 */

public class Main {
	
	// constants
	
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final String FILE_NAME = "teams.txt";
	private static final String FILE_NOT_FOUND = "Ficheiro inexistente.";
	private static final String EXIT = "sair";
	private static final String INVALID_JUMP = "Salto invalido";
	private static final String INVALID_TEAM = "Equipa invalida";
	private static final String WAS_EXPELLED = " foi expulsa";
	private static final String BURIED_VALUE = "Riqueza enterrada: ";
	private static final String NO_REMAINING_TEAMS = "Todas as equipas foram expulsas.";
	private static final String REMAINING_TREASURES = "Ainda havia tesouros por descobrir...";
	private static final String NO_REMAINING_TREASURES = "Todos os tesouros foram descobertos!";
	private static final String TERRAIN = "terreno";
	private static final String RANKING = "classificacao";
	private static final String DIG = "escavacao";
	private static final String WEALTH = "riqueza";
	private static final String STAR = "estrela";
	private static final String INVALID_COMMAND = "Comando invalido";
	private static final String TREAURE_PLOT = "*";
	private static final String EMPTY_PLOT = "-";
	
	// methods
	
	/**
	 * Main method. Creates a scanner and reads two integer values for rows and columns respectively, and creates an the system class with that
	 * information. Reads and creates the terrain and teams, and reads the input commands.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int rows = in.nextInt();
		int cols = in.nextInt();
		in.nextLine();
		ArchaeologySystem as = new ArchaeologySystem(rows, cols);
		readTerrain(as, in);
		int numberOfTeams = in.nextInt();
		in.nextLine();
		addAllTeams(as, numberOfTeams, in);
		commandExecuter(as, in);
		in.close();
		exit(as);
	}
	
	 /**
	 * Reads the terrain information and adds it to the terrain in the system class.
	 * @param as ArchaeologySystem object.
	 * @param in Scanner input.
	 * @pre as != null && in != null
	 */
	private static void readTerrain(ArchaeologySystem as, Scanner in) {
		for (int i = ZERO; i < as.getTerrainSize(); i++) {
			as.addPlot(new Plot(in.nextInt()));
		}
		in.nextLine();
	}
	
	/**
	 * Reads the information concerning the teams and adds them to the system class.
	 * @param as ArchaeologySystem object.
	 * @param numberOfTeams Number of participating teams.
	 * @param in Scanner input.
	 * @pre as != null && numberOfTeams != null && in != null
	 */
	private static void addAllTeams(ArchaeologySystem as, int numberOfTeams, Scanner in) {
		for (int i = ZERO; i < numberOfTeams; i++) {
			Scanner inFile = createFileScanner();
			int team = in.nextInt();
			for (int j = ONE; j <= readNumberOfTeams(); j++) {
				int numberOfPlayers = inFile.nextInt();
				inFile.nextLine();
				String teamName = inFile.nextLine();
				if (team == j)
					addSingularTeam(as, teamName, numberOfPlayers, inFile);
				else {
					for (int k = ZERO; k < numberOfPlayers; k++) {
						inFile.nextLine();
					}
				}
			}
		}
	}
	
	/**
	 * Auxiliary method that reads the information concerning a singular team, and adds it to the system class.
	 * @param as ArchaeologySystem object.
	 * @param teamName Name of a team.
	 * @param numberOfPlayers Number of players in a team.
	 * @param in Scanner input.
	 * @pre as != null && numberOfPlayers != null && in != null
	 */
	private static void addSingularTeam(ArchaeologySystem as, String teamName, int numberOfPlayers, Scanner in) {
		Team team = new Team(teamName, numberOfPlayers);
		for (int i = ZERO; i < numberOfPlayers; i++) {
			team.addPlayer(new Player(in.nextLine()));
		}
		as.addTeam(team);
	}
	
	/**
	 * Auxiliary method that reads the total number of teams in a file.
	 * @return number of teams in file.
	 */
	private static int readNumberOfTeams() {
		Scanner in = createFileScanner();
		int number = ZERO;
		while (in.hasNext()) {
			int numberOfPlayers = in.nextInt();
			for (int i = ZERO; i < numberOfPlayers + 2; i++) {
				in.nextLine();
			}
			number++;
		}
		return number;
	}
	
	/**
	 * Auxiliary method that creates a scanner that reads from a file.
	 * @return file scanner.	
	 */
	private static Scanner createFileScanner() {
		Scanner in;
		try {
			in = new Scanner(new FileReader(FILE_NAME));
		} catch (FileNotFoundException e) {
			System.out.println(FILE_NOT_FOUND);
			in = new Scanner(System.in);
		}
		return in;
	}
	
	/**
	 * Reads and executes commands.
	 * @param as ArchaeologySystem object.
	 * @param in Scanner input.
	 * @pre as != null && in != null
	 */
	private static void commandExecuter(ArchaeologySystem as, Scanner in) {
		String command = in.next();
		while (!command.equals(EXIT)) {
			commandSwitch(command, as, in);
			command = in.next();
		}
	}

	/**
	 * Reads and verifies the name of a team and the jump values, and if both are valid, digs the terrain using those values.
	 * If team is disqualified due to this command, prints out a warning informing that the team was disqualified.
	 * @param as ArchaeologySystem object.
	 * @param in Scanner input.
	 * @pre as != null && in != null
	 */
	private static void dig(ArchaeologySystem as, Scanner in) {
		int jumpR = in.nextInt();
		int jumpC = in.nextInt();
		String teamName = in.nextLine().trim();
		if (jumpR == ZERO && jumpC == ZERO) {
			System.out.println(INVALID_JUMP);
			return;
		} else if (!as.checkTeam(teamName)) {
			System.out.println(INVALID_TEAM);
			return;
		} else
			as.dig(teamName, jumpR, jumpC);
		if (!as.teamIsInPlay(teamName)) {
			as.removeTeam(teamName);
			System.out.println(teamName + WAS_EXPELLED);
		}
	}

	/**
	 * Reads and verifies the name of a team, and if the team is valid, prints out the name of the star player of said team.
	 * @param as ArchaeologySystem object.
	 * @param in Scanner input.
	 * @pre as != null && in != null
	 */
	private static void getStar(ArchaeologySystem as, Scanner in) {
		String teamName = in.nextLine().trim();
		if (!as.checkTeam(teamName)) {
			System.out.println(INVALID_TEAM);
			return;
		} else {
			PlayerIterator it = as.getOrderedPlayerIterator(teamName);
			Player player = it.next();
			System.out.println("Estrela de " + teamName + ": " + player.getName());
		}
	}

	/**
	 * Prints out the combined value of all treasures in the terrain.
	 * @param as ArchaeologySystem object.
	 * @pre as != null
	 */
	private static void wealth(ArchaeologySystem as) {
		System.out.println(BURIED_VALUE + as.getWealth());
	}

	/**
	 * Checks if there are still teams in play, and if so, prints the teams ordered by their ranking.
	 * @param as ArchaeologySystem object.
	 * @pre as != null
	 */
	private static void ranking(ArchaeologySystem as) {
		if (as.getNumberOfTeams() == ZERO)
			System.out.println(NO_REMAINING_TEAMS);
		else {
			TeamIterator it = as.getOrderedTeamIterator();
			while (it.hasNext()) {
				Team team = it.next();
				System.out.println(
						team.getName() + ": " + team.getScore() + " pts; " + team.getDisqualifiedArchaeologists()
								+ " descl.; " + team.getLicensedArchaeologists() + " com lic.");
			}
		}
	}

	/**
	 * Prints out the current state of the terrain.
	 * @param as ArchaeologySystem object.
	 * @pre as != null
	 */
	private static void printTerrain(ArchaeologySystem as) {
		boolean[][] treasureMap = as.getTreasureMap();
		for (int i = ZERO; i < treasureMap.length; i++) {
			for (int j = ZERO; j < treasureMap[ZERO].length; j++) {
				if (treasureMap[i][j])
					System.out.print(TREAURE_PLOT);
				else
					System.out.print(EMPTY_PLOT);
			}
			System.out.println();
		}
	}

	/**
	 * Checks if there are remaining teams and remaining treasures and ends the contest.
	 * @param as ArchaeologySystem object.
	 * @pre as != null
	 */
	private static void exit(ArchaeologySystem as) {
		if (as.getNumberOfTeams() == ZERO)
			System.out.println(NO_REMAINING_TEAMS);
		else if (hasRemainingTreasures(as))
			System.out.println(REMAINING_TREASURES);
		else
			System.out.println(NO_REMAINING_TREASURES);
	}
	
	/**
	 * Auxiliary method that checks if there are any remaining treasures in the terrain.
	 * @param as ArchaeologySystem object.
	 * @return whether there are remaining treasures or not.
	 * @pre as != null
	 */
	private static boolean hasRemainingTreasures(ArchaeologySystem as) {
		boolean[][] treasureMap = as.getTreasureMap();
		boolean hasRemainingTreasures = false;
		for (int i = ZERO; i < treasureMap.length; i++) {
			for (int j = ZERO; j < treasureMap[ZERO].length; j++) {
				if (treasureMap[i][j])
					hasRemainingTreasures = true;
			}
		}
		return hasRemainingTreasures;
	}

	/**
	 * Either executes a method or prints an invalid command warning depending on an input command.
	 * @param command input command.
	 * @param as ArchaeologySystem object.
	 * @param in Scanner input.
	 * @pre command != null && as != null && in != null
	 */
	private static void commandSwitch(String command, ArchaeologySystem as, Scanner in) {

		switch (command) {

		case TERRAIN:
			printTerrain(as);
			break;

		case RANKING:
			ranking(as);
			break;

		case DIG:
			dig(as, in);
			break;

		case WEALTH:
			wealth(as);
			break;

		case STAR:
			getStar(as, in);
			break;

		default:
			System.out.println(INVALID_COMMAND);
			in.nextLine();
			break;
		}
	}
}
