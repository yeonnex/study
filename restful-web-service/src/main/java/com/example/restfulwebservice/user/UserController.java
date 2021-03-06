package com.example.restfulwebservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> retreiveAllUsers(){
        List<User> users = userService.findAll();

        return users;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue findUser(@PathVariable Long id){
        return userService.findUser(id);
    }

    @PostMapping("/users")
    ResponseEntity<URI> createUser(@Valid @RequestBody User user) {
        User saved = userService.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        Boolean result = userService.deleteById(id);
        return ResponseEntity.ok(result);
    }
}
