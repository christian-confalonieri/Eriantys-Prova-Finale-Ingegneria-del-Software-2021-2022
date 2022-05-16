package it.polimi.ingsw.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.controller.services.GameService;
import it.polimi.ingsw.client.controller.services.LobbyService;
import it.polimi.ingsw.client.controller.services.LoginService;
import it.polimi.ingsw.model.entity.Student;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.PawnColor;
import it.polimi.ingsw.model.enumeration.PowerType;
import it.polimi.ingsw.model.enumeration.Wizard;
import it.polimi.ingsw.model.power.*;

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
                        case "TIPS":
                            if(CLI.getInstance().printTips(command)) {
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
                        case "TIPS":
                            if(CLI.getInstance().printTips(command)) {
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
        if(command.length < 1 || command.length > 2) {
            System.out.println(ConsoleColor.RED + "Invalid play card command" + ConsoleColor.RESET);
            return true;
        }
        /*--------------------LOGOUT AND TIPS SECTION--------------------*/
        if(command.length == 1 && command[0].equalsIgnoreCase("LOGOUT")) {
            LoginService.logoutRequest();
        }
        else {
            if (command[0].equalsIgnoreCase("TIPS") && CLI.getInstance().printTips(command)) {
                return true;
            }
        }
        /*----------------------------------------------------------------*/
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
        if(command.length <1 || command.length > 14) {
            System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
            return true;
        }
        /*----------LOGOUT, CHARACTER ACTIVATION AND TIPS SECTION----------*/
        if(command.length == 1 && command[0].equalsIgnoreCase("LOGOUT")) {
            LoginService.logoutRequest();
        }
        else {
            if (command[0].equalsIgnoreCase("CHARACTER") && cliPowersRequest(command)) {
                return true;
            }
            else {
                if (command[0].equalsIgnoreCase("TIPS") && CLI.getInstance().printTips(command)) {
                    return true;
                }
            }
        }
        /*----------------------------------------------------------------*/
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
        if(command.length <1 || command.length > 14) {
            System.out.println(ConsoleColor.RED + "Invalid command" + ConsoleColor.RESET);
            return true;
        }
        /*----------LOGOUT, CHARACTER ACTIVATION AND TIPS SECTION----------*/
        if(command.length == 1 && command[0].equalsIgnoreCase("LOGOUT")) {
            LoginService.logoutRequest();
        }
        else {
            if (command[0].equalsIgnoreCase("CHARACTER") && cliPowersRequest(command)) {
                    return true;
            }
            else {
                if (command[0].equalsIgnoreCase("TIPS") && CLI.getInstance().printTips(command)) {
                    return true;
                }
            }
        }
        /*----------------------------------------------------------------*/
        GameService.moveMotherNatureRequest(Integer.parseInt(command[0]));
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliMoveCloudRequest(String[] command) {
        if(command.length <1 || command.length > 14) {
            System.out.println(ConsoleColor.RED + "Invalid move command" + ConsoleColor.RESET);
            return true;
        }
        /*----------LOGOUT, CHARACTER ACTIVATION AND TIPS SECTION----------*/
        if(command.length == 1 && command[0].equalsIgnoreCase("LOGOUT")) {
            LoginService.logoutRequest();
        }
        else {
            if (command[0].equalsIgnoreCase("CHARACTER") && cliPowersRequest(command)) {
                return true;
            }
            else {
                if (command[0].equalsIgnoreCase("TIPS") && CLI.getInstance().printTips(command)) {
                    return true;
                }
            }
        }
        /*----------------------------------------------------------------*/
        GameService.moveCloudRequest(Client.getInstance().getGameHandler().getGame().getClouds().get(Integer.parseInt(command[0]) - 1));
        return true;
    }

    /**
     * @author Christian Confalonieri
     */
    private static boolean cliPowersRequest(String[] command) {
        if(command.length <= 2) {
            System.out.println(ConsoleColor.RED + "Invalid power command" + ConsoleColor.RESET);
            return true;
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
            switch(command[1].toUpperCase()) {
                case "FRIAR":
                    currentPower = PowerType.FRIAR;
                    List<Student> studentsF = new ArrayList<>();
                    if(powerTypes.contains(currentPower)) {
                        Friar currentFriar = (Friar)powerCards.get(powerTypes.indexOf(currentPower));
                        for(Student student : currentFriar.getStudents()) {
                            if (student.getColor().toString().equalsIgnoreCase(command[2])) {
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
                        if(command[i].equalsIgnoreCase("E:")) {
                            for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                                if(student.getColor().toString().equalsIgnoreCase(command[i+1]) && !toJester.contains(student)) {
                                    toJester.add(student);
                                    break;
                                }
                            }
                        }
                        else {
                            for(Student student : currentJester.getStudents()) {
                                if(student.getColor().toString().equalsIgnoreCase(command[i+1]) && !toEntranceJ.contains(student)) {
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
                    color = switch(command[2].toUpperCase()) {
                        case "RED" -> PawnColor.RED;
                        case "YELLOW" -> PawnColor.YELLOW;
                        case "GREEN" -> PawnColor.GREEN;
                        case "BLUE" -> PawnColor.BLUE;
                        case "PINK" -> PawnColor.PINK;
                        default -> null;
                    };
                    effectHandler.setHarvesterColor(color);
                    break;
                case "MINSTREL":
                    currentPower = PowerType.MINSTREL;
                    List<Student> toDiningRoom = new ArrayList<>();
                    List<Student> toEntranceM = new ArrayList<>();

                    for(int i=2; i< command.length; i+=2) {
                        if(command[i].equalsIgnoreCase("E:")) {
                            for(Student student : Client.getInstance().getGameHandler().getGame().getPlayerFromId(Client.getInstance().getPlayerId()).getSchool().getEntrance()) {
                                if(student.getColor().toString().equalsIgnoreCase(command[i+1]) && !toDiningRoom.contains(student)) {
                                    toDiningRoom.add(student);
                                    break;
                                }
                            }
                        }
                        else {
                            color = switch(command[i+1].toUpperCase()) {
                                case "RED" -> PawnColor.RED;
                                case "YELLOW" -> PawnColor.YELLOW;
                                case "GREEN" -> PawnColor.GREEN;
                                case "BLUE" -> PawnColor.BLUE;
                                case "PINK" -> PawnColor.PINK;
                                default -> null;
                            };
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
                            if (student.getColor().toString().equalsIgnoreCase(command[2])) {
                                studentsP.add(student);
                                break;
                            }
                        }
                    }
                    effectHandler.setChosenStudents1(studentsP);
                    break;
                case "THIEF":
                    currentPower = PowerType.THIEF;
                    color = switch(command[2].toUpperCase()) {
                        case "RED" -> PawnColor.RED;
                        case "YELLOW" -> PawnColor.YELLOW;
                        case "GREEN" -> PawnColor.GREEN;
                        case "BLUE" -> PawnColor.BLUE;
                        case "PINK" -> PawnColor.PINK;
                        default -> null;
                    };
                    effectHandler.setThiefColor(color);
                    break;
            }
            try {
                GameService.powerRequest(currentPower,effectHandler);
            } catch (IllegalArgumentException e) {
                System.out.println(ConsoleColor.RED + "Invalid character selected" + ConsoleColor.RESET);
            }
        }
        return true;
    }
}
