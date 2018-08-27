package com.bboroccu.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by jae-seongheo on 2016. 9. 4..
 */
public interface RequestSongReplyRepo extends JpaRepository<RequestSongReply, Integer> {
    @Query("select r from RequestSongReply r where r.requestseq = :seq")
    List<RequestSongReply> findRequestSongReply(@Param("seq") Integer seq);
}
