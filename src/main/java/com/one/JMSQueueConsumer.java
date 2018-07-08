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
            //建立连接
            connection = connectionFactory.createConnection();
            connection.start();
            //创建Session
            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //创建目的地
            Destination destination = session.createQueue("MyQueue");
            //创建Consumer
            MessageConsumer messageConsumer = session.createConsumer(destination);
            //获取信息
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
