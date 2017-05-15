package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jade.core.AID;
import jade.core.Profile;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.gui.AgentTree.SuperContainer;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Properties;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import jade.wrapper.gateway.JadeGateway;

/**
 * Servlet implementation class AddNumbers
 */
@WebServlet("/AddNumbers")
public class AddNumbers extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNumbers() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException{
    	Properties pp = new Properties();
    	pp.setProperty(Profile.MAIN_HOST, "localhost");
    	pp.setProperty(Profile.MAIN_PORT, "2000");
    	JadeGateway.init(null, pp);
    	//JadeGateway.init("agents.MyGateWayAgent", null);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String as=request.getParameter("a");
		int a=Integer.parseInt(as);
		
		String bs=request.getParameter("b");
		int b=Integer.parseInt(bs);
		
		Addition myobj=new Addition();
		int result=myobj.add(a, b);
		/*
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("<h1>Addition Result</h1>");
		out.print("The result is : <b>"+result+"</b>");*/
		ProcessBehaviour behaviour = new ProcessBehaviour("The result is "+ result);
		try {
			JadeGateway.execute(behaviour);
		} catch (StaleProxyException e) {
			e.printStackTrace();
		} catch (ControllerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		request.setAttribute("result", behaviour.getAnswer());
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public class SendBehaviour extends OneShotBehaviour{
		String content;
		SendBehaviour(String content){
			this.content = content;
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			final ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			AID receiver = new AID("Reception", false);
			msg.addReceiver(receiver);
			msg.setContent(content);
			myAgent.send(msg);
		}
		
	}
	
	private class ProcessBehaviour extends Behaviour {
		private boolean stop = false;
		int step = 0;
		String content;
		String convId;
		String answer;

		public ProcessBehaviour(String content) {
			super();
			this.content = content;
			convId = String.valueOf(System.currentTimeMillis());
		}


		public String getAnswer() {
			return answer;
		}

		@Override
		public void action() {
			switch (step) {
			case 0:
				/*String perfs = (String) params.get(Constants.PERFORMATIVE);
				String agent_id = (String) params.get(Constants.RECEIVER_NAME);
				if (perfs != null && agent_id != null) {
					int perf = Constants.PERFORMATIVES.get(perfs);
					AID aid = new AID(agent_id,AID.ISLOCALNAME);
					ACLMessage message = new ACLMessage(perf);
					message.addReceiver(aid);
					message.setContent((String) params.get(Constants.CONTENT));
					message.setConversationId(convId);
					myAgent.send(message);
					System.out.println("ProcessBehaviour --> Message sent to: " + agent_id);
					step = 1;
				} else {
					stopProcess(Status.CLIENT_ERROR_BAD_REQUEST, "No performative or no aid");
				}*/
				final ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				AID receiver = new AID("Reception", false);
				msg.addReceiver(receiver);
				msg.setContent(content);
				msg.setConversationId(convId);
				myAgent.send(msg);
				step = 1;
				break;
			case 1:
				MessageTemplate mt = MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.INFORM),
						MessageTemplate.MatchConversationId(convId));
				ACLMessage answer = myAgent.receive(mt);
				if (answer != null) {
					stopProcess(convId + " - " + answer.getContent());
				} else
					block();
				break;
			}
		}

		private void stopProcess(String ans) {
			answer = ans;
			System.out.println(answer);
			stop = true;
		}

		@Override
		public boolean done() {
			return stop;
		}
	}
}
