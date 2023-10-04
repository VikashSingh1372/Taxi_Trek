package com.cab.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cab.Repository.RideRepository;
import com.cab.Repository.UserRepository;
import com.cab.config.JwtProvider;
import com.cab.entity.Ride;
import com.cab.entity.User;
import com.cab.exception.UserException;
import com.cab.service.UserService;


@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserRepository userRepo;

	
	@Override
	public User findUserById(long userId) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> user = this.userRepo.findById(userId);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UserException("user not found with id :" + userId);
		}
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		// TODO Auto-generated method stub
		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UserException("user not exist");
		}
		return user;
	}

	@Override
	public List<Ride> completedRide(long userId) {
	return 		userRepo.getCompletedRide(userId);

	}

}
