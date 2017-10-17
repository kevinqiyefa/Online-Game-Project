package core.lobby;

// Other Imports
import core.GameEngine;
import model.Ecosystem;
import model.Player;

/**
 *
 * @author Gary
 */
public class EcosystemLobby extends Lobby {

    private GameEngine engine;
    private Ecosystem ecosystem;

    public EcosystemLobby(int lobby_id, Player host, Ecosystem ecosystem) {
        super(lobby_id, host);
        this.ecosystem = ecosystem;
    }

    public GameEngine getGameEngine() {
        return engine;
    }

    public GameEngine setGameEngine(GameEngine engine) {
        return this.engine = engine;
    }

    public Ecosystem getEcosystem() {
        return ecosystem;
    }
}
