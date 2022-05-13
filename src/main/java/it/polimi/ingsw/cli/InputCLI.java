package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.Wizard;

import java.util.*;

/**
 * @author Christian Confalonieri
 */
public class InputCLI {
    public static void inputHandler(Scanner inputScanner) {
        if (inputScanner.hasNext()) {
            String inputString = inputScanner.next();
            inputString = inputString.trim();

            String[] command = inputString.split(" ");

            switch(Client.getInstance().getClientState()) {
                case LOGIN:
                    if(cliLoginRequest(command)) {
                        break;
                    }
                case MAINMENU:
                    switch(command[0].toUpperCase()) {
                        case "LOGOUT":
                            if(cliLogoutRequest(command)) {
                                break;
                            }
                        case "NEWGAME":
                            if(cliNewGameRequest(command)) {
                                break;
                            }

                        case "JOINGAME":
                            if(cliJoinGameRequest(command)) {
                                break;
                            }
                        default:
                            System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                    }
                    break;
                case WAITINGLOBBY:
                    switch (command[0].toUpperCase()) {
                        case "LOGOUT":
                            if(cliLogoutRequest(command)) {
                                break;
                            }
                        default:
                            System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                    }
                    break;
                case INGAME:
                    switch(Client.getInstance().getGameHandler().getGamePhase()) {
                        case PREPARATION:
                            if(cliPlayCardRequest(command)) {
                                break;
                            }
                        case TURN:
                            switch(Client.getInstance().getGameHandler().getTurnPhase()) {
                                case MOVESTUDENTS:
                                    if(cliMoveStudentsRequest(command)) {
                                        break;
                                    }
                                case MOVEMOTHER:
                                    if(cliMoveMotherNatureRequest(command)) {
                                        break;
                                    }
                                case MOVEFROMCLOUD:
                                    if(cliMoveCloudRequest(command)) {
                                        break;
                                    }
                                default:
                                    System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
                            }
                            break;
                    }
                    break;
                case ENDGAME:
                    Client.getInstance().setClientState(ClientState.MAINMENU);
                    Client.getInstance().getCli().render();
                default:
                    System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
            }
        }
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliLoginRequest(String[] command) {
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid login command" + ConsoleColor.RESET);
            return true;
        }
        LoginService.loginRequest(command[0]);
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliLogoutRequest(String[] command) {
        if (command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid logout command" + ConsoleColor.RESET);
            return true;
        }
        LoginService.logoutRequest();
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliNewGameRequest(String[] command) {
        if(command.length != 3) {
            System.out.println(ConsoleColor.RED + "Invalid new game command" + ConsoleColor.RESET);
            return true;
        }
        try {
            if(Integer.parseInt(command[1]) < 2 || Integer.parseInt(command[1]) > 4) throw new NumberFormatException();
            LobbyService.newGameRequest(Integer.parseInt(command[1]), null, Wizard.valueOf(command[2].toUpperCase()));
        } catch(NumberFormatException castingException) {
            System.out.println("Invalid number of player");
        } catch (IllegalArgumentException wizardException) {
            System.out.println("Invalid wizard selected");
        }
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliJoinGameRequest(String[] command) {
        if(command.length != 3) {
            System.out.println(ConsoleColor.RED + "Invalid join game command" + ConsoleColor.RESET);
            return true;
        }
        try {
            if(Integer.parseInt(command[1]) < 2 || Integer.parseInt(command[1]) > 4) throw new NumberFormatException();
            LobbyService.joinGameRequest(Integer.parseInt(command[1]), Wizard.valueOf(command[2].toUpperCase()));
        } catch(NumberFormatException castingException) {
            System.out.println("Invalid number of player");
        } catch (IllegalArgumentException wizardException) {
            System.out.println("Invalid wizard selected");
        }
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliPlayCardRequest(String[] command) {
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid play card command" + ConsoleColor.RESET);
            return true;
        }
        else {
            if (command[0].equalsIgnoreCase("LOGOUT")) {
                LoginService.logoutRequest();
                return true;
            }
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
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliMoveStudentsRequest(String[] command) {
        if(command.length != 6 && command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid move students command" + ConsoleColor.RESET);
            return true;
        }
        else {
            if (command.length == 1 && command[0].equalsIgnoreCase("LOGOUT")) {
                LoginService.logoutRequest();
                return true;
            }
        }
        List<Student> toDiningRoom = new ArrayList<>();
        Map<Student,String> toIslands = new HashMap<>();

        for(int i=0; i<6; i+=2) {
            if(command[i].equalsIgnoreCase("D:")) {
                for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                    if(student.getColor().toString().equalsIgnoreCase(command[i+1]) && !toDiningRoom.contains(student) && !toIslands.keySet().contains(student)) {
                        toDiningRoom.add(student);
                        break;
                    }
                }
            }
            else {
                for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                    if(student.getColor().toString().equalsIgnoreCase(command[i+1]) && !toDiningRoom.contains(student) && !toIslands.keySet().contains(student)) {
                        toIslands.put(student,Client.getInstance().getGameHandler().getGame().getIslands().get(Integer.parseInt(command[i].substring(0, command[i].length()-1))-1).getUuid());
                        break;
                    }
                }
            }
        }


        GameService.moveStudentsRequest(toDiningRoom,toIslands);
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliMoveMotherNatureRequest(String[] command) {
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid move mother nature command" + ConsoleColor.RESET);
            return true;
        }
        if(command[0].equalsIgnoreCase("LOGOUT")) {
            LoginService.logoutRequest();
        }
        else {
            GameService.moveMotherNatureRequest(Integer.parseInt(command[0]));
        }
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliMoveCloudRequest(String[] command) {
        if(command.length != 1) {
            System.out.println(ConsoleColor.RED + "Invalid move from cloud command" + ConsoleColor.RESET);
            return true;
        }
        if (command[0].equalsIgnoreCase("LOGOUT")) {
            LoginService.logoutRequest();
        }
        else {
            GameService.moveCloudRequest(Client.getInstance().getGameHandler().getGame().getClouds().get(Integer.parseInt(command[0])-1));
        }
        return true;
    }
}
