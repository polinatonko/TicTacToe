package org.example.tictactoe.service;

import java.util.EnumMap;
import java.util.Map;

import org.example.tictactoe.domain.State;

/**
 * Service class responsible for tracking and managing game statistics.
 */
public class GameStatisticsService {

    private final Map<State, Integer> statistics;

    /**
     * Constructs a new statistics service with initialized counters.
     */
    public GameStatisticsService() {
        statistics = new EnumMap<>(State.class);
        initializeStatistics();
    }

    /**
     * Records a game outcome by incrementing the appropriate counter.
     *
     * @param state the final state of the game to record
     */
    public void recordGameOutcome(State state) {
        statistics.compute(state, (key, count) -> count + 1);
    }

    /**
     * Returns the copy of statistics map.
     * 
     * @return the copy statistics map
     */
    public Map<State, Integer> getStatistics() {
        return Map.copyOf(statistics);
    }

    private void initializeStatistics() {
        for (State state : State.values()) {
            statistics.put(state, 0);
        }
    }
} 
