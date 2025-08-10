package cn.kanyu.springai.service;

import org.springframework.stereotype.Service;

@Service
public class TicketService {

    public void cancel(String ticketName,String name){
        System.out.println("退票成功");
    }
}
