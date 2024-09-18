import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Arrays for teams and players
        String[] teams = new String[100];
        String[][] players = new String[100][100];
        int teamCount = 0;
        int[] playerCount = new int[100];

        // Step 1: Get team names
        System.out.println("Enter team names (type 'done' when finished):");
        while (true) {
            String teamName = scanner.nextLine();
            if (teamName.equalsIgnoreCase("done")) {
                break;
            }
            teams[teamCount] = teamName;
            teamCount++;
        }

        // Step 2: Add players to teams
        boolean continueInput = true;
        while (continueInput) {
            System.out.println("Select a team to add players (by name, type 'done' to stop):");
            for (int i = 0; i < teamCount; i++) {
                System.out.println(teams[i]);
            }

            String selectedTeam = scanner.nextLine();
            if (selectedTeam.equalsIgnoreCase("done")) {
                break;
            }

            int teamIndex = -1;
            for (int i = 0; i < teamCount; i++) {
                if (teams[i].equalsIgnoreCase(selectedTeam)) {
                    teamIndex = i;
                    break;
                }
            }

            if (teamIndex == -1) {
                System.out.println("Invalid team name.");
                continue;
            }

            System.out.println("Enter players for team " + selectedTeam + " (type 'done' to stop):");
            while (true) {
                String playerName = scanner.nextLine();
                if (playerName.equalsIgnoreCase("done")) {
                    break;
                }
                players[teamIndex][playerCount[teamIndex]] = playerName;
                playerCount[teamIndex]++;
            }

            System.out.println("Do you want to continue adding players to other teams? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("no")) {
                continueInput = false;
            }
        }

        // Step 3: Select teams for the match
        String team1, team2;
        int team1Index = -1, team2Index = -1;
        System.out.println("Select two teams for the match:");
        while (true) {
            System.out.println("Select the first team:");
            team1 = scanner.nextLine();
            for (int i = 0; i < teamCount; i++) {
                if (teams[i].equalsIgnoreCase(team1)) {
                    team1Index = i;
                    break;
                }
            }
            if (team1Index == -1) {
                System.out.println("Invalid team name.");
                continue;
            }

            System.out.println("Select the second team:");
            team2 = scanner.nextLine();
            for (int i = 0; i < teamCount; i++) {
                if (teams[i].equalsIgnoreCase(team2)) {
                    team2Index = i;
                    break;
                }
            }
            if (team2Index == -1 || team1.equalsIgnoreCase(team2)) {
                System.out.println("Invalid or duplicate team.");
            } else {
                break;
            }
        }

        // Step 4: Select 11 players from both teams
        String[] team1SelectedPlayers = new String[11];
        String[] team2SelectedPlayers = new String[11];

        System.out.println("Select 11 players for team " + team1 + ":");
        for (int i = 0; i < 11; i++) {
            while (true) {
                System.out.println("Select player " + (i + 1) + ":");
                for (int j = 0; j < playerCount[team1Index]; j++) {
                    System.out.println(players[team1Index][j]);
                }
                String selectedPlayer = scanner.nextLine();
                team1SelectedPlayers[i] = selectedPlayer;
                break;
            }
        }

        System.out.println("Select 11 players for team " + team2 + ":");
        for (int i = 0; i < 11; i++) {
            while (true) {
                System.out.println("Select player " + (i + 1) + ":");
                for (int j = 0; j < playerCount[team2Index]; j++) {
                    System.out.println(players[team2Index][j]);
                }
                String selectedPlayer = scanner.nextLine();
                team2SelectedPlayers[i] = selectedPlayer;
                break;
            }
        }

        // Step 5: Select batting and bowling teams for first innings
        System.out.println("Which team will bat first? " + team1 + " or " + team2 + ":");
        String battingTeam = scanner.nextLine();
        while (!battingTeam.equalsIgnoreCase(team1) && !battingTeam.equalsIgnoreCase(team2)) {
            System.out.println("Invalid choice. Please select either " + team1 + " or " + team2 + ":");
            battingTeam = scanner.nextLine();
        }

        String bowlingTeam = (battingTeam.equalsIgnoreCase(team1)) ? team2 : team1;
        String[] battingPlayers = (battingTeam.equalsIgnoreCase(team1)) ? team1SelectedPlayers : team2SelectedPlayers;
        String[] bowlingPlayers = (bowlingTeam.equalsIgnoreCase(team1)) ? team1SelectedPlayers : team2SelectedPlayers;

        // Step 6: Get overs once for both innings
        System.out.println("Enter the number of overs for both innings:");
        int overs = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Step 7: First team batting
        int scoreTeam1 = startInnings(scanner, battingPlayers, bowlingPlayers, overs);

        System.out.println("Innings complete for " + battingTeam + ". Final score: " + scoreTeam1);

        // Switch roles for team 2's innings
        String secondBattingTeam = bowlingTeam;
        String secondBowlingTeam = battingTeam;
        String[] secondBattingPlayers = bowlingPlayers;
        String[] secondBowlingPlayers = battingPlayers;

        System.out.println(secondBattingTeam + " needs " + (scoreTeam1 + 1) + " runs to win.");

        // Step 8: Second team batting (no need to re-enter overs)
        int scoreTeam2 = startInnings(scanner, secondBattingPlayers, secondBowlingPlayers, overs);

        System.out.println("Innings complete for " + secondBattingTeam + ". Final score: " + scoreTeam2);

        // Step 9: Declare winner
        if (scoreTeam2 > scoreTeam1) {
            System.out.println(secondBattingTeam + " wins by " + (scoreTeam2 - scoreTeam1) + " runs.");
        } else if (scoreTeam2 == scoreTeam1) {
            System.out.println("The match is a tie!");
        } else {
            System.out.println(battingTeam + " wins by " + (scoreTeam1 - scoreTeam2) + " runs.");
        }

        scanner.close();
    }

    // Function to simulate the innings for a team
    public static int startInnings(Scanner scanner, String[] battingPlayers, String[] bowlingPlayers, int overs) {
        System.out.println("Select on-strike batsman:");
        String onStrike = scanner.nextLine();
        System.out.println("Select non-strike batsman:");
        String nonStrike = scanner.nextLine();
        System.out.println("Select bowler:");
        String bowler = scanner.nextLine();

        int totalBalls = overs * 6;
        int score = 0;
        int ballsBowled = 0;
        boolean isMatchOver = false;

        while (!isMatchOver && ballsBowled < totalBalls) {
            System.out.println("Enter runs (0, 1, 2, 3, 4, 6) or 'out':");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("out")) {
                System.out.println("Batsman " + onStrike + " is out.");
                System.out.println("Select a new on-strike batsman:");
                onStrike = scanner.nextLine();
            } else {
                int runs = Integer.parseInt(input);
                score += runs;
                ballsBowled++;

                // Swap batsmen on runs of 1, 3, or at the end of the over
                if (runs == 1 || runs == 3 || ballsBowled % 6 == 0) {
                    String temp = onStrike;
                    onStrike = nonStrike;
                    nonStrike = temp;
                }

                System.out.println("On-strike: " + onStrike);
                System.out.println("Non-strike: " + nonStrike);
                System.out.println("Bowler: " + bowler);
                System.out.println("Overs: " + (ballsBowled / 6) + "," + (ballsBowled % 6) + " Score: " + score);

                // At the end of the over, select a new bowler
                if (ballsBowled % 6 == 0 && ballsBowled < totalBalls) {
                    System.out.println("Over completed. Select a new bowler:");
                    bowler = scanner.nextLine();
                }
            }
        }
        return score;
    }
}
