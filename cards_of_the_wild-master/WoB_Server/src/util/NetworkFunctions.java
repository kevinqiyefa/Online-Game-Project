package util;

// Java Imports
import java.util.Arrays;
import java.util.List;

// Other Imports
import core.GameServer;
import core.lobby.Lobby;
import core.lobby.LobbyController;
import core.world.World;
import core.world.WorldController;
import model.Account;
import model.Player;
import net.response.GameResponse;

public class NetworkFunctions {

    private NetworkFunctions() {
    }

    /**
     * Push a pending response to a user's queue.
     *
     * @param account_id holds the player ID
     * @param response is the instance containing the response information
     */
    public static void sendToUser(GameResponse response, int account_id) {
        Account account = GameServer.getInstance().getActiveAccount(account_id);

        if (account != null) {
            account.getClient().add(response);
        } else {
            Log.printf_e("Failed to create response for user, %d.", account_id);
        }
    }

    public static void sendToPlayer(GameResponse response, int player_id) {
        Player player = GameServer.getInstance().getActivePlayer(player_id);

        if (player != null) {
            player.getClient().add(response);
        } else {
            Log.printf_e("Failed to create response for player, %d.", player_id);
        }
    }

    /**
     * Push a pending response to all users' queue except one user.
     *
     * @param response is the instance containing the response information
     * @param exclude_id holds the excluding player ID
     */
    public static void sendToGlobal(GameResponse response, Integer... exclude_id) {
        List<Integer> exclude = Arrays.asList(exclude_id);

        for (Player player : GameServer.getInstance().getActivePlayers()) {
            if (player != null && !exclude.contains(player.getID())) {
                player.getClient().add(response);
            }
        }
    }

    /**
     * Push a pending response to all users' queue in the same world.
     *
     * @param response is the instance containing the response information
     * @param world_id holds the world ID
     * @param exclude_id
     */
    public static void sendToWorld(GameResponse response, int world_id, Integer... exclude_id) {
        World world = WorldController.getInstance().get(world_id);

        if (world != null) {
            List<Integer> exclude = Arrays.asList(exclude_id);

            for (Player player : world.getPlayers().values()) {
                if (!exclude.contains(player.getID())) {
                    player.getClient().add(response);
                }
            }
        }
    }

    /**
     * Push a response to all user's queue in the given lobby.
     *
     * @param response
     * @param lobby_id
     * @param exclude_id
     */
    public static void sendToLobby(GameResponse response, int lobby_id, Integer... exclude_id) {
        Lobby lobby = LobbyController.getInstance().get(lobby_id);

        if (lobby != null) {
            List<Integer> exclude = Arrays.asList(exclude_id);

            for (Player player : lobby.getPlayers()) {
                if (!exclude.contains(player.getID())) {
                    player.getClient().add(response);
                }
            }
        }
    }
}
