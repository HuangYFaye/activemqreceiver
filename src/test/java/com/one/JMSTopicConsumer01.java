package com.one;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by huangyifei on 2018/7/8.
 */
public class JMSTopicConsumer01 {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://35.185.164.128:61616");

            Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("MyTopic");

            MessageConsumer messageConsumer = session.createConsumer(destination);


            MessageListener messageListener = new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        System.out.println(((TextMessage)message).getText());
                        System.out.println("111");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            };

            while (true){
                messageConsumer.setMessageListener(messageListener);
                session.commit();
            }



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
