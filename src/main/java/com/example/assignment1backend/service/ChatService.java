package com.example.assignment1backend.service;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Parser;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.MessageHandler;

@Service
public class ChatService {
    private Dispatcher dispatcher;
    private MessageHandler messageHandler;
    private JSONRPC2Parser jsonrpc2Parser;

    @Autowired
    public ChatService
}
