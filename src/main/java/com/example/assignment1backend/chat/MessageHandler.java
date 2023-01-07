package com.example.assignment1backend.chat;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public class MessageHandler implements RequestHandler {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public String[] handledRequests() {
        return new String[]{"send"};
    }

    @Override
    public JSONRPC2Response process(JSONRPC2Request jsonrpc2Request, MessageContext messageContext) {
        if (jsonrpc2Request.getMethod().equals("send")) {
            System.out.println("Request: " + jsonrpc2Request);
            List parameters = (List) jsonrpc2Request.getParams();
            Object to = parameters.get(0);
            Object from = parameters.get(1);
            Object message = parameters.get(2);

            simpMessagingTemplate.convertAndSend("/all/chat" + to + "/" + from + "/", message);

            return new JSONRPC2Response("success", jsonrpc2Request.getID());
        }
        else{
            return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, jsonrpc2Request.getID());
        }
    }
}
