

public class Process {
	public String Pname;
	public int arrivalTime;
	public int BurstTime;
	public int originalBurst;
	public int priority;
	public int finishingTime;
	
	Process(String name,int at, int bt, int prior )
	{
		this.Pname=name;
		this.arrivalTime=at;
		this.BurstTime=bt;
		this.originalBurst=bt;
		this.priority=prior;
	}
	public  void Aging()
	{
		if(this.priority>0)
		{
			priority--;
		}
	}

}
