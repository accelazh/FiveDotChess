package fiveDotChess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class FiveDotChess extends JPanel
{
	private static final int DEFAULT=-1;
	private static final int PLAYER_TURN=0;
	private static final int OPPONENT_TURN=1;
	private static final int WIN_OR_DIE=2;
	public static final int PLAYER_WIN=3;
	public static final int OPPONENT_WIN=4;
	public static final int DRAW=8;
	
	private static final int PLAYER=5;
	private static final int OPPONENT=6;
	private static final int BLANK=7;
	
	private int currentState;
	private int winner;
	
	//GUI designs
	private JPanel fileBar=new JPanel();
	
	private static final int EIGHT=19;
	private JPanel contentPanel=new JPanel();
	private Cell[][] cells=new Cell[EIGHT][EIGHT];
	
	
	private JPanel stateBar=new JPanel();
	private JLabel whosRound=new JLabel();
	
	private JCheckBox singlePlayer=new JCheckBox();
	public FiveDotChess()
	{
		//game initialization
		currentState=PLAYER_TURN;
	    winner=DEFAULT;	
		//GUI initialization
	    
		setLayout(new BorderLayout());
		fileBar.setBorder(new LineBorder(Color.BLUE,1));
		add(fileBar,BorderLayout.NORTH);
		fileBar.setPreferredSize(new Dimension(160,20));
		
		contentPanel.setLayout(new GridLayout(EIGHT,EIGHT,0,0));
		contentPanel.setBorder(new LineBorder(Color.BLUE,1));
		for(int i=0;i<EIGHT;i++)
		{
			for(int j=0;j<EIGHT;j++)
			{	
				cells[i][j]=new Cell();
			    contentPanel.add(cells[i][j]);
			}
		}
		add(contentPanel,BorderLayout.CENTER);
		
		stateBar.add(whosRound);
		whosRound.setFont(new Font("Times",Font.BOLD,12));
		whosRound.setForeground(Color.RED);
		stateBar.setBorder(new LineBorder(Color.BLUE,1));
		add(stateBar,BorderLayout.SOUTH );
		stateBar.setPreferredSize(new Dimension(160,30));
		
		singlePlayer.setText("SP");
		singlePlayer.setToolTipText("single player");
		stateBar.add(singlePlayer);
		//start game
		switchToPlayerTurn();
	}
	
	private void switchToPlayerTurn()
	{
		currentState=PLAYER_TURN;
		whosRound.setText("Player's Round");
		
	}
	private void switchToOpponentTurn()
	{
		currentState=OPPONENT_TURN;
		whosRound.setText("Opponent's Round");
		if(singlePlayer.isSelected())
		{
			AI.AIStep(cells);
			
		}
	}
	private void switchToWinOrDie()
	{
		currentState=WIN_OR_DIE;
		if(isWinner(PLAYER))
		{
			whosRound.setText("Player WIN");
		    winner=PLAYER;
		}
		else
		{
			whosRound.setText("Opponent WIN");
			winner=OPPONENT;
		}
		
		
			
	}
	
	private void switchToDraw()
	{
		currentState=DRAW;
		winner=DEFAULT;
		whosRound.setText("Draw, Damn!");
	}
	private boolean isAnyBlankLeft()
	{
		for(int i=0;i<EIGHT;i++)
		{
			for(int j=0;j<EIGHT;j++)
			{
				if(BLANK==cells[i][j].state)
			   	{
			    	return true;
			    }
			}
			
		}
		return false;
	}
	
	private boolean isWinner(int mark)
	{
		boolean flag1=false;
		
		
		boolean flag2=false;
		
		
		boolean flag3=false;
		
		boolean flag4=false;
		
		
		
		return flag1||flag2||flag3||flag4;
			
	}
	
	private class Cell extends JPanel implements MouseListener
	{
		private int state;
		
		public Cell()
		{
			state=BLANK;
			setBackground(Color.GRAY);
			addMouseListener(this);
		}
		
		private void setState(int state)
		{
			this.state=state;
			repaint();
		}
		
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			if(state==BLANK)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, getWidth()/2-1,2 , getHeight());
			}
			if(state==PLAYER)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, getWidth()/2-1,2 , getHeight());
				g.setColor(Color.BLACK);
			    g.fillOval((int)(getWidth()/8.0),(int)(getHeight()/8.0), (int)(getWidth()*3/4.0),(int)(getHeight()*3/4.0));
			}
			if(state==OPPONENT)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, getWidth()/2-1,2 , getHeight());
				g.setColor(Color.WHITE);
				g.fillOval((int)(getWidth()/8.0),(int)(getHeight()/8.0), (int)(getWidth()*3/4.0),(int)(getHeight()*3/4.0));
			}
			
		}

		private void mouseClickCell()
		{
			if(BLANK==state)
			{
				if(PLAYER_TURN==currentState)
				{
					setState(PLAYER);
					if(isWinner(PLAYER))
					{
						switchToWinOrDie();
					}
					else
					{
						if(isAnyBlankLeft())
						{
						    switchToOpponentTurn();
						}
						else
						{
							switchToDraw();
						}
					}
				}
				else
				{
				    if(OPPONENT_TURN==currentState)
				    {
					    setState(OPPONENT);
					    if(isWinner(OPPONENT))
					    {
						    switchToWinOrDie();
					    }
					    else
					    {
					    	if(isAnyBlankLeft())
						    {
					    		switchToPlayerTurn();
						    }
					    	else
					    	{
					    		switchToDraw();
					    	}
					    }
				    }
				}
			}
			

		}
		
	
		public void mouseClicked(MouseEvent arg0) 
		{
			if((OPPONENT==currentState)&&(singlePlayer.isSelected()))
			{
				;
			}
			else
			{
				mouseClickCell();
			}
		}

		public Dimension getPreferredSize()
		{
			return new Dimension(26,26);
		}
	
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}


		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}

	private static class AI 
	{
		
		//计算一个cell串的对应分数，b和c表示cell的序号
		private static long calculateSubScore(Cell[] cells,int b,int c)
		{
			//计算三种状态的格子各有多少个
			int nPlayer=0;
			int nOpponent=0;
			int nBlank=0;
			
			if(b>8||b<0)
			{
				return 0;
			}
			if(c>8||c<0)
			{
				return 0;
			}
			
			if(PLAYER==cells[b].state)
			{
				nPlayer++;
			}
			if(OPPONENT==cells[b].state)
			{
				nOpponent++;
			}
			if(BLANK==cells[b].state)
			{
				nBlank++;
			}
			
			if(PLAYER==cells[c].state)
			{
				nPlayer++;
			}
			if(OPPONENT==cells[c].state)
			{
				nOpponent++;
			}
			if(BLANK==cells[c].state)
			{
				nBlank++;
			}
			
			//计算分数
			long score=0;
			if(0==nPlayer&&0==nOpponent&&2==nBlank)
			{
				score+=200;
			}
			if(1==nPlayer&&0==nOpponent&&1==nBlank)
			{ 
				score+=100;
			}
			if(0==nPlayer&&1==nOpponent&&1==nBlank)
			{
				score+=400;
			}
			if(1==nPlayer&&1==nOpponent&&0==nBlank)
			{
				score+=0;
			}
			if(2==nPlayer&&0==nOpponent&&0==nBlank)
			{
				score+=1000;
			}
			if(0==nPlayer&&2==nOpponent&&0==nBlank)
			{
				score+=10000;
			}
			
			return score;
		}
		
		//计算一个blank格子能得多少分
		private static long calculateScore(Cell[] cells,int i)
		{
			//计算横行
			long score1=0;
			if(0==i%3)
			{
			    score1+=calculateSubScore(cells,i+1,i+2);
			}
			if(1==i%3)
			{
			    score1+=calculateSubScore(cells,i+1,i-1);
			}
			if(2==i%3)
			{
				score1+=calculateSubScore(cells,i-1,i-2);
			}
		
		    //计算纵行
			long score2=0;
			if(0==i/3)
			{
				score2+=calculateSubScore(cells,i+3,i+6);
			}
			if(1==i/3)
			{
				score2+=calculateSubScore(cells,i-3,i+3);
			}
			if(2==i/3)
			{
				score2+=calculateSubScore(cells,i-6,i-3);
			}
		
			//计算斜行
			long score3=0;
			if(0==i)
			{
				score3+=calculateSubScore(cells,i+4,i+8);
			}
			if(4==i)
			{
				score3+=calculateSubScore(cells,i-4,i+4);
			}
			if(8==i)
			{
				score3+=calculateSubScore(cells,i-4,i-8);
			}
			
			long score4=0;
			if(2==i)
			{
				score4+=calculateSubScore(cells,i+2,i+4);
			}
			if(4==i)
			{
				score4+=calculateSubScore(cells,i-2,i+2);
			}
			if(6==i)
			{
				score4+=calculateSubScore(cells,i-4,i-2);
			}
			
			
			return (long)score1+(long)score2+(long)score3+(long)score4;
		
		}
		
		//找出哪个格子分数最高，返回序号
		private static Point findMaxValuePoint(Cell[][] cells)
		{
			int indexOfMax_i=0;
			int indexOfMax_j=0;
			
			for(int i=0;i<EIGHT;i++)
			{
				for(int j=0;j<EIGHT;j++)
				{
					if(BLANK==cells[i][j].state)
					{
						indexOfMax_i=i;
						indexOfMax_j=j;
					}
					
				}
			}
			
			
			
		    
		    return new Point(indexOfMax_i,indexOfMax_j);
		}
		
		
		private static void AIStep(Cell[][] cells)
		{
			Point indexOfMaxPoint=findMaxValuePoint(cells);
			cells[indexOfMaxPoint.x][indexOfMaxPoint.y].mouseClickCell();
		}
	}
	
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.getContentPane().add(new FiveDotChess());
		
		frame.setTitle("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.pack();
		frame.setVisible(true);
	}


}
