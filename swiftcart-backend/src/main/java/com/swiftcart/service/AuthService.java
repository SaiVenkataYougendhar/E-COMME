package com.swiftcart.service;

import com.swiftcart.dto.LoginRequest;
import com.swiftcart.dto.RegisterRequest;
import com.swiftcart.dto.AuthResponse;
import com.swiftcart.entity.User;
import com.swiftcart.entity.UserRole;
import com.swiftcart.repository.UserRepository;
import com.swiftcart.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      AuthenticationManager authenticationManager,
                      JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setRole(UserRole.USER);

        userRepository.save(user);

        String token = jwtTokenProvider.generateTokenFromUsername(user.getEmail());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }

    public AuthResponse loginWithGoogle(String idToken) {
        try {
            com.google.firebase.auth.FirebaseToken decodedToken = com.google.firebase.auth.FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();
            
            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setFirstName(name != null ? name.split(" ")[0] : "GoogleUser");
                newUser.setLastName(name != null && name.contains(" ") ? name.substring(name.indexOf(" ") + 1) : "");
                newUser.setPassword(passwordEncoder.encode(java.util.UUID.randomUUID().toString())); // Random password
                newUser.setRole(UserRole.USER);
                return userRepository.save(newUser);
            });

            String localToken = jwtTokenProvider.generateTokenFromUsername(user.getEmail());
            return new AuthResponse(
                    localToken,
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getId()
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid Google Token", e);
        }
    }

    public AuthResponse generateGuestToken() {
        User guestUser = new User();
        String uuid = java.util.UUID.randomUUID().toString();
        guestUser.setEmail("guest_" + uuid + "@swiftcart.local");
        guestUser.setPassword(passwordEncoder.encode(uuid));
        guestUser.setFirstName("Guest");
        guestUser.setLastName("User");
        guestUser.setRole(UserRole.GUEST);

        userRepository.save(guestUser);

        String token = jwtTokenProvider.generateTokenFromUsername(guestUser.getEmail());

        return new AuthResponse(
                token,
                guestUser.getEmail(),
                guestUser.getFirstName(),
                guestUser.getLastName(),
                guestUser.getId()
        );
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        if (userDetails.getFirstName() != null) user.setFirstName(userDetails.getFirstName());
        if (userDetails.getLastName() != null) user.setLastName(userDetails.getLastName());
        if (userDetails.getPhone() != null) user.setPhone(userDetails.getPhone());
        if (userDetails.getAddress() != null) user.setAddress(userDetails.getAddress());
        if (userDetails.getCity() != null) user.setCity(userDetails.getCity());
        if (userDetails.getState() != null) user.setState(userDetails.getState());
        if (userDetails.getZipCode() != null) user.setZipCode(userDetails.getZipCode());
        if (userDetails.getCountry() != null) user.setCountry(userDetails.getCountry());
        
        return userRepository.save(user);
    }
}


