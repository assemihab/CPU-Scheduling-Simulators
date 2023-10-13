

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Mainclass {
	public static void AgScheduling(Process Parr[],int n,Stack<Executed> done)
	{
		int time=0;
		int completed =0;
		int quantumC=0;
		boolean FCF=true;
		int processid=0;

		Process running=new Process("null",1000,1000,1000,1000);
		Queue<Process> q= new LinkedList<>();
		processs:
		while(completed!=n)
		{

			if(FCF)
			{
				if(q.isEmpty())
				{
					processid=getFirstJob(Parr,time, n);
					running=Parr[processid];
					while(time<running.at)
					{
						time++;
					}
					isArrived(q,Parr,n,time);
				}
				running =q.peek();
				q.remove();
				FCF=false;
			}
			quantumC=(int) Math.ceil(running.remQuantem*0.25);
			while(quantumC!=0)
			{
				isArrived(q,Parr,n,time);
				
				if(running.burstTime!=0)
				{
					quantumC--;
					time++;
					process(running,done,time);
				}
				else
				{
					done(running,time);
					FCF=true;
					completed++;
					continue processs;
				}
			}
			if(running.burstTime==0)
			{
				done(running,time);
				FCF=true;
				completed++;
				continue processs;
			}
			if(running.remQuantem==0)
			{
				running.remQuantem=running.orgQuantem=running.orgQuantem+2;
				System.out.println("process "+ running.Pname+" has this updated quantum time3 "+running.remQuantem);
				q.add(running);
				continue processs;
			}
			
			Executed exObj;
			exObj=done.peek();
			isArrived(q,Parr,n,time);
			int parrindex=getHighestPrior(Parr,time,n);
			running=Parr[parrindex];
			if(running.Pname==exObj.proc.Pname)
			{
				quantumC=(int)Math.ceil(running.remQuantem*0.25);
				while(quantumC!=0)
				{
					if(running.burstTime!=0)
					{
						time++;
						quantumC--;
						process(running,done,time);
						
					}
					else
					{
						done(running,time);
						FCF=true;
						completed++;
						continue processs;
						
						
					}
				}
				if(running.burstTime==0)
				{
					done(running,time);
					FCF=true;
					completed++;
					continue processs;
				}
				if(running.remQuantem==0)
				{
					running.remQuantem=running.orgQuantem=running.orgQuantem+2;
					System.out.println("process "+ running.Pname+" has this updated quantum time5 "+running.remQuantem);
					FCF=true;
					q.add(running);
					continue processs;
				}
			}
			else
			{
				removeFromQue(q,running);
				exObj.proc.orgQuantem=exObj.proc.remQuantem=(int)Math.ceil(0.5*exObj.proc.remQuantem)+exObj.proc.orgQuantem;
				System.out.println("process "+ exObj.proc.Pname+" has this updated quantum time6 "+exObj.proc.remQuantem);
				q.add(exObj.proc);
				continue processs;
			}
			while(running.remQuantem!=0)
			{
				isArrived(q,Parr,n,time);
				exObj=done.peek();
				running=Parr[getShortestJob(Parr,time,n)];
				if(running.Pname==exObj.proc.Pname)
				{
					if(running.burstTime!=0)
					{
						time++;
						process(running,done,time);
						if(running.burstTime==0)
						{
							done(running,time);
							FCF=true;
							completed++;
							continue processs;
						}
					}

					
				}
				else
				{
					removeFromQue(q,running);
					exObj.proc.orgQuantem=exObj.proc.remQuantem=exObj.proc.remQuantem+exObj.proc.orgQuantem;
					System.out.println("process "+ exObj.proc.Pname+" has this updated quantum time8 "+exObj.proc.remQuantem);
					q.add(exObj.proc);
					continue processs;
				}
				
			}

			if(running.remQuantem==0)
			{
				running.remQuantem=running.orgQuantem=running.orgQuantem+2;
				System.out.println("process "+ running.Pname+" has this updated quantum time9 "+running.remQuantem);
				q.add(running);
				FCF=true;
				continue processs;
			}
				
		}
	}
	public static void done(Process running,int time)
	{
		running.orgQuantem=running.remQuantem=0;
		System.out.println("process "+ running.Pname+" has this updated quantum time2 "+running.remQuantem);
		running.finishingTime=time;
	}
	public static void process(Process running,Stack<Executed> done,int time)
	{
		running.remQuantem--;
		running.burstTime--;
		Executed exObjj=new Executed();
		exObjj.proc=running;
		exObjj.start=time-1;
		exObjj.end=time;
		done.push(exObjj);
	}
	public static void removeFromQue(Queue<Process> que,Process p)
	{
		Queue<Process> store=new LinkedList<>();
		while(!(que.isEmpty()))
				{
					if(que.peek().Pname==p.Pname)
						que.remove();
					else
					{
						store.add(que.peek());
						que.remove();
					}
				}
		while(!(store.isEmpty()))
		{
			que.add(store.peek());
			store.remove();
		}
	}
	public static void isArrived(Queue<Process> que,Process parr[],int n,int time)
	{
		for (int i=0;i<n;i++)
		{
			if(parr[i].at<=time&& !(parr[i].Arrived) )
			{
				parr[i].Arrived=true;
				que.add(parr[i]);
			}
		}
	}
	public static int getHighestPrior(Process parr[],int time, int n)
	{
		int priorArray[]=new int[n];
		int minPrior=Integer.MAX_VALUE;
		int counter=0;
		
		for(int i=0;i<n;i++)
		{
			priorArray[i]=Integer.MAX_VALUE;
			if(parr[i].burstTime!=0 && parr[i].at<=time)
			{
				priorArray[i]=parr[i].priority;
			}
		}
		for(int i=0;i<n;i++)
		{
			if(minPrior>priorArray[i])
			{
				minPrior=priorArray[i];
				counter =i;
			}
		}
		return counter;
	}
	public static int getFirstJob(Process parr[],int time, int n)
	{
		int arrayOfjobs[]=new int [n];
		int minAT=Integer.MAX_VALUE;
		for(int i=0;i<n;i++)
		{
			arrayOfjobs[i]=Integer.MAX_VALUE;
			if(parr[i].burstTime!=0)
			{
				arrayOfjobs[i]=parr[i].at;
			}
		}
		int counter=0;
		for(int i=0;i<n;i++)
		{
			if(minAT>arrayOfjobs[i])
			{
				minAT=arrayOfjobs[i];
				counter=i;
			}
		}
		return counter;
	}
	public static int getShortestJob(Process parr[],int time,int n)
	{
		int shortest[]=new int[n];
		
		for(int i=0;i<n;i++)
		{
			if(parr[i].at<=time&&parr[i].burstTime!=0)
				shortest[i]=parr[i].burstTime;
			else
				shortest[i]=Integer.MAX_VALUE;
				
		}
		int counter =0;
		int minimum=Integer.MAX_VALUE;
		for(int i=0;i<n;i++)
		{
			if(minimum>shortest[i])
			{
				minimum=shortest[i];
				counter=i;
			}
		}
		return counter;
	}
	public static void WaitTurnaroundT(WaitingTurnaroundTime Warr[],int n)
	{
		int totalWaiting=0,TotalTurn=0;
		float avgWaiting=0,AvgTurn=0;
		for(int i=0;i<n;i++)
		{
			System.out.println("process name: " +Warr[i].proce.Pname+" waiting time: "+ Warr[i].waitingTime );
			totalWaiting+=Warr[i].waitingTime;
		}
		System.out.println();
		for(int i=0;i<n;i++)
		{
			System.out.println("process name: " +Warr[i].proce.Pname+" turnAround time: "+ Warr[i].turnAroundTime );
			TotalTurn+=Warr[i].turnAroundTime;
		}
		System.out.println();
		avgWaiting=(float)totalWaiting/n;
		AvgTurn=(float)TotalTurn/n;
		System.out.println("Average waiting time is: "+avgWaiting);
		System.out.println("Average Turnaround Time is: "+ AvgTurn);
		
		
	}

	public static void main(String[] args) {
		int Size=4;
		Process[] procList=new Process[Size];
		
		Stack<Executed> execOrder=new Stack<Executed>();
		procList[0]=new Process("P1",17,0,4,7);
		procList[1]=new Process("P2",6,2,7,9);
		procList[2]=new Process("P3",11,5,3,4);
		procList[3]=new Process("p4",4,15,6,6);
//		procList[0]=new Process("P1",10,0,3,7);
//		procList[1]=new Process("P2",20,4,5,9);
//		procList[2]=new Process("P3",5,7,1,10);
//		procList[3]=new Process("p4",4,15,6,6);
		AgScheduling(procList,Size,execOrder);
		int start;
		int end;
		Executed Previous, succ,ex;
		System.out.println();
		Stack<Executed>rightExec=new Stack<Executed>();
		while(!(execOrder.isEmpty()))
		{
			ex=execOrder.peek();
			rightExec.push(ex);
			execOrder.pop();
		
		}
		while(!(rightExec.isEmpty()))
		{
			ex=rightExec.peek();
			rightExec.pop();
			Previous=ex;
			succ=rightExec.peek();
			while(Previous.proc.Pname==succ.proc.Pname)
			{
				Previous.end=succ.end;
				rightExec.pop();
				if(rightExec.isEmpty())
					break;
				succ=rightExec.peek();
			}
			System.out.println("process name: "+Previous.proc.Pname+ ", start time: "+Previous.start+", end: "+ Previous.end);
		}
		System.out.println();
		WaitingTurnaroundTime[]waitTurnlist=new WaitingTurnaroundTime[Size];
		for(int i=0;i<Size;i++)
		{
			waitTurnlist[i]=new WaitingTurnaroundTime(procList[i]);
		}
		WaitTurnaroundT(waitTurnlist,Size);

	}
}
