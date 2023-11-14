package com.ewei.pma.services;

import com.ewei.pma.dao.IEmployeeRepository;
import com.ewei.pma.entities.Employee;
import com.ewei.pma.security.AppEmployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeAuthService implements UserDetailsService{

    @Autowired
    EmployeeService empService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee emp = empService.findByUsername(username);
        if (emp == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AppEmployee(emp);
    }
    
}
