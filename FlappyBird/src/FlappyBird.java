import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, MouseListener{
    int board_width = 400;
    int board_height = 640;

    //Images
    Image background;
    Image bird_img;
    Image top_pipe_img;
    Image bottom_pipe_img;

    //Bird
    int bird_x = board_width / 8;
    int bird_y = board_height / 2;
    int bird_width = 34;
    int bird_height = 24;
    boolean start = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!start)
            game_loop.start();
        if(e.getButton() == MouseEvent.BUTTON1)
            velocity_y = -6;
        if(game_over && e.getButton() == MouseEvent.BUTTON3)
        {
            velocity_y = 0;
            bird.y = bird_y;
            pipes.clear();
            score = 0;
            game_over = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        game_loop.start();
        place_pipes_timer.start();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    class Bird {
        int x = bird_x;
        int y = bird_y;
        int height = bird_height;
        int width = bird_width;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    int pipe_x = board_width;
    int pipe_y = 0;
    int pipe_width = 64;
    int pipe_height = 512;

    class Pipe {
        int x = pipe_x;
        int y = pipe_y;
        int width = pipe_width;
        int height = pipe_height;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    double velocity_y = 0;
    int velocity_x = -4;
    double gravity = 0.4;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer game_loop;
    Timer place_pipes_timer;

    boolean game_over = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(board_width, board_height));
        setBackground(Color.blue);
        setFocusable(true);
        addMouseListener(this);
        background =  new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        bird_img = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        bottom_pipe_img = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        top_pipe_img = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();

        bird = new Bird(bird_img);
        pipes = new ArrayList<Pipe>();

        place_pipes_timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        }); //1.5s

        game_loop = new Timer(1000/60, this);// 1s/6
        //game_loop.start();
    }

    public void placePipes() {
        int random_y = (int)(pipe_y - pipe_height / 4 - Math.random() * (pipe_height / 2));
        int opening_space = board_height / 4;
        Pipe top_pipe = new Pipe(top_pipe_img);
        top_pipe.y = random_y;
        pipes.add(top_pipe);

        Pipe bottom_pipe = new Pipe(bottom_pipe_img);
        bottom_pipe.y = top_pipe.y + pipe_height + opening_space;
        pipes.add(bottom_pipe);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //System.out.println("draw");

        g.drawImage(background, 0, 0, board_width, board_height, null); // last parameter it's ImageObserver
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if(game_over) {
            g.drawString("Game Over: " + String.valueOf((int) score), 200, 50);
        }
        else {
            g.drawString(String.valueOf((int) score),200, 50);
        }
    }

    public void move() {
        velocity_y += gravity;
        bird.y += velocity_y;
        bird.y = Math.max(bird.y, 0);

        for(int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocity_x;

            if(!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5;
            }
            if(collision(bird, pipe))
                game_over = true;
        }
        if(bird.y > board_height)
            game_over = true;
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!game_over) {
            move();
            repaint();
        }
        if(game_over) {
            place_pipes_timer.stop();
            game_loop.stop();
        }
    }
}
