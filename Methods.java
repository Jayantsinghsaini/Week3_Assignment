/***
 * this is a Method class which contains methods for Cricket Scoring program
 * Name - Jayant Singh
 * Date - 19/19/24
 */
import java.util.Scanner;
public class Methods {
    /***
     * Method for playing an inning
     * input - int - overs, targetScore
     *         array of String - battingTeam, bowlingTeam
     *         String - battingTeamName, bowlingTeamName
     * output - score
     */
    public static int playInning( int overs, int targetScore, String[] battingTeam, String[] bowlingTeam, String battingTeamName, String bowlingTeamName) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int ballsBowled = 0;
        boolean[] outBatsmen = new boolean[11];
        int outCount = 0;
        System.out.println();
        System.out.println(Constant.SELECT_ONSTRICK + battingTeamName + ": ");
        String onStrike = validatePlayerSelection( battingTeam, outBatsmen);
        System.out.println(Constant.SELECT_NONSTRICK + battingTeamName + ": ");
        String nonStrike = validatePlayerSelection( battingTeam, outBatsmen);
        System.out.println(Constant.SELECT_BOWLER + bowlingTeamName + ": ");
        String bowler = validatePlayerSelection( bowlingTeam, null);
        int totalBalls = overs * 6;
        int runs = 0;
        while (ballsBowled < totalBalls) {
            System.out.println();
            System.out.println(Constant.ENTER_RUN);
            String input = scanner.nextLine();
            if (input.equals("0") || input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("6") || input.equalsIgnoreCase("out") || input.equalsIgnoreCase("wide") || input.equalsIgnoreCase("noball") ){
                if (input.equalsIgnoreCase("out")) {
                    System.out.println( onStrike + Constant.IS_OUT);
                    outBatsmen[getPlayerIndex(battingTeam, onStrike)] = true;
                    outCount++;
                    if (outCount == 10) {
                        System.out.println(Constant.ALL_OUT);
                        break;
                    }
                    onStrike = validatePlayerSelection( battingTeam, outBatsmen);
                    ballsBowled++;
                }
                else if (input.equalsIgnoreCase("wide")) {
                    score += 1;
                    boolean check = true;
                    while (check) {
                        System.out.println(Constant.WIDE_RUNS);
                        int wideRuns = scanner.nextInt();
                        scanner.nextLine();
                        if (wideRuns <= 4 && wideRuns >= 0) {
                            score += wideRuns;
                            System.out.println(Constant.WIDE_SCORE + score);
                            if (wideRuns == 1 || wideRuns == 3) {
                                String temp = onStrike;
                                onStrike = nonStrike;
                                nonStrike = temp;
                            }
                            check = false;
                        }
                        else {
                            System.out.println(Constant.INVALID_INPUT);
                            continue;
                        }
                    }
                }
                else if (input.equalsIgnoreCase("noball")) {
                    score += 1;
                    boolean check = true;
                    while (check) {
                        System.out.println(Constant.NOBALL_RUNS);
                        int noBallRuns = scanner.nextInt();
                        scanner.nextLine();
                        if (noBallRuns <= 4 && noBallRuns >= 0 || noBallRuns == 6) {
                            score += noBallRuns;
                            System.out.println(Constant.NOBALL_SCORE + score);
                            if (noBallRuns == 1 || noBallRuns == 3) {
                                String temp = onStrike;
                                onStrike = nonStrike;
                                nonStrike = temp;
                            }
                            check = false;
                        }
                        else {
                            System.out.println(Constant.INVALID_INPUT);
                            continue;
                        }
                    }
                }
                else {
                    runs = Integer.parseInt(input);
                    score += runs;
                    ballsBowled++;
                    if (runs == 1 || runs == 3 ) {
                        String temp = onStrike;
                        onStrike = nonStrike;
                        nonStrike = temp;
                    }
                    System.out.println("On-strike: " + onStrike);
                    System.out.println("Non-strike: " + nonStrike);
                    System.out.println("Bowler: " + bowler);
                    System.out.println("Overs: " + (ballsBowled / 6) + "," + (ballsBowled % 6) + " Score: " + score);
                    if (ballsBowled % 6 == 0 && ballsBowled < totalBalls) {
                        String temp = onStrike;
                        onStrike = nonStrike;
                        nonStrike = temp;
                        System.out.println(Constant.OVER_COMPLETE);
                        bowler = validatePlayerSelection( bowlingTeam, null);
                    }
                }
            }
            else {
                System.out.println(Constant.INVALID_INPUT);
            }
            if (targetScore != -1 && score > targetScore) {
                System.out.println( battingTeamName + Constant.WICKET_REMAIN+ (10 - outCount));
                break;
            }
        }
        return score;
    }
    /***
     * Method to validate player selection from the team's 11 players
     * input - array of String - teamPlayers, outBatsmen
     * output - selectedPlayer
     */
    public static String validatePlayerSelection( String[] teamPlayers, boolean[] outBatsmen) {
        Scanner scanner = new Scanner(System.in);
        String selectedPlayer = null;
        boolean playerExists;
        boolean check = true;
        while (check) {
            selectedPlayer = scanner.nextLine();
            playerExists = false;
            for (int i = 0; i < teamPlayers.length; i++) {
                if (teamPlayers[i].equalsIgnoreCase(selectedPlayer)) {
                    if (outBatsmen != null && outBatsmen[i]) {
                        playerExists = false;
                        check = false;
                    }
                    playerExists = true;
                    check = false;
                }
            }
            if (playerExists) {
                check = false;
            }
            else {
                System.out.println(Constant.INVALID_PLAYER);
            }
        }
        return selectedPlayer;
    }
    /***
     *  Method to get the index of a player in the team array
     *  input - array of String - teamPlayers
     *          String - playerName
     *  output - playerIndex
     */
    public static int getPlayerIndex(String[] teamPlayers, String playerName) {
        for (int i = 0; i < teamPlayers.length; i++) {
            if (teamPlayers[i].equalsIgnoreCase(playerName)) {
                return i;
            }
        }
        return -1;
    }
}
