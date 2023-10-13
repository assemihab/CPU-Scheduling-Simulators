

import java.util.LinkedList;
import java.util.Queue;



public class PreemptivePrior {

	public static void preemptivePriority(Process Parr[],int n,Queue<executed>exec)
	{
		int time =0;
		int completed=0;
		Process running=new Process("Null",10000,10000,10000);
		bigloop:
		while(completed!=n)
		{
			running=getHighestPrior(Parr,n,time);
			while(running.arrivalTime>time)
			{
				time++;
			}
			while(running.BurstTime!=0)
			{
				running=getHighestPrior(Parr,n,time);
				running.BurstTime--;
				time++;
				executed ex=new executed(running,time-1,time);
				exec.add(ex);
				if(running.BurstTime==0)
				{
					running.finishingTime=time;
					completed++;
					continue bigloop;
				}
				aging(Parr,n,time,running);
				
			}

		}
	}
	public static void aging(Process Parr[],int n, int time,Process running)
	{
		int minTime=Integer.MAX_VALUE;
		Process minarrival=new Process("null",1000,1000,1000);
		for(int i=0;i<n;i++)
		{
			if(Parr[i].arrivalTime<=time&& Parr[i].arrivalTime<minTime && Parr[i].BurstTime!=0)
			{
				minTime=Parr[i].arrivalTime;
				minarrival=Parr[i];
			}
		}
		if(minarrival.Pname!=running.Pname)
		{
			if(minarrival.priority>0)
			{
				minarrival.priority--;
			}
		}
		
	}
	public static Process getHighestPrior(Process Parr[],int n,int time)
	{
		int minPriority=Integer.MAX_VALUE;
		int counter=0;
		for(int i=0;i<n;i++)
		{
			if(Parr[i].arrivalTime<=time && Parr[i].priority<minPriority &&Parr[i].BurstTime!=0)
			{
				minPriority=Parr[i].priority;
				counter=i;
			}
		}
		return Parr[counter];
	}
	public static void printWaitingTime(Process Parr[],int n)
	{
		System.out.println("Process"+" \t\t"+"Arrival Time"+" \t\t"+"burstTime"+" \t\t"+"Wait time"+" \t\t"+"turnAroundTime");
		for(int i=0;i<n;i++)
		{
			int waitingTime=Parr[i].finishingTime-Parr[i].arrivalTime-Parr[i].originalBurst;
			int turnAroundTime=Parr[i].finishingTime-Parr[i].arrivalTime;
			System.out.println(Parr[i].Pname+"\t\t\t"+Parr[i].arrivalTime+"\t\t\t"+Parr[i].originalBurst+"\t\t\t"+waitingTime+"\t\t\t"+turnAroundTime);

		}
	}
	public static void main(String[] args) {
		
		int size =4;
		Process[]proclist=new Process[size];
		Queue<executed> exec= new LinkedList<>();
		//String name,int at, int bt, int prior
		proclist[0]=new Process("P1",0,10,7);
		proclist[1]=new Process("P2",2,9,2);
		proclist[2]=new Process("P3",3,10,6);
		proclist[3]=new Process("P4",4,4,4);
		preemptivePriority(proclist,size,exec);
		executed Previous, succ,ex;
		while(!(exec.isEmpty()))
		{
			ex=exec.peek();
			exec.remove();
			Previous=ex;
			succ=exec.peek();
			while(Previous.proc.Pname==succ.proc.Pname)
			{
				Previous.end=succ.end;
				exec.remove();
				if(exec.isEmpty())
					break;
				succ=exec.peek();
			}
			System.out.println("process name: "+Previous.proc.Pname+ ", start time: "+Previous.start+", end: "+ Previous.end);
		}
		printWaitingTime(proclist,size);
		
	}

}
