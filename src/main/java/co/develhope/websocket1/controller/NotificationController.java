package co.develhope.websocket1.controller;
import co.develhope.websocket1.entities.MessageClientDTO;
import co.develhope.websocket1.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @PostMapping("/notification")
    public ResponseEntity sendNotificationToClient(@RequestBody MessageDTO messageDTO){
        messagingTemplate.convertAndSend("/topic/messages", messageDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @MessageMapping("/hello")
    @SendTo("/topic/messages")
    public MessageDTO handleMessageFromWebSocket(@RequestBody MessageClientDTO messageFromClientDTO){
        System.out.printf("Something arrived on /app/hello : %s" , messageFromClientDTO.toString());
        return new MessageDTO("message from Client : " + messageFromClientDTO.getFrom(),
                " " + messageFromClientDTO.getText());
    }


}