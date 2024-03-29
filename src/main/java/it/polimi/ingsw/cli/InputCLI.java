package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.exceptions.InvalidColor;
import it.polimi.ingsw.model.entity.Pawn;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.*;
import it.polimi.ingsw.model.power.*;

import java.util.*;

/**
 * @author Christian Confalonieri
 */
public class InputCLI {
    private static boolean globalCommand;
    /**
     * Call the appropriate methods for each client state to handle input
     * @param inputScanner The input scanner
     * @author Christian Confalonieri
     */
    public static void inputHandler(Scanner inputScanner) {
        if (inputScanner.hasNext()) {
            String inputString = inputScanner.next();
            inputString = inputString.trim();

            String[] command = inputString.split(" ");

            if(command[0].equalsIgnoreCase("refresh") || command[0].equalsIgnoreCase("r")) {
                Client.getInstance().getCli().render();
                return;
            }

            globalCommand = globalCommand(command);

            switch(Client.getInstance().getClientState()) {
                case LOGIN:
                    cliLoginRequest(command);
                    break;
                case MAINMENU:
                    mainMenu(command);
                    break;
                case WAITINGLOBBY:
                    break;
                case INGAME:
                    inGame(command);
                    break;
                case ENDGAME:
                    Client.getInstance().setClientState(ClientState.MAINMENU);
                    Client.getInstance().setPollAllLobbies(true);
                    Client.getInstance().getCli().render();
                    break;
            }
        }
    }

    /**
     * After some checks on the fairness of the incoming string it sends a login request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliLoginRequest(String[] command) {
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid login command" + ConsoleColor.RESET);
            return;
        }
        LoginService.loginRequest(command[0]);
    }

    /**
     * After some checks on the fairness of the incoming string it sends a logout request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliLogoutRequest(String[] command) {
        if (command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid logout command" + ConsoleColor.RESET);
            return;
        }
        LoginService.logoutRequest();
    }

    /**
     * After some checks on the fairness of the incoming string it sends a new game request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliNewGameRequest(String[] command) {
        try {
            if(Integer.parseInt(command[1]) < 2 || Integer.parseInt(command[1]) > 4) throw new NumberFormatException();
            LobbyService.newGameRequest(Integer.parseInt(command[1]), null, Wizard.parseColor(command[2].toUpperCase()));
        } catch(NumberFormatException castingException) {
            System.out.println(ConsoleColor.RED + "Invalid number of player" + ConsoleColor.RESET);
        } catch (IllegalArgumentException wizardException) {
            System.out.println(ConsoleColor.RED + "Invalid wizard selected" + ConsoleColor.RESET);
        } catch (InvalidColor e) {
            System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
        }
    }

    /**
     * After some checks on the fairness of the incoming string it sends a join game request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliJoinGameRequest(String[] command) {
        try {
            if(Integer.parseInt(command[1]) < 2 || Integer.parseInt(command[1]) > 4) throw new NumberFormatException();
            LobbyService.joinGameRequest(Integer.parseInt(command[1]), Wizard.parseColor(command[2].toUpperCase()));
        } catch(NumberFormatException castingException) {
            System.out.println(ConsoleColor.RED + "Invalid number of player" + ConsoleColor.RESET);
        } catch (IllegalArgumentException wizardException) {
            System.out.println(ConsoleColor.RED + "Invalid wizard selected" + ConsoleColor.RESET);
        } catch (InvalidColor e) {
            System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
        }
    }

    /**
     * After some checks on the fairness of the incoming string it sends a join game (ID) request to the server
     * @param command The current command
     * @author Leonardo Airoldi, Christian Confalonieri
     */
    private static void cliJoinGameIdRequest(String[] command) {
        try {
            LobbyService.joinGameIdRequest(Client.getInstance().getAllServerLobbys().get(Integer.parseInt(command[1])-1).getGameLobbyId(), Wizard.parseColor(command[2].toUpperCase()));
        } catch (InvalidColor e) {
            System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColor.RED + "Invalid lobby number" + ConsoleColor.RESET);
        }
    }

    /**
     * After some checks on the fairness of the incoming string it sends a play card request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliPlayCardRequest(String[] command) {
        if(globalCommand) return;
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid play card command" + ConsoleColor.RESET);
            return;
        }
        String color = switch (command[0]) {
            case "1" -> "ONE";
            case "2" -> "TWO";
            case "3" -> "THREE";
            case "4" -> "FOUR";
            case "5" -> "FIVE";
            case "6" -> "SIX";
            case "7" -> "SEVEN";
            case "8" -> "EIGHT";
            case "9" -> "NINE";
            case "10" -> "TEN";
            default -> command[0];
        };
        try {
            GameService.playCardRequest(Card.valueOf(color));
        } catch (IllegalArgumentException e) {
            System.out.println(ConsoleColor.RED + "Invalid card selected" + ConsoleColor.RESET);
        }
    }

    /**
     * After some checks on the fairness of the incoming string it sends a move students request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliMoveStudentsRequest(String[] command) {
        if(globalCommand) return;
        if(command.length != Client.getInstance().getGameHandler().getGame().getGameRules().studentsRules.turnStudents * 2) {
            System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
            return;
        }
        List<Student> toDiningRoom = new ArrayList<>();
        Map<Student,String> toIslands = new HashMap<>();

        try {
            for (int i = 0; i < Client.getInstance().getGameHandler().getGame().getGameRules().studentsRules.turnStudents * 2; i += 2) {
                if (command[i].equalsIgnoreCase("D")) {
                    for (Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                        if (student.getColor() == Pawn.parseColor(command[i + 1]) && !toDiningRoom.contains(student) && !toIslands.keySet().contains(student)) {
                            toDiningRoom.add(student);
                            break;
                        }
                    }
                } else {
                    for (Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                        if (student.getColor() == Pawn.parseColor(command[i + 1]) && !toDiningRoom.contains(student) && !toIslands.keySet().contains(student)) {
                            toIslands.put(student, Client.getInstance().getGameHandler().getGame().getIslands().get(Integer.parseInt(command[i]) - 1).getUuid());
                            break;
                        }
                    }
                }
            }
            GameService.moveStudentsRequest(toDiningRoom, toIslands);
        } catch (InvalidColor e) {
            System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
        }
    }

    /**
     * After some checks on the fairness of the incoming string it sends a move mother nature request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliMoveMotherNatureRequest(String[] command) {
        if(globalCommand) return;
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
            return;
        }
        GameService.moveMotherNatureRequest(Integer.parseInt(command[0]));
    }

    /**
     * After some checks on the fairness of the incoming string it sends a move cloud request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliMoveCloudRequest(String[] command) {
        if(globalCommand) return;
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid move command" + ConsoleColor.RESET);
            return;
        }
        GameService.moveCloudRequest(Client.getInstance().getGameHandler().getGame().getClouds().get(Integer.parseInt(command[0]) - 1));
    }

    /**
     * After some checks on the fairness of the incoming string it sends a power request to the server
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void cliPowersRequest(String[] command) {
        if(command.length < 2 || command.length > 14) {
            System.out.println(ConsoleColor.RED + "Invalid power command" + ConsoleColor.RESET);
            return;
        }
        else {
            PowerType currentPower = null;
            PawnColor color;
            EffectHandler effectHandler = new EffectHandler();
            List<PowerCard> powerCards = Client.getInstance().getGameHandler().getGame().getPowerCards();
            List<PowerType> powerTypes = new ArrayList<>();
            for(int i=0; i<3; i++) {
                powerTypes.add(Client.getInstance().getGameHandler().getGame().getPowerCards().get(i).getType());
            }
            try{
            switch(command[1].toUpperCase()) {
                case "FRIAR":
                    currentPower = PowerType.FRIAR;
                    List<Student> studentsF = new ArrayList<>();
                    if(powerTypes.contains(currentPower)) {
                        Friar currentFriar = (Friar)powerCards.get(powerTypes.indexOf(currentPower));
                        for(Student student : currentFriar.getStudents()) {
                            if (student.getColor() == Pawn.parseColor(command[2])) {
                                studentsF.add(student);
                                break;
                            }
                        }
                    }
                    effectHandler.setChosenStudents1(studentsF);
                    effectHandler.setChosenIslandUuid(Client.getInstance().getGameHandler().getGame().getIslands().get(Integer.parseInt(command[3])-1).getUuid());
                    break;
                case "FARMER":
                    currentPower = PowerType.FARMER;
                    break;
                case "HERALD":
                    currentPower = PowerType.HERALD;
                    effectHandler.setChosenIslandUuid(Client.getInstance().getGameHandler().getGame().getIslands().get(Integer.parseInt(command[2])-1).getUuid());
                    break;
                case "MAILMAN":
                    currentPower = PowerType.MAILMAN;
                    break;
                case "HERBALIST":
                    currentPower = PowerType.HERBALIST;
                    effectHandler.setChosenIslandUuid(Client.getInstance().getGameHandler().getGame().getIslands().get(Integer.parseInt(command[2])-1).getUuid());
                    break;
                case "CENTAUR":
                    currentPower = PowerType.CENTAUR;
                    break;
                case "JESTER":
                    currentPower = PowerType.JESTER;
                    Jester currentJester = (Jester)powerCards.get(powerTypes.indexOf(currentPower));
                    List<Student> toJester = new ArrayList<>();
                    List<Student> toEntranceJ = new ArrayList<>();

                    for(int i=2; i< command.length; i+=2) {
                        if(command[i].equalsIgnoreCase("E")) {
                            for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                                if(student.getColor() == Pawn.parseColor(command[i+1]) && !toJester.contains(student)) {
                                    toJester.add(student);
                                    break;
                                }
                            }
                        }
                        else {
                            for(Student student : currentJester.getStudents()) {
                                if(student.getColor() == Pawn.parseColor(command[i+1]) && !toEntranceJ.contains(student)) {
                                    toEntranceJ.add(student);
                                    break;
                                }
                            }
                        }
                    }

                    effectHandler.setChosenStudents1(toEntranceJ);
                    effectHandler.setChosenStudents2(toJester);
                    break;
                case "KNIGHT":
                    currentPower = PowerType.KNIGHT;
                    break;
                case "HARVESTER":
                    currentPower = PowerType.HARVESTER;
                    color = Pawn.parseColor(command[2]);
                    effectHandler.setHarvesterColor(color);
                    break;
                case "MINSTREL":
                    currentPower = PowerType.MINSTREL;
                    List<Student> toDiningRoom = new ArrayList<>();
                    List<Student> toEntranceM = new ArrayList<>();

                    for(int i=2; i< command.length; i+=2) {
                        if(command[i].equalsIgnoreCase("E")) {
                            for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                                if(student.getColor() == Pawn.parseColor(command[i+1]) && !toDiningRoom.contains(student)) {
                                    toDiningRoom.add(student);
                                    break;
                                }
                            }
                        }
                        else {
                            color = Pawn.parseColor(command[i+1]);
                            for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getStudentsDiningRoom(color)) {
                                if(!toEntranceM.contains(student)) {
                                    toEntranceM.add(student);
                                    break;
                                }
                            }
                        }
                    }
                    effectHandler.setChosenStudents1(toDiningRoom);
                    effectHandler.setChosenStudents2(toEntranceM);
                    break;
                case "PRINCESS":
                    currentPower = PowerType.PRINCESS;
                    List<Student> studentsP = new ArrayList<>();
                    if(powerTypes.contains(currentPower)) {
                        Princess currentPrincess = (Princess) powerCards.get(powerTypes.indexOf(currentPower));
                        for (Student student : currentPrincess.getStudents()) {
                            if (student.getColor() == Pawn.parseColor(command[2])) {
                                studentsP.add(student);
                                break;
                            }
                        }
                    }
                    effectHandler.setChosenStudents1(studentsP);
                    break;
                case "THIEF":
                    currentPower = PowerType.THIEF;
                    color = Pawn.parseColor(command[2]);
                    effectHandler.setThiefColor(color);
                    break;
            }
                GameService.powerRequest(currentPower,effectHandler);
            } catch (IllegalArgumentException e) {
                System.out.println(ConsoleColor.RED + "Invalid character selected" + ConsoleColor.RESET);
            } catch (InvalidColor e) {
                System.out.println(ConsoleColor.RED + "Invalid color" + ConsoleColor.RESET);
            }
        }
    }

    /**
     * Handles request invocation for methods that can be invoked in almost any client state.
     * It is a boolean method because the returned value will be used by the other methods
     * to avoid triggering when global methods are invoked.
     * @param command The current command
     * @return True if one of the cases within the switch is invoked otherwise false
     * @author Christian Confalonieri
     */
    private static boolean globalCommand(String[] command) {
        switch (command[0].toUpperCase()) {
            case "LOGOUT", "L", "LG" -> {
                if (Client.getInstance().getClientState() == ClientState.LOGIN) {
                    System.out.println(ConsoleColor.RED + "invalid client state" + ConsoleColor.RESET);
                    return true;
                }
                cliLogoutRequest(command);
                return true;
            }
            case "HELP", "TIPS", "H", "T" -> {
                if (Client.getInstance().getClientState() == ClientState.LOGIN) {
                    System.out.println(ConsoleColor.RED + "invalid client state" + ConsoleColor.RESET);
                    return true;
                }
                Client.getInstance().getCli().printHelp(command);
                return true;
            }
            case "CHARACTERS", "POWERS", "CHARACTER", "POWER", "CHAR", "C", "P" -> {
                if (Client.getInstance().getClientState() == ClientState.INGAME && Client.getInstance().getGameHandler().getGamePhase() != GamePhase.PREPARATION) {
                    if (command.length > 14) {
                        System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                        return true;
                    }
                    cliPowersRequest(command);
                } else {
                    System.out.println(ConsoleColor.RED + "invalid phase" + ConsoleColor.RESET);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the invocation of methods in the Main Menu state
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void mainMenu(String[] command) {
        if(globalCommand) return;
        if(command.length != 3) {
            System.out.println(ConsoleColor.RED + "Invalid main menu command" + ConsoleColor.RESET);
            return;
        }
        switch (command[0].toUpperCase()) {
            case "NEWGAME", "N", "NEW", "NG" -> cliNewGameRequest(command);
            case "JOINGAMEID", "JID", "JOINID" -> cliJoinGameIdRequest(command);
            case "JOINGAME", "J", "JOIN", "JG" -> cliJoinGameRequest(command);
        }
    }

    /**
     * Handles the invocation of methods in the Game state
     * @param command The current command
     * @author Christian Confalonieri
     */
    private static void inGame(String[] command) {
        switch(Client.getInstance().getGameHandler().getGamePhase()) {
            case PREPARATION:
                cliPlayCardRequest(command);
                break;
            case TURN:
                switch (Client.getInstance().getGameHandler().getTurnPhase()) {
                    case MOVESTUDENTS -> cliMoveStudentsRequest(command);
                    case MOVEMOTHER -> cliMoveMotherNatureRequest(command);
                    case MOVEFROMCLOUD -> cliMoveCloudRequest(command);
                }
                break;
        }
    }
}
