import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class MyFrame extends JFrame {
    static final int offsetX = 100;
    static final int offsetY = 160;
    public static final int cellDemention = 60;
    static Checkers board ;
    static Point  selected;
    static JFrame parent;
    JMenu menu;
    JMenuItem i1,i2,i3;
    private static class Listener implements AWTEventListener {

        public void eventDispatched(AWTEvent event) {

            int i,j;
            if(event.getID() == MouseEvent.MOUSE_CLICKED ) {
                int X = MouseInfo.getPointerInfo().getLocation().x - parent.getLocation().x;
                int Y = MouseInfo.getPointerInfo().getLocation().y - parent.getLocation().y;
                System.out.println(new Point(X, Y));
                j = (X - offsetX) / cellDemention;
                i = (Y - offsetY) / cellDemention;
                if (!board.isGameOver()){
                    if (i >= 0 && i < 8 && j >= 0 && j < 8 && board.getPieceAt(Position.generateFromRankAndFile(i, j)) != null &&
                            board.getTurn() == board.getPieceAt(Position.generateFromRankAndFile(i, j)).getPieceColor()) {
                        if (i > -1 && i < 8 && j > -1 && j < 8) {
                            selected = new Point(j, i);
                            parent.repaint();
                        }
                    } else {
                        if (selected != null && !board.isGameOver()) {
                            Move move = new Move(Position.generateFromRankAndFile(selected.y, selected.x),
                                    Position.generateFromRankAndFile(i, j));
                            board.performMove(move);

//                        if(board.isGameOver()){
//
//                        }
                            selected = null;
                            parent.repaint();
                        }
                    }
            }
            }
        }
    }
    MyFrame(){
        parent = this;
        board = new Checkers();
        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Menu");
        i1 = new JMenuItem("New Game");
        i2 = new JMenuItem("Save Game");
        i3 = new JMenuItem("Load Game");
        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        i1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                JFileChooser chooser = new JFileChooser();
//                chooser.showOpenDialog(null);
                board = new Checkers();
                repaint();
            }

        });
        i2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                JFileChooser chooser = new JFileChooser();
//                chooser.showOpenDialog(null);
                GameManagment.saveGame(board);
            }

        });
        i3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                JFileChooser chooser = new JFileChooser();
//                chooser.showOpenDialog(null);
               board = GameManagment.loadGame();
               repaint();
            }

        });

        mb.add(menu);
        this.setJMenuBar(mb);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200,900);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        Toolkit.getDefaultToolkit().addAWTEventListener(
                new Listener(), AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Sylfaen", Font.BOLD, 28));
        g2.setColor(Color.red);
        if (board.isGameOver()) {
            if(board.getTurn() == Checkers.PieceColor.BLACK) {
                g2.drawString("Game Over! Whites win", offsetX, offsetY - 55);
            }else{
                g2.drawString("Game Over! Black's win", offsetX, offsetY - 55);

            }
        } else{
        g2.drawString(board.getTurn() == Checkers.PieceColor.WHITE ? "Whites' turn!" : "Blacks' turn!",
                offsetX, offsetY - 55);
    }
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(offsetX,offsetY,offsetX + 8 * cellDemention,offsetY);
        g2.drawLine(offsetX + 8 * cellDemention,offsetY,offsetX + 8 * cellDemention,offsetY + 8 * cellDemention);
        g2.drawLine(offsetX + 8 * cellDemention,offsetY + 8 * cellDemention,offsetX,offsetY + 8 * cellDemention);
        g2.drawLine(offsetX,offsetY + 8 * cellDemention,offsetX,offsetY);
        String[] horText = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for(int h=0;h<8;h++){
            g2.drawString(horText[h],offsetX-10+cellDemention/2+h*cellDemention, offsetY-10);
        }
        String[] verText = {"8", "7", "6", "5", "4", "3", "2", "1"};
        for(int h=0;h<8;h++){
            g2.drawString(verText[h],offsetX-25, offsetY+10+cellDemention/2 + h*cellDemention);
        }

        for(int i = 0;i<8;i++){
            for (int j = 0; j<8;j++){

                if ((i+j)%2==1){

                    g2.fillRect(offsetX +j*cellDemention,offsetY +i*cellDemention,cellDemention,cellDemention);
                    Position temp = Position.generateFromRankAndFile(i,j);
                    if(!board.isEmpty(temp) && board.getPieceAt(temp)!=null && board.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.BLACK) {
                        g2.setColor(Color.GRAY);
                    } else if (!board.isEmpty(temp) && board.getPieceAt(temp)!=null && board.getPieceAt(temp).getPieceColor()== Checkers.PieceColor.WHITE) {
                        g2.setColor(Color.WHITE);
                    }
                    g2.fillOval((int)(offsetX*1.06) +j*cellDemention,(int)(offsetY*1.04) +i*cellDemention,
                            (int)(cellDemention*0.8),(int)(cellDemention*0.8));
                    g2.setColor(Color.BLACK);
                    if(!board.isEmpty(temp) && board.getPieceAt(temp).isKing()) {
                        g2.drawString("\u265A",(int)(offsetX*1.15) +j*cellDemention,(int)(offsetY*1.2) +i*cellDemention);
                    }
                }
            }
        }
        if(selected != null && !board.isEmpty(Position.generateFromRankAndFile(selected.y,selected.x))){
            g2.setColor(Color.GREEN);
            Position[] possMoves = board.reachableFrom(Position.generateFromRankAndFile(selected.y,selected.x));
            if(possMoves==null || possMoves.length==0){
                g2.setColor(Color.RED);
            }
            g2.drawOval((int)(offsetX*1.06) +selected.x*cellDemention,(int)(offsetY*1.04) +selected.y*cellDemention,
                    (int)(cellDemention*0.8),(int)(cellDemention*0.8));
            //Position[] possMoves = board.reachableFrom(Position.generateFromRankAndFile(selected.y,selected.x));
            if(possMoves!=null && possMoves.length>0){
                for(int t = 0;t<possMoves.length;t++){
                    g2.drawOval((int)(offsetX*1.06) +possMoves[t].getFile()*cellDemention,(int)(offsetY*1.04) +
                            possMoves[t].getRank()*cellDemention, (int)(cellDemention*0.8),(int)(cellDemention*0.8));
                }
            }
            g2.setColor(Color.BLACK);
        }

    }


}
