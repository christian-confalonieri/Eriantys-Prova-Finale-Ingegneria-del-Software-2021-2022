package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.entity.Player;
import it.polimi.ingsw.model.enumeration.Card;
import it.polimi.ingsw.model.enumeration.GamePhase;
import it.polimi.ingsw.model.enumeration.TurnPhase;
import it.polimi.ingsw.model.power.PowerCard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameHandler {

    protected Player currentPlayer;
    private List<Player> orderedTurnPlayers;
    protected GamePhase gamePhase;
    protected TurnPhase turnPhase;



    protected Player firstTurnPlayer;
    protected boolean ended;
    protected Game game;

    /**
     * Advance the state of the game after all the actions in the current state has been performed
     */
    public void advance() {
        switch(gamePhase) {

            case PREPARATION:
                currentPlayer = getNextPlayer();
                if(currentPlayer == firstTurnPlayer) { // All players played a card
                    gamePhase = GamePhase.TURN;
                    turnPhase = TurnPhase.MOVESTUDENTS;
                    orderedTurnPlayers = calculateTurnOrder(); // Calculate the turn order
                    firstTurnPlayer = orderedTurnPlayers.get(0);
                    currentPlayer = firstTurnPlayer;
                }
                else {
                    gamePhase = GamePhase.PREPARATION;
                }
                return;

            case TURN:
                switch (turnPhase) {
                    case MOVESTUDENTS:
                        turnPhase = TurnPhase.MOVEMOTHER;
                        return;
                    case MOVEMOTHER:
                        turnPhase = TurnPhase.MOVEFROMCLOUD;
                        return;
                    case MOVEFROMCLOUD: // END TURN
                        this.ended = checkEndGame(); // Checks at the end of each turn if the game has ended
                        for(PowerCard pc : game.powerCards) {
                            pc.endPower();
                        }
                        currentPlayer = getNextPlayer();
                        turnPhase = TurnPhase.MOVESTUDENTS;
                        if(currentPlayer == firstTurnPlayer) {
                            orderedTurnPlayers = new ArrayList<>(game.players); // Return to a clockwise order for preparation
                            gamePhase = GamePhase.PREPARATION;
                            game.refillClouds();
                        }
                }
        }
    }

    /**
     * Get the player that has to play after the current player
     *
     * @return the player that has to play after the current player
     */
    public Player getNextPlayer() {
        return orderedTurnPlayers.get((orderedTurnPlayers.indexOf(currentPlayer) + 1) % (orderedTurnPlayers.size()));
    }

    /**
     * Returns a list of the cards played from the players before the current player in the current turn
     *
     * @return the list of cards
     * @author Christian Confalonieri
     */
    public List<Card> previousPlayedCards() {
        List<Card> playedCard = new ArrayList<>();

        int firstPlayerIndex = orderedTurnPlayers.indexOf(firstTurnPlayer);

        List<Player> orderedPlayerFromFirst = new ArrayList<>(orderedTurnPlayers).stream().skip(firstPlayerIndex).collect(Collectors.toCollection(ArrayList::new));
        orderedPlayerFromFirst.addAll(orderedTurnPlayers.stream().limit(firstPlayerIndex).toList());

        for(Player player : orderedPlayerFromFirst) {
            if(player != currentPlayer) {
                Card card = player.getLastPlayedCard();
                if(card != null) {
                    playedCard.add(card);
                }
            }
            else  {
                return playedCard;
            }
        }
        return null;
    }

    /**
     * Return the first player of the turn after the preparation by comparing the last played card
     *
     * @return the player with the smallest number lastPlayedCard
     */
    public List<Player> calculateTurnOrder() {
        // TODO check equals card priority
        return game.players.stream().sorted((p1, p2) -> p1.getLastPlayedCard().getNumber() <= p2.getLastPlayedCard().getNumber() ? -1 : 1).collect(Collectors.toList());
    }

    public boolean isEnded() {
        return ended;
    }

    public Game getGame() {
        return game;
    }

    /**
     * Constructs a game handler, adding the state to the passed game object
     *
     * @param game the game to handle
     */
    protected GameHandler(Game game) {
        this.game = game;
        firstTurnPlayer = game.players.get(game.gameRules.playerRules.startingPlayer);
        orderedTurnPlayers = new ArrayList<>(game.players);
        gamePhase = GamePhase.PREPARATION;
        turnPhase = TurnPhase.MOVESTUDENTS;
        currentPlayer = firstTurnPlayer;
        ended = false;
    }

    /**
     * Constructs a game handler, adding the state to the passed game object
     * Initializes then the PowerCards giving them the access to the handler
     *
     * @param game the game to handle
     */
    public static GameHandler gameHandlerBuilder(Game game) {
        GameHandler gameHandler = new GameHandler(game);
        game.powerCards = PowerCard.getThreeUniquePowerCards(gameHandler);
        return gameHandler;
    }

    /**
     * Check if the game is in an end situation
     * Must be called at the end of each player turn
     * this.advance calls
     *
     * @return true if the game has ended
     */
    public boolean checkEndGame() {
        return  // One player has finished his towers
                game.players.stream().map(p -> p.getSchool().getTowers().size()).anyMatch(size -> size == 0) ||
                        // There are only 3 islands
                        game.islands.size() <= 3 ||
                        // The cards or the students finished and all the players finished their turns
                        (game.players.stream().map(p -> p.getHandCards().size()).anyMatch(size -> size == 0) ) &&
                                firstTurnPlayer == getNextPlayer()
                        // The students in the bag finished and all the players finished their turns
                        || game.bag.isEmpty() && gamePhase == GamePhase.TURN && turnPhase == TurnPhase.MOVEFROMCLOUD &&
                                firstTurnPlayer == getNextPlayer()
                ;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getOrderedTurnPlayers() {
        return orderedTurnPlayers;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setOrderedTurnPlayers(List<Player> orderedTurnPlayers) {
        this.orderedTurnPlayers = orderedTurnPlayers;
    }

    public Player getFirstTurnPlayer() {
        return firstTurnPlayer;
    }

    public void setFirstTurnPlayer(Player firstTurnPlayer) {
        this.firstTurnPlayer = firstTurnPlayer;
    }
}

