package com.bboroccu.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by bboroccu on 16. 1. 22..
 */
public interface MemberRepo extends JpaRepository<Member, String> {
    @Query("select u from Member u where u.email = :email")
    Member findMemberInfo(@Param("email") String email);
}
