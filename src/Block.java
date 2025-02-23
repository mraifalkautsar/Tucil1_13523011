import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Block {
    private final Set<Ball> balls;
    private final List<Set<Ball>> rotations;

    public Block(Set<Ball> balls) {
        // Menormalisasi bola supaya x dan y minimal-nya menjadi 0
        this.balls = normalize(balls);
        // Menghitung semua rotasi untuk menghemat performa.
        this.rotations = precomputeRotations();
    }

    private Set<Ball> normalize(Set<Ball> balls) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (Ball ball : balls) {
            minX = Math.min(minX, ball.x);
            minY = Math.min(minY, ball.y);
        }
        Set<Ball> normalized = new HashSet<>();
        for (Ball ball : balls) {
            normalized.add(new Ball(ball.x - minX, ball.y - minY, ball.id));
        }
        return normalized;
    }

    private List<Set<Ball>> precomputeRotations() {
        Set<Set<Ball>> uniqueRotations = new HashSet<>();
        Set<Ball> current = balls;
        for (int i = 0; i < 4; i++) {
            current = rotate90(current);
            uniqueRotations.add(current);
            uniqueRotations.add(mirror(current));
        }
        return new ArrayList<>(uniqueRotations);
    }

    private Set<Ball> rotate90(Set<Ball> balls) {
        Set<Ball> rotated = new HashSet<>();
        for (Ball ball : balls) {
            rotated.add(new Ball(-ball.y, ball.x, ball.id));
        }
        return normalize(rotated);
    }

    private Set<Ball> mirror(Set<Ball> balls) {
        Set<Ball> mirrored = new HashSet<>();
        for (Ball ball : balls) {
            mirrored.add(new Ball(ball.x, -ball.y, ball.id));
        }
        return normalize(mirrored);
    }

    public List<Set<Ball>> getRotations() {
        return rotations;
    }
}
