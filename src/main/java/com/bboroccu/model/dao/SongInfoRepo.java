package com.bboroccu.model.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Created by bboroccu on 16. 1. 22..
 */
public interface SongInfoRepo extends JpaRepository<SongInfo, Integer> {
    @Query("select s from SongInfo s where s.title LIKE %:title%")
    List<SongInfo> findSongListToTitle(@Param("title") String title);

    @Query("select s from SongInfo s where s.creator LIKE %:creator%")
    List<SongInfo> findSongListToCreator(@Param("creator") String creator);

    @Query("select s from SongInfo s where s.title LIKE %:title% or s.creator LIKE %:creator%")
    List<SongInfo> searchSongList(@Param("title") String title, @Param("creator") String creator);

    @Query("select s from SongInfo s where s.creator = :creator and s.title = :title and s.filename = :filename")
    SongInfo findSongInfoToInfo(@Param("creator") String creator, @Param("title") String title, @Param("filename") String filename);
}
