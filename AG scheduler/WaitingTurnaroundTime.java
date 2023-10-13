

public class WaitingTurnaroundTime {
	int arrivalTime;
	int finishingTime;
	Process proce;
	int turnAroundTime;
	int burstTime;
	int waitingTime;
	WaitingTurnaroundTime(Process proc)
	{
		
		this.proce=proc;
		this.arrivalTime=proce.at;
		this.finishingTime=proce.finishingTime;
		this.burstTime=proce.orgBurst;
		this.turnAroundTime=finishingTime-arrivalTime;
		this.waitingTime=turnAroundTime-burstTime;
	}

}
