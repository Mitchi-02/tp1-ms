package com.esisba.tp1.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esisba.tp1.entity.Configuration;
import com.esisba.tp1.entity.Server;
import com.esisba.tp1.entity.User;
import com.esisba.tp1.entity.Vm;
import com.esisba.tp1.repository.ServerRepository;
import com.esisba.tp1.repository.UserRepository;
import com.esisba.tp1.repository.VmRepository;
import com.esisba.tp1.request.CreateVmBody;
import com.esisba.tp1.request.UpdateUserBody;

@RestController 
@RequestMapping("/rsu")
public class RsuController {

    @Autowired
	private UserRepository userRepository;
	@Autowired 
	private ServerRepository serverRepository;
	@Autowired
	private VmRepository vmRepository;

    @GetMapping("/serveur/{serverId}/vms")
    public Collection<Vm> getVmsByServerId(@PathVariable("serverId") Long id) {
        Server s = serverRepository.findById(id).orElse(null);
        if(s == null) {
            return null;
        }
        Collection<Vm> vms = s.getPrimary_vms();
        Collection<Vm> vms2 = s.getBackup_vms();
        Collection<Vm> mergedVms = new ArrayList<>(vms);
        mergedVms.addAll(vms2);

        return mergedVms;
    }

    @PostMapping("/vm")
    public Vm createVm(@RequestBody CreateVmBody body) {
        Optional<User> user = userRepository.findById(body.idUtilisateur);
        if(!user.isPresent()) {
            return null;
        }
        Optional<Server> primaryServer = serverRepository.findById(body.idServeur);
        if(!primaryServer.isPresent()) {
            return null;
        }
        Optional<Server> backupServer = serverRepository.findById(body.idServeurBackup);
        if(!backupServer.isPresent()) {
            return null;
        }
        Vm vm = new Vm();
        vm.setUser(user.get());
        vm.setPrimary_server(primaryServer.get());
        vm.setBackup_server(backupServer.get());
        Configuration config = new Configuration();
        config.setCpu(body.configuration.cpu);
        config.setRam(body.configuration.ram);
        config.setDisk(body.configuration.disk);
        vm.setConfiguration(config);
        vmRepository.save(vm);

        return vm;
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserBody user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            return null;
        }
        User existingUser = optionalUser.get();
        existingUser.setName(user.name);
        existingUser.setEmail(user.email);

        userRepository.save(existingUser);
        return existingUser;
    }

    @PatchMapping("/user/{id}")
    public User patcheUser(@PathVariable("id") Long id, @RequestBody UpdateUserBody user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()) {
            return null;
        }
        User existingUser = optionalUser.get();
        if(user.name != null) {
            existingUser.setName(user.name);
        }
        if(user.email != null) {
            existingUser.setEmail(user.email);
        }
        userRepository.save(existingUser);
        return existingUser;
    }

    @DeleteMapping("/serveur/{idserveur}/{idvm}")
    public String deleteVmFromServer(@PathVariable("idserveur") Long idServeur, @PathVariable("idvm") Long idVm) {
        vmRepository.deleteByIdAndServerId(idVm, idServeur);
        return "Done";
    }
}
