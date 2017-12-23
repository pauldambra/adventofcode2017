package com.pauldambra.adventofcode2017.day22

enum class NodeState {
    CLEANED {
        override fun weaken() = WEAKENED
    },
    WEAKENED {
        override fun weaken() = INFECTED
    },
    INFECTED {
        override fun weaken() = FLAGGED
    },
    FLAGGED {
        override fun weaken() = CLEANED
    };

    abstract fun weaken(): NodeState
}