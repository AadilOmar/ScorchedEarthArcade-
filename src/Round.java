
public class Round {

	int angle=45;
	int power=50;
	
	//constructor- Initially, angle is set to 45 degrees and power to 50. When a round is created, new parameters are given and
	//the values are set accordingly
	public Round(int angle,int power){
		this.angle=angle;
		this.power=power;	
	}
	
	//returns the angle
	public int getAngle(){
		return angle;
	}
	
	//returns the power
	public int getPower(){
		return power;
	}
}
