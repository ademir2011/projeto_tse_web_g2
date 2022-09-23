package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.Role;
import com.residenciaTst.AtividadePratica.model.User;
import com.residenciaTst.AtividadePratica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public User salvar(User user){
        return userRepository.save(user);
    }

    public List<User> listarTodos(){
        return userRepository.findAll();
    }

    public User listarPeloId(Long id){
        if(userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public User atualizar(Long id, User role){
        if(userRepository.findById(id).isPresent()){
            return userRepository.save(role);
        }else{
            return null;
        }
    }

    public boolean deletarPeloId(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
