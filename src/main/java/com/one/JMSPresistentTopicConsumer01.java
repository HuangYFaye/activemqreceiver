package com.one;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by huangyifei on 2018/7/8.
 */
public class JMSPresistentTopicConsumer01 {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://35.185.164.128:61616");

            Connection connection = null;
        try {
            //建立连接
            connection = connectionFactory.createConnection();
            //因为是持久化订阅模式，后面创建订阅式Consumer的时候需要用到，所以需要设置ClientID
            connection.setClientID("LINE-ONE");
            connection.start();
            //创建Session
            Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //创建目的地
            Topic destination = session.createTopic("MyTopic");
            //创建订阅式Consumer
            MessageConsumer messageConsumer = session.createDurableSubscriber(destination,"LINE-ONE");

            //创建监听器
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
                //订阅式Consumer绑定监听器
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
