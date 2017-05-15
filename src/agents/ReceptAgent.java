package agents;

import java.util.Scanner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceptAgent extends Agent {
	
	int conversationId = 0;
	
	protected void setup(){
		System.out.println(this.getAID());
		this.addBehaviour(new ReceptBahaviour());
	}

	class ReceptBahaviour extends Behaviour {

		@Override
		public void action() {		
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM); 
			ACLMessage message = receive(mt);
			if(message != null){
				String s = message.getContent();
				System.out.println(s);
			}
		}

		@Override
		public boolean done() {
			return false;
		}
	}
	
}
