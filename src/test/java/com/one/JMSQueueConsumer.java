package com.one;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by huangyifei on 2018/7/8.
 */
public class JMSQueueConsumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://35.185.164.128:61616");

            Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("MyQueue");

            MessageConsumer messageConsumer = session.createConsumer(destination);

            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            System.out.println(textMessage.getText());

            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection !=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
