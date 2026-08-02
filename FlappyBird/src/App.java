import javax.swing.*;
public class App {
    public static void main(String[] args) {
        int board_height = 640;
        int board_width = 500;

        JFrame frame = new JFrame("Flappy bird"); // frame for game with name "Flappy bird"
        frame.setSize(board_width, board_height); // set size to frame
        frame.setLocationRelativeTo(null); // set location of frame? if null it places frame in center
        frame.setResizable(false); // to deny resize window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if we close our frame then program will close too

        FlappyBird flappy_bird = new FlappyBird();
        frame.add(flappy_bird);
        frame.pack();
        flappy_bird.requestFocus();
        frame.setVisible(true);// set visibility, false - not visible, true - visible

    }
}