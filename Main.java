import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class DinosaurGame extends JFrame implements KeyListener {
    private int maxX = 40;
    private int maxY = 10;
    private int playerX = 10;

    private int piso = 10;
    private int playerY = maxY - 3;
    private int jump = 0;
    private int obstacleX = maxX;
    private int score = 0;
    private Timer timer;
    private JLabel scoreLabel;

    public DinosaurGame() {
        setTitle("Dinosaur Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        addKeyListener(this);

        // Crear JLabel para mostrar el puntaje
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(10, 5, 100, 20);
        add(scoreLabel);

        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    private void update() {
        obstacleX--;

        if (playerX == obstacleX && playerY == maxY - 3) {
            timer.stop();
            int choice = JOptionPane.showConfirmDialog(this, "¡Has perdido!\nTu puntaje es: " + score + "\n¿Quieres jugar de nuevo?", "Replay", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                restart();
            } else {
                System.exit(0);
            }
        }

        if (obstacleX == 0) {
            score++;
            obstacleX = maxX;
            // Actualizar el texto del JLabel con el nuevo puntaje
            scoreLabel.setText("Score: " + score);
        }

        if (jump != 0) {
            playerY -= 2;
            if (playerY <= 0) {
                jump = 0;
                playerY = maxY - 3;
            }
        }
    }

    private void restart() {
        playerX = 10;
        playerY = maxY - 3;
        jump = 0;
        obstacleX = maxX;
        score = 0;
        // Reiniciar el texto del JLabel
        scoreLabel.setText("Score: " + score);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < maxX; i++) {
            g.drawString("_", i * 20, (maxY - 3) * 20);
        }

        g.drawString("&", playerX * 10, playerY * 20);
        g.drawString("X", obstacleX * 10, (maxY - 3) * 20);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && jump == 0) {
            jump = 2;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DinosaurGame().setVisible(true);
            }
        });
    }
}
