import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Roundrobin {
	public static void roundRobin(Process Parr[],int n,Queue<Execution>execq,int quantum,int cs)
	{
		int completed=0;
		int time=0;
		int remquantum=quantum;
		int orgquantum=quantum;
		Queue<Process> que=new LinkedList<>();
		
		Process Running=new Process("null",1000,1000);
		bigLoop:
		while(completed!=n)
		{
			remquantum=orgquantum;
			if(que.isEmpty())
			{
				while(!(isArrived(que,Parr,time,n)))
				{
					time++;
				}
			}
			Running=getFromQueue(que);
			Execution obj=new Execution();
			Execution obj2=new Execution();
			obj.Pname=Running.Pname;
			obj.start=time;
			while(Running.burstTime!=0&& remquantum!=0)
			{
				time++;
				isArrived(que,Parr,time,n);
				Running.burstTime--;
				remquantum--;
				
			}
			obj.end=time;
			execq.add(obj);
			int contextSwitch=cs;
			obj2.Pname="CS";
			obj2.start=time;
			time=time+contextSwitch;
			obj2.end=time;
			execq.add(obj2);
			
			if(Running.burstTime==0)
			{
				completed++;
				continue bigLoop;
			}
			isArrived(que,Parr,time,n);
			que.add(Running);
			
		}
	}
	
	public static Process getFromQueue(Queue<Process> q)
	{
		Process running=q.peek();
		q.remove();
		return running;
	}
	public static boolean isArrived(Queue<Process>q,Process parr[],int time,int n)
	{
		
		for(int i=0;i<n;i++)
		{
			if(parr[i].at<=time && parr[i].burstTime!=0&&!(parr[i].arrived))
			{
				q.add(parr[i]);
				parr[i].arrived=true;
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		int size=6,context_switch=0,quantamm=4;
		String store[]=new String[size];
		int waiting[]=new int[size];
		int Arrival[]=new int[size];
		int Turn_around[]=new int[size];
		String names[]=new String[size];
		Queue<Execution> executionOrder=new LinkedList<>();
		
		Process[] proclist=new Process[size];
		//String name,int arrival, int burst
		proclist[0]=new Process("P1",0,5);
		proclist[1]=new Process("P2",1,6);
		proclist[2]=new Process("P3",2,3);
		proclist[3]=new Process("P4",3,1);
		proclist[4]=new Process("P5",4,5);
		proclist[5]=new Process("P6",6,4);
		for(int i=0;i<size;i++)
		{
			store[i]=proclist[i].Pname; //array has the process names
			waiting[i]=-1;
			Arrival[i]=proclist[i].at;
			names[i]=proclist[i].Pname;
		}
		Execution obj=new Execution();
		Queue<Execution> executionOrder2=new LinkedList<>();
		
		//Process Parr[],int n,Queue<Execution>execq,int quantum,int cs
		roundRobin(proclist,size,executionOrder,quantamm,context_switch);
		//execution order queue has the output
		while(!(executionOrder.isEmpty()))
		{
			
			obj=executionOrder.peek();
			if(obj.Pname!="CS")
				{executionOrder2.add(obj);}
			
			
			//System.out.println("Process "+name+" started at: "+start+" ended at: "+end);
			executionOrder.remove();
			
		}
		
		
		
		while(!(executionOrder2.isEmpty()))
		{
			obj=executionOrder2.peek();
			for(int i=0;i<size;i++)
			{
				if(obj.Pname==names[i] &&waiting[i]==-1)
				{
					waiting[i]=obj.start-Arrival[i];
				}
			}
			
			
			
			
			
			
			int  start=obj.start,end=obj.end; 
			String name=obj.Pname;
			System.out.println("Process "+name+" started at: "+start+" ended at: "+end );
			executionOrder2.remove();
			
		}
		
		
		
		for(int i=0;i<size;i++)
		{
			Turn_around[i]=waiting[i]+Arrival[i];
		}
		System.out.println("Processes " +" Burst time " + " Waiting time " + " Turn around time"); 
		   for (int i = 0; i < size; i++) {
			   
			   System.out.println(" "+names[i]+"\t\t" +Arrival[i]+"\t\t" +waiting[i]+"\t\t"+Turn_around[i]);
				
		   
		   }
		

	}

}
