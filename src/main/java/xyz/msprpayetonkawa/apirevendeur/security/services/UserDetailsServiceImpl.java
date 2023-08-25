package xyz.msprpayetonkawa.apirevendeur.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.msprpayetonkawa.apirevendeur.retailer.Retailer;
import xyz.msprpayetonkawa.apirevendeur.retailer.RetailerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  RetailerRepository retailerRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Retailer retailer = retailerRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

    return UserDetailsImpl.build(retailer);
  }

}
