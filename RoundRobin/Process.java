
public class Process {
	public String Pname;
	public int at;
	public int burstTime;
	public int originalBurst;
	public boolean arrived=false;
	Process(String name,int arrival, int burst)
	{
		this.Pname=name;
		this.at=arrival;
		this.burstTime=burst;
		this.originalBurst=burst;
	}

}
