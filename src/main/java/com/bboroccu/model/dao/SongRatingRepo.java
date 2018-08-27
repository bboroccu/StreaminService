package com.bboroccu.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by jae-seongheo on 2016. 5. 22..
 */
public interface SongRatingRepo extends JpaRepository<SongRating, Integer> {
    @Query("select p from SongRating p where p.songseq = :songseq")
    SongRating findSongRatingToSeq(@Param("songseq") Integer songSeq);
}
