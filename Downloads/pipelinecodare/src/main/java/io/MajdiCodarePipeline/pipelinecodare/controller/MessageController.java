package io.MajdiCodarePipeline.pipelinecodare.controller;

import io.MajdiCodarePipeline.pipelinecodare.Repository.MySqlRepository;
import io.MajdiCodarePipeline.pipelinecodare.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MySqlRepository mySqlRepository;

    @PostMapping("/addMessage")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message savedMessage = mySqlRepository.save(message);
        return ResponseEntity.status(201).body(savedMessage);
    }

    @GetMapping("/getMessages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.status(200).body(mySqlRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getOneMessage(@PathVariable Long id) {
        return mySqlRepository.findById(id)
                .map(message -> ResponseEntity.status(200).body(message))
                .orElse(ResponseEntity.status(204).build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Message> editOneMessage(@PathVariable Long id, @RequestBody Message payload) {
        return mySqlRepository.findById(id)
                .map(message -> {
                    if (payload.getContent() != null) message.setContent(payload.getContent());
                    // Uppdatera andra fält här om det finns fler
                    mySqlRepository.save(message);
                    return ResponseEntity.status(200).body(message);
                })
                .orElse(ResponseEntity.status(404).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        if (mySqlRepository.existsById(id)) {
            mySqlRepository.deleteById(id);
            return ResponseEntity.status(200).body("Message with ID " + id + " was deleted.");
        } else {
            return ResponseEntity.status(404).body("Message with ID " + id + " not found.");
        }
    }
}