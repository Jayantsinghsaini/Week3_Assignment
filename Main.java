/***
 * this is our Main Cricket Scoring program.
 * Name - Jayant Singh
 * Date - 19/19/24
 */
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] teams = new String[100];
        String[][] players = new String[100][100];
        int teamsCount = 0;
        int[] playersCount = new int[100];
        System.out.println(Constant.ENTER_TEAM);
        while (true) {
            String teamName = scanner.nextLine();
            if (teamName.equalsIgnoreCase("done")) {
                break;
            }
            teams[teamsCount] = teamName;
            teamsCount++;
        }
        boolean check = true;
        while (check) {
            System.out.println(Constant.SELECT_TEAM);
            for (int i = 0; i < teamsCount; i++) {
                System.out.println(teams[i]);
            }
            String selectedTeam = scanner.nextLine();
            if (selectedTeam.equalsIgnoreCase("done")) {
                break;
            }
            int teamIndex = -1;
            for (int i = 0; i < teamsCount; i++) {
                if (teams[i].equalsIgnoreCase(selectedTeam)) {
                    teamIndex = i;
                    break;
                }
            }
            if (teamIndex == -1) {
                System.out.println(Constant.INVALID_TEAM);
                continue;
            }
            System.out.println(Constant.ENTER_PLAYER + selectedTeam + Constant.DONE_STOP);
            int currentPlayerCount = 0;
            while (true) {
                String playerName = scanner.nextLine();
                if (playerName.equalsIgnoreCase("done")) {
                    if (currentPlayerCount < 11) {
                        System.out.println(Constant.ENTER_ATLEAST_11);
                    } else {
                        break;
                    }
                }
                else {
                    players[teamIndex][playersCount[teamIndex]] = playerName;
                    playersCount[teamIndex]++;
                    currentPlayerCount++;
                }
            }
            System.out.println(Constant.YES_NO);
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("no")) {
                check = false;
            }
        }
        String team1, team2;
        int team1Index = -1, team2Index = -1;
        System.out.println(Constant.SELECT_TWO_TEAM);
        while (true) {
            System.out.println(Constant.SELECT_FIRST_TEAM);
            team1 = scanner.nextLine();
            for (int i = 0; i < teamsCount; i++) {
                if (teams[i].equalsIgnoreCase(team1)) {
                    team1Index = i;
                    break;
                }
            }
            if (team1Index == -1) {
                System.out.println(Constant.INVALID_TEAM);
                continue;
            }
            System.out.println(Constant.SELECT_SECOND_TEAM);
            team2 = scanner.nextLine();
            for (int i = 0; i < teamsCount; i++) {
                if (teams[i].equalsIgnoreCase(team2)) {
                    team2Index = i;
                    break;
                }
            }
            if (team2Index == -1 || team1.equalsIgnoreCase(team2)) {
                System.out.println(Constant.DUPLICATE_TEAM);
            }
            else {
                break;
            }
        }
        String[] team1SelectedPlayers = new String[11];
        String[] team2SelectedPlayers = new String[11];
        System.out.println(Constant.SELECT_11_PLAYERS + team1 );
        for (int j = 0; j < playersCount[team1Index]; j++) {
            System.out.print(players[team1Index][j] + " ");
        }
        for (int i = 0; i < 11; i++) {
            while (true) {
                String selectedPlayer = scanner.nextLine();
                boolean playerExists = false;
                for (int j = 0; j < playersCount[team1Index]; j++) {
                    if (players[team1Index][j].equalsIgnoreCase(selectedPlayer)) {
                        playerExists = true;
                        break;
                    }
                }
                if (playerExists) {
                    team1SelectedPlayers[i] = selectedPlayer;
                    break;
                }
                else {
                    System.out.println(Constant.INVALID_PLAYER);
                }
            }
        }
        System.out.println(Constant.SELECT_11_PLAYERS + team2 );
        for (int j = 0; j < playersCount[team2Index]; j++) {
            System.out.print(players[team2Index][j] + " ");
        }
        for (int i = 0; i < 11; i++) {
            while (true) {
                String selectedPlayer = scanner.nextLine();
                boolean playerExists = false;
                for (int j = 0; j < playersCount[team2Index]; j++) {
                    if (players[team2Index][j].equalsIgnoreCase(selectedPlayer)) {
                        playerExists = true;
                        break;
                    }
                }
                if (playerExists) {
                    team2SelectedPlayers[i] = selectedPlayer;
                    break;
                }
                else {
                    System.out.println(Constant.INVALID_PLAYER);
                }
            }
        }
        System.out.println(Constant.BAT_FIRST + team1 + " or " + team2 );
        String battingFirst = scanner.nextLine();
        String bowlingFirst = battingFirst.equalsIgnoreCase(team1) ? team2 : team1;
        String[] battingTeam = battingFirst.equalsIgnoreCase(team1) ? team1SelectedPlayers : team2SelectedPlayers;
        String[] bowlingTeam = bowlingFirst.equalsIgnoreCase(team1) ? team1SelectedPlayers : team2SelectedPlayers;
        System.out.println(Constant.ENTER_OVER);
        int overs = 2;
        try{
            boolean checkOn = true;
            while (checkOn){
                System.out.println(Constant.ENTER_OVER);
                overs = scanner.nextInt();
                if(overs<0){
                    System.out.println(Constant.INVALID_INPUT);
                }
                else {
                    checkOn = false;
                }
            }
        }
        catch (Exception e){
            System.out.println(Constant.EXCEPTION_INPUT);
        }
        System.out.println("Team " + battingFirst + " is batting.");
        int targetScore = Methods.playInning( overs, -1, battingTeam, bowlingTeam, battingFirst, bowlingFirst);
        System.out.println();
        System.out.println(battingFirst + Constant.FINISH_SCORE + targetScore + " runs.");
        System.out.println("Target for " + bowlingFirst + " is " + (targetScore + 1) + " runs.");
        String[] secondBattingTeam = battingFirst.equalsIgnoreCase(team1) ? team2SelectedPlayers : team1SelectedPlayers;
        String[] secondBowlingTeam = battingFirst.equalsIgnoreCase(team1) ? team1SelectedPlayers : team2SelectedPlayers;
        System.out.println("Team " + bowlingFirst + " is batting.");
        int secondInningScore = Methods.playInning( overs, targetScore, secondBattingTeam, secondBowlingTeam, bowlingFirst, battingFirst);
        System.out.println();
        System.out.println(bowlingFirst + Constant.FINISH_SCORE + secondInningScore + " runs.");
        if (secondInningScore > targetScore) {
            System.out.println(" --------- " + bowlingFirst + " wins!" + " --------- ");
        }
        else if (secondInningScore < targetScore) {
            System.out.println(" --------- " + battingFirst + " wins!" + " --------- ");
        }
        else {
            System.out.println(Constant.TIE);
        }
    }
}
