package com.javamaster.repository;

import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

import com.javamaster.entity.User;

@Repository
public interface UserRepository extends FirestoreReactiveRepository<User> {

}
