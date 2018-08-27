package com.bboroccu.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bboroccu on 16. 1. 26..
 */
public interface SongListRepo extends JpaRepository <SongList, Integer> {
    @Query("select p from SongList p where p.memberseq = :memberseq")
    List<SongList> findPlayListToSeq(@Param("memberseq") Integer memberSeq);
}
