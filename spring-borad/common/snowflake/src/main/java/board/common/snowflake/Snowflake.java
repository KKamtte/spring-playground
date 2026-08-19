package board.common.snowflake;

import java.util.random.RandomGenerator;

public class Snowflake {
    /**
     * 분산 시스템에서 고유한 64비트 ID를 생성하는 알고리즘
     * [1비트][41비트: 타임스탬프][10비트: 노드 ID][12비트: 시퀀스 번호]
     * 분산 환경에서도 중복 없이 순차적 ID 생성하기 위한 규칙으로 사용 가능
     */
    private static final int UNUSED_BITS = 1;
    private static final int EPOCH_BITS = 41;
    private static final int NODE_ID_BITS = 10;
    private static final int SEQUENCE_BITS = 12;

    private static final long maxNodeId = (1L << NODE_ID_BITS) - 1;
    private static final long maxSequence = (1L << SEQUENCE_BITS) - 1;

    private final long nodeId = RandomGenerator.getDefault().nextLong(maxNodeId + 1);
    // UTC = 2024-01-01T00:00:00Z
    private final long startTimeMillis = 1704067200000L;

    private long lastTimeMillis = startTimeMillis;
    private long sequence = 0L;

    public synchronized long nextId() {
        long currentTimeMillis = System.currentTimeMillis();

        if (currentTimeMillis < lastTimeMillis) {
            throw new IllegalStateException("Invalid Time");
        }

        if (currentTimeMillis == lastTimeMillis) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                currentTimeMillis = waitNextMillis(currentTimeMillis);
            }
        } else {
            sequence = 0;
        }

        lastTimeMillis = currentTimeMillis;

        return ((currentTimeMillis - startTimeMillis) << (NODE_ID_BITS + SEQUENCE_BITS))
                | (nodeId << SEQUENCE_BITS)
                | sequence;
    }

    private long waitNextMillis(long currentTimestamp) {
        while (currentTimestamp <= lastTimeMillis) {
            currentTimestamp = System.currentTimeMillis();
        }
        return currentTimestamp;
    }
}
