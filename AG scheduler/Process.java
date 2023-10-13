

public class Process {
	public String Pname;
	public int at;
	public int burstTime;
	public int priority;
	public int remQuantem;
	public int orgQuantem;
	public int finishingTime;
	public int orgBurst;
	public boolean Arrived=false;
	////declare here queue////
	
	Process (String name, int burst,int arrival, int prior,int Quant)
	{
		this.Pname=name;
		this.at=arrival;
		this.burstTime=burst;
		this.priority=prior;
		this.orgBurst=burst;
		this.remQuantem=Quant;
		this.orgQuantem=Quant;
	}
	
	

}
