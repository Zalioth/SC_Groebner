package util;

public class Time {
	
	long time_start, time_end;
	long final_time;
	
	
	public Time(){
		time_start = time_end = final_time = 0;
	}
	
	public void start(){
		time_start = System.currentTimeMillis();
		final_time = 0;
	}
	
	public void pause(){
		this.final_time += System.currentTimeMillis() - this.time_start;
	}
	
	public void stop(){
		time_end = System.currentTimeMillis();
		this.final_time += this.time_end - this.time_start;
		time_start = time_end = 0;
	}

	
	public long getTime(){
		return this.final_time;
	}
	

}
