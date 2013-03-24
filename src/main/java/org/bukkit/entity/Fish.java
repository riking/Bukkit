package org.bukkit.entity;

import org.bukkit.Bukkit;

/**
 * Represents a fishing hook.
 */
public interface Fish extends Projectile {

    /**
     * Gets the chance of a fish biting.
     * @return a BiteChance object
     */
    public BiteChance getBiteChance();

    /**
     * Sets the chance of a fish biting.
     * @param a BiteChance object
     */
    public void setBiteChance(BiteChance chance);

    /**
     * Construct a new BiteChance for this FishHook.
     * @param clear chance per tick of a bite in clear weather
     * @param rainy chance per tick of a bite in rainy weather
     * @return a BiteChance with the given values
     */
    public BiteChance constructBiteChance(double clear, double rainy);

    /**
     * Construct a BiteChance for this FishHook with vanilla behavior.
     * @return a BiteChance with vanilla behavior
     */
    public BiteChance constructDefaultBiteChance();

    /**
     * A class to represent the chance of a fish biting. Chances are evaluated
     * every 1/20th of a second.
     * <p />
     * A BiteChance belongs to a fishing hook and should not be reused; use
     * {@link Fish#constructBiteChance(double, double)} to construct one.
     */
    public abstract class BiteChance {

        /**
         * The vanilla chance per tick of a fish biting in clear weather.
         */
        public static final double DEFAULT_CLEAR_CHANCE = 1 / 500d;

        /**
         * The vanilla chance per tick of a fish biting in rainy weather.
         */
        public static final double DEFAULT_RAINY_CHANCE = 1 / 300d;

        public double clear_chance = DEFAULT_CLEAR_CHANCE;
        public double rainy_chance = DEFAULT_RAINY_CHANCE;

        /**
         * Gets the current chance of a fish biting, based on the current
         * weather the fishing hook is in.
         * @return chance per tick of a bite
         */
        public abstract double getChance();

        /**
         * Get the chance given by this BiteChance in clear weather.
         * @return chance per tick of a bite given clear weather
         */
        public double getClearBiteChance() {
            return clear_chance;
        }

        /**
         * Get the chance given by this BiteChance in rainy weather.
         * @return chance per tick of a bite given rainy weather
         */
        public double getRainyBiteChance() {
            return rainy_chance;
        }

        /**
         * Set the chance given by this BiteChance in clear weather.
         * @param chance per tick of a bite given clear weather
         */
        public void setClearBiteChance(double chance) {
            clear_chance = chance;
        }

        /**
         * Set the chance given by this BiteChance in rainy weather.
         * @param chance per tick of a bite given rainy weather
         */
        public void setRainyBiteChance(double chance) {
            rainy_chance = chance;
        }
    }
}
