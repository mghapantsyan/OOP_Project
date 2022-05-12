package am.aua.Checkers.UI;
import am.aua.Checkers.IO.GameManagement;
import am.aua.Checkers.core.Move;
import am.aua.Checkers.core.Checkers;
import am.aua.Checkers.core.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class MyFrame extends JFrame {
    static final int offsetX = 80;
    static final int offsetY = 140;
    public static final int CELL_DIMENSION = 60;
    static Checkers board;
    static Point selected;
    static JFrame parent;
    JMenu menu;
    JMenuItem i1, i2, i3;
    ImageIcon imageIcon = new ImageIcon("QueenW.png");
    Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");

    private static class Listener implements AWTEventListener {

        public void eventDispatched(AWTEvent event) {

            int i, j;
            if (event.getID() == MouseEvent.MOUSE_CLICKED) {
                int X = MouseInfo.getPointerInfo().getLocation().x - parent.getLocation().x;
                int Y = MouseInfo.getPointerInfo().getLocation().y - parent.getLocation().y;
                System.out.println(new Point(X, Y));
                j = (X - offsetX) / CELL_DIMENSION;
                i = (Y - offsetY) / CELL_DIMENSION;
                if (i >= 0 && i < 8 && j >= 0 && j < 8 && board.getPieceAt(Position.generateFromRankAndFile(i, j)) != null &&
                        board.getTurn() == board.getPieceAt(Position.generateFromRankAndFile(i, j)).getPieceColor()) {
                    if (i > -1 && i < 8 && j > -1 && j < 8) {
                        selected = new Point(j, i);
                        parent.repaint();
                    }
                } else {
                    if (selected != null) {
                        if (Position.generateFromRankAndFile(i, j) != null) {
                            Move move = new Move(Position.generateFromRankAndFile(selected.y, selected.x),
                                    Position.generateFromRankAndFile(i, j));
                            board.performMove(move);
                            selected = null;
                            parent.repaint();
                        }
                    }
                }

            }
        }
    }

    public MyFrame() {

        parent = this;
        board = new Checkers();
        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Game");
        i1 = new JMenuItem("New Game");
        i2 = new JMenuItem("Save Game");
        i3 = new JMenuItem("Load Game");
        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        i1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board = new Checkers();
                repaint();
            }

        });
        i2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameManagement.saveGame(board);
                JOptionPane.showMessageDialog(null, "the game is saved");
            }

        });
        i3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    board = GameManagement.loadGame();
                    repaint();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Problems with loading file");
                }
            }

        });
        mb.setDoubleBuffered(true);
        mb.add(menu);
        parent.setTitle("Checkers");
        parent.setIconImage(icon);
        parent.setJMenuBar(mb);
        parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parent.setSize(640, 670);
        parent.setResizable(false);
        parent.setLocationRelativeTo(null);
        parent.setVisible(true);
        Toolkit.getDefaultToolkit().addAWTEventListener(
                new Listener(), AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Areal", Font.BOLD, 28));
        drawTurn(g2);
        drawCoordinates(g2);
        drawBoard(g2);
        if (!board.isGameOver()) {
            if (selected != null && !board.isEmpty(Position.generateFromRankAndFile(selected.y, selected.x))) {
                drawDestinations(g2);
            }

        }

    }

    private void drawBoard(Graphics2D g2) {
        for (int i = 0; i < Checkers.BOARD_RANKS; i++) {
            for (int j = 0; j < Checkers.BOARD_FILES; j++) {

                if ((i + j) % 2 == 1) {

                    g2.fillRect(offsetX + j * CELL_DIMENSION, offsetY + i * CELL_DIMENSION, CELL_DIMENSION, CELL_DIMENSION);
                    Position temp = Position.generateFromRankAndFile(i, j);
                    if (!board.isEmpty(temp) && board.getPieceAt(temp) != null && board.getPieceAt(temp).getPieceColor() == Checkers.PieceColor.BLACK) {
                        g2.setColor(Color.GRAY);
                    } else if (!board.isEmpty(temp) && board.getPieceAt(temp) != null && board.getPieceAt(temp).getPieceColor() == Checkers.PieceColor.WHITE) {
                        g2.setColor(Color.WHITE);
                    }
                    g2.fillOval((int) (offsetX * 1.08) + j * CELL_DIMENSION, (int) (offsetY * 1.04) + i * CELL_DIMENSION,
                            (int) (CELL_DIMENSION * 0.8), (int) (CELL_DIMENSION * 0.8));
                    g2.setColor(Color.BLACK);
                    if (!board.isEmpty(temp) && board.getPieceAt(temp).isKing()) {
                        g2.drawImage(imageIcon.getImage(), (int) (offsetX * 1.18) + j * CELL_DIMENSION, (int) (offsetY * 1.09) + i * CELL_DIMENSION, CELL_DIMENSION / 2, CELL_DIMENSION / 2, this);
                    }
                }
            }
        }
    }

    private void drawCoordinates(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(offsetX, offsetY, offsetX + 8 * CELL_DIMENSION, offsetY);
        g2.drawLine(offsetX + Checkers.BOARD_RANKS * CELL_DIMENSION, offsetY, offsetX + Checkers.BOARD_RANKS * CELL_DIMENSION, offsetY + Checkers.BOARD_FILES * CELL_DIMENSION);
        g2.drawLine(offsetX + Checkers.BOARD_FILES * CELL_DIMENSION, offsetY + Checkers.BOARD_RANKS * CELL_DIMENSION, offsetX, offsetY + Checkers.BOARD_RANKS * CELL_DIMENSION);
        g2.drawLine(offsetX, offsetY + Checkers.BOARD_RANKS * CELL_DIMENSION, offsetX, offsetY);
        String[] horText = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int h = 0; h < 8; h++) {
            g2.drawString(horText[h], offsetX - 10 + CELL_DIMENSION / 2 + h * CELL_DIMENSION, offsetY - 10);
        }
        String[] verText = {"8", "7", "6", "5", "4", "3", "2", "1"};
        for (int h = 0; h < 8; h++) {
            g2.drawString(verText[h], offsetX - 25, offsetY + 10 + CELL_DIMENSION / 2 + h * CELL_DIMENSION);
        }
    }

    private void drawDestinations(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        Position[] possMoves = board.reachableFrom(Position.generateFromRankAndFile(selected.y, selected.x));
        if (possMoves == null || possMoves.length == 0) {
            g2.setColor(Color.RED);
        }
        g2.drawOval((int) (offsetX * 1.08) + selected.x * CELL_DIMENSION, (int) (offsetY * 1.04) + selected.y * CELL_DIMENSION,
                (int) (CELL_DIMENSION * 0.8), (int) (CELL_DIMENSION * 0.8));

        if (possMoves != null && possMoves.length > 0) {
            for (int t = 0; t < possMoves.length; t++) {
                g2.drawOval((int) (offsetX * 1.08) + possMoves[t].getFile() * CELL_DIMENSION, (int) (offsetY * 1.04) +
                        possMoves[t].getRank() * CELL_DIMENSION, (int) (CELL_DIMENSION * 0.8), (int) (CELL_DIMENSION * 0.8));
            }
        }
        g2.setColor(Color.BLACK);

    }

    private void drawTurn(Graphics2D g2){
        g2.setColor(Color.red);
        if (board.isGameOver()) {
            if (board.getTurn() == Checkers.PieceColor.BLACK) {
                g2.drawString("Game Over! Whites win", offsetX + 70, offsetY - 55);
            } else {
                g2.drawString("Game Over! Black's win", offsetX + 70, offsetY - 55);

            }
        } else {
            g2.drawString(board.getTurn() == Checkers.PieceColor.WHITE ? "Whites' turn!" : "Blacks' turn!",
                    offsetX + 150, offsetY - 55);
        }
    }
}
