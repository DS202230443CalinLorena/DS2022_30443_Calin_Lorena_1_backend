package com.example.assignment1backend.service;

import com.example.assignment1backend.chat.MessageHandler;
import com.thetransactioncompany.jsonrpc2.*;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private SimpMessagingTemplate simpMessagingTemplate;
    private Dispatcher dispatcher;
    private JSONRPC2Parser jsonrpc2Parser;
    private MessageHandler messageHandler;

    @Autowired
    public ChatService(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.dispatcher = new Dispatcher();
        this.messageHandler = new MessageHandler();
        this.jsonrpc2Parser = new JSONRPC2Parser();
        dispatcher.register(messageHandler);
    }

    public JSONRPC2Response sendMessage(String request){
        JSONRPC2Request req;
        try{
            req = this.jsonrpc2Parser.parseJSONRPC2Request(request);
            System.out.println("Method:" + req.getMethod());
            System.out.println("Req:" + req);
            return dispatcher.process(req, null);
        } catch (JSONRPC2ParseException e){
            e.printStackTrace();
        }
        return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, null);
    }
}
