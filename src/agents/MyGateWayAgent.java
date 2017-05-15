package agents;

import agents.ReceptAgent.ReceptBahaviour;
import jade.core.Agent;
import jade.wrapper.gateway.GatewayAgent;
import jade.wrapper.gateway.GatewayBehaviour;
import jade.wrapper.gateway.JadeGateway;

public class MyGateWayAgent extends GatewayAgent{
	protected void setup(){
		System.out.println(this.getAID());
		this.addBehaviour(new MyGatewayBehaviour());
	}
	public class MyGatewayBehaviour extends GatewayBehaviour{

		@Override
		protected void processCommand(Object arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
