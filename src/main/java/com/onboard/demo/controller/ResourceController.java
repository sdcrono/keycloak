package com.onboard.demo.controller;

import com.onboard.demo.model.Resource;
import com.onboard.demo.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping
//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @Secured({"ROLE_user"})
    public ResponseEntity all() {
        return ok(resourceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        return ok(resourceService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyAuthority('administrator')")
    @Secured({"ROLE_admin"})
    public void add(@RequestBody Resource resource) {
        resourceService.save(resource);
    }

    @PutMapping("/{id}")
    @Secured({"ROLE_admin"})
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Resource resource) {
        resourceService.update(resource.getId(), resource);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_admin"})
    public @ResponseBody
    ResponseEntity delete(@PathVariable("id") Long id) {
        resourceService.delete(id);
        return noContent().build();
    }

}
