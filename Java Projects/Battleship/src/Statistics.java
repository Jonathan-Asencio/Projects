
/**class that records number of hits and misses*/
public class Statistics
{
    /**
     * creates new count of hits and misses
     */
    public class HitMiss
    {
        private int player;
        private int hits = 0;
        private int misses = 0;

        /**
         * Creates a new object
         * @param player
         */
        public HitMiss(int player)
        {
            this.player = player;
        }

        /**
         * Which player this object is for
         * @return
         */
        public int getPlayer()
        {
            return player;
        }

        /**
         * gets number of hits
         * @return number of hits
         */
        public int getHits()
        {
            return hits;
        }

        /**
         * get number of misses
         * @return number of misses
         */
        public int getMisses()
        {
            return misses;
        }

        /**
         * get total number of missiles fired
         * @return total number of missiles fired
         */
        public int getTotal()
        {
            return hits + misses;
        }

        /**
         * counts number of hits
         */
        public void recordHit()
        {
            hits++;
        }

        /**
         * counts number of misses
         */
        public void recordMiss()
        {
            misses++;
        }
    }

    /**
     * creates an array of number of hits and misses
     */
    HitMiss[] players = {
            new HitMiss(0),
            new HitMiss(1)
    };

    /**
     * Record a hit against a specified user
     * @param user
     */
    public void recordHit(int user)
    {
        players[user].recordHit();
    }

    /**
     * Record a miss against a specified user
     * @param user
     */
    public void recordMiss(int user)
    {
        players[user].recordMiss();
    }

    /**
     * Returns the number of hits by the specified user
     * @param user
     * @return
     */
    public int hits(int user)
    {
        return players[user].getHits();
    }

    /**
     * Returns the number of misses by the specified user
     * @param user
     * @return
     */
    public int misses(int user)
    {
        return players[user].getMisses();
    }

    /**
     * Returns the total firings by the specified user
     * @param user
     * @return
     */
    public int total(int user)
    {
        return players[user].getTotal();
    }

    /**
     * prints out statistics of hits and misses
     * @param gameLogger
     */
    public void writeStatistics(GameLogger gameLogger)
    {
        gameLogger.println("----------------------------");
        gameLogger.println("STATISTICS");
        gameLogger.println("----------------------------");

        for(HitMiss player : players)
        {
            gameLogger.println("Player " + player.getPlayer() + ": " + player.getTotal() + " move(s) // " + player.getHits() + " hit(s) / " + player.getMisses() + " miss(es)");
        }

        gameLogger.println("");
    }
}
