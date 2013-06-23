package org.bukkit.event.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Warning;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.google.common.collect.ImmutableList;

/**
 * This event is called when a player attempts to tab-complete a chat message,
 * to allow plugins to change the results returned to the client.
 */
public class PlayerChatTabCompleteEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private final String message;
    private final String lastToken;
    private List<String> completions;

    @Deprecated
    @Warning(reason = "The type of completions has been narrowed to a List")
    public PlayerChatTabCompleteEvent(final Player who, final String message, final Collection<String> completions) {
        this(who, message, new ArrayList<String>(completions));
    }

    public PlayerChatTabCompleteEvent(final Player who, final String message, final List<String> completions) {
        super(who);
        Validate.notNull(message, "Message cannot be null");
        Validate.notNull(completions, "Completions cannot be null");
        this.message = message;
        int i = message.lastIndexOf(' ');
        if (i < 0) {
            this.lastToken = message;
        } else {
            this.lastToken = message.substring(i + 1);
        }
        this.completions = completions;
    }

    /**
     * Gets the chat message being tab-completed.
     *
     * @return the chat message
     */
    public String getChatMessage() {
        return message;
    }

    /**
     * Gets the last 'token' of the message being tab-completed. The token is
     * the substring starting with the character after the last space in the
     * message.
     *
     * @return The last token for the chat message
     */
    public String getLastToken() {
        return lastToken;
    }

    /**
     * This is the collection of completions for this event.
     *
     * @return the current completions
     * @deprecated In the past, plugins were required to change this
     *             collection. Use {@link #setCompletions(List)} instead.
     *
     * @see #setCompletions(List)
     */
    @Deprecated
    @Warning(reason = "This method is returning a mutable list")
    public Collection<String> getTabCompletions() {
        return completions;
    }

    /**
     * Get the current suggested list of tab-completions as a mutable list. To
     * actually change the list of tab-completions, use
     * {@link #setCompletions(List)}.
     *
     * @return the current completions
     */
    public List<String> getCompletions() {
        return new ArrayList<String>(completions);
    }

    /**
     * Set the new list of tab-completions to send to the player.
     *
     * @param completions the new list of tab-completions
     * @throws ClassCastException if the list is not a list of String
     */
    public void setCompletions(List<String> completions) {
        // this.completions = completions
        // completions must be made mutable, for legacy purposes.
        this.completions = new ArrayList<String>(completions);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
