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
// att skapa post
    @PostMapping("/addMessage")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message savedMessage = mySqlRepository.save(message);
        return ResponseEntity.status(201).body(savedMessage);
    }

    @GetMapping("/getMessages")
    public ResponseEntity<List<Message>> getMessages() {
        //return ResponseEntity.status(200).body(mySqlRepository.findAll());
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
            return ResponseEntity.status(404).body("Message with ID " + id + " not found. yala");
        }
    }
}

//Jag ska ta screenshots för uppgift

//post
//http://localhost:5000/api/messages/addMessage
//{
//  "content": ""
//}
//Method: GET
//URL: http://localhost:5000/api/messages/getMessages

//get id
//URL: http://localhost:5000/api/messages/{id}

//update
//Edit One Message by ID
   //     Method: PATCH
      //  URL: http://localhost:5000/api/messages/{id}
     //   Body: (JSON format)

       // {"content": "Updated message content here"
     //   }

//Method: DELETE
//URL: http://localhost:5000/api/messages/{id}




//http://beanstalk-pipelinecodare-env.eba-5qfnyxp5.us-east-1.elasticbeanstalk.com/api/messages/getMessages
//http://beanstalk-pipelinecodare-env.eba-5qfnyxp5.us-east-1.elasticbeanstalk.com/api/messages/addMessage
//{
//  "content": ""
//}

//get id
//http://beanstalk-pipelinecodare-env.eba-5qfnyxp5.us-east-1.elasticbeanstalk.com/api/messages/{id}


//update
//Edit One Message by ID
//     Method: PATCH

//   Body: (JSON format)

// {"content": "Updated message content here"
//   }

//http://beanstalk-pipelinecodare-env.eba-5qfnyxp5.us-east-1.elasticbeanstalk.com/api/messages/{id}


//Method: DELETE
//http://beanstalk-pipelinecodare-env.eba-5qfnyxp5.us-east-1.elasticbeanstalk.com/api/messages/{id}

// hello