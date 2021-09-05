package libraries.generalFunctions;

import java.io.IOException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.MessageProducer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.activemq.ActiveMQConnectionFactory;

public class AMQMessageProducer { // extends HttpServlet {
    //@Override
  //  public void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
    	
    	  public static void createTopic(String Semsymsg) throws ServletException, IOException {
        try {
            //created ConnectionFactory object for creating connection 
           ConnectionFactory factory = new ActiveMQConnectionFactory("root", "dmvn@123", "tcp://192.168.1.39:61613");
           
           System.out.println("new connection Factory created");
          // ConnectionFactory factory = new JmsConnectionFactory("tcp://192.168.1.19:61613");
            
            //Establish the connection
            Connection connection = factory.createConnection();
            connection.start();
            System.out.println("connection started !");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         //   Queue queue = session.createQueue("Test");
            Destination destination = session.createTopic("asa.contravention");
            //Added as a producer
            MessageProducer producer = session.createProducer(destination);
            // Create and send the message
            TextMessage msg = session.createTextMessage();
            msg.setText(Semsymsg);
            producer.send(msg);
            
            session.commit();
            session.close();
            connection.close();
            System.out.println("sent messages using " + factory.getClass());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}