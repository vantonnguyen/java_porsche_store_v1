package vn.tonnguyen.porsche_store_v1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.tonnguyen.porsche_store_v1.model.Staff;
import vn.tonnguyen.porsche_store_v1.repository.StaffRepository;
import vn.tonnguyen.porsche_store_v1.repository.UserRepository;

@Service("staffDetailsService")
public class StaffDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Staff staff = staffRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Staff not found"));
        return new CustomStaffDetails(staff);
    }
}
