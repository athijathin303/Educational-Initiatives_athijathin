interface PlaybackStrategy {
    void play(String song);
}

class NormalPlay implements PlaybackStrategy {
    @Override
    public void play(String song) {
        System.out.println("Playing song: " + song + " in normal mode.");
    }
}

class ShufflePlay implements PlaybackStrategy {
    @Override
    public void play(String song) {
        System.out.println("Playing song: " + song + " in shuffle mode.");
    }
}

class RepeatPlay implements PlaybackStrategy {
    @Override
    public void play(String song) {
        System.out.println("Repeating song: " + song + " infinitely.");
    }
}

class MusicPlayer {
    private PlaybackStrategy strategy;

    public void setStrategy(PlaybackStrategy strategy) { this.strategy = strategy; }

    public void playSong(String song) { strategy.play(song); }
}

// Demo
public class StrategyDemo {
    public static void main(String[] args) {
        MusicPlayer player = new MusicPlayer();

        player.setStrategy(new NormalPlay());
        player.playSong("Imagine");

        player.setStrategy(new ShufflePlay());
        player.playSong("Shape of You");

        player.setStrategy(new RepeatPlay());
        player.playSong("Bohemian Rhapsody");
    }
}
