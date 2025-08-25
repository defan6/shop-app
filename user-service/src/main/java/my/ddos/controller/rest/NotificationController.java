package my.ddos.controller.rest;

import lombok.RequiredArgsConstructor;
import my.ddos.model.NotificationResponse;
import my.ddos.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/notifications")
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping
    ResponseEntity<List<NotificationResponse>> getAllNotificationsByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(notificationService.getAllNotificationsByUserId(userId));
    }
}
